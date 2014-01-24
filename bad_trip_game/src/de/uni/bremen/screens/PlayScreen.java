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

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import de.uni.bremen.entities.Player;
import de.uni.bremen.physics.WorldPhysics;


public class PlayScreen implements Screen {
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	private final float timeStep = 1 / 60f;
	private final int velocityIterations=8, positionIterations =3;
	
	World world;
	
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
		
		// debug rendering for box2d
		WorldPhysics.getDebugRenderer().render(world, camera.combined);
		
		world.step(timeStep, velocityIterations, positionIterations);
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
	}

	@Override
	public void show() {
		
		// load the level
		map = new TmxMapLoader().load("maps/map.tmx");
		
		
		// create the physics for level
		world = WorldPhysics.CreateWorldWithMap(map);
		
		
		player = new Player(new Sprite(new Texture("img/player.png")), 
				   (TiledMapTileLayer) map.getLayers().get(0));
		
		float startx = 27  * player.getCollisionLayer().getTileWidth();
		float starty = (player.getCollisionLayer().getHeight()-29) * player.getCollisionLayer().getTileHeight();
		
		
		
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixDef = new FixtureDef();
		
		PolygonShape playerShape = new PolygonShape();
		playerShape.setAsBox(player.getWidth()/2,player.getHeight()/2);
		
		
		//player definition
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(startx-player.getWidth(),
							 starty-player.getHeight());
		
		
		
		
		fixDef.shape = playerShape;
		fixDef.friction = .75f;
		fixDef.restitution = .1f;
		fixDef.density = 50;
		
		//world.createBody(bodyDef).createFixture(fixDef);
		Body body = world.createBody(bodyDef);
		body.createFixture(fixDef);
		
		player.setBody(body);
		playerShape.dispose();
		
		camera = new OrthographicCamera();
		
		
		renderer = new OrthogonalTiledMapRenderer(map);
		
		
		

		//player.initAnimation();
		player.setPosition(startx,starty);
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

	
	private final float pixelsToMeters=64;
	
	protected float pixels(float meters)
	{
		return meters / pixelsToMeters;
	}
}
