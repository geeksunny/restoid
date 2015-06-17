package com.radicalninja.restoid.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class HeaderEntry implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mKey);
        dest.writeString(mValue);
        dest.writeInt(mIsEnabled ? 1 : 0);
    }
}
