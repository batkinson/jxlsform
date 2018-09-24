package com.github.batkinson.jxlsform.xform;

import com.github.batkinson.jxlsform.api.XLSFormException;

public class Binding {

    private final Item item;
    private final String name;

    public Binding(Item item, String name) {
        this.item = item;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return item.getType().orElseThrow(() -> new XLSFormException("type was expected, but not found"));
    }
}
