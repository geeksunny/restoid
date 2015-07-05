package com.radicalninja.restoid.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.radicalninja.restoid.R;
import com.radicalninja.restoid.data.model.Connection;
import com.radicalninja.restoid.data.model.HeaderEntry;
import com.radicalninja.restoid.ui.adapter.HeadersAdapter;
import com.radicalninja.restoid.util.Ln;

public class HeadersFragment extends BaseConnectionFragment {

    // Layout
    private RecyclerView mRecyclerView;
    private TextView mMessage;
    private HeadersAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static HeadersFragment newInstance() {
        // TODO: In the future, saved connections will be used here for building new fragments.
        return new HeadersFragment();
    }

    public HeadersFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_headers_params, container, false);

        Ln.e("onCreateView!");
        mMessage = (TextView) rootView.findViewById(R.id.message_noitems);
        mMessage.setText(R.string.message_placeholder_headers);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new HeadersAdapter(getActivity(), mMessage, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_headers, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_header) {
            if (mAdapter != null) {
                mAdapter.add(new HeaderEntry());
                onConnectionChanged();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void populateConnectionInfo(Connection connection) {
        Ln.i("Populating Connection Info on HeadersFragment");
        mAdapter.setHeaders(connection.getHeaders());
    }
}
