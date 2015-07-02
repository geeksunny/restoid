package com.radicalninja.restoid.data.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.radicalninja.restoid.data.model.Connection;

import java.util.List;

public class ConnectionManager extends DatabaseManager<Connection, Integer> {

    public ConnectionManager(Context context) {
        super(context);
    }

    @Override
    public Dao<Connection, Integer> getDao() {
        return getDbHelper().getDao(Connection.class, Integer.class);
    }

    public List<Connection> getAllConnections() {
        return getObjects();
    }

    public Connection getConnection(Integer id) {
        return getObject(id);
    }

    public SqlResult saveConnection(Connection connection) {
        return saveObject(connection);
    }

    public boolean deleteConnection(Connection connection) {
        return deleteObject(connection);
    }

}
