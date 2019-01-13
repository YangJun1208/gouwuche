package com.example.zhoukaolianxi03;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhoukaolianxi03.persenter.IPersenterImpl;
import com.example.zhoukaolianxi03.view.IView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.gavin.com.library.StickyDecoration;
import com.gavin.com.library.listener.GroupListener;
import com.gavin.com.library.listener.OnGroupClickListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IView {

    @BindView(R.id.gouwuche_recycle)
    RecyclerView recycler;
    @BindView(R.id.gouwuche_checkbox)
    CheckBox checkBox;
    @BindView(R.id.num_price)
    TextView text_price;
    @BindView(R.id.sum_num)
    TextView text_sum;
    private ShopAdapter shopAdapter;
    private String TYPE_STYLE="product/getCarts";
    private IPersenterImpl iPersenter;
    private List<ShopBean.DataBean> data1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);
        ButterKnife.bind(this);
        iPersenter=new IPersenterImpl(this);
        loadData();

    }

    private void loadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        shopAdapter = new ShopAdapter(this);
        recycler.setAdapter(shopAdapter);

        shopAdapter.setOnClickListener(new ShopAdapter.OnsClickListener() {

            @Override
            public void CallBack(List<ShopBean.DataBean> list) {
                int num = 0;
                double price = 0;
                int numSum = 0;
                for (int i = 0; i < list.size(); i++) {
                    List<ShopBean.DataBean.ListBean> listBeans = list.get(i).getList();
                    for (int a = 0; a < listBeans.size(); a++) {
                        numSum += listBeans.get(a).getNum();
                        if (listBeans.get(a).isCheck()) {
                            num += listBeans.get(a).getNum();
                            price += listBeans.get(a).getPrice() * listBeans.get(a).getNum();
                        }
                    }
                }
                if (num < numSum) {
                    checkBox.setChecked(false);
                } else {
                    checkBox.setChecked(true);
                }
                text_price.setText("总价为:" + price);
                text_sum.setText("数量为:" + num);
            }
        });
        GroupListener groupListener = new GroupListener() {
            @Override
            public String getGroupName(int position) {
                //获取分组名
                return data1.get(position).getSellerName();
            }
        };
        StickyDecoration decoration = StickyDecoration.Builder
                .init(groupListener)
                //重置span（使用GridLayoutManager时必须调用）
                //.resetSpan(mRecyclerView, (GridLayoutManager) manager)
                .setGroupHeight(80)
                .setGroupTextSize(20)
                .build();
        recycler.setLayoutManager(layoutManager);
//需要在setLayoutManager()之后调用addItemDecoration()
        recycler.addItemDecoration(decoration);
        loadsData();
    }

    private void loadsData() {
        Map<String,String> parasm=new HashMap<>();
        parasm.put("uid","80");
        iPersenter.postRequest(TYPE_STYLE,parasm,ShopBean.class);
    }

  //  @OnClick({R.id.gouwuche_checkbox,R.id.text_view})
    public void setDtas(View view){
        switch (view.getId()){
           /* case R.id.text_view:
                new Intent(this,LoginActivity.class);
                break;*/
            case R.id.gouwuche_checkbox:
                sellSum(checkBox.isChecked());
                shopAdapter.notifyDataSetChanged();
                break;
                default:break;
        }
    }

    private void sellSum(boolean checked) {
        int price=0;
        int num=0;
        for (int a=0;a<data1.size();a++){
            ShopBean.DataBean bean = data1.get(a);
            bean.setCheck(checked);
            List<ShopBean.DataBean.ListBean> list = data1.get(a).getList();
            for (int i=0;i<list.size();i++){
                list.get(i).setCheck(checked);
                num+=list.get(i).getNum();
                price+=list.get(i).getPrice()*list.get(i).getNum();
            }
        }
        if(checked){
            text_price.setText("总价为:"+price);
            text_sum.setText("数量为:"+num);
        }else{
            text_price.setText("总价为:"+0.0);
            text_sum.setText("数量为:"+0);
        }
    }

    @Override
    public void onSuccess(Object data) {
        if(data instanceof ShopBean){
            ShopBean zhanShiBean= (ShopBean) data;
            data1 = zhanShiBean.getData();
            if(data1 !=null){
                shopAdapter.setDats(zhanShiBean.getData());
            }
        }
    }
}
