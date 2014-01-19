package de.uni.bremen.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;



public class Entity extends Sprite {
	
	
	//============================== INTERNAL STATES ==============================//
	//
	//	Note: we should make the player as a state machine
	//
	//===============================================================================//

	protected States currentState = States.IDLE_RIGHT;
	
	protected enum States{
		IDLE_LEFT,
		IDLE_RIGHT,
		JUMPING,
		DUCKING,
		WALK_LEFT,
		WALK_RIGHT,
		ACTION;
		
		
	};

	
	
	protected Texture animationSheet;
	protected int animationFrameCols;
	protected int animationFrameRows;
	protected Animation animation;
	protected TextureRegion[] animationFrames;
	protected TextureRegion currentFrame;
	protected float stateTime;
	protected float animationTime;
	
	public Entity(Sprite sprite, Texture animationSheet, float animationTime, int frameCols, int frameRows){
		super(sprite);
		this.animationSheet = animationSheet;
		this.animationTime = animationTime;
		this.animationFrameCols = frameCols;
		this.animationFrameRows = frameRows;
		this.animation = initAnimation();
	}
	
	protected Animation initAnimation(){
		TextureRegion[][] tmp = TextureRegion.split(animationSheet, animationSheet.getWidth() / animationFrameCols, animationSheet.getHeight() / animationFrameRows);                                // #10
        animationFrames = new TextureRegion[animationFrameCols * animationFrameRows];
        int index = 0;
        for (int i = 0; i < animationFrameRows; i++) {
                for (int j = 0; j < animationFrameCols; j++) {
                        animationFrames[index++] = tmp[i][j];
                }
        }
           
        stateTime = 0f;
        return new Animation(this.animationTime, animationFrames);
	}
	
	@Override
	public void draw(SpriteBatch batch){
		//get entity coordinates
		float[]  vertz = getVertices();
		
		//update graphics
		stateTime += Gdx.graphics.getDeltaTime();                 
        currentFrame = animation.getKeyFrame(stateTime, true);
		
        switch (currentState) 
        {
			case IDLE_LEFT:{
				super.draw(batch);
				break;
			}
			case IDLE_RIGHT:{
				super.draw(batch);
				break;
			}
			case WALK_LEFT:{
				batch.draw(currentFrame,vertz[0]+getWidth(),vertz[1],0,0,getWidth(),getHeight(),-1.0f,1,0);
				break;
			}
			case WALK_RIGHT:{
				batch.draw(currentFrame,vertz[0],vertz[1],0,0,getWidth(),getHeight(),1.0f,1,0);
				break;
			}
			default:
				super.draw(batch);
				break;
		}	
	}
	
	public void dispose(){
		this.animationSheet.dispose();
	}
}
