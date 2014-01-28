package de.uni.bremen.entities;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import de.uni.bremen.physics.WorldPhysics;
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
	
	Timer itemTimer = Timer.instance();
	public long itemTimerActivationTime;
	
	protected float itemDuration = WorldPhysics.ITEM_RESPAWN;
	
	public synchronized void respawn(){
		
		
		itemTimerActivationTime = TimeUtils.millis();
		this.itemTimer.scheduleTask(new Task(){
		    @Override
		    public void run() {
		    	isDead=false;
		    }
		},itemDuration );
	}
	
	
	
	
}
