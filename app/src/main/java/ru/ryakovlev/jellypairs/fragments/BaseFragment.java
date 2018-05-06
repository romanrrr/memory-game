package ru.ryakovlev.jellypairs.fragments;

import android.support.v4.app.Fragment;

import ru.ryakovlev.jellypairs.events.EventObserver;
import ru.ryakovlev.jellypairs.events.engine.FlipDownCardsEvent;
import ru.ryakovlev.jellypairs.events.engine.GameWonEvent;
import ru.ryakovlev.jellypairs.events.engine.HidePairCardsEvent;
import ru.ryakovlev.jellypairs.events.ui.BackGameEvent;
import ru.ryakovlev.jellypairs.events.ui.FlipCardEvent;
import ru.ryakovlev.jellypairs.events.ui.NextGameEvent;
import ru.ryakovlev.jellypairs.events.ui.ResetBackgroundEvent;
import ru.ryakovlev.jellypairs.events.ui.ThemeSelectedEvent;
import ru.ryakovlev.jellypairs.events.ui.DifficultySelectedEvent;
import ru.ryakovlev.jellypairs.events.ui.StartEvent;

public class BaseFragment extends Fragment implements EventObserver {

	@Override
	public void onEvent(FlipCardEvent event) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void onEvent(DifficultySelectedEvent event) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void onEvent(HidePairCardsEvent event) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void onEvent(FlipDownCardsEvent event) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void onEvent(StartEvent event) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void onEvent(ThemeSelectedEvent event) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void onEvent(GameWonEvent event) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void onEvent(BackGameEvent event) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void onEvent(NextGameEvent event) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void onEvent(ResetBackgroundEvent event) {
		throw new UnsupportedOperationException();
	}

}
