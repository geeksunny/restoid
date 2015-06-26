package com.radicalninja.restoid.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "restoid.db";

    private static final int DATABASE_VERSION = 1;

    // DAO objects
    private HashMap<Class, Dao> daoMap;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        daoMap = new HashMap<Class, Dao>();
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
//        try {
//            TableUtils.createTable(connectionSource, Medications.class);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    protected <T, K> Dao<T, K> getDao(Class<T> modelClass, Class<K> keyClass) {

        Dao<T, K> dao = daoMap.get(modelClass);
        if (dao == null) {
            try {
                dao = getDao(modelClass);
                daoMap.put(modelClass, dao);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dao;
    }
}