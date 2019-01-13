package com.example.zhoukaolianxi03;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private Context context;
    private List<ShopBean.DataBean> mData;

    public ShopAdapter(Context context) {
        this.context = context;
        mData=new ArrayList<>();
    }

    public void setDats(List<ShopBean.DataBean> data) {
        //this.mData = mData;
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addDats(List<ShopBean.DataBean> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.shop_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        ButterKnife.bind(viewHolder,view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final ShopAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.text_shop.setText(mData.get(i).getSellerName());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        viewHolder.recycler.setLayoutManager(layoutManager);
        final GoodsAdapter goodsAdapter = new GoodsAdapter(context, mData.get(i).getList());
        viewHolder.recycler.setAdapter(goodsAdapter);
        goodsAdapter.setOnClicks(new GoodsAdapter.OnClicks() {
            @Override
            public void CallBack() {
                if(onClickListener!=null){
                    onClickListener.CallBack(mData);
                }
                List<ShopBean.DataBean.ListBean> list = mData.get(i).getList();
                boolean isAllCkeck=true;
                for (ShopBean.DataBean.ListBean listBean:list){
                    if(!listBean.isCheck()){
                        isAllCkeck=false;
                        break;
                    }
                }
                viewHolder.check_item.setChecked(isAllCkeck);
                mData.get(i).setCheck(isAllCkeck);
            }
        });
        viewHolder.check_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.get(i).setCheck(viewHolder.check_item.isChecked());
               goodsAdapter.setDates(viewHolder.check_item.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shop_item)
        CheckBox check_item;
        @BindView(R.id.shop_textView)
        TextView text_shop;
        @BindView(R.id.shop_recycle)
        RecyclerView recycler;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    OnsClickListener onClickListener;

    public void setOnClickListener(OnsClickListener onClickListener){
        this.onClickListener=onClickListener;
    }

    public interface OnsClickListener {
        void CallBack(List<ShopBean.DataBean> list);
    }
}
