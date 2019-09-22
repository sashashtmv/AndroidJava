package com.sashashtmv.myquiz.web;

//будет отвечать за загрузку страницы по ссылке

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sashashtmv.myquiz.listeners.WebListener;

public class WebEngine {
    private WebView mWebView;
    private Activity mActivity;
    Context mContext;

    //константа с адресом для просмотра документов в гугл
    private static final String GOOGLE_DOCS_VIEWER = "https://docs.google.com/viewerng/viewer?url=";
    //переменная интерфейса слушателя
    private WebListener mWebListener;

    //в контекст сохраняем глобальный контекст приложения. Это даст возможность доступа к ресурсам и системным функциям из любого места приложения
    public WebEngine(WebView webView, Activity activity) {
        mWebView = webView;
        mActivity = activity;
        mContext = activity.getApplicationContext();
    }

    //устанавливает настройки вебвью, такие , как поддержку джаваскрипт, масштабирование страницы по ширине экрана, управление кешированием, кодировкой текста и др.
    public void initWebView(){
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 10);
        mWebView.getSettings().setAppCachePath(mContext.getCacheDir().getAbsolutePath());
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);

        //если сетевое соединение недоступно, загружаем страницу из кеша
        if(!isNetworkAvalaible(mContext)){
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
    }

    //инициализирует интерфейс слушателя
    //здесь мы устанавливаем веб-хром-клайм для реализации окна просмотра веб-адреса, как в браузере
    public void initListener(final WebListener webListener){
        this.mWebListener = webListener;

        mWebView.setWebChromeClient(new WebChromeClient(){
            //сообщает текущий прогресс загрузки страницы
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mWebListener.onProgress(newProgress);
            }

            //сообщает об изменении заголовка страницы, передаем новый заголовок

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mWebListener.onPageTitle(mWebView.getTitle());
            }
        });

        //устанавливаем клиент для вебвью и переопределяем его методы
        mWebView.setWebViewClient(new WebViewClient(){
            //вызываем загрузку веб-адреса
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String webUrl) {
                loadPage(webUrl);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mWebListener.onStart();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mWebListener.onLoaded();
            }
        });
    }

    //определяет всевозможные типы адресов и в зависимости от выбранного типа, использует различные методы открытия ссылки
    public void loadPage(String webUrl){
        if(isNetworkAvalaible(mContext)){
            if(webUrl.startsWith("tel:") || webUrl.startsWith("sms:") || webUrl.startsWith("smsto:") || webUrl.startsWith("mailto:") || webUrl.startsWith("geo:")){
                invokeNativeApp(webUrl);
            }else  if(webUrl.contains("?target=blank")){
                invokeNativeApp(webUrl.replace("?target=blank", ""));
            }else  if(webUrl.endsWith(".doc") || webUrl.endsWith(".docx") || webUrl.endsWith(".xls") || webUrl.endsWith(".pdf") || webUrl.endsWith(".xlsx") || webUrl.endsWith(".pptx")){
                mWebView.loadUrl(GOOGLE_DOCS_VIEWER + webUrl);
                mWebView.getSettings().setBuiltInZoomControls(true);
            }else {
                //если используется стандартный вебадрес, то он открывается через вебвью
                mWebView.loadUrl(webUrl);
            }
        }else {
            mWebListener.onNetworkError();
        }
    }

    //проверяет доступность сетевого соединения
    private boolean isNetworkAvalaible(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    //создает интент для загрузки веб-адреса через нативное приложение
    private void invokeNativeApp(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        mActivity.startActivity(intent);
    }
}
