package com.sashashtmv.myquiz.activity;

//import android.support.v7.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.gms.ads.AdView;
import com.google.android.material.snackbar.Snackbar;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.sashashtmv.myquiz.R;
import com.sashashtmv.myquiz.adapters.CategoryAdapter;
import com.sashashtmv.myquiz.constants.AppConstants;
import com.sashashtmv.myquiz.data.sqlite.NotificationDbController;
import com.sashashtmv.myquiz.listeners.ListItemClickListener;
import com.sashashtmv.myquiz.models.notification.NotificationModel;
import com.sashashtmv.myquiz.models.quiz.CategoryModel;
import com.sashashtmv.myquiz.utilities.ActivityUtilities;
import com.sashashtmv.myquiz.utilities.AdsUtilities;
import com.sashashtmv.myquiz.utilities.AppUtilities;
import com.sashashtmv.myquiz.utilities.DialogUtilities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import android.support.v7.widget.Toolbar;

public class MainActivity extends BaseActivity implements DialogUtilities.OnCompleteListener, BillingProcessor.IBillingHandler{
    private Toolbar mToolbar;
    private Activity mActivity;
    private Context mContext;
    private RelativeLayout mNotificationView;

    private CategoryAdapter mAdapter = null;
    private RecyclerView mRecyclerView;

    CheckSubscribe checks;
    BillingProcessor bp;
    //переменные элементов панели навигации
    private AccountHeader mAccountHeader = null;
    private Drawer mDrawer = null;

    private ArrayList<CategoryModel> mCategoryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        mActivity = MainActivity.this;
        mContext = getApplicationContext();
        mCategoryList = new ArrayList<>();

        mNotificationView = (RelativeLayout) findViewById(R.id.notificationView);
        mRecyclerView = (RecyclerView)findViewById(R.id.rvContentScore);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2, GridLayoutManager.VERTICAL, false));
        mAdapter = new CategoryAdapter(mContext, mActivity, mCategoryList);
        mRecyclerView.setAdapter(mAdapter);

        initLoader();
        loadData();
        initListener();

        //создаем профайл пользователя
        final IProfile iProfile = new ProfileDrawerItem().withIcon(R.drawable.ic_dev);
        //его методы добавляют привязку к активити, устанавливают прозрачность статус бара,
        //добавляют фоновую картинку и слушатель клика  по профилю пользователя
        mAccountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.header)
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                    @Override
                    public boolean onProfileImageClick(@NotNull View view, @NotNull IProfile<?> iProfile, boolean b) {
                        ActivityUtilities.getInstance().invokeCustomUrlActivity(mActivity, CustomUrlActivity.class,
                                getResources().getString(R.string.site), getResources().getString(R.string.site_url),false);
                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(@NotNull View view, @NotNull IProfile<?> iProfile, boolean b) {
                        return false;
                    }
                })
                .addProfiles(iProfile)
                .build();

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withHasStableIds(true)
                .withAccountHeader(mAccountHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("О приложении").withIcon(R.drawable.ic_dev).withIdentifier(10).withSelectable(false),

                        new SecondaryDrawerItem().withName("YouTube").withIcon(R.drawable.ic_youtube).withIdentifier(20).withSelectable(false),
                        new SecondaryDrawerItem().withName("Facebook").withIcon(R.drawable.ic_facebook).withIdentifier(21).withSelectable(false),
                        new SecondaryDrawerItem().withName("Twitter").withIcon(R.drawable.ic_twitter).withIdentifier(22).withSelectable(false),
                        new SecondaryDrawerItem().withName("Google+").withIcon(R.drawable.ic_google_plus).withIdentifier(23).withSelectable(false),

                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Настройки").withIcon(R.drawable.ic_settings).withIdentifier(30).withSelectable(false),
                        new SecondaryDrawerItem().withName("Оцените приложение").withIcon(R.drawable.ic_rating).withIdentifier(31).withSelectable(false),
                        new SecondaryDrawerItem().withName("Поделитесь").withIcon(R.drawable.ic_share).withIdentifier(32).withSelectable(false),
                        new SecondaryDrawerItem().withName("Соглашения").withIcon(R.drawable.ic_privacy_policy).withIdentifier(33).withSelectable(false),

                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Выход").withIcon(R.drawable.ic_exit).withIdentifier(40).withSelectable(false)

                        )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(@Nullable View view, int i, @NotNull IDrawerItem<?> iDrawerItem) {
                        if(iDrawerItem != null){
                            Intent intent = null;
                            if(iDrawerItem.getIdentifier() == 10){
                                ActivityUtilities.getInstance().invokeNewActivity(mActivity, AboutDevActivity.class, false);
                            }else if(iDrawerItem.getIdentifier() == 20){
                                AppUtilities.youtubeLink(mActivity);
                            }
                            else if(iDrawerItem.getIdentifier() == 21){
                                AppUtilities.facebookLink(mActivity);
                            }else if(iDrawerItem.getIdentifier() == 22){
                                AppUtilities.twitterLink(mActivity);
                            }else if(iDrawerItem.getIdentifier() == 23){
                                AppUtilities.googlePlusLink(mActivity);
                            }else if(iDrawerItem.getIdentifier() == 30){
                                ActivityUtilities.getInstance().invokeNewActivity(mActivity, SettingsActivity.class, false);
                            }else if(iDrawerItem.getIdentifier() == 31){
                                AppUtilities.rateThisApp(mActivity);
                            }else if(iDrawerItem.getIdentifier() == 32){
                                AppUtilities.shareApp(mActivity);
                            }else if(iDrawerItem.getIdentifier() == 33){
                                ActivityUtilities.getInstance().invokeCustomUrlActivity(mActivity, CustomUrlActivity.class,
                                        getResources().getString(R.string.privacy), getResources().getString(R.string.privacy_url), false);
                            }else if(iDrawerItem.getIdentifier() == 40){

                            }
                        }
                        return false;
                    }
                })
                //сохраняет состояние дровера
                //регулирует возможность принудительного открытия панели навигации при первом запуске приложения
                // определяет отображение дровера при свайпе, и метод билд - завершает создание дровера
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(false)
                .withShowDrawerUntilDraggedOpened(false)
                .build();

        checks = new CheckSubscribe();
        checks.execute();
    }

    //переопределим реакцию приложения на нажатие кнопки назад. Она будет закрывать панель навигации, если та открыта,
    // а в противном случае показывать тост и закрывать приложение при двойном нажатии
    @Override
    public void onBackPressed() {
        if(mDrawer != null && mDrawer.isDrawerOpen()){
            mDrawer.closeDrawer();
        }else {
            AppUtilities.tapPromToExit(this);
        }
    }

    //отображает процесс загрузки и вызывает метод лоадджейсон
    private void loadData(){
        showLoader();
        loadJson();

        // show banner ads
        AdsUtilities.getInstance(mContext).showBannerAd((AdView) findViewById(R.id.adsView));
    }

    private void loadJson(){
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = null;
        try{
            bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open(AppConstants.CONTENT_FILE)));
            String temp;
            while ((temp = bufferedReader.readLine()) != null){
                stringBuffer.append(temp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                bufferedReader.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        parseJson(stringBuffer.toString());
    }

    private void parseJson(String jsonData){
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.JSON_KEY_ITEMS);

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);

                String categoryId = object.getString(AppConstants.JSON_KEY_CATEGORY_ID);
                String categoryName = object.getString(AppConstants.JSON_KEY_CATEGORY_NAME);

                mCategoryList.add(new CategoryModel(categoryId, categoryName));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        hideLoader();
        //информируем адаптер о том, что набор данных изменился
        mAdapter.notifyDataSetChanged();
    }

    private void initListener(){
        //notification view click listener
        mNotificationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtilities.getInstance().invokeNewActivity(mActivity, NotificationListActivity.class, false);
            }
        });
        // recycler list item click listener
        mAdapter.setListItemClickListener(new ListItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {

                //show snackbar and return if not purchased

                boolean purchased = PreferenceManager.getDefaultSharedPreferences(mActivity).getBoolean(AppConstants.PRODUCT_ID_BOUGHT, false);
                boolean subscribed = PreferenceManager.getDefaultSharedPreferences(mActivity).getBoolean(AppConstants.PRODUCT_ID_SUBSCRIBE, false);

                if (position > 3 && !purchased && !subscribed) {
                    // Toast.makeText(mActivity, R.string.alert_for_purchase , Toast.LENGTH_SHORT).show();
                    Snackbar.make(view, R.string.alert_for_purchase, Snackbar.LENGTH_LONG)
                            .setAction("Ок", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ActivityUtilities.getInstance().invokeNewActivity(mActivity, SettingsActivity.class, true);
                                }
                            }).setDuration(4000).show();
                    return;
                }

                CategoryModel model = mCategoryList.get(position);
                ActivityUtilities.getInstance().invokeCommonQuizActivity(mActivity, QuizPromptActivity.class, model.getCategoryId(), true);
            }
        });
    }

    // received new broadcast
    private BroadcastReceiver newNotificationReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            initNotification();
        }
    };

    //считывает из базы новые, не прочитанные уведомления и управляет отображением счетчика непрочитанных уведомлений
    public void initNotification() {
        NotificationDbController notificationDbController = new NotificationDbController(mContext);
        TextView notificationCount = (TextView) findViewById(R.id.notificationCount);
        notificationCount.setVisibility(View.INVISIBLE);

        ArrayList<NotificationModel> notiArrayList = notificationDbController.getUnreadData();

        if (notiArrayList != null && !notiArrayList.isEmpty()) {
            int totalUnread = notiArrayList.size();
            if (totalUnread > 0) {
                notificationCount.setVisibility(View.VISIBLE);
                notificationCount.setText(String.valueOf(totalUnread));
            } else {
                notificationCount.setVisibility(View.INVISIBLE);
            }
        }
    }

    //регистрируем широковещательный приемник и вызываем метод initNotification
        @Override
    protected void onResume() {
        super.onResume();

        //register broadcast receiver
        IntentFilter intentFilter = new IntentFilter(AppConstants.NEW_NOTI);
        LocalBroadcastManager.getInstance(this).registerReceiver(newNotificationReceiver, intentFilter);

        initNotification();
            // show full-screen ads
            AdsUtilities.getInstance(mContext).loadFullScreenAd(mActivity);
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {

    }

    @Override
    public void onPurchaseHistoryRestored() {

        if (bp.isSubscribed(SUBSCRIPTION_ID())) {
            setIsSubscribe(true, mContext);
            Log.v("TAG", "Subscribe actually restored");

        } else {
            setIsSubscribe(false, mContext);
        }
    }

    private String SUBSCRIPTION_ID(){
        return getResources().getString(R.string.subscription_id);
    }
    //сохраняет значение подписки в настройках приложения
    public void setIsSubscribe(boolean purchased, Context c){
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(c);

        SharedPreferences.Editor editor= prefs.edit();

        editor.putBoolean(AppConstants.PRODUCT_ID_SUBSCRIBE, purchased);
        editor.apply();
    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    public void onComplete(Boolean isOkPressed, String viewIdText) {
        if (isOkPressed) {
            if (viewIdText.equals(AppConstants.BUNDLE_KEY_EXIT_OPTION)) {
                mActivity.finishAffinity();
            }
        }

    }

    //в фоне будет выполнять запрос к серверу для проверки покупок и подписок.
    private class CheckSubscribe extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String license = getResources().getString(R.string.google_play_license);
            bp = new BillingProcessor(mContext, license, MainActivity.this);
            bp.loadOwnedPurchasesFromGoogle();
            return null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        //unregister broadcast receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(newNotificationReceiver);

    }
}
