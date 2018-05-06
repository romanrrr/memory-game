package ru.ryakovlev.jellypairs.events.ui;

import ru.ryakovlev.jellypairs.events.AbstractEvent;
import ru.ryakovlev.jellypairs.events.EventObserver;
import ru.ryakovlev.jellypairs.themes.Theme;

public class ThemeSelectedEvent extends AbstractEvent {

	public static final String TYPE = ThemeSelectedEvent.class.getName();
	public final Theme theme;

	public ThemeSelectedEvent(Theme theme) {
		this.theme = theme;
	}

	@Override
	protected void fire(EventObserver eventObserver) {
		eventObserver.onEvent(this);
	}

	@Override
	public String getType() {
		return TYPE;
	}

}
