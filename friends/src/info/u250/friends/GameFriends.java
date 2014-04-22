package info.u250.friends;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.SceneGroup;
import info.u250.c2d.engine.events.Event;
import info.u250.c2d.engine.events.EventListener;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.graphic.AdControl;
import info.u250.friends.scenes.SceneFriends;
import info.u250.friends.scenes.SceneMain;
import info.u250.friends.scenes.SceneScore;
import info.u250.friends.scenes.main.FriendsParallaxLayerDrawable;
import info.u250.friends.scenes.main.Gui;

public class GameFriends extends Engine {
	public AdControl ad ;
	public GameFriends(AdControl ad){
		this.ad = ad;
	}
	public final static String Event_SceneGame ="_Event_SceneGame";
	public final static String Event_SceneMain ="_Event_SceneMain";
	public final static String Event_SceneScore ="_Event_SceneScore";

	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineDrive() {
			@Override
			public EngineOptions onSetupEngine() {
				final EngineOptions opt = new EngineOptions(new String[]{
						"friends.atlas",
						"sounds",
						"musics",
						"font.fnt",
						"smallfont.fnt"
				}, 800, 480);
				opt.useGL20 = false;
				opt.configFile = "info.u250.friends.conf";
				opt.debug = false;
				return opt;
			}
			
			@Override
			public void onResourcesRegister(AliasResourceManager<String> reg) {
				reg.textureAtlas("RES", "friends.atlas");
				
				reg.font("Font", "font.fnt");
				reg.font("SmallFont", "smallfont.fnt");
				
				reg.music("MusicBg", "musics/bg.mp3");
				
				reg.sound("SoundApple", "sounds/apple.mp3");
				reg.sound("SoundAppleDrop", "sounds/appledrop.mp3");
				reg.sound("SoundBomb", "sounds/bomb.mp3");
				reg.sound("SoundFire", "sounds/fire.mp3");
				reg.sound("SoundsHit", "sounds/hit.mp3");
				reg.sound("SoundHitMe", "sounds/hitme.mp3");
				reg.sound("SoundCow", "sounds/cow.mp3");
				reg.sound("SoundHeal", "sounds/heal.ogg");
				reg.sound("SoundMu", "sounds/mu.mp3");
				reg.sound("SoundExchange", "sounds/exchange.ogg");
				
			}
			private SceneMain  sceneMain ;
			private SceneGroup sceneGame ;
			private SceneScore sceneScore;
			
			
			@Override
			public void onLoadedResourcesCompleted() {
				sceneMain = new SceneMain();
				sceneGame = new SceneGroup();
				sceneGame.add(SceneFriends.getInstance());
				sceneGame.add(Gui.getInstance());
				sceneScore= new SceneScore();
				Engine.setMainScene(sceneMain);
				
				Engine.getEventManager().register(Event_SceneGame, new EventListener() {
					@Override
					public void onEvent(Event event) {
						Gui.getInstance().reset();
						SceneFriends.getInstance().reset();
						FriendsParallaxLayerDrawable.getInstance().reset();
						Engine.setMainScene(sceneGame);
					}
				});
				Engine.getEventManager().register(Event_SceneMain, new EventListener() {
					@Override
					public void onEvent(Event event) {
						Engine.setMainScene(sceneMain);
					}
				});
				Engine.getEventManager().register(Event_SceneScore, new EventListener() {
					@Override
					public void onEvent(Event event) {
						Engine.setMainScene(sceneScore);
					}
				});
				Engine.getMusicManager().playMusic("MusicBg", true);
			}
			
			@Override
			public void dispose() {}
		};
	}

}
