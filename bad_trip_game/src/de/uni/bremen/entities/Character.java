package de.uni.bremen.entities;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;


import de.uni.bremen.physics.WorldPhysics;
import de.uni.bremen.utils.AnimationDictionary;

public class Character extends Entity{

	
	protected int maxHealth = 100;
	protected int currentHealth = 100;
	public float maxSpeed = 160;
	public Vector2 velocity = new Vector2();
	
	
	protected TiledMapTileLayer collisionLayer;
	
	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}
	
	//================================
	// collision
	//================================
	
	protected boolean collisionX;
	
	public boolean isCollisonX(){
		return collisionX;
	}
	
	protected boolean  collisionY;
	
	public boolean isCollisionY(){
		return collisionY;
	}
	
	//============================== PRIVATE VARIABLES ==============================//
	//
	//	VARIABLES WITH INTERNAL PURPOSE
	//
	//===============================================================================//	
	
	/**
	 * The character only can jump, if he stands on the ground:
	 */
	protected boolean canJump;
	/**
	 * the width dimensions of a single tile
	 */
	protected float tileWidth;
	
	/**
	 * the height dimensions of a single tile
	 */
	protected float tileHeight;
	
	protected boolean isOrientationLeft;
	
	protected States currentState = States.IDLE;
		
		protected enum States{
			WALK(0),
			JUMP(1),
			IDLE(2);
			
			
			
			public int key;
			private States(int key) {
				this.key = key;
			}
		};
	
	
	public Character( Vector2 position,AnimationDictionary animationDict,  float animationTime, 
			float width, float height, TiledMapTileLayer collisionLayer, float maxSpeed) 
	{
		super(position, animationDict, animationTime, width,height);
		
		this.maxSpeed = maxSpeed;
		
		this.collisionLayer = collisionLayer;
		
		tileWidth = collisionLayer.getTileWidth();
		tileHeight = collisionLayer.getTileHeight();
	}
	
	
	
	@Override
	public void draw(SpriteBatch batch, float deltaTime){
		
		update(deltaTime);
		
		
		//update graphics
		stateTime += deltaTime;                 
        
		
		
		currentFrame = ((Animation) animationDict.get( new Integer(currentState.key) )).getKeyFrame(stateTime, true);
		System.out.println(currentState.key+ " => ");
		float moirror_x = isOrientationLeft ? -1 : 1;
		float newx = isOrientationLeft ? postion.x + width : postion.x;
		batch.draw(currentFrame,newx,postion.y,0,0, width, height, moirror_x, 1,0);
        //batch.draw(region, x, y, originX, originY, moirror_x, moirror_x, scaleX, scaleY, rotation);
	}
	
	
	/** This update function manages the general collision with the environment.
	 * source: 
	 * http://www.youtube.com/watch?v=DOpqkaX9844&list=SPXY8okVWvwZ0qmqSBhOtqYRjzWtUCWylb&index=4
	 * Basic idea:
	 * 1) apply the gravity to the velocity.
	 * 2) save the current position of the character.
	 * 3) move the character on each axis separately.
	 * 4) then check the collision with the surrounded tiles.
	 * 5) if a collision happens, then reset the changes.
	 * 
	 * @param deltaTime
	 */
	
	public void update(float deltaTime) 
	{
		System.out.println( currentState.toString() );
		//1) apply gravity:
		velocity.y -= WorldPhysics.GRAVITY * deltaTime;
		
		//clam velocity:
		if(velocity.y > maxSpeed){
			velocity.y = maxSpeed;
		}else if(velocity.y < -maxSpeed){
			velocity.y = -maxSpeed;
		}
		
		//2)
		float oldX = postion.x;
		float oldY = postion.y;
		
		collisionX = false;
		collisionY = false;
		
		//3) move on x:
		postion.x += velocity.x * deltaTime;
		
		// moving left:
		if(velocity.x < 0){
			collisionX = collidesLeft();
		}
		
		// moving right:
		else if(velocity.x > 0){
			collisionX = collidesRight();
		}
		// 5)
		if(collisionX){
			postion.x = oldX;
			velocity.x = 0;
		}
		
		//3) move on y:
		postion.y += velocity.y * deltaTime;
		
		// moving down:
		if(velocity.y < 0){
			collisionY = collidesBottom();
			canJump = collisionY; // if the character stands on the ground, he can jump
		}
		// moving up:
		else if(velocity.y > 0)
		{	
			collisionY = collidesTop();
		}
		// 5)
		if(collisionY){
			postion.y = oldY;
			velocity.y = 0;
		}
	}
	
	public boolean isCellBlocked(float x, float y){
		Cell cell = collisionLayer.getCell((int)(x/tileWidth), (int)(y/tileHeight));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(WorldPhysics.BLOCKED_CELL_KEY);
	}

	public boolean collidesLeft(){
		for(float step = 0; step < height; step += tileHeight/2){
			if( isCellBlocked(postion.x, postion.y + step ) ){
				return true;
			}
		}
		return false;
	}
	
	public boolean collidesRight(){
		for(float step = 0; step < height; step += tileHeight/2){
			if( isCellBlocked(postion.x + width, postion.y + step ) ){
				return true;
			}
		}
		return false;
	}
	
	public boolean collidesTop(){
		for(float step = 0; step < width; step += tileWidth/2){
			if( isCellBlocked(postion.x + step, postion.x + height ) ){
				return true;
			}
		}
		return false;
	}
	
	public boolean collidesBottom(){
		for(float step = 0; step < width; step += tileWidth/2){
			if( isCellBlocked(postion.x + step, postion.y) ){
				return true;
			}
		}
		
		return false;
	}

}
