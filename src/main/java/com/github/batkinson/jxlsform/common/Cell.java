package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.XLSFormException;

public class Cell implements com.github.batkinson.jxlsform.api.Cell {

    private final com.github.batkinson.jxlsform.api.Row row;
    private final String col;
    private final Type type;
    private final Object value;

    Cell(com.github.batkinson.jxlsform.api.Row row, String col, Type type, Object value) {
        if (row == null) {
            throw new XLSFormException("row is required");
        }
        if (col == null) {
            throw new XLSFormException("column is required");
        }
        if (!col.matches("[a-zA-Z]+")) {
            throw new XLSFormException("column must be a series of letters");
        }
        if (type == null) {
            throw new XLSFormException("type is required");
        }
        if (value == null) {
            throw new XLSFormException("value is required");
        }
        this.row = row;
        this.col = col;
        this.type = type;
        this.value = value;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public com.github.batkinson.jxlsform.api.Row getRow() {
        return row;
    }

    @Override
    public String getCol() {
        return col;
    }

    @Override
    public String getName() {
        return row.getSheet().getHeader()
                .getCell(getCol())
                .orElseThrow(() -> new XLSFormException("cell for header " + getCol() + " not found"))
                .getValue();
    }

    @Override
    public String getValue() {
        return value.toString();
    }

    @Override
    public String toString() {
        return String.format("cell %s%d, value '%s'", getCol(), row.getRowNumber(), getValue());
    }
}
