package info.u250.friends.scenes.main;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.AnimationSprite;
import info.u250.c2d.graphic.AdvanceSpriteImage;
import info.u250.friends.scenes.SceneFriends;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Bird extends Group {
	final AdvanceSpriteImage _bird ;
	final AdvanceSpriteImage _bird_hited ;
	
	final Image _heal ;

	static Bird lastBird = null;
	private boolean _fire = false;
	
	private static Array<Bird> list = new Array<Bird>();
	private static Pool<Bird> pool = new Pool<Bird>() {
		@Override
		protected Bird newObject() {
			return new Bird();
		}
		public Bird obtain() {
			Bird bird = super.obtain();
			list.add(bird);
			bird.reset();
			lastBird = bird;
			return bird;
		};
	};
	static void $free(Bird object){
		pool.free(object);
		list.removeValue(object, false);
		object.remove();
	}
	static Array<Bird> $list(){
		return list;
	}
	static Bird $(){
		return pool.obtain();
	}
	
	public boolean hasFire(){
		return _fire;
	}
	void reset(){
		this._fire = false;
		this._bird.setPosition(-15, 20);
		this._heal.setPosition(0, 0);
		this._bird_hited.setPosition(0, 0);
		this._heal.getColor().a = 1;
		
		this.clear();
		this.addActor(_heal);
		this.addActor(_bird);
	}
	public void beFire(){
		_fire = true;
		_bird.remove();
		this.addActor(_bird_hited);
		_bird_hited.addAction(Actions.sequence(Actions.moveBy(-100, 50,0.3f),Actions.moveBy(200, 200,1,Interpolation.circleIn)));
		_heal.addAction(Actions.moveTo(0, -(this.getY())+20,1,Interpolation.circleIn));
	}
	public void releaseLife(){
		this._fire = false;
		_heal.addAction(Actions.fadeOut(1));
		Gui.getInstance().addLife();
		Engine.getSoundManager().playSound("SoundHeal");
	}
	private Bird(){
		final TextureAtlas atlas = Engine.resource("RES");
		this._bird = new AdvanceSpriteImage(new AnimationSprite(0.02f,new TextureRegion[]{
				atlas.findRegion("bird0001"),
				atlas.findRegion("bird0002"),
				atlas.findRegion("bird0003"),
				atlas.findRegion("bird0004"),
				atlas.findRegion("bird0005"),
				atlas.findRegion("bird0006"),
				atlas.findRegion("bird0007"),
				atlas.findRegion("bird0008"),
				atlas.findRegion("bird0009"),
				atlas.findRegion("bird0010"),
		}));
		this._bird_hited =  new AdvanceSpriteImage(new AnimationSprite(0.01f,new TextureRegion[]{
				atlas.findRegion("bird-hited0001"),
				atlas.findRegion("bird-hited0002"),
				atlas.findRegion("bird-hited0003"),
				atlas.findRegion("bird-hited0004"),
				atlas.findRegion("bird-hited0005"),
				atlas.findRegion("bird-hited0006"),
				atlas.findRegion("bird-hited0007"),
				atlas.findRegion("bird-hited0008"),
				atlas.findRegion("bird-hited0009"),
				atlas.findRegion("bird-hited0010"),
		}));
		this._heal = new Image(atlas.findRegion("life"));
		
		this.setSize(40, 60);
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		if(!_fire)this.setX( this.getX()-( SceneFriends.getInstance().getGroup().getSpeed().x+Values.Bird_Speed)*delta );
		else this.setX(this.getX() - SceneFriends.getInstance().getGroup().getSpeed().x*delta );
	}
}
