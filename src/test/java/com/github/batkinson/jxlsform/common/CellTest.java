package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Row;
import com.github.batkinson.jxlsform.api.XLSFormException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Optional;

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
        new Cell(null, "X", Cell.Type.BLANK, "");
    }

    @Test
    public void createNoCol() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("column is required");
        new Cell(mockRow, null, Cell.Type.BLANK, "");
    }

    @Test
    public void createInvalidCol() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("column must be a series of letters");
        new Cell(mockRow, "", Cell.Type.BLANK, "");
    }

    @Test
    public void createNoType() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("type is required");
        new Cell(mockRow, "X", null, "");
    }

    @Test
    public void createNoValue() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("value is required");
        new Cell(mockRow, "X", Cell.Type.BLANK, null);
    }

    @Test
    public void create() {
        String col = "B";
        Cell.Type type = Cell.Type.STRING;
        Object value = "sample value";
        Cell cell = new Cell(mockRow, col, type, value);
        assertSame(mockRow, cell.getRow());
        assertEquals(col, cell.getCol());
        assertEquals(type, cell.getType());
        assertEquals(value, cell.getValue());
    }

    @Test
    public void getName() {
        String col = "X";
        String headerName = "cell-one";
        when(mockRow.getSheet()).thenReturn(mockSheet);
        when(mockSheet.getHeader()).thenReturn(mockHeader);
        when(mockHeader.getCell(col)).thenReturn(Optional.of(mockHeaderCell));
        when(mockHeaderCell.getValue()).thenReturn(headerName);
        Cell cell = new Cell(mockRow, col, Cell.Type.STRING, "sample value");
        assertEquals(headerName, cell.getName());
    }

    @Test
    public void toStringValue() {
        when(mockRow.getRowNumber()).thenReturn(12);
        Cell cell = new Cell(mockRow, "P", Cell.Type.STRING, "sample value");
        assertEquals("cell P12, value 'sample value'", cell.toString());
    }
}
