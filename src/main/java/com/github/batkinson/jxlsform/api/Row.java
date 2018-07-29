package com.github.batkinson.jxlsform.api;

public interface Row extends Iterable<Cell> {

    Sheet getSheet();

    int getRowNumber();

    boolean isHeader();

    Cell getCell(int index);

    Cell getCell(String name);
}
