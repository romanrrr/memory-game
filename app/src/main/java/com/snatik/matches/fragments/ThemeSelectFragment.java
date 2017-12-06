package com.snatik.matches.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snatik.matches.GameApplication;
import com.snatik.matches.R;
import com.snatik.matches.common.Memory;
import com.snatik.matches.common.Shared;
import com.snatik.matches.config.Config;
import com.snatik.matches.events.ui.ThemeSelectedEvent;
import com.snatik.matches.themes.Theme;
import com.snatik.matches.utils.FontLoader;
import com.snatik.matches.utils.ItemClickSupport;

import java.util.List;
import java.util.Locale;

public class ThemeSelectFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = LayoutInflater.from(Shared.context).inflate(R.layout.theme_select_fragment, container, false);

		RecyclerView themeList = (RecyclerView) view.findViewById(R.id.themeList);
		final ThemeAdapter themeAdapter = new ThemeAdapter(getActivity(), ((GameApplication)getActivity().getApplication()).getConfig().getThemeList());
		themeList.setAdapter(themeAdapter);
		themeList.setLayoutManager(new GridLayoutManager(getActivity(),3));
		ItemClickSupport.addTo(themeList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
			@Override
			public void onItemClicked(RecyclerView recyclerView, int position, View v) {
				Shared.eventBus.notify(new ThemeSelectedEvent(themeAdapter.getThemeList().get(position)));
			}
		});

		/*View animals = view.findViewById(R.id.theme_animals_container);
		View monsters = view.findViewById(R.id.theme_monsters_container);
		View emoji = view.findViewById(R.id.theme_emoji_container);

		final Theme themeAnimals = Themes.createAnimalsTheme();
		setStars((ImageView) animals.findViewById(R.id.theme_animals), themeAnimals, "animals");
		final Theme themeMonsters = Themes.createMosterTheme();
		setStars((ImageView) monsters.findViewById(R.id.theme_monsters), themeMonsters, "monsters");
		final Theme themeEmoji = Themes.createEmojiTheme();
		setStars((ImageView) emoji.findViewById(R.id.theme_emoji), themeEmoji, "emoji");

		animals.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Shared.eventBus.notify(new ThemeSelectedEvent(themeAnimals));
			}
		});

		monsters.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Shared.eventBus.notify(new ThemeSelectedEvent(themeMonsters));
			}
		});

		emoji.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Shared.eventBus.notify(new ThemeSelectedEvent(themeEmoji));
			}
		});

		animateShow(animals);
		animateShow(monsters);
		animateShow(emoji);*/

		return view;
	}

	private void animateShow(View view) {
		ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1f);
		ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1f);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.setDuration(300);
		animatorSet.playTogether(animatorScaleX, animatorScaleY);
		animatorSet.setInterpolator(new DecelerateInterpolator(2));
		view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		animatorSet.start();
	}

	private void setStars(ImageView imageView, Theme theme, String type) {
		int sum = 0;
		for (int difficulty = 1; difficulty <= 6; difficulty++) {
			sum += Memory.getHighStars(theme.id, difficulty);
		}
		int num = sum / 6;
		if (num != 0) {
			String drawableResourceName = String.format(Locale.US, type + "_theme_star_%d", num);
			int drawableResourceId = Shared.context.getResources().getIdentifier(drawableResourceName, "drawable", Shared.context.getPackageName());
			imageView.setImageResource(drawableResourceId);
		}
	}

	public static class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ViewHolder> {

		private final List<Theme> themeList;
		private Context context;

		public ThemeAdapter(Context context, List<Theme> downloadItemList) {
			this.themeList = downloadItemList;
			this.context = context;
		}

		public List<Theme> getThemeList() {
			return themeList;
		}

		//------------------------------------------------------

		@Override
		public ThemeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			Context context = parent.getContext();
			LayoutInflater inflater = LayoutInflater.from(context);
			return new ViewHolder(inflater.inflate(R.layout.theme_item, parent, false));

		}

		@Override
		public void onBindViewHolder(final ThemeAdapter.ViewHolder holder, int position) {
			Theme theme = themeList.get(position);
			holder.themeLogo.setImageDrawable(Config.createDrawable(Shared.context, theme.themeLogo));
			holder.name.setText(theme.name);

			if(Shared.config.getThemePopup() != null) {
				holder.themeContainer.setBackgroundDrawable(Shared.config.getThemePopup());
			}
		}


		static class ViewHolder extends RecyclerView.ViewHolder {
			ImageView themeLogo;
			RelativeLayout themeContainer;
			TextView name;

			ViewHolder(View view) {
				super(view);
				themeLogo = (ImageView) view.findViewById(R.id.theme_logo);
				themeContainer = (RelativeLayout) view.findViewById(R.id.theme_container);
				name = (TextView) view.findViewById(R.id.name);
				FontLoader.setTypeface(view.getContext(), new TextView[] { name }, FontLoader.Font.GROBOLD);
			}
		}

		@Override
		public int getItemCount() {
			return themeList.size();
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}
}
