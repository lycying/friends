package info.u250.friends;

import info.u250.c2d.graphic.AdControl;
import info.u250.friends.Platform.PlatformType;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.guohead.sdk.GHView;

public class FriendsAndroidActivity extends AndroidApplication {
	RelativeLayout layout;
	GHView adView;
	private final int SHOW_ADS = 1;
    private final int HIDE_ADS = 0;
	protected Handler handler = new Handler()  {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case SHOW_ADS:
                {
                    adView.setVisibility(View.VISIBLE);
                    break;
                }
                case HIDE_ADS:
                {
                    adView.setVisibility(View.GONE);
                    break;
                }
            }
        }
    };
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Platform.type = PlatformType.GooglePlay;
		
		 // Create the layout
        RelativeLayout layout = new RelativeLayout(this);
        
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);  
	
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useGL20 = false;
		config.numSamples = 2;
		View gameView = initializeForView(new GameFriends(new AdControl() {
			@Override
			public void show() {
				handler.sendEmptyMessage(SHOW_ADS );
			}
			
			@Override
			public void hide() {
				handler.sendEmptyMessage( HIDE_ADS);
			}
		}),config);


		adView = new GHView(FriendsAndroidActivity.this);
		adView.setAdUnitId("400a0ca4b532889fc6c4936e2d4e6c7e");
		adView.startLoadAd();
		adView.setMinimumWidth(320);
		adView.setMinimumHeight(50);
		

        // Add the libgdx view
        layout.addView(gameView);

        // Add the AdMob view
        RelativeLayout.LayoutParams adParams = 
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        layout.addView(adView, adParams);

        // Hook it all up
        setContentView(layout);
	}
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		adView.destroy();
	}
   
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			try {
				Gdx.input.getInputProcessor().keyDown(Keys.BACK);
			} catch (Exception ex) {
			}
			return true;
		}
		return false;
	}
}