package ru.ryakovlev.jellypairs.events.ui;

import ru.ryakovlev.jellypairs.events.AbstractEvent;
import ru.ryakovlev.jellypairs.events.EventObserver;

/**
 * When the 'back to menu' was pressed.
 */
public class BackGameEvent extends AbstractEvent {

	public static final String TYPE = BackGameEvent.class.getName();

	@Override
	protected void fire(EventObserver eventObserver) {
		eventObserver.onEvent(this);
	}

	@Override
	public String getType() {
		return TYPE;
	}

}
