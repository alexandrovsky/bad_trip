package de.uni.bremen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import de.uni.bremen.BadTripGame;
import de.uni.bremen.utils.AnimationDictionary;

public class EndScreen implements Screen {

	BadTripGame gameRef;
	private BitmapFont font;
	private BitmapFont enterFont;
	
	
	
	public EndScreen(BadTripGame gameref) {
		// TODO Auto-generated constructor stub
		gameRef =gameref;
		font = new BitmapFont();
		font.setColor(1.0f, .7f, 0.0f, 1.0f);
		font.scale(2.6f);
		enterFont = new BitmapFont();
		enterFont.setColor(.89f,.41f,.26f,1f);
		enterFont.scale(1.6f);
		
	}
	
	
	protected TextureRegion currentFrame;
	protected float stateTime;
	protected float animationTime;
	
	public Vector2 postion;
	public float height, width; 
	
	public boolean goodEnd;
	public long score;
	
	Color bg = new Color(0.522f,0.769f,0.992f,1.0f);
	
	@Override
	public void render(float delta) {
		if(Gdx.input.isKeyPressed(Keys.ENTER))
		{
			gameRef.initMain();
		}
		
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(bg.r,bg.g,bg.b,1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		stateTime += delta;                 
        
		
		
		currentFrame = ((Animation) playerAnimDict.get( new Integer(0) )).getKeyFrame(stateTime, true);
		
	
		batch.draw(currentFrame,0,0,0,0, width, height, 1.0f, 1,0f);
    
		if(!dead)
		{
			if(goodEnd)
			{
				enterFont.draw(batch, "You recovered by living a healthier lifestyle!", 10, height-30);
			}else{
				enterFont.draw(batch, "You took too many drugs!", 10, height-30);
			}
			font.draw(batch, "your score is "+score, 10, 120);
		}
		
		
		enterFont.draw(batch, "PRESS ENTER TO TRY AGAIN!", 10, 60);
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	AnimationDictionary playerAnimDict;
	SpriteBatch batch;
	
	public boolean dead;
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		batch=new SpriteBatch();
		
		if(dead)
		{
			playerAnimDict = new AnimationDictionary(new Texture("img/screens/end.png"), 0.125f, 5);
			BadTripGame.playMusic(BadTripGame.MUSIC_BAD_END_THEME);
			dead = false;
			goodEnd = false;
		}else{
			if(!goodEnd)
			{
				System.out.println("sadend");
				playerAnimDict = new AnimationDictionary(new Texture("img/screens/sadend2.png"), 0.125f, 6);
				BadTripGame.playMusic(BadTripGame.MUSIC_GOOD_END_THEME);
			}else{
				System.out.println("happyend");
				playerAnimDict = new AnimationDictionary(new Texture("img/screens/happy3.png"), 0.25f, 23);
				BadTripGame.playMusic(BadTripGame.MUSIC_GOOD_END_THEME);
			}
		}
		
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
