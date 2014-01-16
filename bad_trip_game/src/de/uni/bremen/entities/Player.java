package de.uni.bremen.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Player extends Character implements InputProcessor{

	private static final Texture ANIMATION_SHEET = new Texture("img/animation_sheet.png");
	private static final float ANIMATION_DURATION = 0.025f;
	private static final int ANIMATION_FRAME_COLS = 6;
	private static final int ANIMATION_FRAME_ROWS = 5;
	private static final float MAX_SPEED = 160;
	private static final float MAX_HEALTH = 100;
	
	
	
	public Player(Sprite sprite, TiledMapTileLayer collisionLayer){
		super(sprite, ANIMATION_SHEET, ANIMATION_DURATION, ANIMATION_FRAME_COLS, ANIMATION_FRAME_ROWS,
				collisionLayer, MAX_SPEED, MAX_HEALTH);
		
	}
	
	

		
	@Override
	public void draw(SpriteBatch batch){
		
		//update frame delta
		update(Gdx.graphics.getDeltaTime());
		super.draw(batch);
	}


	public void update(float deltaTime){
		super.update(deltaTime);
	}
	
	public void dispose(){
		super.dispose();
	}

	//============================== INPUT PROCESSOR ================================//
	//
	//
	//===============================================================================//
	
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.LEFT:
			velocity.x = -maxSpeed;
			currentState = States.WALK_LEFT;
			break;
		case Keys.RIGHT:
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
		default:
			break;
		}
		return true;
	}


	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.LEFT:
			velocity.x = 0;
			currentState = States.IDLE_LEFT;
			break;
		case Keys.RIGHT:
			velocity.x = 0;
			currentState = States.IDLE_RIGHT;
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
