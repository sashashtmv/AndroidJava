package com.sashashtmv.myquiz.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.sashashtmv.myquiz.R;

public class AppUtilities {

    private static long backPressed = 0;

    //упростит нам показ оповещений для пользователя
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    //будет отображать тост с предложением нажать кнопку назад еще раз, для выхода
    public static void tapPromToExit(Activity activity) {
        if (backPressed + 2500 > System.currentTimeMillis()) {
            activity.finish();
        } else {
            showToast(activity.getApplicationContext(), activity.getResources().getString(R.string.tap_again));
        }
        backPressed = System.currentTimeMillis();
    }

    //методы для открытия ссылок из панели навигации
    public static void youtubeLink(Activity activity) {
        updateLink(activity, activity.getString(R.string.youtube_url));
    }

    //проверяет наличие приложений для этих соцсетей, установленных на устройстве и в зависимости от этого вызывает разные ссылки
    public static void facebookLink(Activity activity) {
        try {
            ApplicationInfo applicationInfo = activity.getPackageManager().getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                updateLink(activity, "fb://facewebmodal/f?href=" + activity.getString(R.string.facebook_url));
                return;
            }
        } catch (PackageManager.NameNotFoundException ignored) {
            updateLink(activity, activity.getString(R.string.facebook_url));
        }
    }

    public static void twitterLink(Activity activity) {
        try {
            ApplicationInfo applicationInfo = activity.getPackageManager().getApplicationInfo("com.twitter.android", 0);
            if (applicationInfo.enabled) {
                updateLink(activity, activity.getString(R.string.twitter_user_id));
                return;
            }
        } catch (PackageManager.NameNotFoundException ignored) {
            updateLink(activity, activity.getString(R.string.twitter_url));
        }
    }

    public static void googlePlusLink(Activity activity) {
        updateLink(activity, activity.getString(R.string.google_plus_url));
    }

    //создаем интент для открытия ссылки и вызываем его через проверку, где метод resolveActivity() выбирает подходящее активити, а флаг
    //MatchDefaultOnly ограничивает переченю активити только теми, которые поддерживают такой вызов
    private static void updateLink(Activity activity, String text) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(text));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PackageManager packageManager = activity.getPackageManager();
        if (packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            activity.startActivity(intent);
        }
    }

    //метод для возможности делиться ссылкой на приложение
    public  static  void shareApp(Activity activity){
        try{
            final String appPackageName = activity.getPackageName();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, activity.getResources().getString(R.string.share) +
                    " https://play.google.com/store/apps/details?id=" + appPackageName);
            sendIntent.setType("text/plain");
            activity.startActivity(sendIntent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //откроет страницу приложения в маркете, если оно будет там опубликовано, для отзыва и оценки
    public static void rateThisApp(Activity activity){
        try{
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
