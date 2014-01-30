package de.uni.bremen;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
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
	
	
	public static final String MUSIC_MAIN_THEME = "audio/main_theme.mp3";
	public static final String MUSIC_WEED_THEME = "audio/weed_theme.mp3";
	public static final String MUSIC_XTC_THEME = "audio/xtc_theme.mp3";
	public static final String MUSIC_MUSHROOM_THEME = "audio/mushroom_theme.mp3";
	public static final String MUSIC_BAD_END_THEME = "audio/mushroom_theme.mp3";
	public static final String MUSIC_GOOD_END_THEME = "audio/xtc_theme.mp3";
	
	public static String currentMusic = MUSIC_GOOD_END_THEME;
	public static Map<String, Music> music = new HashMap<String, Music>();
	
	
	public static void playMusic(String key){
		if(music.containsKey(key) && !currentMusic.equals(key)){
			stopMusic(currentMusic);
			music.get(key).play();
			currentMusic = key;
		}
	}
	
	public static void stopMusic(String key){
		if(music.containsKey(key)){
			music.get(key).stop();
		}
	}
	
	public static void setMusicLooping(boolean isLooping){
		for (Map.Entry<String, Music> entry : music.entrySet()){
			entry.getValue().setLooping(isLooping);
		}
	}
	public static void disposeMusic(){
		for (Map.Entry<String, Music> entry : music.entrySet()){
			entry.getValue().dispose();
		}
	}
	
	
	@Override
	public void create() {
		
		
	    Map<String,Music> temp = new HashMap<String, Music>();
	    temp.put(MUSIC_MAIN_THEME,
				Gdx.audio.newMusic(Gdx.files.internal(MUSIC_MAIN_THEME)) );
	    temp.put(MUSIC_XTC_THEME, 
				Gdx.audio.newMusic(Gdx.files.internal(MUSIC_XTC_THEME)) );
	    temp.put(MUSIC_WEED_THEME, 
				Gdx.audio.newMusic(Gdx.files.internal(MUSIC_WEED_THEME)));
	    temp.put(MUSIC_MUSHROOM_THEME, 
	    		Gdx.audio.newMusic(Gdx.files.internal(MUSIC_MUSHROOM_THEME)));
	    temp.put(MUSIC_BAD_END_THEME, 
	    		Gdx.audio.newMusic(Gdx.files.internal(MUSIC_BAD_END_THEME)));
	    temp.put(MUSIC_GOOD_END_THEME, 
	    		Gdx.audio.newMusic(Gdx.files.internal(MUSIC_GOOD_END_THEME)));
		setMusicLooping(true);
		music = Collections.unmodifiableMap(temp);
		
		
		mapManager = new AssetManager();
		mapManager.setLoader(TiledMap.class,new TmxMapLoader(new InternalFileHandleResolver()));
		mapManager.load(mainScreen.getLevelname(), TiledMap.class);
		
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
		//mapManager.clear();
		//mapManager.dispose();
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
