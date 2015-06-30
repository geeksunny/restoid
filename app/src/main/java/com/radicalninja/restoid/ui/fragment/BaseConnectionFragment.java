package com.radicalninja.restoid.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.radicalninja.restoid.application.App;
import com.radicalninja.restoid.data.event.ConnectionDataEvent;
import com.radicalninja.restoid.data.model.Connection;
import com.radicalninja.restoid.ui.listener.ConnectionChangedListener;
import com.radicalninja.restoid.util.Ln;
import com.squareup.otto.Subscribe;

public abstract class BaseConnectionFragment extends Fragment {

    private OttoDelegate mDelegate = new OttoDelegate();
    private Connection mConnection;
    private ConnectionChangedListener mConnectionChangedListener = new ConnectionChangedListener();

    private class OttoDelegate {
        public void register() {
            App.getOttoBus().register(this);
        }
        public void unregister() {
            App.getOttoBus().unregister(this);
        }
        @Subscribe
        public void connectionResponseReceived(ConnectionDataEvent.ReadResponse response) {
            mConnection = response.connection;
            populateConnectionInfo(mConnection);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mDelegate.register();
        if (mConnection == null) {
            //Toast.makeText(getActivity(), "mConnection is null", Toast.LENGTH_LONG).show();
            App.getOttoBus().post(new ConnectionDataEvent.ReadRequest());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mDelegate.unregister();
    }

    protected Connection getConnection() {
        return mConnection;
    }

    protected abstract void populateConnectionInfo(Connection connection);

    protected void onConnectionChanged() {
        mConnectionChangedListener.didChange(mConnection);
    }
}
