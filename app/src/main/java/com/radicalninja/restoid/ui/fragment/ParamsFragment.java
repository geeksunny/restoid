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

import com.radicalninja.restoid.R;
import com.radicalninja.restoid.data.model.Connection;
import com.radicalninja.restoid.data.model.QueryEntry;
import com.radicalninja.restoid.ui.adapter.ParamsAdapter;
import com.radicalninja.restoid.util.Ln;

public class ParamsFragment extends BaseConnectionFragment {

    // Layout
    private RecyclerView mRecyclerView;
    private ParamsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static ParamsFragment newInstance() {
        // TODO: In the future, saved connections will be used here for building new fragments.
        return new ParamsFragment();
    }

    public ParamsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_headers, container, false);

        Ln.e("onCreateView!");
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_headers);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ParamsAdapter(getActivity());
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
                mAdapter.add(new QueryEntry());
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void populateConnectionInfo(Connection connection) {
        Ln.i("Populating Connection Info on ParamsFragment");
        mAdapter.setHeaders(connection.getQuery());
    }
}
