package com.github.batkinson.jxlsform.api;

public interface Sheet extends Iterable<Row> {

    Workbook getWorkbook();

    String getName();

    Row getHeader();

    Iterable<Row> getData();

    Row getRow(int index);
}
