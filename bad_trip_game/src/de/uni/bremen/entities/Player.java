package de.uni.bremen.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import de.uni.bremen.physics.WorldPhysics;
import de.uni.bremen.utils.AnimationDictionary;
import de.uni.bremen.utils.HealthStates;


public class Player extends Character implements InputProcessor, Disposable{

	//============================== CONSTANTS ======================================//
	//
	//	
	//
	//===============================================================================//
	
	
	protected final int normalSpeed=WorldPhysics.PLAYER_MAXSPEED;
	protected final int normalJumpHeight = WorldPhysics.PLAYER_MAXJUMP_HEIGHT;
	protected float oldy,oldw, oldh;
	
	public Sound jumpSound = Gdx.audio.newSound(Gdx.files.internal("audio/Jump-SoundBible.com-1007297584.mp3"));
	public Sound hitSound = Gdx.audio.newSound(Gdx.files.internal("audio/Jab-SoundBible.com-1806727891.mp3"));
	
	public ArrayList<Item> items;
	public ArrayList<Character> enemies;
	
	public long score=0;
	
	public float drugTime;
	
	public HealthStates currentHealthState = HealthStates.CLEAN;
	
	
	public boolean win;
	
	//============================== CONSTRUCTOR ======================================//
	//
	//	
	//
	//===============================================================================//
	
	public Player( Vector2 position,AnimationDictionary animationDict,  float animationTime, 
			float width, float height, TiledMapTileLayer collisionLayer)
	{
		
		super(position, animationDict, animationTime, width, height, collisionLayer, WorldPhysics.PLAYER_MAX_SPEED);
		currentHealth=100;
		message="";
	} 
	
	
	//============================== TIMERS ======================================//
	//
	//	
	//
	//===============================================================================//	
	
	
			
	float delay = 1; // seconds
	Timer drugTimer = Timer.instance();
	public long drugTimerActivationTime;
	
	
	public synchronized void activateDrugTimer(float activationTime){
		drugTime = 100.0f;
		
		drugTimerActivationTime = TimeUtils.millis();
		
		drugTimer.clear();
		this.drugTimer.scheduleTask(new Task(){
		    @Override
		    public void run() {
		    	currentHealthState = HealthStates.CLEAN;
		    	resetStatus();
		    }
		}, activationTime);
	}


	

	//============================== UPDATE ================================//
	//
	//	ALL UPDATE
	//
	//===============================================================================//

	@Override
	public void update(float deltaTime){
		
		if(message.length()>0)
		{
			messageScale+=0.001;
		}
		
		if(drugTime>0 && currentHealthState != HealthStates.CLEAN)
		{
			long deltaT=   (int)TimeUtils.millis() - (int)drugTimerActivationTime ;
			drugTime = 100 - deltaT / (WorldPhysics.DRUG_TIME_ACTIVATION_DURATION*1000) * 100;
			//drugTime = 100 - deltaT;
		}
		
		for (Item item : items) {
			if(item.isDead)continue;
			if(hit(item.postion.x,item.postion.y,item.width,item.height))
			{
				if(item instanceof Goal)
				{
					win=true;
					System.out.println("Goal reached");
				}
				if(item instanceof Fruit)
				{
					score+=25;
					message="yummy fruits!";
					message();
					currentHealth+=1;
				}
				
				if(item instanceof Drug)
				{
					score-=100;
					Drug d = (Drug)item;
					currentHealth-=5;
					resetStatus();
					switch (d.current) {
					case MUSHROOM:
						currentHealthState = HealthStates.ON_MUSHRROM;
						message="MushroOoOom!";
						message();
						break;
					case CANNABIS:
						currentHealthState = HealthStates.ON_WEED;
						message="Dopehead";
						message();
						oldw = width;
						oldh= height;
						width /= WorldPhysics.PLAYER_SCALE_MULTIPLIER;
						height /= WorldPhysics.PLAYER_SCALE_MULTIPLIER;
						break;
					case XTC:
						message="Partytime";
						message();
						currentHealthState = HealthStates.ON_XTC;
						maxSpeed = normalSpeed;
						maxSpeed *= WorldPhysics.PLAYER_SPEED_MULTIPLIER;
						
						maxJumpHeight *= WorldPhysics.PLAYER_JUMP_MULTIPLIER;
						break;
					default:
						break;
					}
					item.respawn();
					activateDrugTimer(WorldPhysics.DRUG_TIME_ACTIVATION_DURATION);
					
				}
				item.isDead = true;
			}
			

		}
		for (Character character : enemies) {
			if(character.isDead)continue;
			if(hit(character.postion.x, character.postion.y, character.width ,character.height))
			{
				if(character instanceof Enemy)
				{
					message="OUCH! -20";
					message();
					currentHealth-=20;
					score-=20;
					character.isDead=true;
					
					final long id = hitSound.loop();
					Timer.schedule(new Task(){
					   @Override
					   public void run(){
						   hitSound.stop(id);
					      }
					   }, 0.8f);
				}
				if(character instanceof Dealer)
				{
					Dealer d = (Dealer)character;
					d.talk();
					
					
				}
			}
		}
		if(currentHealth<=0)isDead=true;
		
		
		//if(score<0)score=0; disable negative values?
		
		
		
		super.update(deltaTime);
	}
	
	/**
	 * box test
	 * @return
	 */
	private boolean hit(float x1, float y1, float w1, float h1)
	{
		if(x1 > postion.x+ width || x1+w1 < postion.x)return false;
		if(y1 > postion.y+ height || y1+h1 < postion.y)return false;
		
		return true;
	}
	
	

	//============================== INPUT PROCESSOR ================================//
	//
	//	ALL FUNCTIONS RELATED TO USER INPUT
	//
	//===============================================================================//
	
	private boolean rightDown=false;
	private boolean leftDown=false;
	
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode)
		{
			case Keys.LEFT:
				velocity.x = -maxSpeed;
				currentState = States.WALK;
				leftDown = true;
				isOrientationLeft = true;
				break;
			case Keys.RIGHT:
				rightDown = true;
				velocity.x = maxSpeed;
				currentState = States.WALK;
				isOrientationLeft = false;
				break;
			case Keys.UP:
			case Keys.SPACE:
				if(canJump){
					velocity.y = maxJumpHeight + Math.abs(velocity.x) * maxSpeed;
					canJump = false;
					currentState = States.JUMP;
					
					final long id = jumpSound.play();
					Timer.schedule(new Task(){
					   @Override
					   public void run(){
						   jumpSound.stop(id);
					      }
					   }, 0.5f);
				}
				break;
			case Keys.R: // reset;
				resetStatus();
				win=false;
				//reset position separatly:
				postion = new Vector2(initPostion.x, initPostion.y);
				break;
			default:
				break;
		}
		return true;
	}

	
	void resetStatus()
	{
		width=oldw>width?oldw:width;
		height=oldh>height?oldh:height;
		maxSpeed=normalSpeed;
		maxJumpHeight = normalJumpHeight;
		currentHealthState= HealthStates.CLEAN;
		score -= 50;
		//
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.LEFT:
			velocity.x = rightDown?maxSpeed:0;
			currentState = rightDown?States.WALK:States.IDLE;
			leftDown=false;
			break;
		case Keys.RIGHT:
			velocity.x = leftDown?-maxSpeed:0;
			currentState = leftDown?States.WALK:States.IDLE;
			rightDown=false;
			break;
		case Keys.UP:
			break;
		default:
			break;
		}
		
		return true;
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	



	public int getCurrentHealth() {
		return currentHealth;
	}
	
	
	public void dispose(){
		
		jumpSound.dispose();
		hitSound.dispose();
	}
}
