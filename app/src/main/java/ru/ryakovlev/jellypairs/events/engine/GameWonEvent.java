package ru.ryakovlev.jellypairs.events.engine;

import ru.ryakovlev.jellypairs.events.AbstractEvent;
import ru.ryakovlev.jellypairs.events.EventObserver;
import ru.ryakovlev.jellypairs.model.GameState;

/**
 * When the 'back to menu' was pressed.
 */
public class GameWonEvent extends AbstractEvent {

	public static final String TYPE = GameWonEvent.class.getName();

	public GameState gameState;

	
	public GameWonEvent(GameState gameState) {
		this.gameState = gameState;
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
