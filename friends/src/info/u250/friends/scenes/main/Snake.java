package info.u250.friends.scenes.main;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.AnimationSprite;
import info.u250.c2d.graphic.AdvanceSpriteImage;
import info.u250.friends.scenes.SceneFriends;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Snake extends Group{
	AdvanceSpriteImage _snake ;
	
	static Snake lastSnake = null;
	private boolean _fire = false;
	
	private static Array<Snake> list = new Array<Snake>();
	private static Pool<Snake> pool = new Pool<Snake>() {
		@Override
		protected Snake newObject() {
			return new Snake();
		}
		public Snake obtain() {
			Snake snake = super.obtain();
			list.add(snake);
			snake.reset();
			lastSnake = snake;
			return snake;
		};
	};
	static void $free(Snake object){
		pool.free(object);
		list.removeValue(object, false);
		object.remove();
	}
	static Array<Snake> $list(){
		return list;
	}
	static Snake $(){
		return pool.obtain();
	}
	
	private void reset(){
		this._fire = false;
		this.setRotation(0);
		this.setY(30);
	}
	
	boolean hasFire(){
		return this._fire;
	}
	
	void beFire(){
		this._fire = true;
		this.addAction(Actions.moveBy(100, -100,0.5f));
	}
	void fire(){
		this._fire = true;
		Engine.getSoundManager().playSound("SoundHitMe");
		this.addAction(Actions.sequence(Actions.scaleTo(1.2f, 1,0.1f),Actions.scaleTo(1, 1,0.1f)));
		FriendsParallaxLayerDrawable.getInstance()._friends.beFire();
	}
	private Snake() {
		final TextureAtlas atlas = Engine.resource("RES");
		this._snake = new AdvanceSpriteImage(new AnimationSprite(0.05f, new TextureRegion[]{
				atlas.findRegion("snake0001"),
				atlas.findRegion("snake0002"),
				atlas.findRegion("snake0003"),
				atlas.findRegion("snake0004"),
				atlas.findRegion("snake0005"),
				atlas.findRegion("snake0006"),
				atlas.findRegion("snake0007"),
				atlas.findRegion("snake0008"),
				atlas.findRegion("snake0009"),
				atlas.findRegion("snake0010"),
				atlas.findRegion("snake0011"),
				atlas.findRegion("snake0012"),
				atlas.findRegion("snake0013"),
				atlas.findRegion("snake0014"),
				atlas.findRegion("snake0015"),
				atlas.findRegion("snake0016"),
				atlas.findRegion("snake0017"),
				atlas.findRegion("snake0018"),
				atlas.findRegion("snake0019"),
				atlas.findRegion("snake0020"),
				atlas.findRegion("snake0021"),
				atlas.findRegion("snake0022"),
				atlas.findRegion("snake0023"),
				atlas.findRegion("snake0024"),
				atlas.findRegion("snake0025"),
				atlas.findRegion("snake0026"),
				atlas.findRegion("snake0027"),
				atlas.findRegion("snake0028"),
				atlas.findRegion("snake0029"),
				atlas.findRegion("snake0030"),
		}));
		this.setSize(_snake.getWidth(), _snake.getHeight());
		this.addActor(_snake);
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		this.setX(this.getX()- ( SceneFriends.getInstance().getGroup().getSpeed().x+Values.Snake_Speed)*delta);
	}
}
