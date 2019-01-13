package com.example.zhoukaolianxi03.persenter;

import java.util.Map;

public interface IPersenter {
    void postRequest(String dataUrl, Map<String, String> param, Class clazz);
}
