package com.sashashtmv.myquiz.utilities;

import android.app.Activity;
import android.content.Intent;

import com.sashashtmv.myquiz.constants.AppConstants;
import com.sashashtmv.myquiz.models.quiz.ResultModel;

import java.util.ArrayList;

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
    public void invokeNewActivity(Activity activity, Class<?> tClass, boolean shouldFinish){
        Intent intent = new Intent(activity, tClass);
        activity.startActivity(intent);

        if(shouldFinish){
            activity.finish();
        }
    }

    //для вызова экрана customUrlActivity
    public void invokeCustomUrlActivity(Activity activity, Class<?> tClass, String pageTitle, String pageUrl, boolean shouldFinish){
        Intent intent = new Intent(activity, tClass);
        intent.putExtra(AppConstants.BUNDLE_KEY_TITLE, pageTitle);
        if(!pageUrl.contains("http")){
            pageUrl = "http://www." + pageUrl;
        }
        intent.putExtra(AppConstants.BUNDLE_KEY_URL, pageUrl);
        activity.startActivity(intent);

        if(shouldFinish){
            activity.finish();
        }
    }

    public void invokeCommonQuizActivity(Activity activity, Class<?> tClass, String categoryId, boolean shouldFinish){
        Intent intent = new Intent(activity, tClass);
        intent.putExtra(AppConstants.BUNDLE_KEY_INDEX, categoryId);
        activity.startActivity(intent);

        if(shouldFinish){
            activity.finish();
        }
    }


    //для вызова экрана результатов
    public void invokeScoreCardActivity(Activity activity, Class<?> tClass, int questionsCount, int score, int wrongAns, int skip, String categoryId, ArrayList<ResultModel> resultList, boolean shouldFinish) {
        Intent intent = new Intent(activity, tClass);
        intent.putExtra(AppConstants.BUNDLE_KEY_SCORE, score);
        intent.putExtra(AppConstants.QUESTIONS_IN_TEST, questionsCount);
        intent.putExtra(AppConstants.BUNDLE_KEY_WRONG_ANS, wrongAns);
        intent.putExtra(AppConstants.BUNDLE_KEY_SKIP, skip);
        intent.putExtra(AppConstants.BUNDLE_KEY_INDEX, categoryId);
        intent.putParcelableArrayListExtra(AppConstants.BUNDLE_KEY_ITEM, resultList);
        activity.startActivity(intent);
        if (shouldFinish) {
            activity.finish();
        }
    }
}
