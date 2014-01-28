package de.uni.bremen.entities;

import com.badlogic.gdx.math.Vector2;
import de.uni.bremen.utils.AnimationDictionary;
import de.uni.bremen.utils.Kind;



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

	public Kind  current = Kind.CANNABIS;



}
