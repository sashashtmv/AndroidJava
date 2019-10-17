package com.sashashtmv.game4in1.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.sashashtmv.game4in1.R;
import com.sashashtmv.game4in1.fragments.LevelFragment;
import com.sashashtmv.game4in1.fragments.SettingsFragment;
import com.sashashtmv.game4in1.utilities.AdsUtilities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.app.Fragment;


public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "tag";
    private Context mContext;
    private Activity mActivity;
    private Toolbar mToolbar;
    private LinearLayout loadingView, noDataView;

//    private static String PRODUCT_ID_BOUGHT = "item_1_bought";
//    private static String PRODUCT_ID_SUBSCRIBE = "item_1_subscribe";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = SettingsActivity.this;
        mContext = mActivity.getApplicationContext();

//        boolean purchased = PreferenceManager.getDefaultSharedPreferences(mActivity).getBoolean(PRODUCT_ID_BOUGHT, false);
//        boolean subscribed = PreferenceManager.getDefaultSharedPreferences(mActivity).getBoolean(PRODUCT_ID_SUBSCRIBE, false);

//        if (purchased || subscribed) disableAds();
        initView();

        //disableAds();
    }

//    //для отключения рекламы во всем приложении
//    private void disableAds() {
//        AdsUtilities.getInstance(mContext).disableBannerAd();
//        AdsUtilities.getInstance(mContext).disableInterstitialAd();
//    }

    //определяет и устанавливает тулбар для экрана
    public void initToolbar(boolean isTitleEnabled){
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(isTitleEnabled);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

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

    private void initView() {
        setContentView(R.layout.activity_settings);

        // replace linear layout by preference screen
        getFragmentManager().beginTransaction().replace(R.id.content, new SettingsFragment()).commit();

        initToolbar(true);
        setToolbarTitle(getString(R.string.bay_coins));

       // enableUpButton();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                onBackPressed();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
