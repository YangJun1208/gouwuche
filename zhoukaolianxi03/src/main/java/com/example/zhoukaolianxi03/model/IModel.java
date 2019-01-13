package com.example.zhoukaolianxi03.model;


import com.example.zhoukaolianxi03.callback.MyCallBack;

import java.util.Map;

public interface IModel {
    void postRequest(String dataUrl, Map<String, String> param, Class Clazz, MyCallBack callBack);
}
