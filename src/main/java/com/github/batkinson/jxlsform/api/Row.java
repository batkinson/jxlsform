package com.github.batkinson.jxlsform.api;

import java.util.Optional;

public interface Row extends Streamable<Cell> {

    Sheet getSheet();

    int getRowNumber();

    boolean isHeader();

    Optional<Cell> getCell(String col);

    Optional<Cell> getCellByHeader(String name);

}
