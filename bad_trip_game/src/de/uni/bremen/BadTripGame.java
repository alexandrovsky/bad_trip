package de.uni.bremen;

import com.badlogic.gdx.Game;

import de.uni.bremen.screens.EndScreen;
import de.uni.bremen.screens.PlayScreen;
import de.uni.bremen.screens.TitleScreen;

public class BadTripGame extends Game {

	public PlayScreen mainScreen;
	public TitleScreen title;
	public EndScreen end;
	
	@Override
	public void create() {		
		end = new EndScreen(this);
		setScreen(new TitleScreen(this));
	}

	public void initMain()
	{
		mainScreen = new PlayScreen(this);
		setScreen(mainScreen);
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
