package com.radicalninja.restoid.data.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DatabaseManager<T, ID> {

    private static DatabaseHelper sDbHelper;

    public DatabaseManager(Context context) {
        if (sDbHelper == null) {
            sDbHelper = new DatabaseHelper(context);
        }
    }

    public static DatabaseHelper getDbHelper() {
        return sDbHelper;
    }

    public abstract Dao<T, ID> getDao();

    // TODO: basic crud methods here for managers to use
    protected List<T> getObjects() {
        try {
            return getDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    protected T getObject(ID id) {
        try {
            return getDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected SqlResult saveObject(T object) {
        try {
            Dao.CreateOrUpdateStatus status = getDao().createOrUpdate(object);
            if (status.isCreated()) {
                return SqlResult.CREATED;
            } else if (status.isUpdated()) {
                return SqlResult.UPDATED;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return SqlResult.NONE;
    }

    protected boolean deleteObject(T object) {
        try {
            return getDao().delete(object) > 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
