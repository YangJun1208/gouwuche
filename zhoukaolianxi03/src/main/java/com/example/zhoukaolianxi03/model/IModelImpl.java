package com.example.zhoukaolianxi03.model;

import com.example.zhoukaolianxi03.callback.MyCallBack;
import com.example.zhoukaolianxi03.network.RestofitMessage;
import com.google.gson.Gson;

import java.util.Map;

public class IModelImpl implements IModel {

    @Override
    public void postRequest(String dataUrl, Map<String, String> param, final Class clazz, final MyCallBack callBack) {
        RestofitMessage.getInstsnce().postResponce(dataUrl, param, new RestofitMessage.Httpclient() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                callBack.CallBack(o);
            }

            @Override
            public void onFaile(String error) {
                callBack.CallBack(error);
            }
        });
    }
}
