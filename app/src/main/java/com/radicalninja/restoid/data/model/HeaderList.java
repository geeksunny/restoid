package com.radicalninja.restoid.data.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class HeaderList extends ArrayList<HeaderEntry> {

    public HeaderList() { }

    public HeaderList(Collection<? extends HeaderEntry> collection) {
        super(collection);
    }

    public List<HeaderEntry> getEnabledHeaders() {
        List<HeaderEntry> list = new ArrayList<>();
        Iterator<HeaderEntry> iterator = iterator();
        while (iterator.hasNext()) {
            HeaderEntry entry = iterator.next();
            if (entry.isEnabled()) {
                list.add(entry);
            }
        }
        return list;
    }
}
