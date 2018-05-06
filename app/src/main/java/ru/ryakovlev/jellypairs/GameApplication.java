package ru.ryakovlev.jellypairs;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import ru.ryakovlev.jellypairs.config.Config;
import ru.ryakovlev.jellypairs.utils.FontLoader;

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
