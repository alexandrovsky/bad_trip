package de.uni.bremen.entities;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import de.uni.bremen.utils.AnimationDictionary;

public class Item extends Entity {

	public Item(Vector2 position, AnimationDictionary animationDict,
			float animationTime, float width, float height) {
		super(position, animationDict, animationTime, width, height);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void draw(SpriteBatch batch, float deltaTime){
		
		update(deltaTime);
		
		
		//update graphics
		stateTime += deltaTime;                 
        
		
		
		currentFrame = ((Animation) animationDict.get( new Integer(0) )).getKeyFrame(stateTime, true);
		
		
		batch.draw(currentFrame,postion.x,postion.y,0,0, width, height, 1.0f, 1,0f);
        //batch.draw(region, x, y, originX, originY, moirror_x, moirror_x, scaleX, scaleY, rotation);
	}
	

	
	
	
	
}
