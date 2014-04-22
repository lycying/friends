package info.u250.friends;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

public class TexturePackFriends {

	public static void main(String[] args) {
		TexturePacker2.Settings settings = new TexturePacker2.Settings();
		TexturePacker2.process(settings, 
				"raw", 
				"../friends-android/assets","friends");
	}

}
