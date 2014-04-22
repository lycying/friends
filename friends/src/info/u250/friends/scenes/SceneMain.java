package info.u250.friends.scenes;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.graphic.C2dStage;
import info.u250.friends.GameFriends;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class SceneMain extends C2dStage implements Scene {
	
@Override
public boolean touchDown(int x, int y, int pointer, int button) {
	Engine.getEventManager().fire(GameFriends.Event_SceneGame);
	return super.touchDown(x, y, pointer, button);
}
final ParticleEmitter pEmitter ;

	public SceneMain(){
		final TextureAtlas atlas = Engine.resource("RES");
		final Image bg  = new Image(atlas.findRegion("bg"));
		final Image tree = new Image(atlas.findRegion("bigtree"));
		final Image tree1 = new Image(atlas.findRegion("tree"));
		final Image tree2 = new Image(atlas.findRegion("tree"));
		final Image title = new Image(atlas.findRegion("title"));
		tree.setX(tree1.getWidth());
		bg.setSize(this.getWidth(), this.getHeight());
		title.setX(this.getWidth()-title.getWidth());
		title.setY(400);
		title.setOrigin(title.getWidth()/2, title.getHeight()/2);
		title.addAction(Actions.forever(Actions.sequence(Actions.delay(2),Actions.scaleTo(0, 1,0.3f),Actions.scaleTo(1, 1,0.3f))));
		this.addActor(bg);
		this.addActor(tree);
		this.addActor(tree1);
		this.addActor(tree2);
		this.addActor(title);
		
		
		final Image start = new Image(atlas.findRegion("start"));
		start.setPosition(this.getWidth()/2-start.getWidth()/2, 200);
		start.addAction(Actions.forever(Actions.sequence(Actions.moveBy(0, 60,1),Actions.moveBy(0, -60,1))));
		this.addActor(start);
		
		//apple
		Image apple = new Image(atlas.findRegion("apple"));
		apple.setPosition(60, tree.getY()+tree.getHeight()-60);
		this.addActor(apple);
		Label appleLabel = new Label("= shoot for bonus points   ",new LabelStyle(Engine.resource("SmallFont",BitmapFont.class),Color.BLACK));
		appleLabel.setPosition(100, apple.getY());
		this.addActor(appleLabel);
		
		final ParticleEffect effect = new ParticleEffect();
		effect.load(Gdx.files.internal("default.p"), Gdx.files.internal(""));
		pEmitter = effect.getEmitters().get(0);
		pEmitter.setPosition(0, 480);
		
	}
	@Override
	public void update(float delta) {}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		this.act(delta);
		this.draw();
		Engine.getSpriteBatch().begin();
		this.pEmitter.draw(Engine.getSpriteBatch(), delta);
		Engine.getSpriteBatch().end();
	}

	@Override
	public void show() {
		((GameFriends)Engine.get()).ad.hide();
	}

	@Override
	public void hide() {}

	@Override
	public InputProcessor getInputProcessor() {
		return this;
	}
	@Override
	public boolean keyDown(int keycode) {
		if (Gdx.app.getType() == ApplicationType.Android) {
			if (keycode == Keys.BACK) {
				System.exit(0);
			}
		} else {
			if (keycode == Keys.DEL) {
				//do nothing
			}
		}
		return super.keyDown(keycode);
	}
}
