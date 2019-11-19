package com.sashashtmv.vkapp.rest.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//это корневой уровень пришедшего ответа, он содержит в себе response. Класс будет отвечать за парсинг секции ответа response
public class Full<T> {
    @SerializedName("response")
    @Expose
    //задаем параметризированный тип
    public T response;
}
