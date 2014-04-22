package info.u250.friends.scenes.main;



import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.graphic.C2dStage;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Gui extends C2dStage implements Scene {
	
	final Label _points ;
	private int _num_points = 0;
	private InputMultiplexer _input ;
	private Image _btn ;
	private LifeTable _life ;
//	private Image _adControl ;
	
	private static Gui instance ;
	public  static Gui getInstance(){
		if(null == instance){
			instance = new Gui();
		}
		return instance;
	}
	private Gui(){
		
		TextureAtlas atlas = Engine.resource("RES");
		_life = new LifeTable(5);
		_life.setY( this.getHeight()-_life.getHeight()) ;
		
		Table _pointsTable = new Table();
		_points = new Label("00000",new LabelStyle(Engine.resource("Font",BitmapFont.class),Color.RED));
		_pointsTable.add(new Label("point:",new LabelStyle(Engine.resource("Font",BitmapFont.class),Color.BLACK)));
		_pointsTable.add(_points);
		_pointsTable.pack();
		_pointsTable.setPosition(150, _life.getY()- 10 );
		this.addActor(_pointsTable);
		this.addActor(_life);
		
		_btn = new Image(atlas.findRegion("btn"));
		_btn.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				FriendsParallaxLayerDrawable.getInstance().exchange();
			}
		});
		_btn.setOrigin(_btn.getWidth()/2, _btn.getHeight()/2);
		_btn.getColor().a = 0.7f;
		_btn.setScale(1.5f);
		_btn.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(1.5f, 1.5f,0.2f),Actions.scaleTo(1.7f, 1.7f, 0.2f))));
		_btn.setPosition(20, 20);
		this.addActor(_btn);
		this._input = new InputMultiplexer();
		this._input.addProcessor(this);
	}

	public void reset(){
		_num_points = 0;
		_points.setText(_num_points+"");
		_life.reset();
//		this.addActor(_adControl);
	}
	public void addPoints(){
		_num_points++;
		_points.setText(_num_points+"");
	}
	public int getPoints (){
		return _num_points;
	}
	public boolean subLife(){
		return this._life.subLife();
	}
	public void addLife(){
		this._life.addLife();
	}
	@Override
	public void update(float delta) {}

	@Override
	public void render(float delta) {
		this.act(delta);
		this.draw();
	}

	@Override
	public void show() {}

	@Override
	public void hide() {}

	@Override
	public InputProcessor getInputProcessor() {
		return _input;
	}
	
	static class LifeTable extends Table{
		static class Life extends Image{
			public Life(TextureRegion region) {
				super(region);
			}
			boolean die = false;
		}
		Life[] _lifes ;
		
		public LifeTable(int life){
			TextureAtlas atlas = Engine.resource("RES");
			_lifes = new Life[life];
			for(int i=0;i<life;i++){
				_lifes[i] = new Life(atlas.findRegion("life"));
				this.add(_lifes[i]);
			}
			this.pack();
		}
		
		public void reset(){
			for(Life life:_lifes){
				life.clearActions();
				life.getColor().a = 1;
				life.die = false;
			}
		}
		public void addLife(){
			for(int i=0;i<_lifes.length;i++){
				if(_lifes[i].die){
					_lifes[i].die = false;
					_lifes[i].clearActions();
					_lifes[i].getColor().a = 1;
					break;
				}
			}
		}
		/**
		 * return if is die or not .
		 */
		public boolean subLife(){
			for(int i=_lifes.length-1; i>=0;i--){
				if(!_lifes[i].die){
					_lifes[i].die = true;
					_lifes[i].addAction(Actions.sequence(Actions.repeat(3, Actions.sequence(Actions.alpha(0.5f, 0.1f),Actions.alpha(0.9f, 0.1f))),Actions.alpha(0.1f,0.5f)));
					if(i==0){
						return true;
					}else{
						return false;
					}
				}
			}
			return true;
		}
	}
}
