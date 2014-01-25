package de.uni.bremen.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import de.uni.bremen.utils.AnimationDictionary;
import de.uni.bremen.utils.HealthStates;


public class Player extends Character implements InputProcessor{

	//============================== CONSTANTS ======================================//
	//
	//	
	//
	//===============================================================================//
	
	
	private static final float ANIMATION_DURATION = 0.025f;
	
	
	public ArrayList<Item> items;
	public ArrayList<Character> enemies;
	
	//============================== CONSTRUCTOR ======================================//
	//
	//	
	//
	//===============================================================================//
	
	public Player( Vector2 position,AnimationDictionary animationDict,  float animationTime, 
			float width, float height, TiledMapTileLayer collisionLayer)
	{
		
		super(position, animationDict, animationTime, width, height, collisionLayer, 360);
		
		currentHealth=100;
	} 
	
	
	
	
	
		

	
	public HealthStates currentHealthState = HealthStates.CLEAN;
			
	


	@Override
	public void update(float deltaTime){
		
		for (Item item : items) {
			if(item.isDead)continue;
			if(hit(item.postion.x,item.postion.y,item.width,item.height))
			{
				if(item instanceof Fruit)
				{
					System.out.println("Fruit Collected");
					currentHealth+=1;
				}
				
				if(item instanceof Drug)
				{
					Drug d = (Drug)item;
					currentHealth-=5;
					switch (d.current) {
					case MUSHROOM:
						currentHealthState = HealthStates.ON_MUSHRROM;
						break;
					case CANNABIS:
						currentHealthState = HealthStates.ON_WEED;
					case XTC:
						currentHealthState = HealthStates.ON_XTC;
					default:
						break;
					}
				}
				item.isDead = true;
			}
		}
		for (Character character : enemies) {
			if(character.isDead)continue;
		}
		
		
		super.update(deltaTime);
	}
	
	private boolean hit(float x1, float y1, float w1, float h1)
	{
		if(x1 > postion.x+ width || x1+w1 < postion.x)return false;
		if(y1 > postion.y+ height || y1+h1 < postion.y)return false;
		
		return true;
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
	
	



	public int getCurrentHealth() {
		return currentHealth;
	}
	
}
