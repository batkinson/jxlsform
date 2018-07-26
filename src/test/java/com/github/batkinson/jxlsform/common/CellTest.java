package com.github.batkinson.jxlsform.common;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CellTest {

    @Mock
    com.github.batkinson.jxlsform.api.Row mockRow, mockHeader;

    @Mock
    com.github.batkinson.jxlsform.api.Sheet mockSheet;

    @Mock
    com.github.batkinson.jxlsform.api.Cell mockCell;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private int cellNum = 0;
    private Cell.Type type = Cell.Type.STRING;
    private String value = "a value";
    private String name = "a name";

    @Test
    public void createSimple() {
        Cell c = new Cell(cellNum, type, value);
        assertEquals(0, c.getCellNumber());
        assertEquals(type, c.getType());
        assertEquals(value, c.getValue());
    }

    @Test
    public void getName() {
        when(mockRow.getSheet()).thenReturn(mockSheet);
        when(mockSheet.getHeader()).thenReturn(mockHeader);
        when(mockHeader.getCell(cellNum)).thenReturn(mockCell);
        when(mockCell.toString()).thenReturn(name);
        Cell c = new Cell(cellNum, type, value);
        c.setRow(mockRow);
        assertEquals(name, c.getName());
    }

    @Test
    public void getRow() {

    }
}
