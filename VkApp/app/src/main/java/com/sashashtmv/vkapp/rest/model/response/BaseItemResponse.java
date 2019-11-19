package com.sashashtmv.vkapp.rest.model.response;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

//этот уровень содержит в себе count, items, profiles, groups
public class BaseItemResponse<T> {
    //названия переменных будут совпадать с параметрами ответа сервера
    public Integer count;
    public List<T> items = new ArrayList<>();

    public Integer getCount() {
        return count;
    }

    public List<T> getItems() {
        return items;
    }
}
