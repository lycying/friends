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

public class Cow extends Group {
	final AdvanceSpriteImage _cow ;
	final AdvanceSpriteImage _cow_hited ;
	final Image _bomb ;
	final Image _explose;
	private boolean _fire = false;
	private boolean _expose = false;
	static Cow lastCow = null;
	private float _y_axis_delta = 0;
	
	private static Array<Cow> list = new Array<Cow>();
	private static Pool<Cow> pool = new Pool<Cow>() {
		@Override
		protected Cow newObject() {
			return new Cow();
		}
		public Cow obtain() {
			Cow cow = super.obtain();
			list.add(cow);
			lastCow = cow;
			cow.reset();
			return cow;
		};
	};
	static void $free(Cow object){
		pool.free(object);
		list.removeValue(object, false);
		object.remove();
	}
	static Array<Cow> $list(){
		return list;
	}
	static Cow $(){
		return pool.obtain();
	}
	
	void reset(){
		this._fire = false;
		this._cow.setX( -10 );
		this._cow.setY( 20  );
		this._bomb.setX(0);
		this._bomb.setY(0);
		this._cow_hited.setX( 0 );
		this._cow_hited.setY( 0 );
		this._y_axis_delta = 0;
		this.clear();
		this.addActor(_bomb);
		this.addActor(_cow);
	}
	public boolean hasFire(){
		return _fire;
	}
	public void fire(){
		_fire = true;
		_expose = true;
		_bomb.addAction(Actions.sequence(Actions.moveTo(0, -(this.getY()-40),0.8f,Interpolation.circleIn),Actions.run(new Runnable() {
			@Override
			public void run() {
				_bomb.remove();
				addActor(_explose);
				_explose.setX(_bomb.getX());
				_explose.setY(_bomb.getY() - 10) ;
				_explose.setScaleX( 0 );
				_explose.setScaleY( 0.5f );
				FriendsParallaxLayerDrawable.getInstance()._friends.beFire();
				Engine.getSoundManager().playSound("SoundBomb");
				_explose.addAction(Actions.sequence(Actions.scaleTo(1, 1,0.1f),Actions.run(new Runnable() {
					@Override
					public void run() {
						_explose.remove();
						_expose = false;
					}
				})));
			}
		})));
		_bomb.addAction(Actions.moveTo(0, -(this.getY()-40),0.8f,Interpolation.circleIn));
	}
	public void beFire(){
		Engine.getSoundManager().playSound("SoundCow");
		_fire = true;
		_cow.remove();
		this.addActor(_cow_hited);
		_cow_hited.addAction(Actions.sequence(Actions.moveBy(-100, 50,0.3f),Actions.moveBy(200, 200,1,Interpolation.circleIn)));
		_bomb.addAction(Actions.moveTo(0, -(this.getY()+150),1,Interpolation.circleIn));
	}
	@Override
	public void act(float delta) {
		if(!_expose)this.setX(this.getX() - ( SceneFriends.getInstance().getGroup().getSpeed().x+Values.Cow_Speed)*delta);
		else this.setX(this.getX()-  SceneFriends.getInstance().getGroup().getSpeed().x*delta);
		if(_y_axis_delta<-2f){
			_y_axis_delta = 0f;
		}else if(_y_axis_delta>2f){
			_y_axis_delta = -2f;
		} 
		if(_y_axis_delta<2f){
			this.setY(this.getY() + (_y_axis_delta>0?1:-1)*30*delta);
		}
		_y_axis_delta += delta;
		
		super.act(delta);
	}
	//width is bomb with = 40;
	//height is 20+40
	private Cow(){
		final TextureAtlas atlas = Engine.resource("RES");
		this._cow = new AdvanceSpriteImage(new AnimationSprite(0.02f,new TextureRegion[]{
				atlas.findRegion("cow0001"),
				atlas.findRegion("cow0002"),
				atlas.findRegion("cow0003"),
				atlas.findRegion("cow0004"),
				atlas.findRegion("cow0005"),
				atlas.findRegion("cow0006"),
				atlas.findRegion("cow0007"),
				atlas.findRegion("cow0008"),
				atlas.findRegion("cow0009"),
				atlas.findRegion("cow0010"),
		}));
		this._cow_hited = new AdvanceSpriteImage(new AnimationSprite(0.01f,new TextureRegion[]{
				atlas.findRegion("cow-hited0001"),
				atlas.findRegion("cow-hited0002"),
				atlas.findRegion("cow-hited0003"),
				atlas.findRegion("cow-hited0004"),
				atlas.findRegion("cow-hited0005"),
				atlas.findRegion("cow-hited0006"),
				atlas.findRegion("cow-hited0007"),
				atlas.findRegion("cow-hited0008"),
				atlas.findRegion("cow-hited0009"),
				atlas.findRegion("cow-hited0010"),
		}));
		this._bomb = new Image(atlas.findRegion("bomb"));
		this._explose = new Image(atlas.findRegion("explose"));
		this._explose.setOrigin(_explose.getWidth()/2, _explose.getHeight()/2);
		this.setSize(40, 60);
		
	}
	
}
