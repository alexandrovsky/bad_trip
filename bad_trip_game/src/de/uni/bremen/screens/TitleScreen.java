package de.uni.bremen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import de.uni.bremen.BadTripGame;

public class TitleScreen implements Screen {

	SpriteBatch batch;
	Sprite splash;
	
	BitmapFont font;
	
	BadTripGame gameRef;
	ShapeRenderer shapeRenderer;
	public TitleScreen(BadTripGame gamref) {
		// TODO Auto-generated constructor stub
		gameRef = gamref;
		
		font= new BitmapFont();
		font.setColor(.89f,.41f,.26f,1f);
		font.scale(1.6f);
		shapeRenderer = new ShapeRenderer();
	}
	
	Color bg = new Color(0.522f,0.769f,0.992f,1.0f);
	float progress = 0.0f;
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(bg.r,bg.g,bg.b,1.0f);
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
			splash.draw(batch);
			
			font.draw(batch, "PRESS ENTER", 700, 80);
		batch.end();
		
		
		int screen_w = Gdx.graphics.getWidth();
		int screen_h = Gdx.graphics.getHeight();
		int w = 200;
		int h = 60;
		int x = screen_w/2-w/2;
		int y = screen_h/4;
		
		if(!gameRef.mapManager.update()){
			
			shapeRenderer.begin(ShapeType.Filled);
			
			shapeRenderer.setColor(1.0f, 0.5f, 0.3f, 1.0f);
			shapeRenderer.rect(x, y, w*progress, h);
			progress = (progress + Gdx.graphics.getDeltaTime() ) % w;
			shapeRenderer.end();
		}else{
			System.out.println("Enter game");
			gameRef.initMain();
		}
		
		
		/*
		if (Gdx.input.isButtonPressed(Buttons.LEFT)
			  || Gdx.input.isKeyPressed(Keys.ENTER)){
		  // use your own criterion here
		  System.out.println("Enter game");
			gameRef.initMain();
		}
        */      

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		batch = new SpriteBatch();
		
		Texture tex =new Texture("img/screens/example.png");
		splash = new Sprite(tex);
		splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
