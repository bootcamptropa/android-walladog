package com.walladog.walladog.utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.walladog.walladog.R;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by hadock on 2/02/16.
 *
 */

public class EndRecycleViewAdapter extends RVAWrapper {
    public static final int TYPE_PENDING = 999;
    private final Context context;
    private final int pendingViewResId;
    private AtomicBoolean keepOnAppending;
    private AtomicBoolean dataPending;
    private ReqToLoadMoreListener mReqToLoadMoreListener;

    public EndRecycleViewAdapter(Context context, RecyclerView.Adapter wrapped, ReqToLoadMoreListener reqToLoadMoreListener, @LayoutRes int pendingViewResId, boolean keepOnAppending) {
        super(wrapped);
        this.context = context;
        this.mReqToLoadMoreListener = reqToLoadMoreListener;
        this.pendingViewResId = pendingViewResId;
        this.keepOnAppending = new AtomicBoolean(keepOnAppending);
        dataPending = new AtomicBoolean(false);
    }

    public EndRecycleViewAdapter(Context context, RecyclerView.Adapter wrapped, ReqToLoadMoreListener reqToLoadMoreListener) {
        //this(context, wrapped, reqToLoadMoreListener, com.rockerhieu.rvadapter.endless.R.layout.item_loading, true);
        this(context, wrapped, reqToLoadMoreListener, R.layout.ritem_loading, true);
    }

    private void stopAppending() {
        setKeepOnAppending(false);
    }

    /**
     * Let the adapter know that data is load and ready to view.
     *
     * @param keepOnAppending whether the adapter should request to load more when scrolling to the bottom.
     */
    public void onDataReady(boolean keepOnAppending) {
        dataPending.set(false);
        setKeepOnAppending(keepOnAppending);
    }

    private void setKeepOnAppending(boolean newValue) {
        keepOnAppending.set(newValue);
        getWrappedAdapter().notifyDataSetChanged();
    }

    /**
     *
     */
    public void restartAppending() {
        dataPending.set(false);
        setKeepOnAppending(true);
    }

    private View getPendingView(ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(pendingViewResId, viewGroup, false);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (keepOnAppending.get() ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getWrappedAdapter().getItemCount()) {
            return TYPE_PENDING;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_PENDING) {
            return new PendingViewHolder(getPendingView(parent));
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_PENDING) {
            if (!dataPending.get()) {
                dataPending.set(true);
                mReqToLoadMoreListener.OnNeedMorePages();
            }
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    public interface ReqToLoadMoreListener {
        void OnNeedMorePages();
    }

    static class PendingViewHolder extends RecyclerView.ViewHolder {

        public PendingViewHolder(View itemView) {
            super(itemView);
        }
    }
}