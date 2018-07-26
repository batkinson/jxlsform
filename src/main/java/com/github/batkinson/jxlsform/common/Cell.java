package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Row;
import com.github.batkinson.jxlsform.api.XLSFormException;

public class Cell implements com.github.batkinson.jxlsform.api.Cell {

    private final int cellNumber;
    private final Type type;
    private final Object value;
    private Row row;

    public Cell(int cellNumber, Type type, Object value) {
        this.cellNumber = cellNumber;
        this.type = type;
        this.value = value;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Row getRow() {
        if (row == null) {
            throw new XLSFormException("cell not associated with row");
        }
        return row;
    }

    @Override
    public void setRow(Row row) {
        this.row = row;
    }

    @Override
    public int getCellNumber() {
        return cellNumber;
    }

    @Override
    public String getName() {
        return row.getSheet().getHeader().getCell(getCellNumber()).toString();
    }

    @Override
    public String getValue() {
        return value.toString();
    }
}
