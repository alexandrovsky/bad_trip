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
		
		for(int i = 0; i < numAnimations.length; i++){
			this.put(new Integer(i), getAnimation(sheet, numAnimations[i], animationTime));
		}
		
		
	}

		
	protected Animation getAnimation(Texture animationSheet, int animationFrameCols, float animationTime)
	{
		TextureRegion[][] tmp = TextureRegion.split(animationSheet, this.width, this.height ); // rows is always 1                                // #10
		TextureRegion[] animationFrames = new TextureRegion[animationFrameCols];
        int index = 0;
        
        for (int j = 0; j < animationFrameCols; j++) {
                animationFrames[index++] = tmp[0][j];
        }
        
           

        return new Animation(animationTime, animationFrames);
	}

}
