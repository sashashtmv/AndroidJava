package com.sashashtmv.vkapp;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdk;

public class CurrentUser {
    //будет возвращать токен доступа от сервера вк или нал, если не получен токен
    public static String getAccessToken(){
        if(VKAccessToken.currentToken() == null){
            return null;
        }
        return VKAccessToken.currentToken().accessToken;
    }
    //будет возвращать идентификатор текущего пользователя, если токен получен
    public static String getId(){
        if(VKAccessToken.currentToken() == null){
            return null;
        }
        return VKAccessToken.currentToken().userId;
    }
    //будет возвращать фолл, если пользователь не авторизован, если пользовательский токен равен нулю или устарел,
    public static boolean isAuthorised(){
        return VKSdk.isLoggedIn()
                && VKAccessToken.currentToken() != null
                && !VKAccessToken.currentToken().isExpired();
    }
}
