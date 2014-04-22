package info.u250.friends.scenes.main;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Dot extends Group {
	final Image _dot ;
	
	Vector2 speed = new Vector2();
	
	
	private static Array<Dot> list = new Array<Dot>();
	private static Pool<Dot> pool = new Pool<Dot>() {
		@Override
		protected Dot newObject() {
			return new Dot();
		}
		public Dot obtain() {
			Dot dot = super.obtain();
			list.add(dot);
			return dot;
		};
	};
	static void $free(Dot object){
		pool.free(object);
		list.removeValue(object, false);
		object.remove();
	}
	static Array<Dot> $list(){
		return list;
	}
	static Dot $(){
		return pool.obtain();
	}
	
	private Dot(){
		final TextureAtlas atlas = Engine.resource("RES");
		
		this._dot = new Image(atlas.findRegion("dot"));
		this.addActor(_dot);
		this._dot.setSize(6, 6);
		this.setWidth(this._dot.getWidth());
		this.setHeight(this._dot.getHeight());
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		this.setX(this.getX() +  speed.x*delta);
		this.setY(this.getY() +  speed.y*delta);
	}
}
