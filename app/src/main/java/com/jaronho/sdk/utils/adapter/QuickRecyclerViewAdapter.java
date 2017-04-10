package com.jaronho.sdk.utils.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaronho.sdk.utils.viewholder.QuickRecyclerViewHolder;

import java.util.List;

/**
 * Author:  Administrator
 * Date:    2017/4/9
 * Brief:   QuickRecyclerViewAdapter
 */

public abstract class QuickRecyclerViewAdapter<T> extends Adapter<QuickRecyclerViewHolder> {
    private Context mContext = null;
    private List<T> mDatas = null;
    private int mLayoutId = 0;
    private MultiItemTypeSupport<T> mMultiItemTypeSupport = null;

    public static abstract class MultiItemTypeSupport<T> {
        public abstract int getLayoutId(int position, T data);
    }

    public QuickRecyclerViewAdapter(Context context, List<T> datas, int layoutId) {
        mContext = context;
        mDatas = datas;
        mLayoutId = layoutId;
    }

    public QuickRecyclerViewAdapter(Context context, List<T> datas, MultiItemTypeSupport<T> multiItemTypeSupport) {
        mContext = context;
        mDatas = datas;
        mMultiItemTypeSupport = multiItemTypeSupport;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (null == mMultiItemTypeSupport) {
            return mMultiItemTypeSupport.getLayoutId(position, mDatas.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public QuickRecyclerViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if (null != mMultiItemTypeSupport) {
            mLayoutId = viewType;
        }
        View itemView = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
        return new QuickRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuickRecyclerViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
    }

    public abstract void convert(QuickRecyclerViewHolder holder, T data);
}
