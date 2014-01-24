package de.uni.bremen.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;



public class Entity extends Sprite {
	
	protected Body body;
	
	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
		body.setUserData(this);
	}


	protected BodyDef bdef;
	
	protected FixtureDef fdef;
	
	
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
	
	
	public void update()
	{
		
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
