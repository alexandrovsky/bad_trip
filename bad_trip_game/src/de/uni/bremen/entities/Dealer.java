package de.uni.bremen.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import de.uni.bremen.utils.AnimationDictionary;

public class Dealer extends Character {

	public Dealer(
			Vector2 position, 
			AnimationDictionary animationDict,
			float animationTime, 
			float width, float height,
			TiledMapTileLayer collisionLayer) {
		super(position, animationDict, animationTime, width, height,
				collisionLayer, 1);
		// TODO Auto-generated constructor stub
		messageDuration = -1;
		currentState = States.ZERO;
		message = message_holder;
	}
	
	String message_holder ="";
	
	public void setMessage(String s)
	{
		message_holder = s;
	}
	
	
	public void talk()
	{
		message=message_holder;
		//message();
	}

	public void stopTalk()
	{
		message="";
	}
}
