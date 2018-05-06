package ru.ryakovlev.jellypairs.events;

import ru.ryakovlev.jellypairs.events.engine.FlipDownCardsEvent;
import ru.ryakovlev.jellypairs.events.engine.GameWonEvent;
import ru.ryakovlev.jellypairs.events.engine.HidePairCardsEvent;
import ru.ryakovlev.jellypairs.events.ui.BackGameEvent;
import ru.ryakovlev.jellypairs.events.ui.DifficultySelectedEvent;
import ru.ryakovlev.jellypairs.events.ui.FlipCardEvent;
import ru.ryakovlev.jellypairs.events.ui.NextGameEvent;
import ru.ryakovlev.jellypairs.events.ui.ResetBackgroundEvent;
import ru.ryakovlev.jellypairs.events.ui.StartEvent;
import ru.ryakovlev.jellypairs.events.ui.ThemeSelectedEvent;


public interface EventObserver {

	void onEvent(FlipCardEvent event);

	void onEvent(DifficultySelectedEvent event);

	void onEvent(HidePairCardsEvent event);

	void onEvent(FlipDownCardsEvent event);

	void onEvent(StartEvent event);

	void onEvent(ThemeSelectedEvent event);

	void onEvent(GameWonEvent event);

	void onEvent(BackGameEvent event);

	void onEvent(NextGameEvent event);

	void onEvent(ResetBackgroundEvent event);

}
