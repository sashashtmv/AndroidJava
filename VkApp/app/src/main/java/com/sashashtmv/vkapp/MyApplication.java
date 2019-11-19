package com.sashashtmv.vkapp;


import android.app.Application;

import com.sashashtmv.vkapp.di.component.ApplicationComponent;
import com.sashashtmv.vkapp.di.component.DaggerApplicationComponent;
import com.sashashtmv.vkapp.di.module.ApplicationModule;
import com.vk.sdk.VKSdk;

public class MyApplication extends Application {

    private static ApplicationComponent sApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initComponent();
        VKSdk.initialize(this);
    }

    //метод для инициализации компонента ApplicationComponent
    private void initComponent(){
        //даггер использует кодогенерацию, поэтому в момент написания метода инициализации класса DaggerApplicationComponent не существует
        sApplicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    //метод для получения компонента
    public static ApplicationComponent getApplicationComponent(){
        return sApplicationComponent;
    }
}
