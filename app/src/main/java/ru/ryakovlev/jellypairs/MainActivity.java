package ru.ryakovlev.jellypairs;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import ru.ryakovlev.jellypairs.common.Shared;
import ru.ryakovlev.jellypairs.engine.Engine;
import ru.ryakovlev.jellypairs.engine.ScreenController;
import ru.ryakovlev.jellypairs.events.EventBus;
import ru.ryakovlev.jellypairs.events.ui.BackGameEvent;
import ru.ryakovlev.jellypairs.ui.PopupManager;

public class MainActivity extends AppCompatActivity {

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
		ScreenController.getInstance().openScreen(ScreenController.Screen.MENU);

		MobileAds.initialize(this, getString(R.string.admobAppId));

		adView = findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
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
			if (ScreenController.getLastScreen() == ScreenController.Screen.GAME) {
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
