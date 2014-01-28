package de.uni.bremen.screens;



import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
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
	private OrthogonalTiledMapRenderer tileRenderer;
	ShapeRenderer shapeRenderer;
	private OrthographicCamera camera;
	
	public Sound mainTheme = Gdx.audio.newSound(Gdx.files.internal("audio/main_theme.mp3"));
	 long mainThemeId;
	private Player player;
	
	BitmapFont font,messageFont;
	
	BadTripGame gameRef;
	
	ArrayList<Item> itemsList;
	ArrayList<Character> charactersList;
	
	private static final String FRUIT_SPAWN ="fruit";
	private static final String ENEMY_SPAWN ="enemy";
	private static final String DRUG_SPAWN ="drug";
	
	
	int width,height;
	
	public PlayScreen(BadTripGame gameref) {
		// TODO Auto-generated constructor stub
		gameRef = gameref;
		font = new BitmapFont();
		messageFont=new BitmapFont();
		messageFont.setColor(1.0f, .7f, 0.0f, 1.0f);
		font.scale(1.6f);
		font.setColor(1.0f, .7f, 0.0f, 1.0f);
		width=Gdx.graphics.getWidth();
		height=Gdx.graphics.getHeight();
	}
	Color bg = new Color(0x86C4FD);
	@Override
	public void render(float delta) {
		//if(gameRef.getScreen()!=this)return;
		
		if(player.win)
		{
			gameRef.end.goodEnd= player.currentHealth<25?false:true;
			gameRef.end.score = player.score;
			gameRef.setScreen(gameRef.end);
			return;	
		}
		
		if(player.isDead)//TODO fix this y by create a new tile layer
		{
			gameRef.end.goodEnd=false;
			gameRef.end.score = player.score;
			gameRef.setScreen(gameRef.end);
			return;
		}
		
		Gdx.gl.glClearColor(bg.r,bg.g,bg.b,1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		camera.position.set(new Vector3(player.postion.x + player.width/2, player.postion.y+player.height/2, 0));
		camera.update();
		
		tileRenderer.setView(camera);
		

		SpriteBatch batch = tileRenderer.getSpriteBatch(); 
		batch.begin();
		
		// render background
		
		switch(player.currentHealthState)
		{
			case CLEAN:
				tileRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("sober"));
			break;
			case ON_XTC:
				tileRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("xtc"));
				break;
			case ON_WEED:
				tileRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("weed"));
				break;
			case ON_MUSHRROM:
				tileRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("shrooms"));
				break;
			default:
				break;
		}
		// then render the player
		
		for (Item item : itemsList) {
			if(item == null)continue;
			if(!item.isDead)item.draw(batch, deltaTime);
		}
		

		
		for (Character character : charactersList) 
		{
			if(character == null)continue;
			if(!character.isDead)character.draw(batch, deltaTime);
			//System.out.println("enemy" + i + "loc:" +character.postion);
			
			if(character.message!=null && character.message.length()>0)
			{
				//messageFont.scale(character.messageScale);
				//messageFont.draw(batch,character.message, character.postion.x-50,character.postion.y+120);
				messageFont.drawMultiLine(batch,character.message, character.postion.x-50,character.postion.y+170,character.message.length()/2,HAlignment.LEFT);
			}	
		}
		
		
		player.draw(batch, deltaTime);
		
		switch(player.currentHealthState)
		{
			case CLEAN:
				tileRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("soberforeground"));
			break;
			case ON_MUSHRROM:
				tileRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("shroomsforeground"));
				break;
			case ON_WEED:
				tileRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("weedforeground"));
				break;
			case ON_XTC:
				tileRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("xtcforeground"));
				break;
			default:
				break;
		}
		// finally render the forground
		
		//GUI
		 
		 
		 font.draw(batch,"Score: "+player.score, player.postion.x+100,player.postion.y+400);


		 if(player.message!=null && player.message.length()>0)
		 {
			 //messageFont.scale(player.messageScale);
			 messageFont.draw(batch,player.message, player.postion.x-player.messageScale*1000,player.postion.y+200);
			 
		 }
		 
		
		batch.end();
		
		renderPlayerStatus();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
	}
	
	void renderPlayerStatus(){
		
		shapeRenderer.identity();
		
		shapeRenderer.begin(ShapeType.Filled);
		
		// health bar:
		float x, y, w, h;
		w = 22;
		h = 40;
		x = 50;
		y = camera.viewportHeight - 2*h;
		float offset = 5;
		
		
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
		
		
		shapeRenderer.rect(cross_x, cross_y, cross_w, cross_h); // cross horizontal part
		cross_x = x-h+offset+cross_w/3;
		cross_y = y+offset;
		cross_h = h-2*offset-(2*cross_w/3);
		shapeRenderer.rect(cross_x, cross_y, cross_h, cross_w);// cross vertical part

		
		//----- drug timer
		if(player.currentHealthState != HealthStates.CLEAN)
		{
			
			System.out.println(player.drugTime);
			// healthbar:
			shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
			shapeRenderer.rect(260, y, 200, h); // drug-bar outer shape
			shapeRenderer.setColor(1f, 0.05f, 0.12f, 1.0f);
			shapeRenderer.rect(260+offset, y+offset,
			player.drugTime*2-2*offset, h-2*offset,rect_red_dark,rect_red,rect_red,rect_red_dark); // drug-bar inner shape
			
		}
		
		// healthbar:
		shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
		shapeRenderer.rect(60, y, 200, h); // health-bar outer shape
		
		//shapeRenderer.setColor();
		shapeRenderer.rect(60+offset, y+offset,player.getCurrentHealth()*2-2*offset, h-2*offset,rect_red, rect_green, rect_green,rect_red); // health-bar inner shape
		
		
		
		//TODO Render image for current drug state here
		

		
		shapeRenderer.end();
		}

	Color rect_red_dark = new Color(0.15f, 0.15f, 0.32f, 1.0f);
	Color rect_red = new Color(0.85f, 0.15f, 0.12f, 1.0f);
	Color rect_green = new Color(0.45f, 0.85f, 0.12f, 1.0f);
	
	Vector2 debug;
	@Override
	public void show() {
		
		mainTheme.stop(mainThemeId);
		mainThemeId = mainTheme.loop(0.6f);
		map = new TmxMapLoader().load("maps/laysers/LevelLayerSwitch.tmx");
		tileRenderer = new OrthogonalTiledMapRenderer(map);
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get("soberforeground");
		
		shapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera();
		
		itemsList = new ArrayList<Item>();
		charactersList = new ArrayList<Character>();
		
		
		
		// spawnpoint for player
		MapObjects objs = map.getLayers().get("objects").getObjects();
		for (MapObject mapObject : objs) {
			String name=mapObject.getName();
			if(name==null)continue;
			System.out.println(name);
			//get spawnpoint
			Integer newx = (Integer)mapObject.getProperties().get("x");
			Integer newy = (Integer)mapObject.getProperties().get("y");
			Vector2 newpos = new Vector2( newx.floatValue(),
									      newy.floatValue() +1000);
			
			
			if(name.equals("player"))
			{
				AnimationDictionary playerAnimDict = new AnimationDictionary("img/characters/animation_map_character.png", 0.075f, 4,4,3,5 );
				player = new Player(newpos, 
						playerAnimDict, playerAnimDict.animationTime, 
						playerAnimDict.width, playerAnimDict.height, collisionLayer);
				Gdx.input.setInputProcessor(player);
				player.items = itemsList;
				player.enemies  = charactersList;
						
			}
		}
		
		// spawnpoints get for other objects
		for (MapObject mapObject : objs) {
			String name=mapObject.getName();
			if(name==null)continue;
			System.out.println(name);
			//get spawnpoint
			Integer newx = (Integer)mapObject.getProperties().get("x");
			Integer newy = (Integer)mapObject.getProperties().get("y");
			Vector2 newpos = new Vector2( newx.floatValue(),newy.floatValue() );
			
			AnimationDictionary animDict;
			
			if(name.equals("goal"))
			{
				String path="img/items/apple.png";
				animDict = new AnimationDictionary(path, 0.25f, 1 );
				Goal g = new Goal(newpos, animDict, animDict.animationTime, animDict.width,animDict.height);
				itemsList.add(g);
			}
			
			if(name.equals(FRUIT_SPAWN))
			{
				String path="img/items/apple.png";
				int rand=(int)Math.random()*3;
				switch (rand) {
				case 0:
					path="img/items/orange.png";
					break;
				case 1:
					path="img/items/apple.png";
					break;
				case 2:
					path="img/items/pear.png";
					break;
				}
				
				animDict = new AnimationDictionary(path, 0.25f, 1 );
				Fruit f = new Fruit(newpos, animDict, animDict.animationTime, animDict.width,animDict.height);
				itemsList.add(f);
			}
			
			if(name.equals(ENEMY_SPAWN))
			{	
				//debug=newpos;
				animDict = new AnimationDictionary("img/characters/animation_map_doctor-2.png", 0.25f, 5 );
				Enemy enemy = new Enemy(newpos.add(0.0f,500.0f), player, animDict, animDict.animationTime,animDict.width,animDict.height,collisionLayer);
				charactersList.add(enemy);
			}
			
			if(name.equals("dealer"))
			{	
				//debug=newpos;
				animDict = new AnimationDictionary("img/characters/Dealer.png", 0.25f, 6 );
				Dealer deal = new Dealer(newpos.add(0.0f,1500.0f),animDict, animDict.animationTime,animDict.width,animDict.height,collisionLayer);
				//deal.message = (String)mapObject.getProperties().get("type");
				deal.setMessage((String)mapObject.getProperties().get("type"));
				charactersList.add(deal);
			}
			
			if(name.equals(DRUG_SPAWN))
			{
				String type = (String)mapObject.getProperties().get("type");
				String path="xtc.png";
				int num=1;
				Kind newkind=Kind.XTC;
				if(type.equals("xtc"))
				{
					num=6;
					path="xtc.png";
					newkind = Kind.XTC;
				}
				if(type.equals("weed"))
				{
					num=6;
					path="joint-01.png";
					newkind = Kind.CANNABIS;
				}
				if(type.equals("mushroom"))
				{
					num=6;
					path="mushroom.png";
					newkind = Kind.MUSHROOM;
				}
				
				
				
				animDict = new AnimationDictionary("img/items/"+path, 0.25f, num );
				Drug d = new Drug(newpos, animDict, animDict.animationTime, animDict.width,animDict.height);
				d.current = newkind; //cheap harcode for testing
				itemsList.add(d);
			}
		}
		
		
		//player.postion = debug;
		
		
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
		//mainTheme.dispose();
		player.dispose();
	}
	

}
