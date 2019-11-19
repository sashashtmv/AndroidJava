package com.sashashtmv.vkapp.rest;

//будет использоваться для инициализации ретрофита и наших сервисов апи-запросов

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    //константа для хранения url-запросов к api
    private static final String VK_BASE_URL = "https://api.vk.com/method/";

    private Retrofit mRetrofit;

    public RestClient() {
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(VK_BASE_URL)
                .build();
    }

    //переменные для инициализации rest api сервисов
    public <S> S createService(Class<S> serviceClass){
        return mRetrofit.create(serviceClass);
    }
}
