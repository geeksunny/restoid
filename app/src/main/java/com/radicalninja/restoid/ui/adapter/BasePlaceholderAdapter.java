package com.radicalninja.restoid.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public abstract class BasePlaceholderAdapter<T extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<T>{

    private View mPlaceholderView, mRecyclerView;

    public BasePlaceholderAdapter(TextView placeholderView, RecyclerView recyclerView) {
        mPlaceholderView = placeholderView;
        mRecyclerView = recyclerView;
    }

    protected void toggleViews() {
        if (getItemCount() > 0) {
            toggleIfNeeded(mPlaceholderView, View.GONE);
            toggleIfNeeded(mRecyclerView, View.VISIBLE);
        } else {
            toggleIfNeeded(mPlaceholderView, View.VISIBLE);
            toggleIfNeeded(mRecyclerView, View.GONE);
        }
    }

    private void toggleIfNeeded(View view, int visibility) {
        if (view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }
    }

}
