package de.uni.bremen.utils;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationDictionary extends HashMap {

	private static final long serialVersionUID = 1L;
	public int width;
	public int height;
	public float animationTime;
	Texture sheet;
	
	public AnimationDictionary(String path, float animationTime, int... numAnimations) {
		sheet = new Texture(path);
		this.animationTime = animationTime;
		
		int framesSum = 0;
		for(int framesCount : numAnimations){
			framesSum += framesCount;
		}
		this.width = sheet.getWidth()/framesSum;
		this.height = sheet.getHeight();
		framesSum = 1;
		for(int i = 0; i < numAnimations.length; i++){
			Animation animation = getAnimation(sheet,framesSum-1, numAnimations[i], animationTime);
			this.put(new Integer(i), animation);
			framesSum += numAnimations[i]-1;
		}
		
		
	}

		
	protected Animation getAnimation(Texture animationSheet,int offset, int animationFrameCols, float animationTime)
	{
		TextureRegion[][] tmp = TextureRegion.split(animationSheet, this.width, this.height ); // rows is always 1                                // #10
		TextureRegion[] animationFrames = new TextureRegion[animationFrameCols];
        int index = 0;//offset;
        
        for (int j = offset; j < offset+animationFrameCols; j++) {
                animationFrames[index++] = tmp[0][j];
        }
        
           

        return new Animation(animationTime, animationFrames);
	}

}
