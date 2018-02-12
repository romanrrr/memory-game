package com.snatik.matches.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.snatik.matches.common.Shared;
import com.snatik.matches.config.Config;
import com.snatik.matches.themes.Theme;
import com.snatik.matches.themes.Tile;
import com.snatik.matches.utils.Utils;

import java.util.Map;

/**
 * Before game starts, engine build new board
 * 
 * @author sromku
 */
public class BoardArrangment {

	// like {0-mosters_20, 1-mosters_12, 2-mosters_20, ...}
	public Map<Integer, Tile> tileMap;
	/**
	 * 
	 * @param id
	 *            The id is the number between 0 and number of possible tiles of
	 *            this theme
	 * @return The Bitmap of the tile
	 */
	public Bitmap getTileBitmap(int id, int size) {
		Bitmap bitmap = ((BitmapDrawable) Shared.config.createDrawable(Shared.context, tileMap.get(id).imageLink)).getBitmap();
		return Utils.crop(bitmap, size, size);
	}

	public boolean isPair(int id1, int id2) {
		return tileMap.get(id1).id == tileMap.get(id2).id;
	}

}
