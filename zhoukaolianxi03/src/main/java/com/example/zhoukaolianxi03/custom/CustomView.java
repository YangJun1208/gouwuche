package com.example.zhoukaolianxi03.custom;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhoukaolianxi03.GoodsAdapter;
import com.example.zhoukaolianxi03.R;
import com.example.zhoukaolianxi03.ShopBean;

import java.util.List;

public class CustomView extends LinearLayout {

    private Context context;
    private EditText edit;

    public CustomView(Context context) {
        super(context);
        init(context);
    }

    public CustomView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    int num=0;
    private void init(final Context context) {
        View view = View.inflate(context, R.layout.num_item, null);
        TextView add=view.findViewById(R.id.add);
        TextView jian=view.findViewById(R.id.jian);
        edit = view.findViewById(R.id.editText);

        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                edit.setText(num+"");
                list.get(position).setNum(num);
                goodsAdapter.notifyItemChanged(position);

            }
        });
        jian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num>1){
                    num--;
                    edit.setText(num+"");
                    list.get(position).setNum(num);
                    goodsAdapter.notifyItemChanged(position);
                }else{
                    Toast.makeText(context, "有底线的", Toast.LENGTH_SHORT).show();
                }
            }
        });
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                num= Integer.parseInt(String.valueOf(s));
                try {
                    list.get(position).setNum(num);
                }catch (Exception e){
                    list.get(position).setNum(1);
                }
                if(onClicksListener!=null){
                    onClicksListener.CallBack();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    onClickssListener onClicksListener;

    public void onClickssListener(onClickssListener onClicksListeners){
        this.onClicksListener=onClicksListeners;
    }


    public interface onClickssListener {
        void CallBack();
    }


    private int position;
    private GoodsAdapter goodsAdapter;
    private List<ShopBean.DataBean.ListBean> list;

    public void setData(int position,GoodsAdapter goodsAdapter,List<ShopBean.DataBean.ListBean> list){
        this.goodsAdapter=goodsAdapter;
        this.position=position;
        this.list=list;
        num=list.get(position).getNum();
        edit.setText(num+"");
    }


}
