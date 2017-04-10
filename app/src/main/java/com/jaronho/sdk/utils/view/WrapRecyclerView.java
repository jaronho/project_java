package com.jaronho.sdk.utils.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.jaronho.sdk.utils.adapter.WrapRecyclerViewAdapter;

/**
 * Author:  jaron.ho
 * Date:    2017-04-10
 * Brief:   WrapRecyclerView(可以添加头部和底部)
 */

public class WrapRecyclerView extends RecyclerView {
    private WrapRecyclerViewAdapter mAdapter;
    private AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (null != mAdapter) {
                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (null != mAdapter) {
                mAdapter.notifyItemRemoved(positionStart);
            }
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            if (null != mAdapter) {
                mAdapter.notifyItemMoved(fromPosition, toPosition);
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            if (null != mAdapter) {
                mAdapter.notifyItemChanged(positionStart);
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            if (null != mAdapter) {
                mAdapter.notifyItemChanged(positionStart, payload);
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (null != mAdapter) {
                mAdapter.notifyItemInserted(positionStart);
            }
        }
    };

    public WrapRecyclerView(Context context) {
        super(context);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (!(adapter instanceof WrapRecyclerViewAdapter)) {
            throw new AssertionError("adapter must instance of WrapRecyclerViewAdapter");
        }
        if (null != mAdapter) {
            mAdapter.unregisterAdapterDataObserver(mDataObserver);
            mAdapter = null;
        }
        mAdapter = (WrapRecyclerViewAdapter)adapter;
        super.setAdapter(mAdapter);
        mAdapter.registerAdapterDataObserver(mDataObserver);
        mAdapter.adjustSpanSize(this);  // 解决GridLayout添加头部和底部也要占据一行
    }

    // 获取头部
    public View getHeaderView(int index) {
        if (null != mAdapter) {
            return mAdapter.getHeaderView(index);
        }
        return null;
    }

    // 获取底部
    public View getFooterView(int index) {
        if (null != mAdapter) {
            return mAdapter.getFooterView(index);
        }
        return null;
    }

    // 添加头部
    public void addHeaderView(View view) {
        if (null != mAdapter) {
            mAdapter.addHeaderView(view);
        }
    }

    // 添加底部
    public void addFooterView(View view) {
        if (null != mAdapter) {
            mAdapter.addFooterView(view);
        }
    }

    // 移除头部
    public void removeHeaderView(View view) {
        if (null != mAdapter) {
            mAdapter.removeHeaderView(view);
        }
    }

    // 移除头部
    public void removeHeaderView(int index) {
        if (null != mAdapter) {
            mAdapter.removeHeaderView(index);
        }
    }

    // 移除底部
    public void removeFooterView(View view) {
        if (null != mAdapter) {
            mAdapter.removeFooterView(view);
        }
    }

    // 移除底部
    public void removeFooterView(int index) {
        if (null != mAdapter) {
            mAdapter.removeFooterView(index);
        }
    }
}
