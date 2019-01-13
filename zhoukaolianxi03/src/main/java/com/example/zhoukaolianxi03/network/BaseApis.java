package com.example.zhoukaolianxi03.network;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface BaseApis {
    @GET
    Observable<ResponseBody> get(@Url String Url);
    @POST
    Observable<ResponseBody> post(@Url String Url, @QueryMap Map<String, String> param);
}
