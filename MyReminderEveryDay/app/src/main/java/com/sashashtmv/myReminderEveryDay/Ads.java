package com.sashashtmv.myReminderEveryDay;

import android.app.Activity;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Ads {
    public static  void  showBanner(final Activity activity){
        final AdView banner = activity.findViewById(R.id.banner);
        //строит и загружает баннер
        AdRequest adRequest = new AdRequest.Builder().build();
        banner.loadAd(adRequest);

        //слушатель загрузки баннера
        banner.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                setupContentViewPadding(activity, banner.getHeight());
            }
        });
    }

    //при загрузке рекламного баннера подвигает нижний край экрана главного активити на расстояние равное высоте баннера
    public static void setupContentViewPadding(Activity activity, int pedding){
        View view = activity.findViewById(R.id.coordinator);
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), pedding);
    }
}
