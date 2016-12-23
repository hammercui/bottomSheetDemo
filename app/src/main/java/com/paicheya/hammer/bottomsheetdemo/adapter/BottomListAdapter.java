package com.paicheya.hammer.bottomsheetdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.paicheya.hammer.bottomsheetdemo.R;
import com.paicheya.hammer.bottomsheetdemo.listener.RVItemClickListener;

import java.util.List;

/**
 * 底部列表的adapter
 * Created by cly on 16/12/23.
 */

public class BottomListAdapter extends RecyclerView.Adapter<BottomListAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> mData;
    private RVItemClickListener mItemClickListener;
    public BottomListAdapter(Context context,List data,RVItemClickListener itemClickListener){
        this.mContext = context;
        this.mData = data;
        this.mItemClickListener = itemClickListener;
    }


    @Override
    public BottomListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.bottom_item,parent,false));
        return holder;
    }


    @Override
    public void onBindViewHolder(BottomListAdapter.MyViewHolder holder, int position) {
            holder.tv.setText(mData.get(position));
            holder.tv.setOnClickListener(view -> {
                mItemClickListener.onClick(view,position);
            });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

   public  class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView)itemView.findViewById(R.id.tv_title);
        }
    }

}



