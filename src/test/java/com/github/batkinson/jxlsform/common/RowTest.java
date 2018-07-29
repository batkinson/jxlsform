package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Sheet;
import com.github.batkinson.jxlsform.api.XLSFormException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Iterator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class RowTest {

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
    public void createNoSheet() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("sheet is required");
        new Row(null, 0);
    }

    @Test
    public void createNegativeSheet() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("row number can not be negative");
        new Row(mockSheet, -1);
    }

    @Test
    public void create() {
        int rowNum = 0;
        Row row = new Row(mockSheet, rowNum);
        assertSame(mockSheet, row.getSheet());
        assertEquals(rowNum, row.getRowNumber());
    }

    @Test
    public void addCell() {
        Row row = new Row(mockSheet, 0);
        Cell cell = row.addCell(0, Cell.Type.BLANK, "");
        Iterator<com.github.batkinson.jxlsform.api.Cell> iter = row.iterator();
        assertSame(cell, iter.next());
        assertFalse("expected added cell only", iter.hasNext());
    }

    @Test
    public void getCellByIndex() {
        Row row = new Row(mockSheet, 0);
        for (int i = 0; i < 3; i++) {
            Cell cell = row.addCell(i, Cell.Type.BLANK, "");
            assertSame(cell, row.getCell(i));
        }
    }

    @Test
    public void isHeader() {
        Row row = new Row(mockSheet, 0);
        when(mockSheet.getHeader()).thenReturn(row);
        assertTrue(row.isHeader());
    }

    @Test
    public void getCellByName() {
        int cellNum = 0;
        String cellName = "name";
        when(mockSheet.getHeader()).thenReturn(mockHeader);
        when(mockHeader.getCell(cellName)).thenReturn(mockHeaderCell);
        when(mockHeaderCell.getCellNumber()).thenReturn(cellNum);
        Row row = new Row(mockSheet, 1);
        Cell cell = row.addCell(cellNum, Cell.Type.BLANK, "");
        assertSame(cell, row.getCell(cellName));
    }
}
