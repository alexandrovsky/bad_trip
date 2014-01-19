package de.uni.bremen.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;


public class Player extends Character implements InputProcessor{

	//============================== CONSTANTS ======================================//
	//
	//	
	//
	//===============================================================================//
	
	private static final Texture ANIMATION_SHEET = new Texture("img/animation_sheet.png");
	private static final float ANIMATION_DURATION = 0.025f;
	private static final int ANIMATION_FRAME_COLS = 6;
	private static final int ANIMATION_FRAME_ROWS = 5;
	private static final float MAX_SPEED = 160;
	
	//============================== CONSTRUCTOR ======================================//
	//
	//	
	//
	//===============================================================================//
	
	public Player(Sprite sprite, TiledMapTileLayer collisionLayer){
		super(sprite, ANIMATION_SHEET, ANIMATION_DURATION, ANIMATION_FRAME_COLS, ANIMATION_FRAME_ROWS,
				collisionLayer, MAX_SPEED);
		
	}
	
	//============================== INTERNAL STATES ==============================//
	//
	//	
	//
	//===============================================================================//

	protected States currentState = States.IDLE_RIGHT;
	
	protected enum States{
		IDLE_LEFT,
		IDLE_RIGHT,
		JUMPING,
		DUCKING,
		WALK_LEFT,
		WALK_RIGHT,
		ACTION;
	};
	

	//============================== OVERRIDES ======================================//
	//
	//	
	//
	//===============================================================================//

		
	@Override
	public void draw(SpriteBatch batch){
	
		update(Gdx.graphics.getDeltaTime());
		
		//get entity coordinates
		float[]  vertz = getVertices();
		
		//update graphics
		stateTime += Gdx.graphics.getDeltaTime();                 
        currentFrame = animation.getKeyFrame(stateTime, true);
		
        switch (currentState) 
        {
			case IDLE_LEFT:{
				//TODO include animation
				super.draw(batch);
				break;
			}
			case IDLE_RIGHT:{
				//TODO include animation
				super.draw(batch);
				break;
			}
			case WALK_LEFT:{
				batch.draw(currentFrame,vertz[0]+getWidth(),vertz[1],0,0,getWidth(),getHeight(),-1.0f,1,0);
				break;
			}
			case WALK_RIGHT:{
				batch.draw(currentFrame,vertz[0],vertz[1],0,0,getWidth(),getHeight(),1.0f,1,0);
				break;
			}
			case ACTION:{
				//TODO include animation
				super.draw(batch);
				break;
			}
			case DUCKING:{
				//TODO include animation
				super.draw(batch);
				break;
			}
			case JUMPING:{
				//TODO include animation
				super.draw(batch);
				break;
			}
			default:
				super.draw(batch);
				break;
		}	
	}

	@Override
	public void update(float deltaTime){
		System.out.println(currentState);
		super.update(deltaTime);
	}
	
	@Override
	public void dispose(){
		super.dispose();
	}

	//============================== INPUT PROCESSOR ================================//
	//
	//	ALL FUNCTIONS RELATED TO USER INPUT
	//
	//===============================================================================//
	
	private boolean rightDown=false;
	private boolean leftDown=false;
	
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode)
		{
			case Keys.LEFT:
				velocity.x = -maxSpeed;
				currentState = States.WALK_LEFT;
				leftDown = true;
				break;
			case Keys.RIGHT:
				rightDown = true;
				velocity.x = maxSpeed;
				currentState = States.WALK_RIGHT;
				break;
			case Keys.UP:
				if(canJump){
					velocity.y = maxSpeed + Math.abs(velocity.x) * maxSpeed;
					canJump = false;
					currentState = States.JUMPING;
				}
				break;
			case Keys.DOWN:
				if(canJump)
				{
					currentState = States.DUCKING;
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
			velocity.x = rightDown?maxSpeed:0;
			currentState = rightDown?States.WALK_RIGHT:States.IDLE_LEFT;
			leftDown=false;
			break;
		case Keys.RIGHT:
			velocity.x = leftDown?-maxSpeed:0;
			currentState = leftDown?States.WALK_LEFT:States.IDLE_RIGHT;
			rightDown=false;
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
