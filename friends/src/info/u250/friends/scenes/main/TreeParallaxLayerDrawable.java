package info.u250.friends.scenes.main;

import info.u250.c2d.engine.C2dCamera;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.c2d.graphic.parallax.ParallaxLayer.ParallaxLayerDrawable;
import info.u250.c2d.graphic.parallax.ParallaxLayer.ParallaxLayerResult;

import java.util.Random;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;

public class TreeParallaxLayerDrawable extends Group implements ParallaxLayerDrawable {
	private static TreeParallaxLayerDrawable instance = null;
	public  static TreeParallaxLayerDrawable getInstance(){
		if(null == instance){
			instance = new TreeParallaxLayerDrawable();
		}
		return instance;
	}
	Rectangle cullingArea = new Rectangle();
	Random _random = new Random();
	private TreeParallaxLayerDrawable(){
		this.setSize(Engine.getWidth(), Engine.getHeight());
		
		this.cullingArea.x = this.cullingArea.y = 0;
		this.cullingArea.width = this.getWidth();
		this.cullingArea.height= this.getHeight();
		
		Tree item =  Tree.$();
		item.setX( Engine.getWidth() );
		
		this.addActor(item);
	}
	
	@Override
	public float obtainWidth() {
		return Engine.getWidth();
	}

	@Override
	public float obtainHeight() {
		return Engine.getHeight();
	}

	@Override
	public void renderLayer(float delta, C2dCamera camera,
			ParallaxLayerResult parallaxLayerResult, ParallaxLayer parallaxLayer) {
		
		this.setX( camera.position.x - Engine.getWidth()/2);	
		
		if(Tree._lastTree.getX() <  this.getWidth()){
			Tree item = Tree.$();
			item.setX(this.getWidth() + 300+300* _random.nextFloat());
			item.setY( 40 -_random.nextFloat()*150 );
			
			this.addActor(item);
		}
		
		
		
		Engine.getSpriteBatch().setProjectionMatrix(camera.combined);
		Engine.getSpriteBatch().begin();
		this.act(delta);
		this.draw(Engine.getSpriteBatch(), 1);
		Engine.getSpriteBatch().end();
		Engine.getSpriteBatch().setProjectionMatrix(Engine.getDefaultCamera().combined);
		
	}

}
