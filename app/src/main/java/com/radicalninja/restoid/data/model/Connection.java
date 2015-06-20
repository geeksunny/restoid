package com.radicalninja.restoid.data.model;

import com.radicalninja.restoid.ui.fragment.RequestFragment;

import java.util.ArrayList;
import java.util.List;

public class Connection {

    String mUrl = "";
    RequestFragment.RequestType mRequestType = RequestFragment.RequestType.GET;
    RequestFragment.ResultType mResultType = RequestFragment.ResultType.RAW;
    HeaderList mHeaders = new HeaderList();
    List<ParamsEntry> mParams = new ArrayList<>();
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

    public List<ParamsEntry> getParams() {
        return mParams;
    }

    public void setParams(List<ParamsEntry> params) {
        this.mParams = params;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }
}
