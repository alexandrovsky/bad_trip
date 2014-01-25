package de.uni.bremen.entities;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Character {

	
	Player player;
	public Enemy(Vector2 position, Player player,
			de.uni.bremen.utils.AnimationDictionary animationDict,
			float animationTime, float width, float height,
			TiledMapTileLayer collisionLayer, float maxSpeed) 
	{
		super(position, animationDict, animationTime, width, height, collisionLayer,
				maxSpeed);
		
		this.player = player;
		
	}
	
	@Override
	public void update(float deltaTime) 
	{
		if(player!=null)
		{
			Vector2 delta = postion.sub(player.postion);
			if(delta.len() < this.width){
				System.out.println("hit player");
			}else if (delta.len() > Gdx.graphics.getWidth() ) {
				currentState = States.IDLE;
			}else{
				//postion.add( delta.scl(this.maxSpeed*deltaTime) );
			}			
		}

		
		
		super.update(deltaTime);
		
		
	}


	

}
