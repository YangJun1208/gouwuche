package com.example.zhoukaolianxi03;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhoukaolianxi03.custom.CustomView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {

    private Context context;
    private List<ShopBean.DataBean.ListBean> mData;


    public GoodsAdapter(Context context, List<ShopBean.DataBean.ListBean> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public GoodsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.goods_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        ButterKnife.bind(viewHolder,view);
        Fresco.initialize(context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.text_goods.setText(mData.get(i).getTitle());
        viewHolder.text_price.setText(mData.get(i).getPrice()+"");
        String replace = mData.get(i).getImages().split("\\|")[0].replace("https", "http");
        viewHolder.imageView.setImageURI(Uri.parse(replace));

        viewHolder.checkBox.setChecked(mData.get(i).isCheck());
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mData.get(i).setCheck(isChecked);
                if(onClicks!=null){
                    onClicks.CallBack();
                }
            }
        });

        viewHolder.custom_jia.setData(i,this,mData);
        viewHolder.custom_jia.onClickssListener(new CustomView.onClickssListener() {
            @Override
            public void CallBack() {
                if(onClicks!=null){
                    onClicks.CallBack();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public  void setDates(boolean bool){
        for (ShopBean.DataBean.ListBean listBean:mData){
            listBean.setCheck(bool);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shop_checkbox)
        CheckBox checkBox;
        @BindView(R.id.image_goods)
        SimpleDraweeView imageView;
        @BindView(R.id.goods_title)
        TextView text_goods;
        @BindView(R.id.goods_price)
        TextView text_price;
        @BindView(R.id.custom_jia)
        CustomView custom_jia;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    OnClicks onClicks;

    public void setOnClicks(OnClicks onClicks){
        this.onClicks=onClicks;
    }
    public interface OnClicks {
        void CallBack();
    }
}
