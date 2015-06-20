package com.radicalninja.restoid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.radicalninja.restoid.R;
import com.radicalninja.restoid.data.model.QueryEntry;

import java.util.ArrayList;
import java.util.List;

public class ParamsAdapter extends RecyclerView.Adapter<ParamsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public EditText key;
        public EditText value;
        public Button deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private List<QueryEntry> mParams = new ArrayList<>();
    private Context mContext;

    public ParamsAdapter(Context context) {
        mContext = context;
    }

    public ParamsAdapter(Context context, List<QueryEntry> headers) {
        mContext = context;
        mParams = headers;
    }

    @Override
    public int getItemCount() {
        return mParams.size();
    }

    public QueryEntry getItem(int i) {
        return mParams.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_params_editor, parent, false);
        final ViewHolder h = new ViewHolder(view);
        h.key = (EditText) view.findViewById(R.id.edittext_key);
        h.value = (EditText) view.findViewById(R.id.edittext_value);
        h.deleteButton = (Button) view.findViewById(R.id.button_delete);
        return h;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int pos) {
        final int i = pos;
        final QueryEntry entry = getItem(i);
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

    public void setHeaders(List<QueryEntry> headers) {
        this.mParams = headers;
        notifyDataSetChanged();
    }

    public void add(QueryEntry queryEntry) {
        mParams.add(queryEntry);
        notifyDataSetChanged();
    }

    public void add(List<QueryEntry> headers) {
        mParams.addAll(headers);
        notifyDataSetChanged();
    }

    public void remove(QueryEntry queryEntry) {
        mParams.remove(queryEntry);
        notifyDataSetChanged();
    }

    public void remove(int location) {
        mParams.remove(location);
        notifyDataSetChanged();
    }

    public void removeAll(List<QueryEntry> headers) {
        mParams.removeAll(headers);
        notifyDataSetChanged();
    }
}
