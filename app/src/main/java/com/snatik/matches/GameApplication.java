package com.snatik.matches;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

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

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}
}
