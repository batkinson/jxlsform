package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.XLSFormException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Collections.EMPTY_LIST;

public class Sheet implements com.github.batkinson.jxlsform.api.Sheet {

    private final com.github.batkinson.jxlsform.api.Workbook workbook;
    private final String name;
    private final List<com.github.batkinson.jxlsform.api.Row> rows = new ArrayList<>();
    private com.github.batkinson.jxlsform.api.Row header;

    Sheet(com.github.batkinson.jxlsform.api.Workbook workbook, String name) {
        if (workbook == null) {
            throw new XLSFormException("workbook is required");
        }
        if (name == null) {
            throw new XLSFormException("sheet name is required");
        }
        this.workbook = workbook;
        this.name = name;
    }

    public Row addRow(int rowNumber) {
        Row newRow = new Row(this, rowNumber);
        rows.add(newRow);
        if (header == null) {
            header = newRow;
        }
        return newRow;
    }

    @Override
    public com.github.batkinson.jxlsform.api.Workbook getWorkbook() {
        return workbook;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public com.github.batkinson.jxlsform.api.Row getHeader() {
        return header;
    }

    @Override
    public Iterable<com.github.batkinson.jxlsform.api.Row> getData() {
        return rows.size() < 2 ? EMPTY_LIST : rows.subList(1, rows.size());
    }

    @Override
    public com.github.batkinson.jxlsform.api.Row getRow(int index) {
        return rows.get(index);
    }

    @Override
    public Iterator<com.github.batkinson.jxlsform.api.Row> iterator() {
        return rows.iterator();
    }
}
