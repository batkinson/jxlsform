package com.github.batkinson.jxlsform.xform;

public class Element {

    private final Item item;
    private final String name;

    public Element(Item item, String name) {
        this.item = item;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
