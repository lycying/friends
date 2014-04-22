package info.u250.friends.scenes;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.graphic.C2dStage;
import info.u250.friends.Configure;
import info.u250.friends.GameFriends;
import info.u250.friends.scenes.main.Gui;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class SceneScore extends C2dStage implements Scene {
@Override
public boolean touchDown(int x, int y, int pointer, int button) {
	Engine.getEventManager().fire(GameFriends.Event_SceneMain);
	return super.touchDown(x, y, pointer, button);
}

Label scoreLabelX;
Label maxScoreLabelX;
	public SceneScore(){
		final TextureAtlas atlas = Engine.resource("RES");
		final Image gameOver = new Image(atlas.findRegion("gameover"));
		gameOver.setPosition(100, 50);
		this.addActor(gameOver);
		
		final Image gameOver2 = new Image(atlas.findRegion("gameover2"));
		gameOver2.setPosition(170, 200);
		this.addActor(gameOver2);
		
		Label scoreLabel = new Label("Score:   ",new LabelStyle(Engine.resource("Font",BitmapFont.class),Color.BLACK));
		scoreLabel.setPosition(450, 250);
		
		scoreLabelX = new Label( "0000",new LabelStyle(Engine.resource("Font",BitmapFont.class),Color.RED));
		scoreLabelX.setPosition(550, 250);
		this.addActor(scoreLabel);
		this.addActor(scoreLabelX);
		
		Label maxScoreLabel = new Label("Max:   ",new LabelStyle(Engine.resource("Font",BitmapFont.class),Color.BLACK));
		maxScoreLabel.setPosition(450, 200);
		
		maxScoreLabelX = new Label( "0000",new LabelStyle(Engine.resource("Font",BitmapFont.class),Color.RED));
		maxScoreLabelX.setPosition(550, 200);
		this.addActor(maxScoreLabel);
		this.addActor(maxScoreLabelX);
	}
	@Override
	public void update(float delta) {}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		this.act(delta);
		this.draw();
	}

	@Override
	public void show() {
		int max = Configure.getInstance().getMaxScore();
		int current = Gui.getInstance().getPoints();
		if(current>max){
			Configure.getInstance().setMaxScore(current);
		}
		scoreLabelX.setText(""+current);
		maxScoreLabelX.setText(""+max);
		
		((GameFriends)Engine.get()).ad.show();
	}

	@Override
	public void hide() {}

	@Override
	public InputProcessor getInputProcessor() {
		return this;
	}
	@Override
	public boolean keyUp(int keycode) {
		if (Gdx.app.getType() == ApplicationType.Android) {
			if (keycode == Keys.BACK) {
			}
		} 
		return super.keyUp(keycode);
	}
}
