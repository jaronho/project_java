package com.jaronho.sdk.utils.viewholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnDragListener;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Author:  Administrator
 * Date:    2017/4/8
 * Brief:   QuickRecyclerViewHolder
 */

public class QuickRecyclerViewHolder extends ViewHolder {
    private SparseArray<View> mViews;

    public QuickRecyclerViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (null == view) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T)view;
    }

    public void setTag(int viewId, Object tag) {
        getView(viewId).setTag(tag);
    }

    public void setTag(int viewId, int key, Object tag) {
        getView(viewId).setTag(key, tag);
    }

    public void setAlpha(int viewId, float value) {
        getView(viewId).setAlpha(value);
    }

    public void setVisible(int viewId, int visible) {
        getView(viewId).setVisibility(visible);
    }

    public void setBackgroundColor(int viewId, int color) {
        getView(viewId).setBackgroundColor(color);
    }

    public void setBackgroundResource(int viewId, int backgroundRes) {
        getView(viewId).setBackgroundResource(backgroundRes);
    }

    public void setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
    }

    public void setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
    }

    public void setTextColorRes(int viewId, int textColorRes, Context context) {
        TextView view = getView(viewId);
        view.setTextColor(context.getResources().getColor(textColorRes));
    }

    public void linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
    }

    public void setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
    }

    public void setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
    }

    public void setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
    }

    public void setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
    }

    public void setProgress(int viewId, int max, int progress) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
    }

    public void setRating(int viewId, int max, float rating) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
    }

    public void setChecked(int viewId, boolean checked) {
        Checkable view = getView(viewId);
        view.setChecked(checked);
    }

    public void setOnTouchListener(OnTouchListener listener) {
        itemView.setOnTouchListener(listener);
    }

    public void setOnTouchListener(int viewId, OnTouchListener listener) {
        getView(viewId).setOnTouchListener(listener);
    }

    public void setOnClickListener(OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }

    public void setOnClickListener(int viewId, OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
    }

    public void setOnLongClickListener(OnLongClickListener listener) {
        itemView.setOnLongClickListener(listener);
    }

    public void setOnLongClickListener(int viewId, OnLongClickListener listener) {
        getView(viewId).setOnLongClickListener(listener);
    }

    public void setOnDragListener(OnDragListener listener) {
        itemView.setOnDragListener(listener);
    }

    public void setOnDragListener(int viewId, OnDragListener listener) {
        getView(viewId).setOnDragListener(listener);
    }
}
