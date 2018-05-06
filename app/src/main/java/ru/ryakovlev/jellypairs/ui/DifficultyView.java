package ru.ryakovlev.jellypairs.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ru.ryakovlev.jellypairs.common.Shared;
import ru.ryakovlev.jellypairs.utils.FontLoader;

public class DifficultyView extends LinearLayout {

	private TextView name;
	private RelativeLayout mTitle;
	private ImageView star1;
	private ImageView star2;
	private ImageView star3;

	public DifficultyView(Context context) {
		this(context, null);
	}
	
	public DifficultyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(ru.ryakovlev.jellypairs.R.layout.difficult_view, this, true);
		setOrientation(LinearLayout.VERTICAL);
		name = (TextView) findViewById(ru.ryakovlev.jellypairs.R.id.name);
		mTitle = (RelativeLayout) findViewById(ru.ryakovlev.jellypairs.R.id.title);
		star1 = (ImageView) findViewById(ru.ryakovlev.jellypairs.R.id.star1);
		star2 = (ImageView) findViewById(ru.ryakovlev.jellypairs.R.id.star2);
		star3 = (ImageView) findViewById(ru.ryakovlev.jellypairs.R.id.star3);

	}
	
	public void setDifficulty(int difficulty, int stars) {
		mTitle.setBackgroundDrawable(Shared.config.getPanel());
		switch (difficulty){
			case 1:
				this.name.setText("BEGINNER");
				break;
			case 2:
				this.name.setText("EASY");
				break;
			case 3:
				this.name.setText("MEDIUM");
				break;
			case 4:
				this.name.setText("HARD");
				break;
			case 5:
				this.name.setText("HARDEST");
				break;
			case 6:
				this.name.setText("MASTER");
				break;
		}
		FontLoader.setTypeface(getContext(), new TextView[] { this.name }, FontLoader.Font.GROBOLD);
		star1.setImageResource(ru.ryakovlev.jellypairs.R.drawable.tile_back_star);
		star2.setImageResource(ru.ryakovlev.jellypairs.R.drawable.tile_back_star);
		star3.setImageResource(ru.ryakovlev.jellypairs.R.drawable.tile_back_star);
		if(stars >= 1){
			star1.setImageResource(ru.ryakovlev.jellypairs.R.drawable.level_complete_star);
		}
		if(stars >= 2){
			star2.setImageResource(ru.ryakovlev.jellypairs.R.drawable.level_complete_star);
		}
		if(stars >= 3){
			star3.setImageResource(ru.ryakovlev.jellypairs.R.drawable.level_complete_star);
		}
	}
	
}
