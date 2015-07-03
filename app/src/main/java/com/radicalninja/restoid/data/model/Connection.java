package com.radicalninja.restoid.data.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.radicalninja.restoid.ui.fragment.RequestFragment;

@DatabaseTable(tableName = Connection.TABLE_NAME)
public class Connection {

    public static final String TABLE_NAME = "Connections";
    public static final int DEFAULT_TIMEOUT = 20;

    @DatabaseField(generatedId = true)
    Integer _id;

    @DatabaseField(columnName = "name")
    String mName = "";

    @DatabaseField(columnName = "url")
    String mUrl = "";

    @DatabaseField(columnName = "request_type")
    RequestFragment.RequestType mRequestType = RequestFragment.RequestType.GET;

    @DatabaseField(columnName = "results_type")
    RequestFragment.ResultType mResultType = RequestFragment.ResultType.RAW;

    @DatabaseField(columnName = "headers", dataType = DataType.SERIALIZABLE)
    HeaderList mHeaders = new HeaderList();

    @DatabaseField(columnName = "query", dataType = DataType.SERIALIZABLE)
    QueryList mQuery = new QueryList();

    @DatabaseField(columnName = "body")
    String bodyText = "";

    @DatabaseField(columnName = "timeout")
    Integer mTimeout = DEFAULT_TIMEOUT;

    boolean isChanged = false;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public RequestFragment.RequestType getRequestType() {
        return mRequestType;
    }

    public void setRequestType(RequestFragment.RequestType requestType) {
        this.mRequestType = requestType;
    }

    public RequestFragment.ResultType getResultType() {
        return mResultType;
    }

    public void setResultType(RequestFragment.ResultType resultType) {
        this.mResultType = resultType;
    }

    public HeaderList getHeaders() {
        return mHeaders;
    }

    public void setHeaders(HeaderList headers) {
        this.mHeaders = headers;
    }

    public QueryList getQuery() {
        return mQuery;
    }

    public void setQuery(QueryList params) {
        this.mQuery = params;
    }

    public boolean hasQuery() {
        return mQuery.size() > 0;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setIsChanged(boolean isChanged) {
        this.isChanged = isChanged;
    }
}
