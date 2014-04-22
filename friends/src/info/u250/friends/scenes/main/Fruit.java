package info.u250.friends.scenes.main;

import info.u250.c2d.engine.Engine;
import info.u250.friends.RandomHelper;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Fruit extends Group {
	final Image _fruit ;
	final Image _fruit2 ;
	private boolean _fire = false;
	private static Array<Fruit> list = new Array<Fruit>();
	private static Pool<Fruit> pool = new Pool<Fruit>() {
		@Override
		protected Fruit newObject() {
			return new Fruit();
		}
		public Fruit obtain() {
			Fruit fruit = super.obtain();
			list.add(fruit);
			fruit.reset();
			return fruit;
		};
	};
	static void $free(Fruit object){
		pool.free(object);
		list.removeValue(object, false);
		object.remove();
	}
	static Array<Fruit> $list(){
		return list;
	}
	static Fruit $(){
		return pool.obtain();
	}
	void reset(){
		_fire = false;
		this.setPosition(0, 0);
		this.getColor().a = 1;
		this.clear();
		if(RandomHelper.random.nextBoolean()
				&&RandomHelper.random.nextBoolean()
				&&RandomHelper.random.nextBoolean()){
			this.addActor(_fruit2);
		}else{
			this.addActor(_fruit);
		}
	}
	
	boolean hasFire(){
		return this._fire;
	}
	
	void beFire(){
		Gui.getInstance().addPoints();

		Engine.getSoundManager().playSound("SoundApple");
		this._fire = true;
		this.addAction(Actions.sequence(
				Actions.moveBy(0, -(this.getParent().getY()+this.getY())+35,0.8f,Interpolation.circleIn),
				Actions.run(new Runnable() {
					@Override
					public void run() {
						Engine.getSoundManager().playSound("SoundAppleDrop");
					}
				}),
				Actions.moveBy(0, 30,0.3f,Interpolation.circleOut),
				Actions.moveBy(0, -30,0.2f,Interpolation.circleIn),
				Actions.moveBy(0, 10,0.2f,Interpolation.circleOut),
				Actions.moveBy(0, -10,0.1f,Interpolation.circleIn),
				Actions.fadeOut(0.3f)
				));
	}
	private Fruit(){
		final TextureAtlas atlas = Engine.resource("RES");
		
		this._fruit = new Image(atlas.findRegion("apple"));
		this._fruit2 = new Image(atlas.findRegion("apple2"));
		this.addActor(_fruit);
		this.setSize(this._fruit.getWidth()	, this._fruit.getHeight());
	}
	@Override
	public void act(float delta) {
		super.act(delta);
	}
}
