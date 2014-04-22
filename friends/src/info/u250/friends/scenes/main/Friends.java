package info.u250.friends.scenes.main;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.AnimationSprite;
import info.u250.c2d.graphic.AnimationSprite.AnimationSpriteListener;
import info.u250.c2d.graphic.AdvanceSpriteImage;
import info.u250.friends.GameFriends;
import info.u250.friends.scenes.SceneFriends;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Friends extends Group {
	public enum FriendsType{
		TortoiseTop,
		RabbitTop,
		RabbitTopHit,
	}
	
	FriendsType type ;
	final private AdvanceSpriteImage _tortoise_top ;
	final private AdvanceSpriteImage _rabbit_top ;
	final private AdvanceSpriteImage _rabbit_top_hit ;
	final private Image _exchange ;
	
	public Friends() {
		final TextureAtlas atlas = Engine.resource("RES");
		
		this._rabbit_top = new AdvanceSpriteImage(new AnimationSprite(0.05f,new TextureRegion[]{
				atlas.findRegion("rabbit-top0001"),
				atlas.findRegion("rabbit-top0002"),
				atlas.findRegion("rabbit-top0003"),
				atlas.findRegion("rabbit-top0004"),
				atlas.findRegion("rabbit-top0005"),
				atlas.findRegion("rabbit-top0006"),
				atlas.findRegion("rabbit-top0007"),
				atlas.findRegion("rabbit-top0008"),
				atlas.findRegion("rabbit-top0009"),
				atlas.findRegion("rabbit-top0010"),
				atlas.findRegion("rabbit-top0011"),
				atlas.findRegion("rabbit-top0012"),
				atlas.findRegion("rabbit-top0013"),
				atlas.findRegion("rabbit-top0014"),
				atlas.findRegion("rabbit-top0015"),
				atlas.findRegion("rabbit-top0016"),
				atlas.findRegion("rabbit-top0017"),
		}));
		final AnimationSprite _rabbit_top_hit_animation = new AnimationSprite(0.01f,new TextureRegion[]{
				atlas.findRegion("rabbit-top-hit0001"),
				atlas.findRegion("rabbit-top-hit0002"),
				atlas.findRegion("rabbit-top-hit0003"),
				atlas.findRegion("rabbit-top-hit0004"),
				atlas.findRegion("rabbit-top-hit0005"),
				atlas.findRegion("rabbit-top-hit0006"),
				atlas.findRegion("rabbit-top-hit0007"),
				atlas.findRegion("rabbit-top-hit0008"),
				atlas.findRegion("rabbit-top-hit0009"),
				atlas.findRegion("rabbit-top-hit0010"),
				atlas.findRegion("rabbit-top-hit0011"),
				atlas.findRegion("rabbit-top-hit0012"),
				atlas.findRegion("rabbit-top-hit0013"),
				atlas.findRegion("rabbit-top-hit0014"),
				atlas.findRegion("rabbit-top-hit0015"),
				atlas.findRegion("rabbit-top-hit0016"),
				atlas.findRegion("rabbit-top-hit0017"),
		});
//		_rabbit_top_hit_animation.setLoopTimes(2);
//		_rabbit_top_hit_animation.stop();
		_rabbit_top_hit_animation.setAnimationSpriteListener(new AnimationSpriteListener() {
			@Override
			public void onLastFrame() {
				rabbitTop();
			}
		});
		this._rabbit_top_hit = new AdvanceSpriteImage(_rabbit_top_hit_animation);

		this._tortoise_top = new AdvanceSpriteImage(new AnimationSprite(0.04f,new TextureRegion[]{
				atlas.findRegion("tortoise-top0001"),
				atlas.findRegion("tortoise-top0002"),
				atlas.findRegion("tortoise-top0003"),
				atlas.findRegion("tortoise-top0004"),
				atlas.findRegion("tortoise-top0005"),
				atlas.findRegion("tortoise-top0006"),
				atlas.findRegion("tortoise-top0007"),
				atlas.findRegion("tortoise-top0008"),
				atlas.findRegion("tortoise-top0009"),
				atlas.findRegion("tortoise-top0010"),
				atlas.findRegion("tortoise-top0011"),
				atlas.findRegion("tortoise-top0012"),
				atlas.findRegion("tortoise-top0013"),
				atlas.findRegion("tortoise-top0014"),
				atlas.findRegion("tortoise-top0015"),
				atlas.findRegion("tortoise-top0016"),
				atlas.findRegion("tortoise-top0017"),
				atlas.findRegion("tortoise-top0018"),
				atlas.findRegion("tortoise-top0019"),
		}));
		_exchange = new Image(atlas.findRegion("exchage"));
		
		setX( Values.FriendsInitX );
		setY( Values.FriendsInitY );
	}

	public void rabbitTop(){
		SceneFriends.getInstance().getGroup().setSpeed(Values.NormalSpeed, 0);
		this.type = FriendsType.RabbitTop;
		this.clear();
		this.addActor(_rabbit_top);
		this.setSize(_rabbit_top.getWidth(), _rabbit_top.getHeight());
		this.setOrigin(0, 0);
	}
	public void tortoiseTop(){
		SceneFriends.getInstance().getGroup().setSpeed(Values.FasterSpeed, 0);
		this.type = FriendsType.TortoiseTop;
		this.clear();
		this.addActor(_tortoise_top);
		this.setSize(_tortoise_top.getWidth(), _tortoise_top.getHeight());
		this.setOrigin(0, 0);
	}
	public void fire(){
		this.type = FriendsType.RabbitTopHit;
		this.clear();
		this.addActor(_rabbit_top_hit);
		((AnimationSprite)this._rabbit_top_hit.getSprite()).replay();
		this.setSize(_rabbit_top_hit.getWidth(), _rabbit_top_hit.getHeight());
		this.setOrigin(0, 0);
	}
	public void beFire(){
		Action action = null;
		int r = 1;
		if(this.type == FriendsType.TortoiseTop){
			r = 2;
		}else{
			r = 1;
		}
		action = Actions.sequence(Actions.moveBy(0, 10*r,0.2f,Interpolation.circleOut),Actions.moveBy(0, -10*r,0.2f,Interpolation.circleIn),Actions.run(new Runnable() {
			@Override
			public void run() {
				setPosition(Values.FriendsInitX, Values.FriendsInitY);
				setRotation(0);
				boolean die = Gui.getInstance().subLife();
				if(die){
					Engine.getEventManager().fire(GameFriends.Event_SceneScore);
				}
			}
		}));
		this.addAction(action);
	}
	public void exchange(){
		this.clear();
		Engine.getSoundManager().playSound("SoundExchange");
		this._exchange.getColor().a = 1;
		this._exchange.setScaleX( 0.6f );
		this._exchange.setScaleY( 0 );
		this._exchange.setOriginX( this._exchange.getWidth()/2 );
		final InputProcessor ipr = Gdx.input.getInputProcessor();
		this._exchange.addAction(Actions.sequence(Actions.scaleTo(0.6f, 0.6f,0.2f),Actions.run(new Runnable() {
			@Override
			public void run() {
				if(type == FriendsType.RabbitTop || type == FriendsType.RabbitTopHit){
					tortoiseTop();
				}else{
					rabbitTop();
				}
				Gdx.input.setInputProcessor(ipr);
			}
		})));
		this.addActor(this._exchange);
		Gdx.input.setInputProcessor(null);
	}
}
