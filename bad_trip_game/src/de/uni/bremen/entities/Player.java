package de.uni.bremen.entities;

import java.util.Dictionary;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import de.uni.bremen.utils.AnimationDictionary;


public class Player extends Character implements InputProcessor{

	//============================== CONSTANTS ======================================//
	//
	//	
	//
	//===============================================================================//
	
	
	private static final float ANIMATION_DURATION = 0.025f;
	
	//============================== CONSTRUCTOR ======================================//
	//
	//	
	//
	//===============================================================================//
	
	public Player( Vector2 position,AnimationDictionary animationDict,  float animationTime, 
			float width, float height, TiledMapTileLayer collisionLayer)
	{
		
		super(position, animationDict, animationTime, width, height, collisionLayer, 160);
		Gdx.input.setInputProcessor(this);
	} 
	
		
	

			
	


	@Override
	public void update(float deltaTime){
		System.out.println(currentState);
		super.update(deltaTime);
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
				currentState = States.WALK;
				leftDown = true;
				isOrientationLeft = true;
				break;
			case Keys.RIGHT:
				rightDown = true;
				velocity.x = maxSpeed;
				currentState = States.WALK;
				isOrientationLeft = false;
				break;
			case Keys.UP:
			case Keys.SPACE:
				if(canJump){
					velocity.y = maxSpeed + Math.abs(velocity.x) * maxSpeed;
					canJump = false;
					currentState = States.JUMP;
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
			currentState = rightDown?States.WALK:States.IDLE;
			leftDown=false;
			break;
		case Keys.RIGHT:
			velocity.x = leftDown?-maxSpeed:0;
			currentState = leftDown?States.WALK:States.IDLE;
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
