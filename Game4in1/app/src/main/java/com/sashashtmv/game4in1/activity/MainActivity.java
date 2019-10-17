package com.sashashtmv.game4in1.activity;

import android.app.Fragment;
import android.app.FragmentManager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;

import android.preference.PreferenceManager;
import android.util.Log;
//
//import com.anjlab.android.iab.v3.BillingProcessor;
//import com.anjlab.android.iab.v3.TransactionDetails;
import com.sashashtmv.game4in1.R;
import com.sashashtmv.game4in1.alarm.AlarmHelper;
import com.sashashtmv.game4in1.constants.AppConstants;
import com.sashashtmv.game4in1.database.DBHelper;
import com.sashashtmv.game4in1.fragments.AdvicesFragment;
import com.sashashtmv.game4in1.fragments.AskFreandsFragment;
import com.sashashtmv.game4in1.fragments.CoinsFragment;
import com.sashashtmv.game4in1.fragments.LevelFragment;
import com.sashashtmv.game4in1.fragments.ResultFragment;
import com.sashashtmv.game4in1.fragments.SplashFragment;
import com.sashashtmv.game4in1.fragments.StartFragment;
import com.sashashtmv.game4in1.model.ModelLevel;
import com.sashashtmv.game4in1.model.MyApplication;
import com.sashashtmv.game4in1.model.PreferenceHelper;
import com.sashashtmv.game4in1.utilities.AdsUtilities;
import com.sashashtmv.game4in1.utilities.DialogUtilities;


import java.util.List;

public class MainActivity extends AppCompatActivity implements StartFragment.callback, LevelFragment.callbackResult,
        LevelFragment.callbackCoinsFragment, LevelFragment.callbackAskFreandsFragment, LevelFragment.callbackAdvicesFragment, DialogUtilities.OnCompleteListener {

    private GridLayoutManager lLayout;
    static FragmentManager mFragmentManager;
    StartFragment startFragment;
    List<ModelLevel> mItems;
    private int countCoins;
    private PreferenceHelper mPreferenceHelper;
    public DBHelper mDBHelper;
    public AlarmHelper mAlarmHelper;
    private static final String TAG = "MainActivity";
    private static String PRODUCT_ID_BOUGHT = "item_1_bought";
    private static String PRODUCT_ID_SUBSCRIBE = "item_1_subscribe";

    CheckSubscribe checks;
    //BillingProcessor bp;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        PreferenceHelper.getInstance().init(this);
        mPreferenceHelper = PreferenceHelper.getInstance();
        mPreferenceHelper.putString("From", "");
        AlarmHelper.getInstance().init(getApplicationContext());
        mAlarmHelper = AlarmHelper.getInstance();
        countCoins = mPreferenceHelper.getInt("gold");

        if (countCoins == 0) {
            mPreferenceHelper.putInt("gold", 100);
        }
        Log.i(TAG, "init: context - " + countCoins);
        Log.i(TAG, "init: run - " + "oncreated");
        mFragmentManager = getFragmentManager();

        boolean purchased = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(PRODUCT_ID_BOUGHT, false);
        boolean subscribed = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(PRODUCT_ID_SUBSCRIBE, false);
        if (purchased || subscribed) disableAds();


        runStartFragment();
        runSplash();

        checks = new CheckSubscribe();
        checks.execute();

    }

    //для отключения рекламы во всем приложении
    private void disableAds() {
        AdsUtilities.getInstance(getApplicationContext()).disableBannerAd();
        AdsUtilities.getInstance(getApplicationContext()).disableInterstitialAd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPreferenceHelper.putString("From", "");
        Log.i(TAG, "onDestroy: run - " + "destroed");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getIntent().replaceExtras(new Bundle());
        getIntent().setAction("");
        getIntent().setData(null);
        getIntent().setFlags(0);

        MyApplication.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.activityPaused();
    }

    @Override
    protected void onStop() {
        super.onStop();
        AlarmHelper.getInstance().init(getApplicationContext());
        mAlarmHelper = AlarmHelper.getInstance();
        if (mDBHelper == null) {
            mDBHelper = new DBHelper(this);
        }
        for (ModelLevel level : mDBHelper.query().getLevels()) {
            if (level.getStatus() == ModelLevel.STATUS_AVALABLE) {
                Log.i(TAG, "onDestroy: run " + " - stoped");
                mPreferenceHelper.putString("From", "notifyFrag");
                mAlarmHelper.setAlarm(level);
                break;
            }
        }

    }

    public void runSplash() {
        SplashFragment splashFragment = SplashFragment.newInstance();
        mFragmentManager.beginTransaction()
                .replace(R.id.content_frame, splashFragment, "dd")
                .addToBackStack(null)
                .commit();

    }

    public void runStartFragment() {
        StartFragment startFragment = StartFragment.newInstance();
        //StartFragment startFragment = new StartFragment();
        mFragmentManager.beginTransaction()
                .replace(R.id.content_frame, startFragment, "start fragment")
                .addToBackStack(null)
                .commit();

    }

    public void onCreatLevel(List<ModelLevel> items, ModelLevel item, DBHelper DBHelper) {

        Fragment level = LevelFragment.newInstance();
        mFragmentManager.beginTransaction()
                .replace(R.id.content_frame, level, "level")
//                .disallowAddToBackStack()
                .addToBackStack(null)
                .commit();
        mItems = items;
        mDBHelper = DBHelper;

        if (level != null) {
            ((LevelFragment) level).updateLevels(mItems, item, mDBHelper);
            Log.e("mItems=", mItems.toString() + " item - " + item);

        }

    }

    @Override
    public void onCreatResult(ModelLevel item) {
        Fragment result = ResultFragment.newInstance();
        mFragmentManager.beginTransaction()
                .replace(R.id.content_frame, result, "result")
                .addToBackStack(null)
                .commit();
        //mItems = items;
        if (result != null) {
            ((ResultFragment) result).updateResult(item);
            //((InformationFragment) information).updateOrder(mOrder);
        }
    }

    @Override
    public void onCreatCoinsFragment(PreferenceHelper preferenceHelper) {
        Fragment coinsFragment = CoinsFragment.newInstance(preferenceHelper);
        mFragmentManager.beginTransaction()
                .replace(R.id.content_frame, coinsFragment, "coinsFragment")
                .addToBackStack(null)
                .commit();
        //mItems = items;
        if (coinsFragment != null) {
            //Toast.makeText(this, customer.getTelephoneNumber(), Toast.LENGTH_LONG).show();
            ((CoinsFragment) coinsFragment).updateCoins(countCoins);
            //((InformationFragment) information).updateOrder(mOrder);
        }
    }

    @Override
    public void onCreatAskFreandsFragment() {
        Fragment askFreandsFragment = AskFreandsFragment.newInstance();
        mFragmentManager.beginTransaction()
                .replace(R.id.content_frame, askFreandsFragment, "askFreandsFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCreatAdvicesFragment() {
        Fragment advicesFragment = AdvicesFragment.newInstance();
        mFragmentManager.beginTransaction()
                .replace(R.id.content_frame, advicesFragment, "adviceFragment")
                .addToBackStack(null)
                .commit();
    }

//    @Override
//    public void onProductPurchased(String productId, TransactionDetails details) {
//
//    }
//
//    @Override
//    public void onPurchaseHistoryRestored() {
//
//        if (bp.isSubscribed(SUBSCRIPTION_ID())) {
//            setIsSubscribe(true, mContext);
//            Log.v("TAG", "Subscribe actually restored");
//
//        } else {
//            setIsSubscribe(false, mContext);
//        }
//    }
//
//    private String SUBSCRIPTION_ID(){
//        return getResources().getString(R.string.subscription_id);
//    }
//    //сохраняет значение подписки в настройках приложения
//    public void setIsSubscribe(boolean purchased, Context c){
//        SharedPreferences prefs = PreferenceManager
//                .getDefaultSharedPreferences(c);
//
//        SharedPreferences.Editor editor= prefs.edit();
//
//        editor.putBoolean(AppConstants.PRODUCT_ID_SUBSCRIBE, purchased);
//        editor.apply();
//    }
//
//    @Override
//    public void onBillingError(int errorCode, Throwable error) {
//
//    }
//
//    @Override
//    public void onBillingInitialized() {
//
//    }

    @Override
    public void onComplete(Boolean isOkPressed, String viewIdText) {
        if (isOkPressed) {
            if (viewIdText.equals(AppConstants.BUNDLE_KEY_EXIT_OPTION)) {
                this.finishAffinity();
            }
        }

    }

    //в фоне будет выполнять запрос к серверу для проверки покупок и подписок.
    private class CheckSubscribe extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String license = getResources().getString(R.string.google_play_license);
//            bp = new BillingProcessor(mContext, license, MainActivity.this);
//            bp.loadOwnedPurchasesFromGoogle();
            return null;
        }
    }

}