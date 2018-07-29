package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Row;
import com.github.batkinson.jxlsform.api.XLSFormException;

public class Cell implements com.github.batkinson.jxlsform.api.Cell {

    private final Row row;
    private final int cellNumber;
    private final Type type;
    private final Object value;

    Cell(Row row, int cellNumber, Type type, Object value) {
        if (row == null) {
            throw new XLSFormException("row is required");
        }
        if (cellNumber < 0) {
            throw new XLSFormException("cell number can not be negative");
        }
        if (type == null) {
            throw new XLSFormException("type is required");
        }
        if (value == null) {
            throw new XLSFormException("value is required");
        }
        this.row = row;
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
        return row;
    }

    @Override
    public int getCellNumber() {
        return cellNumber;
    }

    @Override
    public String getName() {
        return row.getSheet().getHeader().getCell(getCellNumber()).getValue();
    }

    @Override
    public String getValue() {
        return value.toString();
    }
}
