package de.uni.bremen.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor{

	private Vector2 velocity = new Vector2();
	private float speed = 80 * 2, gravity = 60 * 2.8f;
	private boolean canJump;
	private TiledMapTileLayer collisionLayer;
	
	public Player(Sprite sprite, TiledMapTileLayer collisionLayer){
		super(sprite);
		
		this.collisionLayer = collisionLayer;
	}
	
	
	public void draw(SpriteBatch batch){
		update(Gdx.graphics.getDeltaTime());
		super.draw(batch);
	}


	public void update(float deltaTime) {
		// apply gravity:
		velocity.y -= gravity * deltaTime;
		
		//clam velocity:
		
		if(velocity.y > speed){
			velocity.y = speed;
		}else if(velocity.y < -speed){
			velocity.y = -speed;
		}
		
		float oldX = getX(), oldY = getY();
		float tileWidth = collisionLayer.getTileWidth();
		float tileHeight = collisionLayer.getTileHeight();
		boolean collisionX = false, collisionY = false;
		
		// move on x:
		setX(getX() + velocity.x * deltaTime);
		
		Cell cell = null;
		
		if(velocity.x < 0){
			cell = collisionLayer.getCell( (int) (getX() / tileWidth),
					 (int) (getY() + getHeight() / tileHeight));
			// top left:
			if(cell != null){
				collisionX = cell.getTile().getProperties().containsKey("blocked");
			}
			
			// middle left:
			
			if(!collisionX){
				cell = collisionLayer.getCell( (int) (getX() / tileWidth),
					 	 (int) ((getY() + getHeight()/2) / tileHeight));
				if(cell != null){
					collisionX = cell.getTile().getProperties().containsKey("blocked");
				}
			}		
			// bottom left:
			if(!collisionX){
				cell = collisionLayer.getCell( (int) (getX() / tileWidth),
													 (int) (getY() / tileHeight));
				if(cell != null){
					collisionX = cell.getTile().getProperties().containsKey("blocked");
				}
			}
		}else if(velocity.x > 0){
			// top right:
			cell = collisionLayer.getCell( (int) ((getX() + getWidth()) / tileWidth),
					 							 (int) ((getY() + getHeight()) / tileHeight));
			if(cell != null){
				collisionX = cell.getTile().getProperties().containsKey("blocked");
			}
			
			// middle left:
			if(!collisionX){
				cell = collisionLayer.getCell( (int) ((getX() + getWidth()) / tileWidth),
					 							 	 (int) ((getY() + getHeight()/2) / tileHeight));
				if(cell != null){
					collisionX = cell.getTile().getProperties().containsKey("blocked");
				}
			}		
			// bottom left:
			if(!collisionX){
				cell = collisionLayer.getCell( (int) ((getX()+ getWidth()) / tileWidth),
													 (int) ( getY() / tileHeight));
				if(cell != null){
					collisionX = cell.getTile().getProperties().containsKey("blocked");
				}
			}
		}
		
		if(collisionX){
			setX(oldX);
			velocity.x = 0;
		}
		
		// move on y:
		setY(getY() + velocity.y * deltaTime);
		
		if(velocity.y < 0){
			// bottom left:
			cell = collisionLayer.getCell( (int) ((getX()) / tileWidth),
					 							 (int) ( getY() / tileHeight));
			if(cell != null){
				collisionY = cell.getTile().getProperties().containsKey("blocked");
			}
			// bottom middle:
			if(!collisionY){
				cell = collisionLayer.getCell( (int) ((getX()+ getWidth()/2) / tileWidth),
						 							 (int) ( getY() / tileHeight));
				if(cell != null){
					collisionY = cell.getTile().getProperties().containsKey("blocked");
				}
			}
			//bottom right:
			if(!collisionY){
				cell = collisionLayer.getCell( (int) ((getX() + getWidth()) / tileWidth),
						 							 (int) ( getY() / tileHeight));
				if(cell != null){
					collisionY = cell.getTile().getProperties().containsKey("blocked");
				}
			}
			canJump = collisionY;
		}else if(velocity.y > 0){
			// top left:
			cell = collisionLayer.getCell( (int) ((getX()) / tileWidth),
					 							 (int) ((getY() + getHeight()) / tileHeight));
			if(cell != null){
				collisionY = cell.getTile().getProperties().containsKey("blocked");
			}
			// top middle:
			if(!collisionY){
				cell = collisionLayer.getCell( (int) ((getX() + getWidth()/2) / tileWidth),
													 (int) ((getY() + getHeight()) / tileHeight));
				if(cell != null){
					collisionY = cell.getTile().getProperties().containsKey("blocked");
				}

			}
			//top right:
			if(!collisionY){
				cell = collisionLayer.getCell( (int) ((getX() + getWidth()) / tileWidth),
													 (int) ((getY() + getHeight()) / tileHeight));
				if(cell != null){
					collisionY = cell.getTile().getProperties().containsKey("blocked");
				}
			}
		}
		if(collisionY){
			setY(oldY);
			velocity.y = 0;
		}
		System.out.println(velocity);
	}
	
	public void dispose(){
		getTexture().dispose();
	}


	public Vector2 getVelocity() {
		return velocity;
	}


	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}


	public float getSpeed() {
		return speed;
	}


	public void setSpeed(float speed) {
		this.speed = speed;
	}


	public float getGravity() {
		return gravity;
	}


	public void setGravity(float gravity) {
		this.gravity = gravity;
	}


	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}


	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}


	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.LEFT:
			velocity.x = -speed;
			break;
		case Keys.RIGHT:
			velocity.x = speed;
			break;
		case Keys.UP:
			if(canJump){
				velocity.y = speed + Math.abs(velocity.x) * speed;
				canJump = false;
			}
			break;
		default:
			break;
		}
		return true;
	}


	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.LEFT:
		case Keys.RIGHT:
			velocity.x = 0;
			break;
		case Keys.UP:
			break;
		default:
			break;
		}
		return true;
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
