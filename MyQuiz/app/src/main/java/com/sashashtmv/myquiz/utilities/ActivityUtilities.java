package com.sashashtmv.myquiz.utilities;

import android.app.Activity;
import android.content.Intent;

import com.sashashtmv.myquiz.constants.AppConstants;

public class ActivityUtilities {

    private static ActivityUtilities sActivityUtilities = null;

    //метод инициализации класса для наших активностей
    public  static ActivityUtilities getInstance(){
        if(sActivityUtilities == null){
            sActivityUtilities = new ActivityUtilities();
        }
        return sActivityUtilities;

    }

    //будет содержать интент для запуска нового экрана, метод принимает контекст, вызываемой активити, а также
    //логический параметр для указания необходимости принудительного закрытия активити, из которого происходит вызов
    public void inwokeNewActivity(Activity activity, Class<?> tClass, boolean shoulgFinish){

        Intent intent = new Intent(activity, tClass);
        activity.startActivity(intent);
        if(shoulgFinish){
            activity.finish();
        }

    }

    //для вызова экрана customUrlActivity
    public void invokeCustomUrlActivity(Activity activity, Class<?> tClass, String pageTitle, String pageUrl, boolean shouldFinish){
        Intent intent = new Intent(activity, tClass);
        intent.putExtra(AppConstants.BUNDLE_KEY_TITLE, pageTitle);
        intent.putExtra(AppConstants.BUNDLE_KEY_URL, pageUrl);
        activity.startActivity(intent);

        if(shouldFinish){
            activity.finish();
        }
    }
}
