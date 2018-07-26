package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Row;
import com.github.batkinson.jxlsform.api.XLSForm;

import java.util.Iterator;
import java.util.List;

public class Sheet implements com.github.batkinson.jxlsform.api.Sheet {

    private final String name;
    private final Row header;
    private final List<Row> data;
    private final List<Row> rows;
    private XLSForm form;

    public Sheet(String name, List<Row> rows) {
        this.name = name;
        this.rows = rows;
        this.header = rows.get(0);
        this.data = rows.subList(1, rows.size());
        rows.forEach(r -> r.setSheet(this));
    }

    @Override
    public XLSForm getForm() {
        return form;
    }

    @Override
    public void setForm(XLSForm form) {
        this.form = form;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Row getHeader() {
        return header;
    }

    @Override
    public Iterable<Row> getData() {
        return data;
    }

    @Override
    public Row getRow(int index) {
        return rows.get(index);
    }

    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }
}
