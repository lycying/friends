package info.u250.friends;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.Preferences;

public class Configure {
	private Configure(){
		pre = Engine.getPreferences();
	}
	private Preferences pre ;
	private static Configure instance;
	public static Configure getInstance(){
		if(null == instance){
			instance = new Configure();
		}
		return instance;
	}
	
	public void setMaxScore(int max){
		pre.putInteger("score", max);
		pre.flush();
	}
	public int getMaxScore(){
		try{
			return pre.getInteger("score");
		}catch (Exception e) {
			return 0;
		}
	}
}
