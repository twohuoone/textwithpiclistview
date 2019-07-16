package com.zoro.textwithpiclistview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: lzw
 * Date: 2018/3/8
 * Description: This is BaseRecyclerAdapter
 */

public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {

    protected List<T> mList;

    protected LayoutInflater mLayoutInflater;

    protected Context mContext;

    protected ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public BaseRecyclerAdapter(Context context) {
        this.mContext = context;
        mList = new ArrayList<>();
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(getItemLayout(), parent, false);
        return onCreateViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindViewHolder((VH) holder, mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    //================对外的方法===================


    private void addData(List<T> dataList, boolean isRefresh) {
        if (this.mList == null) {
            this.mList = new ArrayList<>();
        }
        if (isRefresh) {
            this.mList.clear();
        }
        this.mList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void addRefreshData(List<T> dataList) {
        addData(dataList, true);
    }

    public void addLoadData(List<T> dataList) {
        addData(dataList, false);
    }

    protected abstract int getItemLayout();

    protected abstract VH onCreateViewHolder(View view);


    protected abstract void onBindViewHolder(VH holder, T mData, int pos);


    public interface ItemClickListener {
        void onItemClick(int pos);
    }
}
