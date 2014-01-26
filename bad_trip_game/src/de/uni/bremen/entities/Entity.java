package de.uni.bremen.entities;

import java.util.Dictionary;

import javax.swing.text.Position;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import de.uni.bremen.utils.AnimationDictionary;



public class Entity 
{
	


	
	
	
	
	protected AnimationDictionary animationDict;
	protected TextureRegion currentFrame;
	protected float stateTime;
	protected float animationTime;
	
	public Vector2 postion;
	public float height, width; 

	public boolean isDead;
	
	public Entity(Vector2 position, AnimationDictionary animationDict, float animationTime, float width, float height){
		
		this.animationDict = animationDict;
		this.animationTime = animationTime;
		this.postion = position;
		this.width = width;
		this.height = height;
	}
	
	// overload constructor
	
	
	
	//============================== OVERRIDES ======================================//
	//
	//	
	//
	//===============================================================================//
	
	
	public void draw(SpriteBatch batch, float deltaTime){
		update(deltaTime);
		
		
		//update graphics
		stateTime += deltaTime;
		currentFrame = ((Animation) animationDict.get(new Integer(0))).getKeyFrame(stateTime, true);
        batch.draw(currentFrame,this.postion.x+width,this.postion.y,0,0,width,height,-1.0f,1,0);
				
	}
	
	
	
	public void update(float deltaTime){
		
	}
	
	
	
}
