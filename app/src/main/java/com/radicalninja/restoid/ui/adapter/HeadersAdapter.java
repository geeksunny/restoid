package com.radicalninja.restoid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.radicalninja.restoid.R;
import com.radicalninja.restoid.data.model.HeaderEntry;
import com.radicalninja.restoid.data.model.HeaderList;

import java.util.List;

public class HeadersAdapter extends RecyclerView.Adapter<HeadersAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox enabled;
        public EditText key;
        public EditText value;
        public Button deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private HeaderList mHeaders = new HeaderList();
    private Context mContext;

    public HeadersAdapter(Context context) {
        mContext = context;
    }

    public HeadersAdapter(Context context, HeaderList headers) {
        mContext = context;
        mHeaders = headers;
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
        final ViewHolder h = new ViewHolder(view);
        h.enabled = (CheckBox) view.findViewById(R.id.checkbox_is_enabled);
        h.key = (EditText) view.findViewById(R.id.edittext_key);
        h.value = (EditText) view.findViewById(R.id.edittext_value);
        h.deleteButton = (Button) view.findViewById(R.id.button_delete);
        return h;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int pos) {
        final int i = pos;
        final HeaderEntry entry = getItem(i);
        vh.enabled.setChecked(entry.isEnabled());
        vh.enabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getItem(i).setIsEnabled(isChecked);
            }
        });
        vh.key.setText(entry.getKey());
        vh.key.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                getItem(i).setKey(s.toString());
            }
        });
        vh.value.setText(entry.getValue());
        vh.value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                getItem(i).setValue(s.toString());
            }
        });
        vh.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(pos);
            }
        });
    }

    public HeaderList getHeaders() {
        return mHeaders;
    }

    public void setHeaders(HeaderList headers) {
        this.mHeaders = headers;
        notifyDataSetChanged();
    }

    public void add(HeaderEntry headerEntry) {
        mHeaders.add(headerEntry);
        notifyDataSetChanged();
    }

    public void add(List<HeaderEntry> headers) {
        mHeaders.addAll(headers);
        notifyDataSetChanged();
    }

    public void remove(HeaderEntry headerEntry) {
        mHeaders.remove(headerEntry);
        notifyDataSetChanged();
    }

    public void remove(int location) {
        mHeaders.remove(location);
        notifyDataSetChanged();
    }

    public void removeAll(List<HeaderEntry> headers) {
        mHeaders.removeAll(headers);
        notifyDataSetChanged();
    }
}
