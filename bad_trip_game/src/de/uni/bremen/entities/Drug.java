package de.uni.bremen.entities;

import com.badlogic.gdx.math.Vector2;
import de.uni.bremen.utils.AnimationDictionary;



public class Drug extends Item {

	
	
	//============================== INTERNAL STATES ==============================//
	//
	//	
	//
	//===============================================================================//

	public Drug(Vector2 position, AnimationDictionary animationDict,
			float animationTime, float width, float height) {
		super(position, animationDict, animationTime, width, height);
		// TODO Auto-generated constructor stub
	}

	protected Kind  current = Kind.CANNABIS;
	
	public Kind getCurrent() {
		return current;
	}

	public void setCurrent(Kind current) {
		this.current = current;
	}

	protected enum Kind{
		FAKE,CANNABIS,XTC,MUSHROOM;
	};
}