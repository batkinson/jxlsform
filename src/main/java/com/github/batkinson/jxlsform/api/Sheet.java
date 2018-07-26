package com.github.batkinson.jxlsform.api;

public interface Sheet extends Iterable<Row> {

    XLSForm getForm();

    void setForm(XLSForm form);

    String getName();

    Row getHeader();

    Iterable<Row> getData();

    Row getRow(int index);
}
