package com.snatik.matches;

import android.app.Application;

import com.snatik.matches.config.Config;
import com.snatik.matches.utils.FontLoader;

public class GameApplication extends Application {

	private Config config;

	public Config getConfig() {
		return config;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		FontLoader.loadFonts(this);

		config = new Config(getApplicationContext());
	}
}
