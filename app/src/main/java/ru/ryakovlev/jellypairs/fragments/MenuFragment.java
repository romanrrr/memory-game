package ru.ryakovlev.jellypairs.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import ru.ryakovlev.jellypairs.GameApplication;
import ru.ryakovlev.jellypairs.common.Music;
import ru.ryakovlev.jellypairs.common.Shared;
import ru.ryakovlev.jellypairs.events.ui.StartEvent;
import ru.ryakovlev.jellypairs.ui.PopupManager;
import ru.ryakovlev.jellypairs.utils.Utils;

public class MenuFragment extends Fragment {

	private ImageView mTitle;
	private ImageView mStartGameButton;
	private ImageView offerwallButton;
	private ImageView mStartButtonLights;
	private ImageView mSettingsGameButton;
	private ImageView mGooglePlayGameButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(ru.ryakovlev.jellypairs.R.layout.menu_fragment, container, false);
		mTitle = (ImageView) view.findViewById(ru.ryakovlev.jellypairs.R.id.title);
		mTitle.setImageDrawable(((GameApplication)getActivity().getApplication()).getConfig().getLogo());
		final ImageView aboutButton = (ImageView) view.findViewById(ru.ryakovlev.jellypairs.R.id.about);
		aboutButton.setVisibility( View.GONE);
		offerwallButton = (ImageView) view.findViewById(ru.ryakovlev.jellypairs.R.id.offer_wall);
		offerwallButton.setVisibility( View.GONE);

		mStartGameButton = (ImageView) view.findViewById(ru.ryakovlev.jellypairs.R.id.start_game_button);
		mStartGameButton.setImageDrawable(((GameApplication)getActivity().getApplication()).getConfig().getPlayButton());

		mSettingsGameButton = (ImageView) view.findViewById(ru.ryakovlev.jellypairs.R.id.settings_game_button);
		mSettingsGameButton.setImageDrawable(((GameApplication)getActivity().getApplication()).getConfig().getSettingsButton());

		mSettingsGameButton.setSoundEffectsEnabled(false);
		mSettingsGameButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PopupManager.showPopupSettings();
			}
		});
		mGooglePlayGameButton = (ImageView) view.findViewById(ru.ryakovlev.jellypairs.R.id.google_play_button);
		mGooglePlayGameButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Leaderboards will be available in the next game updates", Toast.LENGTH_LONG).show();
			}
		});
		mStartButtonLights = (ImageView) view.findViewById(ru.ryakovlev.jellypairs.R.id.start_game_button_lights);
		mStartGameButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// animate title from place and navigation buttons from place
				animateAllAssetsOff(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						Shared.eventBus.notify(new StartEvent());
					}
				});
			}
		});

		startLightsAnimation();

		// play background music
		Music.playBackgroundMusic();
		return view;
	}

	protected void animateAllAssetsOff(AnimatorListenerAdapter adapter) {
		// title
		// 120dp + 50dp + buffer(30dp)
		ObjectAnimator titleAnimator = ObjectAnimator.ofFloat(mTitle, "translationY", Utils.px(-200));
		titleAnimator.setInterpolator(new AccelerateInterpolator(2));
		titleAnimator.setDuration(300);

		// lights
		ObjectAnimator lightsAnimatorX = ObjectAnimator.ofFloat(mStartButtonLights, "scaleX", 0f);
		ObjectAnimator lightsAnimatorY = ObjectAnimator.ofFloat(mStartButtonLights, "scaleY", 0f);

		// settings button
		ObjectAnimator settingsAnimator = ObjectAnimator.ofFloat(mSettingsGameButton, "translationY", Utils.px(120));
		settingsAnimator.setInterpolator(new AccelerateInterpolator(2));
		settingsAnimator.setDuration(300);

		// google play button
		ObjectAnimator googlePlayAnimator = ObjectAnimator.ofFloat(mGooglePlayGameButton, "translationY", Utils.px(120));
		googlePlayAnimator.setInterpolator(new AccelerateInterpolator(2));
		googlePlayAnimator.setDuration(300);

		// start button
		ObjectAnimator startButtonAnimator = ObjectAnimator.ofFloat(mStartGameButton, "translationY", Utils.px(130));
		startButtonAnimator.setInterpolator(new AccelerateInterpolator(2));
		startButtonAnimator.setDuration(300);

		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(titleAnimator, lightsAnimatorX, lightsAnimatorY, settingsAnimator, googlePlayAnimator, startButtonAnimator);
		animatorSet.addListener(adapter);
		animatorSet.start();
	}


	private void startLightsAnimation() {
		ObjectAnimator animator = ObjectAnimator.ofFloat(mStartButtonLights, "rotation", 0f, 360f);
		animator.setInterpolator(new AccelerateDecelerateInterpolator());
		animator.setDuration(6000);
		animator.setRepeatCount(ValueAnimator.INFINITE);
		mStartButtonLights.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		animator.start();
	}

}
