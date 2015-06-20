package com.radicalninja.restoid.data.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class QueryList extends ArrayList<QueryEntry> {

    public Map<String, String> getQueryMap() {
        Map<String, String> map = new HashMap<>();
        for (QueryEntry entry : this) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}
