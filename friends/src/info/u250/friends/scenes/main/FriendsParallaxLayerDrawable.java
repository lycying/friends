package info.u250.friends.scenes.main;

import info.u250.c2d.engine.C2dCamera;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.friends.RandomHelper;
import info.u250.friends.scenes.main.Friends.FriendsType;

import java.util.Iterator;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class FriendsParallaxLayerDrawable extends Group implements ParallaxLayerDrawable {
	private static FriendsParallaxLayerDrawable instance = null;
	public  static FriendsParallaxLayerDrawable getInstance(){
		if(null == instance){
			instance = new FriendsParallaxLayerDrawable();
		}
		return instance;
	}

	Friends _friends ;
	
	public void reset(){
		Iterator<Actor> itr = this.getChildren().iterator();
		while(itr.hasNext()){
			Actor actor = itr.next();
			if(actor instanceof Bird){
				Bird.$free(Bird.class.cast(actor));
			}else if(actor instanceof Cow){
				Cow.$free(Cow.class.cast(actor));
			}else if(actor instanceof Snake){
				Snake.$free(Snake.class.cast(actor));
			}else if(actor instanceof Dot){
				Dot.$free(Dot.class.cast(actor));
			}
		}
		
		this.clear();
		
		Cow _cow = Cow.$();
		_cow.setPosition(this.getWidth()*2, 260);
		
		Bird _bird = Bird.$();
		_bird.setPosition(this.getWidth()*3,260);
		
		Snake _snake = Snake.$();
		_snake.setPosition(this.getWidth(), 20);
		
		_friends = new Friends();
//		_friends.tortoiseTop();
		_friends.rabbitTop();
		
		
		this.addActor(_snake);
		this.addActor(_cow);
		this.addActor(_bird);
		this.addActor(_friends);
	}
	private FriendsParallaxLayerDrawable(){
		this.setSize( Engine.getWidth(), Engine.getHeight());
		Rectangle cullingArea = new Rectangle();
		cullingArea.x = cullingArea.y = 0;
		cullingArea.width = this.getWidth();
		cullingArea.height= this.getHeight();
		
		this.setCullingArea(cullingArea);
	}
	
	

	Rectangle tmp1 = new Rectangle();
	Rectangle tmp2 = new Rectangle();
	@Override
	public void renderLayer(float delta, C2dCamera camera,
			ParallaxLayerResult parallaxLayerResult, ParallaxLayer parallaxLayer) {
		this.setX(camera.position.x - Engine.getWidth()/2);
		// check the cow 
		if(Cow.lastCow.getX() < Engine.getWidth()){
			Cow cow = Cow.$();
			cow.setX(Engine.getWidth() + 260+800*2*RandomHelper.random.nextFloat());
			cow.setY(260+100*RandomHelper.random.nextFloat());
			this.addActor(cow);
		}
		for(Cow cow : Cow.$list()){
			if(cow.getX() <= -cow.getWidth()){
				Cow.$free(cow);
			}else{
				if(_friends.type == FriendsType.TortoiseTop){
					if(!cow.hasFire() && cow.getX() < _friends.getX() + _friends.getWidth() ){
						cow.fire();
					}
				}else{
					if(!cow.hasFire() && cow.getX() < _friends.getX() + _friends.getWidth()/3 ){
						cow.fire();
					}
				}
			}
		}
		//check the bird
		if(Bird.lastBird.getX() < Engine.getWidth()){
			Bird bird = Bird.$();
			bird.setX(Engine.getWidth() + 800+8*800*RandomHelper.random.nextFloat());
			bird.setY( 260+100*RandomHelper.random.nextFloat() );
			this.addActor(bird);
		}
		for(Bird bird : Bird.$list()){
			if(bird.getX() <= -bird.getWidth()){
				Bird.$free(bird);
			}else{
				if(bird.hasFire() && bird.getX() < _friends.getX() + _friends.getWidth()/2 ){
					bird.releaseLife();
				}
			}
		}
		
		//check the snake
		if(Snake.lastSnake.getX() < Engine.getWidth()){
			Snake snake = Snake.$();
			snake.setX( Engine.getWidth() + 150+800*RandomHelper.random.nextFloat() );
			this.addActor(snake);
		}
		for(Snake snake : Snake.$list()){
			if(snake.getX() <= -snake.getWidth()){
				Snake.$free(snake);
			}else{
				if(_friends.type == FriendsType.RabbitTopHit ){
					if(!snake.hasFire()&&snake.getX()<_friends.getX()+_friends.getWidth()/5*4&&snake.getX()>_friends.getX()+_friends.getWidth()/2){
						snake.beFire();
					}
				}else{
					if(!snake.hasFire() && snake.getX() < _friends.getX()  ){
						snake.fire();
					}
				}
				
			}
		}

		//check the dot
		//every dot and every fly object
		for(Dot dot:Dot.$list()){
			if(dot.getX() < 0 || dot.getY() <0 || dot.getX()>this.getWidth() || dot.getY() > this.getHeight()){
				Dot.$free(dot);
				continue;
			}
			tmp1.x = dot.getX();
			tmp1.y = dot.getY();
			tmp1.width = dot.getWidth();
			tmp1.height = dot.getHeight();
			
			
			//for the apple
			for(Fruit fruit:Fruit.$list()){
				if(fruit.hasFire())continue;
				tmp2.x = fruit.getParent().getX() + fruit.getX() ;
				tmp2.y = fruit.getParent().getY() + fruit.getY() ;
				tmp2.width = fruit.getWidth();
				tmp2.height = fruit.getHeight();
				if(tmp2.overlaps(tmp1)){
					fruit.beFire();
				}
			}
			
			//for the cow
			for(Cow cow : Cow.$list()){
				if(cow.hasFire())continue;
				tmp2.x = cow.getX() ;
				tmp2.y = cow.getY() ;
				tmp2.width = cow.getWidth();
				tmp2.height = cow.getHeight();
				if(tmp2.overlaps(tmp1)){
					Dot.$free(dot);
					cow.beFire();
				}
			}
			//now bird
			for(Bird bird : Bird.$list()){
				if(bird.hasFire())continue;
				tmp2.x = bird.getX() ;
				tmp2.y = bird.getY() ;
				tmp2.width = bird.getWidth();
				tmp2.height = bird.getHeight();
				if(tmp2.overlaps(tmp1)){
					Dot.$free(dot);
					bird.beFire();
				}
			}
			
			
		}
		
		Engine.getSpriteBatch().setProjectionMatrix(camera.combined);
		Engine.getSpriteBatch().begin();
		this.act(delta);
		this.draw(Engine.getSpriteBatch(), 1);
		Engine.getSpriteBatch().end();
		Engine.getSpriteBatch().setProjectionMatrix(Engine.getDefaultCamera().combined);

	}

	public void fire(Vector2 pos){
		if(_friends.type == FriendsType.TortoiseTop){
			if(pos.x < _friends.getX()){
				return;
			}
			final Vector2 start = new Vector2(_friends.getX(),_friends.getY()).add(_friends.getWidth(),_friends.getHeight());
			float degree = pos.sub(start).angle();
			Engine.getSoundManager().playSound("SoundFire");
			final Dot dot = Dot.$();
			dot.setX(start.x - 10);
			dot.setY(start.y - 15);
			
			dot.speed.set(Values.Bullet_Speed*MathUtils.cosDeg(degree), Values.Bullet_Speed*MathUtils.sinDeg(degree));
			this.addActor(dot);
		}else if(_friends.type == FriendsType.RabbitTop){
			_friends.fire();
			Engine.getSoundManager().playSound("SoundMu");
		}
	}
	public void exchange(){
		this._friends.exchange();
	}
}
