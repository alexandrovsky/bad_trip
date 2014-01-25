package de.uni.bremen.screens;



import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import de.uni.bremen.entities.Character;
import de.uni.bremen.entities.Drug;
import de.uni.bremen.entities.Enemy;
import de.uni.bremen.entities.Fruit;
import de.uni.bremen.entities.Item;
import de.uni.bremen.entities.Player;
import de.uni.bremen.physics.WorldPhysics;
import de.uni.bremen.utils.AnimationDictionary;


public class PlayScreen implements Screen {
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer tileRenderer;
	ShapeRenderer shapeRenderer;
	private OrthographicCamera camera;
	
	private Player player;
	
	
	ArrayList<Item> itemsList;
	ArrayList<Character> charactersList;
	
	private static final String FRUIT_SPAWN ="SpawnpointFruit";
	private static final String ENEMY_SPAWN ="SpawnpointEnemy";
	private static final String DRUG_SPAWN ="SpawnpointDrug";
	
	
	private Sound mainTheme;
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		camera.position.set(new Vector3(player.postion.x + player.width/2, player.postion.y+player.height/2, 0));
		camera.update();
		
		tileRenderer.setView(camera);
		

		SpriteBatch batch = tileRenderer.getSpriteBatch(); 
		batch.begin();
		
		// render background
		tileRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("background"));
		// then render the player
		
		for (Item item : itemsList) {
			item.draw(batch, deltaTime);
		}
		for ( Character character : charactersList) {
			character.draw(batch, deltaTime);
		}
		
		
		player.draw(batch, deltaTime);
		// finally render the forground
		//tileRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("foreground"));
		batch.end();
		
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
		map = new TmxMapLoader().load("maps/test2/maptestv3.tmx");
		tileRenderer = new OrthogonalTiledMapRenderer(map);
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
		
		shapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera();
		
		itemsList = new ArrayList<Item>();
		charactersList = new ArrayList<Character>();
		
		
		AnimationDictionary playerAnimDict = new AnimationDictionary("img/characters/animation_map_character.png", 0.25f, 4,4,3,5 );
		
		player = new Player(new Vector2(1200, 600), 
				playerAnimDict, playerAnimDict.animationTime, 
				playerAnimDict.width, playerAnimDict.height, collisionLayer);
		Gdx.input.setInputProcessor(player);
		
		
		Vector2 debugpoint;
		
		//get spawnpoints
		MapObjects objs = map.getLayers().get("Objects").getObjects();
		for (MapObject mapObject : objs) {
			String name=mapObject.getName();
			if(name==null)continue;
			System.out.println(name);
			//get spawnpoint
			Integer newx = (Integer)mapObject.getProperties().get("x");
			Integer newy = (Integer)mapObject.getProperties().get("y");
			Vector2 newpos = new Vector2(
					newx.floatValue(),newy.floatValue()
							);
			AnimationDictionary animDict;
			if(name.equals(FRUIT_SPAWN))
			{
				
				animDict = new AnimationDictionary("img/items/apple.png", 0.25f, 1 );
				Fruit f = new Fruit(newpos, animDict, animDict.animationTime, animDict.width,animDict.height);
				itemsList.add(f);
			}
			if(name.equals(ENEMY_SPAWN))
			{	
				animDict = new AnimationDictionary("img/characters/animation_map_doctor.png", 0.25f, 5 );
				Enemy e = new Enemy(newpos, player, animDict, animDict.animationTime,animDict.width,animDict.height, collisionLayer);
				charactersList.add(e);
			}
			if(name.equals(DRUG_SPAWN))
			{
				
				animDict = new AnimationDictionary("img/items/mushroom_A.png", 0.25f, 6 );
				Drug d = new Drug(newpos, animDict, animDict.animationTime, animDict.width,animDict.height);
				itemsList.add(d);
			}
		}
		
		// load audio:
		mainTheme = Gdx.audio.newSound( Gdx.files.internal("audio/music/main_theme.wav") );
		mainTheme.loop();
		
		
		
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
