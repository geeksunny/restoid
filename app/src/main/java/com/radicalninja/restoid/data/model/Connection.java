package com.radicalninja.restoid.data.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.radicalninja.restoid.ui.fragment.RequestFragment;

@DatabaseTable
public class Connection {

    @DatabaseField
    String mUrl = "";

    RequestFragment.RequestType mRequestType = RequestFragment.RequestType.GET;

    RequestFragment.ResultType mResultType = RequestFragment.ResultType.RAW;

    HeaderList mHeaders = new HeaderList();

    QueryList mQuery = new QueryList();

    String bodyText = "";

    public String getUrl() {
        return mUrl;
    }

    public void setmUrl(String url) {
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

    public void getQuery(QueryList params) {
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
}
