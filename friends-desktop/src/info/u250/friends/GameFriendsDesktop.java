package info.u250.friends;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.AdControl;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class GameFriendsDesktop {

	public static void main(String[] args) {
		final GameFriends game = new GameFriends(new AdControl() {
			
			@Override
			public void show() {
				
			}
			
			@Override
			public void hide() {
				
			}
		});
		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) Engine.getWidth();
		config.height= (int) Engine.getHeight();
		config.useGL20 = Engine.useGL20();
		new LwjglApplication(game, config);
	}

}
