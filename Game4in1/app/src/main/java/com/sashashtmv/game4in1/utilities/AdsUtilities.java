package com.sashashtmv.game4in1.utilities;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.sashashtmv.game4in1.R;

//управляет логикой отображения баннера
public class AdsUtilities {

    //Здесь объявляем статическую приватную переменную текущего класса, переменную класса InterstitialAd для
    // межстраничных объявлений и логические переменные для отключения рекламных баннеров.
    private static AdsUtilities mAdsUtilities;

    private InterstitialAd mInterstitialAd;

    private boolean mDisableBannerAd = false, mDisableInterstitialAd = false;

    //инициализирует библиотечный класс MobileAds, ему передается идентификатор приложения для доступа к рекламным объявлениям.
    // Конструктор вызывается в статическом методе getInstance для создания экземпляра класса AdsUtilities. Такой подход гарантирует
    // создание только одного объекта mAdsUtilities для корректной работы с рекламой.
    private AdsUtilities(Context context) {
        MobileAds.initialize(context, context.getResources().getString(R.string.app_ad_id));
    }

    public static AdsUtilities getInstance(Context context) {
        if (mAdsUtilities == null) {
            mAdsUtilities = new AdsUtilities(context);
        }
        return mAdsUtilities;
    }

    //отображает стандартный баннер. Он проверяет значение переменной mDisableBannerAd и в зависимости от него скрывает баннер или
    // отправляет запрос на загрузку рекламных объявлений. Если запрос выполнен и объявление загружено, то баннер отображается пользователю,
    // в противном случае баннер прячется.
    public void showBannerAd(final AdView mAdView) {
        if (mDisableBannerAd) {
            mAdView.setVisibility(View.GONE);
        } else {
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    mAdView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    super.onAdFailedToLoad(errorCode);
                    mAdView.setVisibility(View.GONE);
                }
            });
        }
    }

    //загружает межстраничный полноэкранный баннер.
    //Здесь проверяется значение переменной mDisableInterstitialAd и создается экран отображения баннера.
    public void loadFullScreenAd(Activity activity) {
        if (!mDisableInterstitialAd) {
            mInterstitialAd = new InterstitialAd(activity);
            mInterstitialAd.setAdUnitId(activity.getResources().getString(R.string.interstitial_ad_unit_id));

            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }
    }

    //отображает окно межстраничного объявления, если не запрещено и если объявление загружено
    public boolean showFullScreenAd() {
        if (!mDisableInterstitialAd) {
            if (mInterstitialAd != null) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    return true;
                }
            }
        }
        return false;
    }

    //Методы disableBannerAd() и disableInterstitialAd() отключают отображение баннеров, управляя значениями соответствующих переменных.
    public void disableBannerAd() {
        this.mDisableBannerAd = true;
    }

    public void disableInterstitialAd() {
        this.mDisableInterstitialAd = true;
    }


}
