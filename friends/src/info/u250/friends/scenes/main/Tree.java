package info.u250.friends.scenes.main;

import info.u250.c2d.engine.Engine;
import info.u250.friends.RandomHelper;
import info.u250.friends.scenes.SceneFriends;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Tree extends Group {
	static Tree _lastTree = null;
	private static Array<Tree> list = new Array<Tree>();
	private static Pool<Tree> pool = new Pool<Tree>() {
		@Override
		protected Tree newObject() {
			return new Tree();
		}
		public Tree obtain() {
			Tree tree = super.obtain();
			tree.reset();
			_lastTree = tree;
			list.add(tree);
			return tree;
		};
	};
	static void $free(Tree object){
		list.removeValue(object, false);
		pool.free(object);
		object.remove();
	}
	static Array<Tree> $list(){
		return list;
	}
	public static Tree $(){
		return pool.obtain();
	}
	
	void reset(){
		for(Actor actor : this.getChildren()){
			if(actor instanceof Fruit){
				Fruit.$free(Fruit.class.cast(actor));
			}
		}
		this.clear();
		
		this.addActor(_tree);
		
		float half = _tree.getWidth()/2 ;
		{
			float number = 5;
			float mixDegree = 180*RandomHelper.random.nextFloat();
			
			for(int i=0;i<number;i++){
				if(RandomHelper.random.nextBoolean())continue;
				float degree = 360/number * i + mixDegree;
				final Fruit apple = Fruit.$();
				apple.setX( half + (half-20)*MathUtils.cosDeg(degree) - apple.getWidth()/2 );
				apple.setY( (_tree.getHeight() - half) + (half-20)*MathUtils.sinDeg(degree) - apple.getHeight()/2 );
				this.addActor(apple);
			}
		}
		{
			float number = 4;
			float mixDegree = 180*RandomHelper.random.nextFloat();
			for(int i=0;i<number;i++){
				if(RandomHelper.random.nextBoolean())continue;
				float degree = 360/number * i + mixDegree;
				final Fruit apple = Fruit.$();
				apple.setX( half + (half-42)*MathUtils.cosDeg(degree) - apple.getWidth()/2 );
				apple.setY( (_tree.getHeight() - half) + (half-42)*MathUtils.sinDeg(degree) - apple.getHeight()/2);
				this.addActor(apple);
			}
		}
		{
			float number = 3;
			float mixDegree = 180*RandomHelper.random.nextFloat();
			
			for(int i=0;i<number;i++){
				if(RandomHelper.random.nextBoolean())continue;
				float degree = 360/number * i + mixDegree ;
				final Fruit apple = Fruit.$();
				apple.setX( half + (half-72)*MathUtils.cosDeg(degree) - apple.getWidth()/2);
				apple.setY( (_tree.getHeight() - half) + (half-72)*MathUtils.sinDeg(degree) - apple.getHeight()/2);
				this.addActor(apple);
			}
		}

	}
	
	
	Image  _tree ;	
	private Tree(){
		TextureAtlas atlas = Engine.resource("RES",TextureAtlas.class);
		_tree = new Image(atlas.findRegion("bigtree"));
		this.setSize(_tree.getWidth(), _tree.getHeight());
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		this.setX(this.getX() -  SceneFriends.getInstance().getGroup().getSpeed().x*delta);
	}
	

}
