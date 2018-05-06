package ru.ryakovlev.jellypairs.config;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;
import android.util.Log;

import ru.ryakovlev.jellypairs.common.Shared;
import ru.ryakovlev.jellypairs.themes.Theme;
import ru.ryakovlev.jellypairs.themes.Tile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by roma on 27.04.2017.
 */

public class Config {
    public static final String CONFIG_PREFERENCES = "ConfigPref";

    private LruCache<String, Bitmap> mMemoryCache;

    private void manageCache() {
        Log.w("cache", "init");
        mMemoryCache = new LruCache<String, Bitmap>(20 * 1000 * 1000) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    private List<Theme> themeList;

    private Drawable backgroundImage;
    private Drawable logo;
    private Drawable playButton;
    private Drawable backButton;
    private Drawable soundButton;
    private Drawable rateButton;
    private Drawable cancelButton;
    private Drawable settingsButton;
    private Drawable settingsPopup;
    private Drawable themePopup;
    private Bitmap panel;
    private Drawable wonPanel;
    private Drawable timeBar;

    public Config(Context context) {
        manageCache();
        try {
            JSONObject settings = new JSONObject(loadSettings(context));
            backgroundImage = createDrawable(context, settings.getString("backgroundImage"));
            logo = createDrawable(context, settings.getString("logo"));
            playButton = createDrawable(context, settings.getString("playButton"));
            backButton = createDrawable(context, settings.getString("backButton"));
            soundButton = createDrawable(context, settings.getString("soundButton"));
            rateButton = createDrawable(context, settings.getString("rateButton"));
            cancelButton = createDrawable(context, settings.getString("cancelButton"));
            settingsButton = createDrawable(context, settings.getString("settingsButton"));
            settingsPopup = createDrawable(context, settings.getString("settingsPopup"));
            themePopup = createDrawable(context, settings.getString("levelPopup"));
            panel = readBitmap(context, settings.getString("panel"));
            wonPanel = createDrawable(context, settings.getString("wonPanel"));
            timeBar = createDrawable(context, settings.getString("timeBar"));

            themeList = new ArrayList<>();

            JSONArray themesArray = settings.getJSONArray("levels");
            for (int i = 0; i < themesArray.length(); i++) {
                Theme theme = readTheme(context, themesArray.getJSONObject(i));
                if (theme.tileList.size() > 0) {
                    theme.id = i;
                    themeList.add(theme);
                }
            }

        } catch (JSONException e) {
            Log.e("Config", "Json parse error: " + e.getMessage());
        } catch (IOException e) {
            Log.e("Config", "Json read error: " + e.getMessage());
        }
    }

    private Theme readTheme(Context context, JSONObject jsonObject) throws JSONException {
        Theme theme = new Theme();
        theme.name = jsonObject.getString("name");
        theme.backgroundImage = jsonObject.getString("levelBackgroundImage");
        theme.themeLogo = jsonObject.getString("levelLogo");
        theme.tileBack = jsonObject.getString("tileBack");
        theme.tileFront = jsonObject.getString("tileFront");

        List<Tile> tileList = new ArrayList<>();
        JSONArray tilesArray = jsonObject.getJSONArray("tiles");
        for (int i = 0; i < tilesArray.length(); i++) {
            Tile tile = new Tile();
            tile.id = i;
            tile.imageLink = tilesArray.getJSONObject(i).getString("path");
            tileList.add(tile);
        }
        theme.tileList = tileList;
        return theme;
    }

    //------------------------------------------------------

    public Drawable createDrawable(Context context, String link) {
        if (!link.equals("")) {
            try {
                Bitmap b = mMemoryCache.get(link);
                if (b == null) {
                    b = BitmapFactory.decodeStream(context.getAssets().open(link));
                    b.setDensity(Bitmap.DENSITY_NONE);
                    mMemoryCache.put(link, b);
                }
                return new BitmapDrawable(context.getResources(), b);
            } catch (FileNotFoundException e) {
                Log.d("Config", "Image " + link + " not found");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Bitmap readBitmap(Context context, String link) {
        if (!link.equals("")) {
            try {
                Bitmap b = mMemoryCache.get(link);
                if (b == null) {
                    b = BitmapFactory.decodeStream(context.getAssets().open(link));
                    b.setDensity(Bitmap.DENSITY_NONE);
                    mMemoryCache.put(link, b);
                }
                return b;
            } catch (FileNotFoundException e) {
                Log.d("Config", "Image " + link + " not found");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Integer readColor(JSONObject jsonTheme, String name) throws JSONException {
        String color = jsonTheme.getString(name);
        if (color == null || color.equals("")) {
            return null;
        }
        if (!color.startsWith("#")) {
            color = "#" + color;
        }
        return Color.parseColor(color);
    }


    public String loadSettings(Context context) throws IOException {
        String json = null;
        try {
            InputStream is = context.getAssets().open("settings.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    //------------------------------------------------------


    public Drawable getTimeBar() {
        return timeBar != null ? timeBar : Shared.context.getResources().getDrawable(ru.ryakovlev.jellypairs.R.drawable.time_bar);
    }

    public Drawable getPanel() {
        return panel != null ? new BitmapDrawable(Shared.context.getResources(), panel) : Shared.context.getResources().getDrawable(ru.ryakovlev.jellypairs.R.drawable.window_panel_pause);
    }

    public Drawable getWonPanel() {
        return wonPanel != null ? wonPanel : Shared.context.getResources().getDrawable(ru.ryakovlev.jellypairs.R.drawable.won_panel);
    }

    public Drawable getBackButton() {
        return backButton != null ? backButton : Shared.context.getResources().getDrawable(ru.ryakovlev.jellypairs.R.drawable.back);
    }

    public Drawable getSoundButton() {
        return soundButton != null ? soundButton : Shared.context.getResources().getDrawable(ru.ryakovlev.jellypairs.R.drawable.sound);
    }

    public Drawable getRateButton() {
        return rateButton != null ? rateButton : Shared.context.getResources().getDrawable(ru.ryakovlev.jellypairs.R.drawable.rate);
    }

    public Drawable getCancelButton() {
        return cancelButton != null ? cancelButton : Shared.context.getResources().getDrawable(ru.ryakovlev.jellypairs.R.drawable.cancel);
    }


    public Drawable getSettingsButton() {
        return settingsButton != null ? settingsButton : Shared.context.getResources().getDrawable(ru.ryakovlev.jellypairs.R.drawable.settings);

    }

    public Drawable getSettingsPopup() {
        return settingsPopup != null ? settingsPopup : Shared.context.getResources().getDrawable(ru.ryakovlev.jellypairs.R.drawable.settings_popup);
    }

    public Drawable getBackgroundImage() {
        return backgroundImage != null ? backgroundImage : Shared.context.getResources().getDrawable(ru.ryakovlev.jellypairs.R.drawable.background);

    }

    public Drawable getLogo() {
        return logo != null ? logo : Shared.context.getResources().getDrawable(ru.ryakovlev.jellypairs.R.drawable.logo);

    }

    public Drawable getPlayButton() {
        return playButton != null ? playButton : Shared.context.getResources().getDrawable(ru.ryakovlev.jellypairs.R.drawable.play);

    }

    public List<Theme> getThemeList() {
        return themeList;
    }

    public Drawable getThemePopup() {
        return themePopup != null ? themePopup : Shared.context.getResources().getDrawable(ru.ryakovlev.jellypairs.R.drawable.window_panel_popup);
    }
}
