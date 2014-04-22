package info.u250.friends.scenes;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.graphic.AdvanceSprite;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.friends.GameFriends;
import info.u250.friends.scenes.main.FriendsParallaxLayerDrawable;
import info.u250.friends.scenes.main.TreeParallaxLayerDrawable;
import info.u250.friends.scenes.main.Values;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SceneFriends implements Scene {
	private static SceneFriends instance = null ;
	public  static SceneFriends getInstance (){
		if(null == instance){
			instance = new SceneFriends();
		}
		return instance ;
	}
	
	public ParallaxGroup getGroup(){
		return _group;
	}
	public void reset(){
		this._group.getStage().getCamera().position.x = Engine.getWidth()/2;
		this._group.getStage().getCamera().position.y = Engine.getHeight()/2;
	}
	final ParallaxGroup _group ;
	
	private SceneFriends(){
		final TextureAtlas atlas = Engine.resource("RES");
		this._group = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2());
		AdvanceSprite bg = new AdvanceSprite(atlas.findRegion("bg"));
		bg.setSize(bg.getWidth(), 350);
		final ParallaxLayer layerBackground = new ParallaxLayer(this._group, new Image(bg), 
				new Vector2(0f,0), new Vector2(0,1000),
				new Vector2(0,130));
		final ParallaxLayer layerTree = new ParallaxLayer(this._group,
				new Image(atlas.findRegion("tree")),
				new Vector2(0.5f,0),new Vector2(0,1000),
				new Vector2(0,40));
		final ParallaxLayer layerGrass = new ParallaxLayer(this._group,
				new Image(atlas.findRegion("grass")),
				new Vector2(1,0),new Vector2(0,1000),new Vector2());
		final ParallaxLayer layerBigTree = new ParallaxLayer(this._group,
				TreeParallaxLayerDrawable.getInstance(),
				new Vector2(0,0),new Vector2(100000,100000),-1,-1,
				new Vector2(0,0));
		final ParallaxLayer layerFriends = new ParallaxLayer("friends",
				FriendsParallaxLayerDrawable.getInstance(),
				new Vector2(0,0),new Vector2(0,0),-1,-1,
				new Vector2(0,0));
		this._group.add(layerBackground);
		this._group.add(layerTree);
		this._group.add(layerBigTree);
		this._group.add(layerGrass);
		this._group.add(layerFriends);
		
		this._group.setSpeed(Values.NormalSpeed, 0);
		
		
	}

	@Override
	public void update(float delta) {}

//	private float adDelta = 0;
	@Override
	public void render(float delta) {
		this._group.render(delta);
//		if(Platform.type == PlatformType.GooglePlay){
//			this.adDelta+=delta;
//			if(this.adDelta>30){
//				((GameFriends)Engine.get()).ad.hide();
//				adDelta =  0;
//			}else if(this.adDelta>20){
//				((GameFriends)Engine.get()).ad.show();
//			}
//		}
	}

	@Override
	public void show() {
		((GameFriends)Engine.get()).ad.show();
	}

	@Override
	public void hide() {}

	@Override
	public InputProcessor getInputProcessor() {
		return new InputAdapter(){
			@Override
			public boolean touchDown(int x, int y, int pointer, int button) {
				FriendsParallaxLayerDrawable.getInstance().fire(Engine.screenToWorld(x, y));
				return super.touchDown(x, y, pointer, button);
			}
			@Override
			public boolean keyUp(int keycode) {
				if (Gdx.app.getType() == ApplicationType.Android) {
					if (keycode == Keys.BACK) {
						Engine.getEventManager().fire(GameFriends.Event_SceneMain);
					}
				} else {
					if (keycode == Keys.DEL){
						Engine.getEventManager().fire(GameFriends.Event_SceneMain);
					}
				}
				if(Keys.SPACE == keycode){
					FriendsParallaxLayerDrawable.getInstance().exchange();
				}else if(Keys.LEFT == keycode){
					_group.setSpeed(-0, 0);
				}else if(Keys.RIGHT == keycode){
					_group.setSpeed(Values.NormalSpeed, 0);
				}
				return super.keyUp(keycode);
			}
		};
	}
}
