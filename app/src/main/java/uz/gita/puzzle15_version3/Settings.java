package uz.gita.puzzle15_version3;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

public class Settings {
    private static Settings settings;
    private static SharedPreferences preferences;

    // private Constructor
    private Settings() { }

    public static Settings getInstance(Context context){
        if(settings == null){
            settings = new Settings();
            preferences = context.getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);

        }
        return settings;
    }

    public void saveCount(int count) {
        preferences.edit().putInt("PREVIOUS", count).apply();

        if(preferences.getInt("BEST", 0) == 0 || preferences.getInt("BEST", 0) > count){
            preferences.edit().putInt("BEST", count).apply();
        }
    }

    public void changeSoundState(boolean bool){
        preferences.edit().putBoolean("SOUND", bool).apply();
    }

    public void clearCache() {
        preferences.edit().putInt("PREVIOUS", 0).apply();
        preferences.edit().putInt("BEST", 0).apply();
    }

    public static boolean getSoundState(){
        return preferences.getBoolean("SOUND", true);
    }

    public String getPrevRecordInString(){
        return "" + preferences.getInt("PREVIOUS", 0);
    }

    public String getBestRecordInString(){
        return "" + preferences.getInt("BEST", 0);
    }

}
