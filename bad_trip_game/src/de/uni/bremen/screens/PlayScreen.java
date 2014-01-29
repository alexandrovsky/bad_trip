package de.uni.bremen.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import de.uni.bremen.BadTripGame;
import de.uni.bremen.entities.Character;
import de.uni.bremen.entities.Dealer;
import de.uni.bremen.entities.Drug;
import de.uni.bremen.entities.Enemy;
import de.uni.bremen.entities.Fruit;
import de.uni.bremen.entities.Goal;
import de.uni.bremen.entities.Item;
import de.uni.bremen.entities.Player;
import de.uni.bremen.utils.AnimationDictionary;
import de.uni.bremen.utils.HealthStates;
import de.uni.bremen.utils.Kind;

public class PlayScreen implements Screen {

	private TiledMap map;
	float levelWidth;
	float levelHeight;
	float tileWidth;
	float tileHeight;
	private OrthogonalTiledMapRenderer tileRenderer;
	ShapeRenderer shapeRenderer;
	private OrthographicCamera camera;

	//public Sound mainTheme = Gdx.audio.newSound(Gdx.files.internal("audio/main_theme.mp3"));
	long mainThemeId;
	private Player player;

	BitmapFont font, messageFont;

	BadTripGame gameRef;

	ArrayList<Item> itemsList;
	ArrayList<Character> charactersList;

	private static final String FRUIT_SPAWN = "fruit";
	private static final String ENEMY_SPAWN = "enemy";
	private static final String DRUG_SPAWN = "drug";
	private static final String GOAL = "goal";

	int width, height;

	Color rect_red_dark = new Color(0.15f, 0.15f, 0.32f, 1.0f);
	Color rect_red = new Color(0.85f, 0.15f, 0.12f, 1.0f);
	Color rect_green = new Color(0.45f, 0.85f, 0.12f, 1.0f);

	Vector2 debug;

	
	Color bg = new Color(0x86C4FD);
	
	public PlayScreen(BadTripGame gameref) {
		// TODO Auto-generated constructor stub
		gameRef = gameref;
		font = new BitmapFont();
		messageFont = new BitmapFont();
		messageFont.setColor(1.0f, .7f, 0.0f, 1.0f);
		font.scale(1.6f);
		font.setColor(1.0f, .7f, 0.0f, 1.0f);
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		
	}
	

	@Override
	public void render(float delta) {
		// if(gameRef.getScreen()!=this)return;

		
		
		if (player.win) {
			gameRef.end.goodEnd = player.currentHealth < 25 ? false : true;
			gameRef.end.score = player.score;
			gameRef.setScreen(gameRef.end);
			return;
		}

		if (player.isDead)// TODO fix this y by create a new tile layer
		{
			gameRef.end.goodEnd = false;
			gameRef.end.score = player.score;
			gameRef.setScreen(gameRef.end);
			return;
		}

		
		Gdx.gl.glClearColor(bg.r,bg.g,bg.b,1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float deltaTime = Gdx.graphics.getDeltaTime();
		
		//
		if(player.postion.x - camera.viewportWidth/2-tileWidth > 0 &&
		   player.postion.x + camera.viewportWidth/2+tileWidth < levelWidth &&
		   player.postion.y - camera.viewportHeight/2-tileHeight > 0 &&
		   player.postion.y + camera.viewportHeight/2+tileHeight < levelHeight){
		
			camera.position.set(new Vector3(player.postion.x + player.width / 2,
				player.postion.y + player.height / 2, 0));
			camera.update();
		}
		
		tileRenderer.setView(camera);

		SpriteBatch batch = tileRenderer.getSpriteBatch();
		batch.begin();

		// render background

		switch (player.currentHealthState) {
		case CLEAN:
			tileRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers()
					.get("sober"));
			break;
		case ON_XTC:
			tileRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers()
					.get("xtc"));
			break;
		case ON_WEED:
			tileRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers()
					.get("weed"));
			break;
		case ON_MUSHRROM:
			tileRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers()
					.get("shrooms"));
			break;
		default:
			break;
		}
		// then render the player

		for (Item item : itemsList) {
			if (item == null)
				continue;
			if (!item.isDead)
				item.draw(batch, deltaTime);
		}

		for (Character character : charactersList) {
			if (character == null)
				continue;
			if (!character.isDead)
				character.draw(batch, deltaTime);
			// System.out.println("enemy" + i + "loc:" +character.postion);

			if (character.message != null && character.message.length() > 0) {
				// messageFont.scale(character.messageScale);
				// messageFont.draw(batch,character.message,
				// character.postion.x-50,character.postion.y+120);
				messageFont.drawMultiLine(batch, character.message,
						character.postion.x - 50, character.postion.y + 170,
						character.message.length() / 2, HAlignment.LEFT);
			}
		}

		player.draw(batch, deltaTime);

		switch (player.currentHealthState) {
		case CLEAN:
			tileRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers()
					.get("soberforeground"));
			break;
		case ON_MUSHRROM:
			tileRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers()
					.get("shroomsforeground"));
			break;
		case ON_WEED:
			tileRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers()
					.get("weedforeground"));
			break;
		case ON_XTC:
			tileRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers()
					.get("xtcforeground"));
			break;
		default:
			break;
		}
		// finally render the forground

		// GUI

		font.draw(batch, "Score: " + player.score, 
				camera.position.x + camera.viewportWidth/2-180,
				camera.position.y + camera.viewportHeight/2-30);

		if (player.message != null && player.message.length() > 0) {
			// messageFont.scale(player.messageScale);
			messageFont.draw(batch, player.message, player.postion.x
					- player.messageScale * 1000, player.postion.y + 200);

		}

		batch.end();

		renderPlayerStatus();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
	}

	void renderPlayerStatus() {

		shapeRenderer.identity();

		shapeRenderer.begin(ShapeType.Filled);

		
		
		// health bar:
		float x, y, h;
		h = 40;
		x = 50;
		y = camera.viewportHeight - 2 * h;
		float offset = 5;

		// health cross
		shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);

		shapeRenderer.rect(x - h, y, h, h); // health-cross outer shape
		shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		shapeRenderer.rect(x - h + offset, y + offset, h - 2 * offset, h - 2
				* offset); // health-cross inner shape
		shapeRenderer.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		float cross_w = h - 2 * offset;
		float cross_h = h - 2 * offset - (2 * cross_w / 3);
		float cross_x = x - h + offset;
		float cross_y = y + offset + cross_w / 3;

		shapeRenderer.rect(cross_x, cross_y, cross_w, cross_h); // cross
																// horizontal
																// part
		cross_x = x - h + offset + cross_w / 3;
		cross_y = y + offset;
		cross_h = h - 2 * offset - (2 * cross_w / 3);
		shapeRenderer.rect(cross_x, cross_y, cross_h, cross_w);// cross vertical
																// part

		// ----- drug timer
		if (player.currentHealthState != HealthStates.CLEAN) {

			System.out.println(player.drugTime);
			// healthbar:
			shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
			shapeRenderer.rect(260, y, 200, h); // drug-bar outer shape
			shapeRenderer.setColor(1f, 0.05f, 0.12f, 1.0f);
			shapeRenderer.rect(260 + offset, y + offset, player.drugTime * 2
					- 2 * offset, h - 2 * offset, rect_red_dark, rect_red,
					rect_red, rect_red_dark); // drug-bar inner shape

		}

		// healthbar:
		shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
		shapeRenderer.rect(60, y, 200, h); // health-bar outer shape

		// shapeRenderer.setColor();
		shapeRenderer.rect(60 + offset, y + offset, player.getCurrentHealth()
				* 2 - 2 * offset, h - 2 * offset, rect_red, rect_green,
				rect_green, rect_red); // health-bar inner shape

		// TODO Render image for current drug state here

		shapeRenderer.end();
	}

	
	
	
	
	@Override
	public void show() {
	
	
		//mainTheme.stop(mainThemeId);
		//mainThemeId = mainTheme.loop(0.6f);
		
		gameRef.mapManager.finishLoading();
		
		map = gameRef.mapManager.get("maps/laysers/LevelLayerSwitchconstructed.tmx");
		tileRenderer = new OrthogonalTiledMapRenderer(map);
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers()
				.get("soberforeground");
		
		
		
		
		levelWidth = collisionLayer.getWidth() * collisionLayer.getTileWidth();
		levelHeight = collisionLayer.getHeight() * collisionLayer.getTileHeight();
		tileWidth = collisionLayer.getTileWidth();
		tileHeight = collisionLayer.getTileHeight();

		shapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera();

		itemsList = new ArrayList<Item>();
		charactersList = new ArrayList<Character>();

		// spawnpoint for player


		TiledMapTileLayer objectLayer = (TiledMapTileLayer) map.getLayers()
				.get("objects");

		//gameRef.textureManager.finishLoading();
		
		for (int x = 0; x < objectLayer.getWidth(); x++) {
			for (int y = 0; y < objectLayer.getHeight(); y++) {
				Cell cell = objectLayer.getCell(x, y);
				if (cell == null) {
					continue;
				}
				TiledMapTile tile = objectLayer.getCell(x, y).getTile();
				if (tile != null && tile.getProperties().containsKey("player")) {
					
					Vector2 newpos = new Vector2((float) x * objectLayer.getTileWidth(), 
							     				 (float) y * objectLayer.getTileHeight());
					System.out.println("player spawn tile:  x:" + x + " y:" + y + "coord:" + newpos );
					AnimationDictionary playerAnimDict = new AnimationDictionary(new Texture("img/characters/animation_map_character2.png"),
							0.125f, 4, 4, 6);
					player = new Player(newpos, playerAnimDict,
							playerAnimDict.animationTime, playerAnimDict.width,
							playerAnimDict.height, collisionLayer);
					Gdx.input.setInputProcessor(player);
					player.items = itemsList;
					player.enemies = charactersList;
				}

			}
		}

		// /spawn the objects:
		for (int x = 0; x < objectLayer.getWidth(); x++) {
			for (int y = 0; y < objectLayer.getHeight(); y++) {
				Cell cell = objectLayer.getCell(x, y);
				if (cell == null) {
					continue;
				}
				TiledMapTile tile = objectLayer.getCell(x, y).getTile();
				if (tile == null) {
					continue;
				}

				Vector2 newpos = new Vector2((float) x
						* objectLayer.getTileWidth(), (float) y
						* objectLayer.getTileHeight());

				if (tile.getProperties().containsKey(GOAL)) {
					String path = "img/items/apple.png";
					AnimationDictionary animDict = new AnimationDictionary(new Texture(path), 0.25f, 1);
					Goal g = new Goal(newpos, animDict, animDict.animationTime,
							animDict.width, animDict.height);
					itemsList.add(g);
				}

				if (tile.getProperties().containsKey(FRUIT_SPAWN)) {
					String path = "img/items/apple.png";
					int rand = (int) Math.random() * 3;
					switch (rand) {
					case 0:
						path = "img/items/orange.png";
						break;
					case 1:
						path = "img/items/apple.png";
						break;
					case 2:
						path = "img/items/pear.png";
						break;
					}

					AnimationDictionary animDict = new AnimationDictionary(new Texture(path), 0.25f, 1);
					Fruit f = new Fruit(newpos, animDict,
							animDict.animationTime, animDict.width,
							animDict.height);
					itemsList.add(f);
				}

				if (tile.getProperties().containsKey(ENEMY_SPAWN)) {
					// debug=newpos;
					AnimationDictionary animDict = new AnimationDictionary(new Texture(
							"img/characters/animation_map_doctor-2.png"), 0.25f,
							5);
					Enemy enemy = new Enemy(newpos.add(0.0f, 500.0f), player,
							animDict, animDict.animationTime, animDict.width,
							animDict.height, collisionLayer);
					charactersList.add(enemy);
				}

				if (tile.getProperties().containsKey("dealer")) {
					// debug=newpos;
					AnimationDictionary animDict = new AnimationDictionary(new Texture("img/characters/Dealer.png"), 0.25f, 6);
					Dealer deal = new Dealer(newpos.add(0.0f, 1500.0f),
							animDict, animDict.animationTime, animDict.width,
							animDict.height, collisionLayer);
					// deal.message =
					// (String)mapObject.getProperties().get("type");
					deal.setMessage((String) tile.getProperties().get("type"));
					charactersList.add(deal);
				}

				if (tile.getProperties().containsKey(DRUG_SPAWN)) {
					String type = (String) tile.getProperties().get("type");
					String path = "xtc.png";
					int num = 1;
					Kind newkind = Kind.XTC;
					if (type.equals("xtc")) {
						num = 6;
						path = "xtc.png";
						newkind = Kind.XTC;
					}
					if (type.equals("weed")) {
						num = 6;
						path = "joint-01.png";
						newkind = Kind.CANNABIS;
					}
					if (type.equals("mushroom")) {
						num = 6;
						path = "mushroom.png";
						newkind = Kind.MUSHROOM;
					}
					String fullPath = "img/items/" + path;
					AnimationDictionary animDict = new AnimationDictionary(new Texture(fullPath), 0.25f, num);
					Drug d = new Drug(newpos, animDict, animDict.animationTime, animDict.width, animDict.height);
					d.current = newkind; // cheap harcode for testing
					itemsList.add(d);
				}
			}
		}
	}

	@Override
	public void hide() {
		dispose();
		
		
		

	}

	@Override
	public void pause() {
		System.out.println("resume");

	}

	@Override
	public void resume() {
		System.out.println("resume");

	}

	@Override
	public void dispose() {
		tileRenderer.dispose();
		shapeRenderer.dispose();
		//mainTheme.dispose();
		player.dispose();
	}

}
