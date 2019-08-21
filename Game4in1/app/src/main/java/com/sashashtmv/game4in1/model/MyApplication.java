package com.sashashtmv.game4in1.model;

import android.app.Application;

public class MyApplication extends Application {
    private static boolean activityVisible;

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed(){
        activityVisible = true;
    }

    public static  void activityPaused(){
        activityVisible = false;
    }
}
