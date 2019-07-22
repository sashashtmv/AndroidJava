package testcom.com.net2.api;

import testcom.com.net2.model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServerAPI {
    @GET("finance/mcm3.json")
    Call<ServerResponse> cResponseCall();
}
