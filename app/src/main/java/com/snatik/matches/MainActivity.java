package com.snatik.matches;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.appsgeyser.sdk.AppsgeyserSDK;
import com.appsgeyser.sdk.ads.AdView;
import com.snatik.matches.common.Shared;
import com.snatik.matches.engine.Engine;
import com.snatik.matches.engine.ScreenController;
import com.snatik.matches.engine.ScreenController.Screen;
import com.snatik.matches.events.EventBus;
import com.snatik.matches.events.ui.BackGameEvent;
import com.snatik.matches.ui.PopupManager;
import com.snatik.matches.utils.Utils;

public class MainActivity extends FragmentActivity {

	private ImageView mBackgroundImage;
	private AdView adView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Shared.context = getApplicationContext();
		Shared.config = ((GameApplication)getApplication()).getConfig();
		Shared.engine = Engine.getInstance();
		Shared.eventBus = EventBus.getInstance();

		setContentView(R.layout.activity_main);
		mBackgroundImage = (ImageView) findViewById(R.id.background_image);

		Shared.activity = this;
		Shared.engine.start();
		Shared.engine.setBackgroundImageView(mBackgroundImage);
		Shared.engine.setDefaultBackground(((GameApplication)getApplication()).getConfig().getBackgroundImage());

		// set background
		setBackgroundImage();

		// set menu
		ScreenController.getInstance().openScreen(Screen.MENU);

		AppsgeyserSDK.takeOff(this,
				getString(R.string.widgetID),
				getString(R.string.app_metrica_on_start_event),
				getString(R.string.template_version));
		adView = findViewById(R.id.adView);
	}

	@Override
	protected void onPause() {
		super.onPause();
		AppsgeyserSDK.onPause(this);
		if (adView != null) {
			adView.onPause();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		AppsgeyserSDK.onResume(this);
		if (adView != null) {
			adView.onResume();//into onResume()
		}
	}

	@Override
	protected void onDestroy() {
		Shared.engine.stop();
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		if (PopupManager.isShown()) {
			PopupManager.closePopup();
			if (ScreenController.getLastScreen() == Screen.GAME) {
				Shared.eventBus.notify(new BackGameEvent());
			}
		} else if (ScreenController.getInstance().onBack()) {
			super.onBackPressed();
		}
	}

	private void setBackgroundImage() {
		/*Bitmap bitmap = Utils.scaleDown(, Utils.screenWidth(), Utils.screenHeight());
		bitmap = Utils.crop(bitmap, Utils.screenHeight(), Utils.screenWidth());
		bitmap = Utils.downscaleBitmap(bitmap, 2);*/
		mBackgroundImage.setImageDrawable(((GameApplication)getApplication()).getConfig().getBackgroundImage());
	}

}
