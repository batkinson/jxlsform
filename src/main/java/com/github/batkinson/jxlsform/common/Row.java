package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.XLSFormException;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class Row implements com.github.batkinson.jxlsform.api.Row {

    private final com.github.batkinson.jxlsform.api.Sheet sheet;
    private final int rowNumber;
    private final Map<String, com.github.batkinson.jxlsform.api.Cell> cells = new TreeMap<>();

    public Row(com.github.batkinson.jxlsform.api.Sheet sheet, int rowNumber) {
        if (sheet == null) {
            throw new XLSFormException("sheet is required");
        }
        if (rowNumber <= 0) {
            throw new XLSFormException("row number must be greater than zero");
        }
        this.sheet = sheet;
        this.rowNumber = rowNumber;
    }

    public Cell addCell(String colName, Cell.Type cellType, Object cellValue) {
        Cell newCell = new Cell(this, colName, cellType, cellValue);
        cells.put(colName, newCell);
        return newCell;
    }

    @Override
    public com.github.batkinson.jxlsform.api.Sheet getSheet() {
        return sheet;
    }

    @Override
    public int getRowNumber() {
        return rowNumber;
    }

    @Override
    public boolean isHeader() {
        return this == getSheet().getHeader();
    }

    @Override
    public Optional<com.github.batkinson.jxlsform.api.Cell> getCell(String col) {
        return Optional.ofNullable(cells.get(col));
    }

    @Override
    public Optional<com.github.batkinson.jxlsform.api.Cell> getCellByHeader(String name) {
        return getSheet()
                .getHeader()
                .getCellByHeader(name)
                .map(com.github.batkinson.jxlsform.api.Cell::getCol)
                .flatMap(this::getCell);
    }

    @Override
    public Iterator<com.github.batkinson.jxlsform.api.Cell> iterator() {
        return cells.values().iterator();
    }

    @Override
    public String toString() {
        return "row " + rowNumber;
    }
}
