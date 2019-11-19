package com.sashashtmv.vkapp.rest.api;

import com.sashashtmv.vkapp.rest.model.response.BaseItemResponse;
import com.sashashtmv.vkapp.rest.model.response.Full;
import com.sashashtmv.vkapp.rest.model.response.WallGetResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

//здесь описываем формат запроса
public interface WallApi {
    //параметры запроса owner id - идентификатор пользователя или группы, access_token - токен доступа,
    // extended - для возвращения дополнительных полей, profile, groups - содержат информацию о пользователях и сообществах, v - версия
    @GET(ApiMethods.WALL_GET)
    //заменяем BaseItemResponse на WallGetResponse
    Call<WallGetResponse> get(@QueryMap Map<String,String> map);
}
