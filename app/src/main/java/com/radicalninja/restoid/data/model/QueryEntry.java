package com.radicalninja.restoid.data.model;

import java.io.Serializable;

public class QueryEntry implements Serializable {

    private String mKey;
    private String mValue;

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        this.mKey = key;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        this.mValue = value;
    }

}
