package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Cell;
import com.github.batkinson.jxlsform.api.Sheet;

import java.util.Iterator;
import java.util.List;

public class Row implements com.github.batkinson.jxlsform.api.Row {

    private final int rowNumber;
    private final List<Cell> cells;
    private Sheet sheet;

    public Row(int rowNumber, List<Cell> cells) {
        this.rowNumber = rowNumber;
        this.cells = cells;
        cells.forEach(c -> c.setRow(this));
    }

    @Override
    public Sheet getSheet() {
        return sheet;
    }

    @Override
    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
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
    public Cell getCell(int index) {
        return cells.get(index);
    }

    @Override
    public Iterator<Cell> iterator() {
        return cells.iterator();
    }
}
