package de.uni.bremen.entities;



import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Character {

	
	
	public Enemy(Vector2 position,
			de.uni.bremen.utils.AnimationDictionary animationDict,
			float animationTime, float width, float height,
			TiledMapTileLayer collisionLayer, float maxSpeed) {
		super(position, animationDict, animationTime, width, height, collisionLayer,
				maxSpeed);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update(float deltaTime) {
		
		super.update(deltaTime);
	}


	

}
