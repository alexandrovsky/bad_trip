package de.uni.bremen.screens;



import java.util.Dictionary;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import de.uni.bremen.entities.Player;
import de.uni.bremen.utils.AnimationDictionary;


public class PlayScreen implements Screen {
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer tileRenderer;
	ShapeRenderer shapeRenderer;
	private OrthographicCamera camera;
	
	private Player player;
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		camera.position.set(new Vector3(player.postion.x + player.width/2, player.postion.y+player.height/2, 0));
		camera.update();
		
		tileRenderer.setView(camera);
		

		tileRenderer.getSpriteBatch().begin();
		
		// render background
		tileRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("background"));
		// then render the player
		player.draw(tileRenderer.getSpriteBatch(), deltaTime);
		// finally render the forground
		tileRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("foreground"));
		tileRenderer.getSpriteBatch().end();
		
		renderPlayerStatus();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
	}
	
	void renderPlayerStatus(){
		float x, y, w, h;
		w = 22;
		h = 40;
		x = 50;
		y = camera.viewportHeight - 2*h;
		float offset = 5;
		shapeRenderer.begin(ShapeType.Filled);
		// health cross
		shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
		shapeRenderer.rect(x-h, y, h, h); // health-cross outer shape
		shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		shapeRenderer.rect(x-h+offset, y+offset, h-2*offset, h-2*offset); //health-cross inner shape
		shapeRenderer.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		float cross_w = h-2*offset;
		float cross_h = h-2*offset-(2*cross_w/3);
		float cross_x = x-h+offset;
		float cross_y = y+offset+cross_w/3;
		shapeRenderer.rect(cross_x, cross_y, cross_w-3, cross_h); // cross horizontal part
		cross_x = x-h+offset+cross_w/3;
		cross_y = y+offset;
		cross_h = h-2*offset-(2*cross_w/3);
		shapeRenderer.rect(cross_x, cross_y, cross_h, cross_w-3);// cross vertical part
		// healthbar:
		shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
		shapeRenderer.rect(60, y, 100, h); // health-bar outer shape
		shapeRenderer.setColor(0.15f, 0.55f, 0.12f, 1.0f);
		shapeRenderer.rect(60+offset, y+offset,
		player.getCurrentHealth()*2-2*offset, h-2*offset); // health-bar inner shape
		//shapeRenderer.identity();
		//shapeRenderer.translate(20, 12, 2);
		//shapeRenderer.rotate(0, 0, 1, 90);
		shapeRenderer.end();
		//TODO Render image for current drug state here
		}

	@Override
	public void show() {
		map = new TmxMapLoader().load("maps/test/maptest1.tmx");
		tileRenderer = new OrthogonalTiledMapRenderer(map);
		shapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera();
		
		//TODO load all items
		//put in items list
		
		//TODO load all enemies
		//put in enemies list
		
		AnimationDictionary playerAnimDict = new AnimationDictionary("img/characters/animation_map_character.png", 0.25f, 4,4,3,5 );
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
		player = new Player(new Vector2(0, 779), 
				playerAnimDict, playerAnimDict.animationTime, 
				playerAnimDict.width, playerAnimDict.height, collisionLayer);
		Gdx.input.setInputProcessor(player);
		
		/*
		player = new Player(new Sprite(new Texture("img/main_character_stehend.png")), 
						   (TiledMapTileLayer) map.getLayers().get(0));
		//player.initAnimation();
		player.setPosition(0  * player.getCollisionLayer().getTileWidth(),
						   (player.getCollisionLayer().getHeight()-79) * player.getCollisionLayer().getTileHeight());
		Gdx.input.setInputProcessor(player);
		*/
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
		tileRenderer.dispose();
		shapeRenderer.dispose();
		//player.dispose();
	}
	

}
