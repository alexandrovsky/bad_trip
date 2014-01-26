package de.uni.bremen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import de.uni.bremen.BadTripGame;
import de.uni.bremen.utils.AnimationDictionary;

public class EndScreen implements Screen {

	BadTripGame gameRef;
	public EndScreen(BadTripGame gameref) {
		// TODO Auto-generated constructor stub
		gameRef =gameref;
	}
	
	
	protected TextureRegion currentFrame;
	protected float stateTime;
	protected float animationTime;
	
	public Vector2 postion;
	public float height, width; 

	
	@Override
	public void render(float delta) {
		if(Gdx.input.isKeyPressed(Keys.ENTER))
		{
			gameRef.initMain();
		}
		
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		stateTime += delta;                 
        
		
		
		currentFrame = ((Animation) playerAnimDict.get( new Integer(0) )).getKeyFrame(stateTime, true);
		
	
		batch.draw(currentFrame,0,0,0,0, width, height, 1.0f, 1,0f);
    
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	AnimationDictionary playerAnimDict;
	SpriteBatch batch;
	@Override
	public void show() {
		// TODO Auto-generated method stub
		batch=new SpriteBatch();
		playerAnimDict = new AnimationDictionary("img/screens/end.png", 0.25f, 5);

		width = Gdx.graphics.getWidth();
		height=Gdx.graphics.getHeight();
		
	}
	



	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
