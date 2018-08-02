package com.github.batkinson.jxlsform.api;

import java.util.Optional;

public interface Sheet extends Iterable<Row> {

    Workbook getWorkbook();

    String getName();

    Row getHeader();

    Optional<Row> getRow(int index);

    Optional<Cell> getCell(String col, int row);

}
