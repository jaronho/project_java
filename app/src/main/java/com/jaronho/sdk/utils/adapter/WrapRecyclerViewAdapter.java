package com.jaronho.sdk.utils.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.jaronho.sdk.utils.viewholder.QuickRecyclerViewHolder;

import java.util.List;

/**
 * Author:  jaron.ho
 * Date:    2017-04-10
 * Brief:   WrapRecyclerViewAdapter(可以添加头部和底部)
 */

public abstract class WrapRecyclerViewAdapter<T> extends QuickRecyclerViewAdapter {
    private static int BASE_ITEM_TYPE_HEADER = 10000000;    // 基本的头部类型开始位置
    private static int BASE_ITEM_TYPE_FOOTER = 20000000;    // 基本的底部类型开始位置
    private SparseArray<View> mHeaderViews;
    private SparseArray<View> mFooterViews;

    public WrapRecyclerViewAdapter(Context context, List<T> datas, int layoutId) {
        super(context, datas, layoutId);
        mHeaderViews = new SparseArray<>();
        mFooterViews = new SparseArray<>();
    }

    public WrapRecyclerViewAdapter(Context context, List<T> datas, MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(context, datas, multiItemTypeSupport);
        mHeaderViews = new SparseArray<>();
        mFooterViews = new SparseArray<>();
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + mHeaderViews.size() + mFooterViews.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position)) {
            return mHeaderViews.keyAt(position);
        }
        if (isFooterPosition(position)) {
            position = position - mHeaderViews.size() - super.getItemCount();
            return mFooterViews.keyAt(position);
        }
        position = position - mHeaderViews.size();
        return super.getItemViewType(position);
    }

    @Override
    public QuickRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isHeaderViewType(viewType)) {
            return new QuickRecyclerViewHolder(mHeaderViews.get(viewType));
        }
        if (isFooterViewType(viewType)) {
            return new QuickRecyclerViewHolder(mFooterViews.get(viewType));
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(QuickRecyclerViewHolder holder, int position) {
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            return;
        }
        position = position - mHeaderViews.size();
        super.onBindViewHolder(holder, position);
    }

    // 是不是头部类型
    private boolean isHeaderViewType(int viewType) {
        return mHeaderViews.indexOfKey(viewType) >= 0;
    }

    // 是不是底部类型
    private boolean isFooterViewType(int viewType) {
        return mFooterViews.indexOfKey(viewType) >= 0;
    }

    // 是不是头部位置
    private boolean isHeaderPosition(int position) {
        return position < mHeaderViews.size();
    }

    // 是不是底部位置
    private boolean isFooterPosition(int position) {
        return position >= (mHeaderViews.size() + super.getItemCount());
    }

    // 获取头部
    public View getHeaderView(int index) {
        if (index >= 0 && index < mHeaderViews.size()) {
            return mHeaderViews.valueAt(index);
        }
        return null;
    }

    // 获取底部
    public View getFooterView(int index) {
        if (index >= 0 && index < mFooterViews.size()) {
            return mFooterViews.valueAt(index);
        }
        return null;
    }

    // 添加头部
    public void addHeaderView(View view) {
        if (null != view && mHeaderViews.indexOfValue(view) < 0) {
            mHeaderViews.put(BASE_ITEM_TYPE_HEADER++, view);
            notifyDataSetChanged();
        }
    }

    // 添加底部
    public void addFooterView(View view) {
        if (null != view && mFooterViews.indexOfValue(view) < 0) {
            mFooterViews.put(BASE_ITEM_TYPE_FOOTER++, view);
            notifyDataSetChanged();
        }
    }

    // 移除头部
    public void removeHeaderView(View view) {
        if (null != view) {
            int index = mHeaderViews.indexOfValue(view);
            if (index >= 0) {
                mHeaderViews.removeAt(index);
                notifyDataSetChanged();
            }
        }
    }

    // 移除头部
    public void removeHeaderView(int index) {
        if (index >= 0 && index < mHeaderViews.size()) {
            mHeaderViews.removeAt(index);
            notifyDataSetChanged();
        }
    }

    // 移除底部
    public void removeFooterView(View view) {
        if (null != view) {
            int index = mFooterViews.indexOfValue(view);
            if (index >= 0) {
                mFooterViews.removeAt(index);
                notifyDataSetChanged();
            }
        }
    }

    // 移除底部
    public void removeFooterView(int index) {
        if (index >= 0 && index < mFooterViews.size()) {
            mFooterViews.removeAt(index);
            notifyDataSetChanged();
        }
    }

    // 解决GridLayoutManager添加头部和底部不占用一行的问题
    public void adjustSpanSize(RecyclerView recycler) {
        if (recycler.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager layoutManager = (GridLayoutManager) recycler.getLayoutManager();
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    boolean isHeaderOrFooter = isHeaderPosition(position) || isFooterPosition(position);
                    return isHeaderOrFooter ? layoutManager.getSpanCount() : 1;
                }
            });
        }
    }
}
