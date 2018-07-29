package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Row;
import com.github.batkinson.jxlsform.api.XLSFormException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

public class CellTest {

    @Mock
    private Row mockRow;

    @Mock
    private Sheet mockSheet;

    @Mock
    private Row mockHeader;

    @Mock
    private Cell mockHeaderCell;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void createNoRow() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("row is required");
        new Cell(null, 0, Cell.Type.BLANK, "");
    }

    @Test
    public void createNegativeCellNum() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("cell number can not be negative");
        new Cell(mockRow, -1, Cell.Type.BLANK, "");
    }

    @Test
    public void createNoType() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("type is required");
        new Cell(mockRow, 0, null, "");
    }

    @Test
    public void createNoValue() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("value is required");
        new Cell(mockRow, 0, Cell.Type.BLANK, null);
    }

    @Test
    public void create() {
        int num = 0;
        Cell.Type type = Cell.Type.STRING;
        Object value = "sample value";
        Cell cell = new Cell(mockRow, num, type, value);
        assertSame(mockRow, cell.getRow());
        assertEquals(num, cell.getCellNumber());
        assertEquals(type, cell.getType());
        assertEquals(value, cell.getValue());
    }

    @Test
    public void getName() {
        int cellNum = 0;
        String headerName = "cell-one";
        when(mockRow.getSheet()).thenReturn(mockSheet);
        when(mockSheet.getHeader()).thenReturn(mockHeader);
        when(mockHeader.getCell(cellNum)).thenReturn(mockHeaderCell);
        when(mockHeaderCell.getValue()).thenReturn(headerName);
        Cell cell = new Cell(mockRow, cellNum, Cell.Type.STRING, "sample value");
        assertEquals(headerName, cell.getName());
    }
}
