package com.example.zhoukaolianxi03.network;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RestofitMessage {
    private String BaseUrl="http://www.zhaoapi.cn/";
    private static RestofitMessage restofitMessage;
    private final BaseApis baseApis;

    public static synchronized RestofitMessage getInstsnce(){
        if(restofitMessage==null){
            restofitMessage=new RestofitMessage();
        }
        return restofitMessage;
    }
    private RestofitMessage(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient builder = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS)
                .build();

        Retrofit builder1 = new Retrofit.Builder()
                .client(builder)
                .baseUrl(BaseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        baseApis = builder1.create(BaseApis.class);

    }
    public RestofitMessage getResponce(String dataUrl,Httpclient listener){
       baseApis.get(dataUrl)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(getObsever(listener));
       return restofitMessage;
    }

    public RestofitMessage postResponce(String dataUrl, Map<String,String> map, Httpclient listener){
        baseApis.post(dataUrl,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObsever(listener));
        return restofitMessage;
    }


    private Observer<? super ResponseBody> getObsever(final Httpclient listener) {
        Observer<ResponseBody> observer = new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(listener!=null){
                    listener.onSuccess(e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String string = responseBody.string();
                    if(listener!=null){
                        listener.onSuccess(string);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        return observer;
    }

    public interface Httpclient {
        void onSuccess(String data);
        void onFaile(String error);
    }
}
