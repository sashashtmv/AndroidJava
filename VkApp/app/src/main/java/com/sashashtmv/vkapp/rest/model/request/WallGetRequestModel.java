package com.sashashtmv.vkapp.rest.model.request;

import com.google.gson.annotations.SerializedName;
import com.sashashtmv.vkapp.consts.ApiConstants;
import com.vk.sdk.api.VKApiConst;

import java.util.Map;

public class WallGetRequestModel extends BaseRequestModel {
        //аннотация, чтобы ретрофит понимал название полей
        @SerializedName(VKApiConst.OWNER_ID)
        int ownerId;

        @SerializedName(VKApiConst.COUNT)
        int count = ApiConstants.DEFAULT_COUNT;

    @SerializedName(VKApiConst.OFFSET)
        int offset;

    //значение переменной всегда будет равно 1 для возможности получения массивов из профайлов и групп
    @SerializedName(VKApiConst.EXTENDED)
    int extended = 1;

    public WallGetRequestModel(int ownerId) {
        this.ownerId = ownerId;
    }

    public WallGetRequestModel(int ownerId, int count, int offset) {
        this.ownerId = ownerId;
        this.count = count;
        this.offset = offset;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getExtended() {
        return extended;
    }

    public void setExtended(int extended) {
        this.extended = extended;
    }

    //для создания модели запроса
    @Override
    public void onMapCreate(Map<String, String> map) {
        map.put(VKApiConst.OWNER_ID, String.valueOf(getOwnerId()));
        map.put(VKApiConst.COUNT, String.valueOf(getCount()));
        map.put(VKApiConst.OFFSET, String.valueOf(getOffset()));
        map.put(VKApiConst.EXTENDED, String.valueOf(getExtended()));
    }
}
