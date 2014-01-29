package de.uni.bremen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import de.uni.bremen.screens.EndScreen;
import de.uni.bremen.screens.PlayScreen;
import de.uni.bremen.screens.TitleScreen;

public class BadTripGame extends Game {

	public AssetManager mapManager;
	
	public PlayScreen mainScreen;
	public TitleScreen title;
	public EndScreen end;
	
	@Override
	public void create() {
		
		mapManager = new AssetManager();
		mapManager.setLoader(TiledMap.class,new TmxMapLoader(new InternalFileHandleResolver()));
		mapManager.load("maps/laysers/LevelLayerSwitchconstructed.tmx", TiledMap.class);
		
		end = new EndScreen(this);
		setScreen(new TitleScreen(this));
		//initMain();
	}

	public void initMain()
	{
		mainScreen = new PlayScreen(this);
		setScreen(mainScreen);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		mapManager.dispose();
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
