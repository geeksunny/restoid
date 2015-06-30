package com.radicalninja.restoid.ui.listener;

import com.radicalninja.restoid.application.App;
import com.radicalninja.restoid.data.event.ConnectionDataEvent;
import com.radicalninja.restoid.data.model.Connection;

public class ConnectionChangedListener {

    public void didChange(Connection connection) {
        if (!connection.isChanged()) {
            App.getOttoBus().post(new ConnectionDataEvent.ChangedAlert());
            connection.setIsChanged(true);
        }
    }
}
