package com.sashashtmv.vkapp.rest.model.response;

import com.sashashtmv.vkapp.model.WallItem;

//будет содержать унаследованную от класса Full переменную response с типом BaseItemResponse.
// Она в свою очередь будет содержать переменную WallItem с типом List<WallItem>
public class WallGetResponse extends Full<BaseItemResponse<WallItem>> {
}
