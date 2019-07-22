package com.sashashtmv.myReminderEveryDay;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {
    private static PreferenceHelper instance;

    private Context mContext;

    private SharedPreferences mSharedPreferences;

    public static final String SPLASH_IS_INVISIBLE = "splash_is_invisible";

    public PreferenceHelper() {
    }

    public static PreferenceHelper getInstance(){
        if(instance == null){
            instance = new PreferenceHelper();
        }
    return instance;
    }

    public void init(Context context){
        this.mContext = context;
        this.mSharedPreferences = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
    }
// передаем состояние секбокса в файл SharedPreferences
    public void putBoolean(String key, boolean value){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    public boolean getBoolean(String key){
        return mSharedPreferences.getBoolean(key, false);
    }
}
