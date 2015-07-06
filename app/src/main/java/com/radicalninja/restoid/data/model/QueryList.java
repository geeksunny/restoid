package com.radicalninja.restoid.data.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class QueryList extends ArrayList<QueryEntry> {

    public QueryList() { }

    public QueryList(Collection<? extends QueryEntry> collection) {
        super(collection);
    }

    public Map<String, String> getQueryMap() {
        Map<String, String> map = new HashMap<>();
        for (QueryEntry entry : this) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}
