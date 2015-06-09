package com.radicalninja.restoid.data.model;

public class HeaderEntry {

    private boolean mIsEnabled = false;
    private String mKey;
    private String mValue;

    public boolean isEnabled() {
        return mIsEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.mIsEnabled = isEnabled;
    }

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
