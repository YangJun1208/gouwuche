package com.example.zhoukaolianxi03.persenter;

import com.example.zhoukaolianxi03.callback.MyCallBack;
import com.example.zhoukaolianxi03.model.IModelImpl;
import com.example.zhoukaolianxi03.view.IView;

import java.util.Map;

public class IPersenterImpl implements IPersenter {

    private IModelImpl iModel;
    private IView iView;

    public IPersenterImpl(IView mIView){
        iModel=new IModelImpl();
        iView=mIView;
    }
    @Override
    public void postRequest(String dataUrl, Map<String, String> param, Class clazz) {
        iModel.postRequest(dataUrl, param, clazz, new MyCallBack() {
            @Override
            public void CallBack(Object data) {
                iView.onSuccess(data);
            }
        });
    }
    public void deteach(){
        iView=null;
        iModel=null;
    }
}
