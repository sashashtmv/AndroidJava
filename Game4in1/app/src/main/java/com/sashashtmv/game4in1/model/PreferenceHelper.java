package com.sashashtmv.game4in1.model;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferenceHelper {
    private static PreferenceHelper instance;

    private Context mContext;

    private SharedPreferences mSharedPreferences;

    public PreferenceHelper() {
    }

    public static PreferenceHelper getInstance(){
        if(instance == null){
            instance = new PreferenceHelper();
//            instance.putInt("gold", 0);
        }
        return instance;
    }

    public void init(Context context){
        //Log.i(TAG, "init: context - " + context.getPackageName());
        this.mContext = context;
        this.mSharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }
    // передаем количество золота в файл SharedPreferences
    public void putInt(String key, int value){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }
    public int getInt(String key){
        return mSharedPreferences.getInt(key, 0);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public String getString(String key){
        return mSharedPreferences.getString(key, "");
    }
}
