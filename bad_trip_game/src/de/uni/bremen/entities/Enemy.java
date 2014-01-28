package de.uni.bremen.entities;



import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import de.uni.bremen.physics.WorldPhysics;
import de.uni.bremen.utils.AnimationDictionary;

public class Enemy extends Character {

	
	Player player;
	public Enemy(Vector2 position, Player player,
			AnimationDictionary animationDict,
			float animationTime, float width, float height,
			TiledMapTileLayer collisionLayer) 
	{
		super(position.add(new Vector2(0.0f, 300.0f)), animationDict, animationTime, width, height, collisionLayer,
				WorldPhysics.ENEMY_MAX_SPEED);
		currentState = States.ZERO;
		this.player = player;
		this.maxSpeed = WorldPhysics.ENEMY_MAXSPEED;
	}
	
	@Override
	public void update(float deltaTime) 
	{
		if(player != null){
			Vector2 playerPos = player.postion.cpy();
			Vector2 enemyPos = postion.cpy(); 
			Vector2 delta = playerPos.sub(enemyPos);
			
			//if(delta.len() < Gdx.graphics.getWidth() ){
			if(delta.x > 0.0){
				velocity.x = maxSpeed;
				isOrientationLeft= false;
			}else{
				velocity.x = -maxSpeed;
				isOrientationLeft = true;
			}
			
		}
		
		
		super.update(deltaTime);
	}
	


	

}
