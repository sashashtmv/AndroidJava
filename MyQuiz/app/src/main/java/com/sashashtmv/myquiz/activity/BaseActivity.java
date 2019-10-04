package com.sashashtmv.myquiz.activity;

//нужен для определения некоторых свойств, которые будут изменяться на всех  экранах

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.LinearLayout;

import com.sashashtmv.myquiz.R;
import com.sashashtmv.myquiz.utilities.AdsUtilities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

//import android.widget.Toolbar;

public class BaseActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;
    private Toolbar mToolbar;
    private LinearLayout loadingView, noDataView;

    private static String PRODUCT_ID_BOUGHT = "item_1_bought";
    private static String PRODUCT_ID_SUBSCRIBE = "item_1_subscribe";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = BaseActivity.this;
        mContext = mActivity.getApplicationContext();

        boolean purchased = PreferenceManager.getDefaultSharedPreferences(mActivity).getBoolean(PRODUCT_ID_BOUGHT, false);
        boolean subscribed = PreferenceManager.getDefaultSharedPreferences(mActivity).getBoolean(PRODUCT_ID_SUBSCRIBE, false);

        if (purchased || subscribed) disableAds();

        //disableAds();
    }

    //для отключения рекламы во всем приложении
    private void disableAds() {
        AdsUtilities.getInstance(mContext).disableBannerAd();
        AdsUtilities.getInstance(mContext).disableInterstitialAd();
    }

    //определяет и устанавливает тулбар для экрана
    public void initToolbar(boolean isTitleEnabled){
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(isTitleEnabled);
    }
        //устанавливает заголовок окна
    public void setToolbarTitle(String title){
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }

    //добавляет кнопку со стрелкой в тулбаре, ведущую на предыдущий экран
    public void enableUpButton(){
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    //будет инициализировать окно состояния загрузки на экране
    public void initLoader(){
        loadingView = findViewById(R.id.loadingView);
        noDataView = findViewById(R.id.noDataView);
    }

    //отображает прогрессбар и скпывает ошибку загрузки
    public  void  showLoader(){
        if(loadingView != null){
            loadingView.setVisibility(View.VISIBLE);
        }
        if(noDataView != null){
            noDataView.setVisibility(View.GONE);
        }
    }
    //скрывает все элементы окна загрузки
    public  void hideLoader(){
        if(loadingView != null){
            loadingView.setVisibility(View.GONE);
        }
        if(noDataView != null){
            noDataView.setVisibility(View.GONE);
        }
    }
    //скрывает прогресс бар и отображает ошибку загрузки
    public  void  showEmptyView(){
        if(loadingView != null){
            loadingView.setVisibility(View.GONE);
        }
        if(noDataView != null){
            noDataView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
