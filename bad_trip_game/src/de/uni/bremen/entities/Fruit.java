package de.uni.bremen.entities;

import com.badlogic.gdx.math.Vector2;

import de.uni.bremen.utils.AnimationDictionary;



public class Fruit extends Item{

	public Fruit(Vector2 position, AnimationDictionary animationDict,
			float animationTime, float width, float height) {
		super(position, animationDict, animationTime, width, height);
		
	}

	//============================== INTERNAL STATES ==============================//
	//
	//	
	//
	//===============================================================================//

	protected Kind  current = Kind.APPLE;
	
	public Kind getCurrent() {
		return current;
	}

	public void setCurrent(Kind current) {
		this.current = current;
	}

	protected enum Kind{
		APPLE,ORANGE,PEAR
	};
}
