package ru.ryakovlev.jellypairs.common;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import ru.ryakovlev.jellypairs.config.Config;
import ru.ryakovlev.jellypairs.engine.Engine;
import ru.ryakovlev.jellypairs.events.EventBus;

public class Shared {

	public static Context context;
	public static Config config;
	public static FragmentActivity activity; // it's fine for this app, but better move to weak reference
	public static Engine engine;
	public static EventBus eventBus;

}
