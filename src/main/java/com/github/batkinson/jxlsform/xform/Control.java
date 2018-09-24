package com.github.batkinson.jxlsform.xform;

public class Control {

    private final Item item;
    private final String name;

    public Control(Item item, String name) {
        this.item = item;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return getName();
    }
}
