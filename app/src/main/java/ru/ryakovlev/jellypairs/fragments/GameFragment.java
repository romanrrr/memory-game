package ru.ryakovlev.jellypairs.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import ru.ryakovlev.jellypairs.common.Shared;
import ru.ryakovlev.jellypairs.events.engine.FlipDownCardsEvent;
import ru.ryakovlev.jellypairs.events.engine.GameWonEvent;
import ru.ryakovlev.jellypairs.events.engine.HidePairCardsEvent;
import ru.ryakovlev.jellypairs.model.Game;
import ru.ryakovlev.jellypairs.ui.BoardView;
import ru.ryakovlev.jellypairs.ui.PopupManager;
import ru.ryakovlev.jellypairs.utils.Clock;
import ru.ryakovlev.jellypairs.utils.FontLoader;

public class GameFragment extends BaseFragment {

    private BoardView mBoardView;
    private TextView mTime;
    private ImageView mTimeImage;
    private LinearLayout ads;
    InterstitialAd  mInterstitialAd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(ru.ryakovlev.jellypairs.R.layout.game_fragment, container, false);
        view.setClipChildren(false);
        ((ViewGroup) view.findViewById(ru.ryakovlev.jellypairs.R.id.game_board)).setClipChildren(false);
        mTime = (TextView) view.findViewById(ru.ryakovlev.jellypairs.R.id.time_bar_text);
        mTimeImage = (ImageView) view.findViewById(ru.ryakovlev.jellypairs.R.id.time_bar_image);
        mTimeImage.setImageDrawable(Shared.config.getTimeBar());
        FontLoader.setTypeface(Shared.context, new TextView[]{mTime}, FontLoader.Font.GROBOLD);
        mBoardView = BoardView.fromXml(getActivity().getApplicationContext(), view);
        FrameLayout frameLayout = (FrameLayout) view.findViewById(ru.ryakovlev.jellypairs.R.id.game_container);
        frameLayout.addView(mBoardView);
        frameLayout.setClipChildren(false);

        // build board
        buildBoard();
        Shared.eventBus.listen(FlipDownCardsEvent.TYPE, this);
        Shared.eventBus.listen(HidePairCardsEvent.TYPE, this);
        Shared.eventBus.listen(GameWonEvent.TYPE, this);

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(ru.ryakovlev.jellypairs.R.string.admobInterstitialId));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        return view;
    }

    @Override
    public void onDestroy() {
        Shared.eventBus.unlisten(FlipDownCardsEvent.TYPE, this);
        Shared.eventBus.unlisten(HidePairCardsEvent.TYPE, this);
        Shared.eventBus.unlisten(GameWonEvent.TYPE, this);
        super.onDestroy();
    }

    private void buildBoard() {
        Game game = Shared.engine.getActiveGame();
        int time = game.boardConfiguration.time;
        setTime(time);
        mBoardView.setBoard(game);

        startClock(time);
    }

    private void setTime(int time) {
        int min = time / 60;
        int sec = time - min * 60;
        mTime.setText(" " + String.format("%02d", min) + ":" + String.format("%02d", sec));
    }

    private void startClock(int sec) {
        Clock clock = Clock.getInstance();
        clock.startTimer(sec * 1000, 1000, new Clock.OnTimerCount() {

            @Override
            public void onTick(long millisUntilFinished) {
                setTime((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                setTime(0);
            }
        });
    }

    @Override
    public void onEvent(final GameWonEvent event) {

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                showWonPopup(event);
            }
        });

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
            showWonPopup(event);
        }
    }

    private void showWonPopup(final GameWonEvent event){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTime.setVisibility(View.GONE);
                mTimeImage.setVisibility(View.GONE);
                PopupManager.showPopupWon(event.gameState);
            }
        });
    }

    @Override
    public void onEvent(FlipDownCardsEvent event) {
        mBoardView.flipDownAll();
    }

    @Override
    public void onEvent(HidePairCardsEvent event) {
        mBoardView.hideCards(event.id1, event.id2);
    }

}
