package de.uni.bremen.screens;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

import de.uni.bremen.entities.Player;


public class PlayScreen implements Screen {

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	private Player player;
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.position.set(new Vector3(player.getX() + player.getWidth()/2, player.getY()+player.getHeight()/2, 0));
		camera.update();
		
		renderer.setView(camera);
		renderer.getSpriteBatch().begin();
		
		// render background
		renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("background"));
		// then render the player
		player.draw(renderer.getSpriteBatch());
		// finally render the forground
		renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("foreground"));
		renderer.getSpriteBatch().end();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
	}

	@Override
	public void show() {
		map = new TmxMapLoader().load("maps/map.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		
		camera = new OrthographicCamera();
		
		player = new Player(new Sprite(new Texture("img/player.png")), 
						   (TiledMapTileLayer) map.getLayers().get(0));
		player.initAnimation();
		player.setPosition(27  * player.getCollisionLayer().getTileWidth(),
						   (player.getCollisionLayer().getHeight()-29) * player.getCollisionLayer().getTileHeight());
		Gdx.input.setInputProcessor(player);
	}

	@Override
	public void hide() {
		dispose();

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
		map.dispose();
		renderer.dispose();
		//player.dispose();
	}

}
