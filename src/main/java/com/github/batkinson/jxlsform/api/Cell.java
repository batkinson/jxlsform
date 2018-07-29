package com.github.batkinson.jxlsform.api;

public interface Cell {

    enum Type {NUMERIC, STRING, FORMULA, BLANK, BOOLEAN, ERROR}

    Row getRow();

    int getCellNumber();

    Type getType();

    String getName();

    String getValue();

}
