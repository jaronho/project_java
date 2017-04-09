package com.jaronho.sdk.utils.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.SparseArray;
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
    private MultiItemTypeSupport<T> mMultiItemTypeSupport = null;
    private SparseArray<View> mHeaderViews;
    private SparseArray<View> mFooterViews;
    // 基本的头部类型开始位置  用于viewType
    private static int TYPE_HEADER = 1;
    // 基本的底部类型开始位置  用于viewType
    private static int TYPE_FOOTER = 2;

    public static abstract class MultiItemTypeSupport<T> {
        public abstract int getLayoutId(int viewType);
        public abstract int getItemViewType(int position, T data);
    }

    public QuickRecyclerViewAdapter(Context context, List<T> datas, MultiItemTypeSupport<T> multiItemTypeSupport) {
        mContext = context;
        mDatas = datas;
        mMultiItemTypeSupport = multiItemTypeSupport;
    }

    @Override
    public int getItemViewType(int position) {
        return mMultiItemTypeSupport.getItemViewType(position, mDatas.get(position));
    }

    @Override
    public QuickRecyclerViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
        View itemView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
        return new QuickRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuickRecyclerViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public abstract void convert(QuickRecyclerViewHolder holder, T data);
}
