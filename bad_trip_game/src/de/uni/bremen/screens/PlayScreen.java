package de.uni.bremen.screens;



import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import de.uni.bremen.entities.Fruit;
import de.uni.bremen.entities.Item;
import de.uni.bremen.entities.Player;
import de.uni.bremen.utils.AnimationDictionary;


public class PlayScreen implements Screen {
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	private Player player;
	
	ArrayList<Item> itemsList;
	
	private final String FRUIT_SPAWN = "SpawnpointFruit";
	private final String DRUG_SPAWN = "SpawnpointDrug";
	private final String ENEMY_SPAWN = "SpawnpointEnemy";
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//prepare vars
		float deltaTime = Gdx.graphics.getDeltaTime();
		SpriteBatch batch = renderer.getSpriteBatch();
		
		camera.position.set(new Vector3(player.postion.x + player.width/2, player.postion.y+player.height/2, 0));
		camera.update();
		
		renderer.setView(camera);
		
		

		batch.begin();
		
		// render background
		renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("background"));
		
		//render items
		for (Item item : itemsList) {
			item.draw(batch, deltaTime);
		}
		
		// then render the player
		player.draw(batch, deltaTime);
		// finally render the forground
		renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("foreground"));
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
	}

	@Override
	public void show() {
		map = new TmxMapLoader().load("maps/test/maptest1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera();
		
		
		
		//put in items list
		itemsList = new ArrayList<Item>();
		MapObjects objs = map.getLayers().get("Objects").getObjects();
		for (MapObject mapObject : objs) {
			String n = mapObject.getName();
			if(n==null)break;
			
			Integer newx = (Integer)mapObject.getProperties().get("x");
			Integer newy = (Integer)mapObject.getProperties().get("y");
			//get position vector
			Vector2 newpos = new Vector2(
					 newx.floatValue(),
					 newy.floatValue()
					);
			
			if(n.equals(FRUIT_SPAWN))
			{
				AnimationDictionary frutDict = new AnimationDictionary("img/items/apple.png", 0.25f, 4,4,3,5 );
				Fruit f =  new Fruit(newpos,frutDict,frutDict.animationTime, frutDict.width, frutDict.height);
				itemsList.add(f);
			}
			if(n.equals(DRUG_SPAWN))
			{
				
			}
			if(n.equals(ENEMY_SPAWN))
			{
				
			}
		}
		
		//TODO load all enemies
		//put in enemies list
		
		AnimationDictionary playerAnimDict = new AnimationDictionary("img/characters/animation_map_character.png", 0.25f, 4,4,3,5 );
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
		player = new Player(new Vector2(280, 350), 
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
		renderer.dispose();
		//player.dispose();
	}
	

}
