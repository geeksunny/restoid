package com.radicalninja.restoid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.radicalninja.restoid.R;
import com.radicalninja.restoid.data.model.HeaderEntry;

import java.util.ArrayList;

public class HeadersAdapter extends RecyclerView.Adapter<HeadersAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox enabled;
        public EditText key;
        public EditText value;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private ArrayList<HeaderEntry> mHeaders = new ArrayList<>();
    private Context mContext;

    public HeadersAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mHeaders.size();
    }

    public HeaderEntry getItem(int i) {
        return mHeaders.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_header_editor, parent, false);
        ViewHolder h = new ViewHolder(view);
        h.enabled = (CheckBox) view.findViewById(R.id.checkbox_is_enabled);
        h.key = (EditText) view.findViewById(R.id.edittext_key);;
        h.value = (EditText) view.findViewById(R.id.edittext_value);
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int i) {
        HeaderEntry entry = getItem(i);
        vh.enabled.setChecked(entry.isEnabled());
        vh.key.setText(entry.getKey());
        vh.value.setText(entry.getValue());
    }

}
