package de.uni.bremen.screens;

import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardDownRightHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.uni.bremen.BadTripGame;

public class TitleScreen implements Screen {

	SpriteBatch batch;
	Sprite splash;
	
	BadTripGame gameRef;
	
	public TitleScreen(BadTripGame gamref) {
		// TODO Auto-generated constructor stub
		gameRef = gamref;
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
			splash.draw(batch);
		batch.end();
		
		  if (Gdx.input.isButtonPressed(Buttons.LEFT)
				  || Gdx.input.isKeyPressed(Keys.ENTER)){
			  // use your own criterion here
			  System.out.println("Enter game");
			  gameRef.initMain();
		  }
              

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
