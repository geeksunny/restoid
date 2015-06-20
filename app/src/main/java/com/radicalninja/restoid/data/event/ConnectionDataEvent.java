package com.radicalninja.restoid.data.event;

import com.radicalninja.restoid.data.model.Connection;

public class ConnectionDataEvent {

    public static class ReadRequest { }

    public static class ReadResponse {
        public Connection connection;

        public ReadResponse(Connection connection) {
            this.connection = connection;
        }
    }

}
