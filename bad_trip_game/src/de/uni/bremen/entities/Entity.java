package de.uni.bremen.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;



public class Entity extends Sprite {
	


	
	
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
	
	//============================== OVERRIDES ======================================//
	//
	//	
	//
	//===============================================================================//
	
	@Override
	public void draw(SpriteBatch batch){
		super.draw(batch);
	}
	
	public void dispose(){
		this.animationSheet.dispose();
	}
}
