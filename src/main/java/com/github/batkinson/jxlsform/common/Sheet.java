package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.XLSFormException;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class Sheet implements com.github.batkinson.jxlsform.api.Sheet {

    private final com.github.batkinson.jxlsform.api.Workbook workbook;
    private final String name;
    private final Map<Integer, com.github.batkinson.jxlsform.api.Row> rows = new TreeMap<>();
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

    public Row addRow(int row) {
        Row newRow;
        if (header == null) {
            header = newRow = new Header(this, row);
        } else {
            newRow = new Row(this, row);
        }
        rows.put(row, newRow);
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
    public Optional<com.github.batkinson.jxlsform.api.Row> getRow(int row) {
        return Optional.ofNullable(rows.get(row));
    }

    @Override
    public Optional<com.github.batkinson.jxlsform.api.Cell> getCell(String col, int row) {
        return getRow(row).flatMap((r) -> r.getCell(col));
    }

    @Override
    public Iterator<com.github.batkinson.jxlsform.api.Row> iterator() {
        return rows.values().iterator();
    }

    @Override
    public String toString() {
        return name + " sheet";
    }
}
