package com.sashashtmv.myquiz.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

import com.sashashtmv.myquiz.R;
import com.sashashtmv.myquiz.constants.AppConstants;
import com.sashashtmv.myquiz.listeners.WebListener;
import com.sashashtmv.myquiz.web.WebEngine;

import androidx.annotation.Nullable;

//для отображения веб-адресов

public class CustomUrlActivity extends BaseActivity {

    private Activity mActivity;
    private Context mContext;
    private String pageTitle, pageUrl;

    private WebView mWebView;
    private WebEngine mWebEngine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVar();
        initView();
        initFunctionality();
    }

    //инициализируем активити и контекст приложения. Получаем интент и извлекаем из него заголовок и адрес
    private void initVar(){
        mActivity = CustomUrlActivity.this;
        mContext = mActivity.getApplicationContext();

        Intent intent = getIntent();
        if(intent != null){
            pageTitle = intent.getStringExtra(AppConstants.BUNDLE_KEY_TITLE);
            pageUrl = intent.getStringExtra(AppConstants.BUNDLE_KEY_URL);
        }
    }

    //Инициализируем макет экрана и экранные компоненты
    private  void  initView(){
        setContentView(R.layout.activity_custom_url);

        initWebEngine();
        initLoader();
        initToolbar(true);
        setToolbarTitle(pageTitle);
        enableUpButton();
    }

    //инициализируем экземпляры классов вебвью и вебенжин, а также определяем слушатель для управления отображением состояния загрузки
    public  void  initWebEngine(){
        mWebView = findViewById(R.id.webView);

        mWebEngine = new WebEngine(mWebView, mActivity);
        mWebEngine.initWebView();
        mWebEngine.initListener(new WebListener() {
            @Override
            public void onStart() {
                showLoader();
            }

            @Override
            public void onLoaded() {
                hideLoader();
            }

            @Override
            public void onProgress(int progress) {

            }

            @Override
            public void onNetworkError() {
                showEmptyView();
            }

            @Override
            public void onPageTitle(String title) {

            }
        });
    }

    //инициируем загрузку страницы
    private void initFunctionality(){
        mWebEngine.loadPage(pageUrl);
    }

    //переопределим метод для обработки нажатия кнопки в тулбаре для возврата на предыдущий экран

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
                default: return super.onOptionsItemSelected(item);
        }


    }
}
