package de.uni.bremen.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

import de.uni.bremen.physics.WorldPhysics;

public class Character extends Entity{

	
	/**
	 * The maximum health of the chacter
	 */
	protected float maxHealth = 100;
	
	/**
	 * The maximum speed value of the character.
	 */	
	protected float maxSpeed = 160;
	/**
	 * The vector for adding velocity to the character.
	 */
	protected Vector2 velocity = new Vector2();
	
	/**
	 * Tile layer for the player.
	 */
	protected TiledMapTileLayer collisionLayer;
	
	/**
	 * The character only can jump, if he stands on the ground:
	 */
	protected boolean canJump;
	
	private boolean collisionX, collisionY;
	
	/**
	 * the dimensions of a single tile
	 */
	float tileWidth;
	float tileHeight;
	
	public Character(Sprite sprite, Texture animationSheet, float animationTime, 
			int frameCols, int frameRows,TiledMapTileLayer collisionLayer, float maxSpeed, float maxHealth) 
	{
		super(sprite, animationSheet, animationTime, frameCols, frameRows);
		this.maxSpeed = maxSpeed;
		this.maxHealth = maxHealth;
		
		this.collisionLayer = collisionLayer;
		
		tileWidth = collisionLayer.getTileWidth();
		tileHeight = collisionLayer.getTileHeight();
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
		//1) apply gravity:
		velocity.y -= WorldPhysics.GRAVITY * deltaTime;
		
		//clam velocity:
		if(velocity.y > maxSpeed){
			velocity.y = maxSpeed;
		}else if(velocity.y < -maxSpeed){
			velocity.y = -maxSpeed;
		}
		
		//2)
		float oldX = getX();
		float oldY = getY();
		
		collisionX = false;
		collisionY = false;
		
		//3) move on x:
		setX(getX() + velocity.x * deltaTime);
		
		Cell cell = null;
		
		// moving left:
		if(velocity.x < 0){
			
			collisionX = collidesLeft();
		}
		// moving right:
		else if(velocity.x > 0)
		{
			collisionX = collidesRight();
		}
		// 5)
		if(collisionX){
			setX(oldX);
			velocity.x = 0;
		}
		
		//3) move on y:
		setY(getY() + velocity.y * deltaTime);
		
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
			setY(oldY);
			velocity.y = 0;
		}
	}
	
	public boolean isCellBlocked(float x, float y){
		Cell cell = collisionLayer.getCell((int)(x/tileWidth), (int)(y/tileHeight));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(WorldPhysics.BLOCKED_CELL_KEY);
	}

	public boolean collidesLeft(){
		for(float step = 0; step < getHeight(); step += tileHeight/2){
			if( isCellBlocked(getX(), getY() + step ) ){
				return true;
			}
		}
		return false;
	}
	
	public boolean collidesRight(){
		for(float step = 0; step < getHeight(); step += tileHeight/2){
			if( isCellBlocked(getX()+getWidth(), getY() + step ) ){
				return true;
			}
		}
		return false;
	}
	
	public boolean collidesTop(){
		for(float step = 0; step < getWidth(); step += tileWidth/2){
			if( isCellBlocked(getX() + step, getY() + getHeight() ) ){
				return true;
			}
		}
		return false;
	}
	
	public boolean collidesBottom(){
		for(float step = 0; step < getWidth(); step += tileWidth/2){
			if( isCellBlocked(getX() + step, getY()) ){
				return true;
			}
		}
		
		return false;
	}
	
	
	public void dispose(){
		getTexture().dispose();
		super.dispose();
	}
	
	//-------------------- SETTERS & GETTERS -------------------\\
	
	/**
	 * The maximum speed value of the player.
	 */	
			
	public float getSpeed() {
		return maxSpeed;
	}


	public void setSpeed(float speed) {
		this.maxSpeed = speed;
	}

	
	public Vector2 getVelocity() {
		return velocity;
	}


	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}
	public boolean isCollisonX(){
		return collisionX;
	}
	public boolean isCollisionY(){
		return collisionY;
	}
}
