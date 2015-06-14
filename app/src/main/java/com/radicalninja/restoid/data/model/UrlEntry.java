package com.radicalninja.restoid.data.model;

import android.net.Uri;

public class UrlEntry {

    private String urlBase;
    private String urlPath;

    public UrlEntry(String urlBase, String urlPath) {
        this.urlBase = urlBase;
        this.urlPath = urlPath;
    }

    public UrlEntry(String url) {
        Uri uri = Uri.parse(url);

        urlBase = String.format("%s://%s", uri.getScheme(), uri.getAuthority());

        StringBuilder sb = new StringBuilder();
        if (uri.getPath() != null && uri.getPath().length() > 1) {
            sb.append(uri.getPath().substring(1));
        }
        if (uri.getQuery() != null) {
            sb.append(uri.getQuery());
        }
        if (uri.getFragment() != null) {
            sb.append(uri.getFragment());
        }
        urlPath = sb.toString();
    }

    public String getUrlBase() {
        return urlBase;
    }

    public void setUrlBase(String urlBase) {
        this.urlBase = urlBase;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }
}
