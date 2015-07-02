package com.radicalninja.restoid.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.radicalninja.restoid.R;
import com.radicalninja.restoid.data.model.Connection;

import java.util.List;

public class ConnectionsAdapter extends BaseAdapter {

    private class ViewHolder {
        TextView name;
        TextView url;
    }

    private Context mContext;
    private List<Connection> mConnections;

    public ConnectionsAdapter(Context context, List<Connection> connections) {
        mContext = context;
        mConnections = connections;
    }

    @Override
    public int getCount() {
        return mConnections.size();
    }

    @Override
    public Connection getItem(int i) {
        return mConnections.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_nav_connection, parent, false);
            ViewHolder vh = new ViewHolder();
            vh.name = (TextView) convertView.findViewById(R.id.name);
            vh.url = (TextView) convertView.findViewById(R.id.url);
            convertView.setTag(vh);
        }
        ViewHolder h = (ViewHolder) convertView.getTag();
        Connection con = getItem(i);
        h.name.setText(con.getName());
        h.url.setText(con.getUrl());
        return convertView;
    }

    public void add(Connection connection) {
        mConnections.add(connection);
    }
}
