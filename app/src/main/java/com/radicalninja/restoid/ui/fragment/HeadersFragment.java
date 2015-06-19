package com.radicalninja.restoid.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.radicalninja.restoid.R;
import com.radicalninja.restoid.data.model.HeaderEntry;
import com.radicalninja.restoid.data.model.HeaderList;
import com.radicalninja.restoid.ui.adapter.HeadersAdapter;
import com.radicalninja.restoid.util.Ln;

public class HeadersFragment extends Fragment {

    private static final String BUNDLE_HEADERS = "Headers";

    // Layout
    private RecyclerView mRecyclerView;
    private HeadersAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    // Data
    private HeaderList mHeaders = new HeaderList();   // TODO: Should we have a local copy stored?

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
        View rootView = inflater.inflate(R.layout.fragment_headers, container, false);

        Ln.e("onCreateView!");
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_headers);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new HeadersAdapter(getActivity(), mHeaders);
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
            mAdapter.add(new HeaderEntry());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setHeaders(HeaderList headerList) {
        mHeaders = headerList;
    }

}
