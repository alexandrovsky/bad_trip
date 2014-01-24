package de.uni.bremen.physics;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class WorldPhysics {

	// constants:
	public static final Vector2 gravity = new Vector2(0.0f, -9.81f);
	public static final float GRAVITY = 60 * 2.8f;
	public static final String BLOCKED_CELL_KEY = "blocked";
	
	
	// box2d:
	private static World world = new World(gravity, true);
	private static Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	
	public static World CreateWorldWithMap(TiledMap map){
		
		
		
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer)map.getLayers().get(0);
		
		float tileWidth = collisionLayer.getTileWidth();
		float tileHeight = collisionLayer.getTileHeight();
		
		BodyDef collisionCellbodyDef = new BodyDef();
		collisionCellbodyDef.type = BodyType.StaticBody;
		collisionCellbodyDef.gravityScale = 0.0f;
		
		FixtureDef collisionCellbodyFixtureDef = new FixtureDef();
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(tileWidth/2, tileHeight/2);
		
		collisionCellbodyFixtureDef.shape = shape;
		
		for(int x = 0; x < collisionLayer.getWidth(); x++){
			for(int y = 0; y < collisionLayer.getHeight(); y++){
				Cell cell = collisionLayer.getCell(x, y);
				if(cell.getTile().getProperties().containsKey(WorldPhysics.BLOCKED_CELL_KEY)){
					collisionCellbodyDef.position.set(x * tileWidth + tileWidth/2,
													  y * tileHeight + tileHeight/2);
					
					Body cellBody = world.createBody(collisionCellbodyDef);
					cellBody.createFixture(collisionCellbodyFixtureDef);
					
				}
			}
		}
		
		
		return world;
	}

	public static World getWorld(){
		return world;
	}
	
	public static Box2DDebugRenderer getDebugRenderer() {
		return debugRenderer;
	}

	
	
}
