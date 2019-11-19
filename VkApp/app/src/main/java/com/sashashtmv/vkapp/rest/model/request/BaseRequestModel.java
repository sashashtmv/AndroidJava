package com.sashashtmv.vkapp.rest.model.request;

import com.google.gson.annotations.SerializedName;
import com.sashashtmv.vkapp.CurrentUser;
import com.sashashtmv.vkapp.consts.ApiConstants;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseRequestModel {
    //аннотация, чтобы ретрофит понимал название полей
    @SerializedName(VKApiConst.VERSION)
    Double version = ApiConstants.DEFAULT_VERSION;

    @SerializedName(VKApiConst.ACCESS_TOKEN)
    String accesToken = CurrentUser.getAccessToken();

    public Double getVersion() {
        return version;
    }

    public String getAccesToken() {
        return accesToken;
    }

    //для преобразования полей класса в массив ключ-значения
    public Map<String,String> toMap(){
        Map<String, String> map = new HashMap<>();

        map.put(VKApiConst.VERSION, String.valueOf(getVersion()));

        if(accesToken != null){
            map.put(VKApiConst.ACCESS_TOKEN, getAccesToken());
        }
        onMapCreate(map);
        return map;
    }

    //чтоб передавать создание map в дочерние классы
    public abstract void onMapCreate(Map<String,String> map);
}
