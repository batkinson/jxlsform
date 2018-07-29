package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.XLSFormException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Row implements com.github.batkinson.jxlsform.api.Row {

    private final com.github.batkinson.jxlsform.api.Sheet sheet;
    private final int rowNumber;
    private final List<com.github.batkinson.jxlsform.api.Cell> cells = new ArrayList<>();

    public Row(com.github.batkinson.jxlsform.api.Sheet sheet, int rowNumber) {
        if (sheet == null) {
            throw new XLSFormException("sheet is required");
        }
        if (rowNumber < 0) {
            throw new XLSFormException("row number can not be negative");
        }
        this.sheet = sheet;
        this.rowNumber = rowNumber;
    }

    public Cell addCell(int cellNum, Cell.Type cellType, Object cellValue) {
        Cell newCell = new Cell(this, cellNum, cellType, cellValue);
        cells.add(newCell);
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
    public com.github.batkinson.jxlsform.api.Cell getCell(int index) {
        return cells.get(index);
    }

    @Override
    public com.github.batkinson.jxlsform.api.Cell getCell(String name) {
        return getCell(getSheet().getHeader().getCell(name).getCellNumber());
    }

    @Override
    public Iterator<com.github.batkinson.jxlsform.api.Cell> iterator() {
        return cells.iterator();
    }
}
