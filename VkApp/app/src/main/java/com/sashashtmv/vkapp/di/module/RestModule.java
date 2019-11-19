package com.sashashtmv.vkapp.di.module;

import com.sashashtmv.vkapp.rest.RestClient;
import com.sashashtmv.vkapp.rest.api.WallApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RestModule {
    private RestClient mRestClient;

    public RestModule() {
        mRestClient = new RestClient();
    }

    //будет возвращать RestClient
    @Singleton
    @Provides
    public RestClient provideRestClient(){
        return mRestClient;
    }

    //инициализируем наши апи-сервисы
    @Singleton
    @Provides
    public WallApi provideWallApi(){
        return mRestClient.createService(WallApi.class);
    }
}
