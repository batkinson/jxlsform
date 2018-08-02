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
import java.util.Optional;

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
        new Row(null, 1);
    }

    @Test
    public void createZeroRowNum() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("row number must be greater than zero");
        new Row(mockSheet, 0);
    }

    @Test
    public void createNegativeRowNum() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("row number must be greater than zero");
        new Row(mockSheet, -1);
    }

    @Test
    public void create() {
        int rowNum = 1;
        Row row = new Row(mockSheet, rowNum);
        assertSame(mockSheet, row.getSheet());
        assertEquals(rowNum, row.getRowNumber());
    }

    @Test
    public void addCell() {
        Row row = new Row(mockSheet, 1);
        Cell cell = row.addCell("X", Cell.Type.BLANK, "");
        Iterator<com.github.batkinson.jxlsform.api.Cell> iter = row.iterator();
        assertSame(cell, iter.next());
        assertFalse("expected added cell only", iter.hasNext());
    }

    @Test
    public void getCellByCol() {
        char colName = 'A';
        Row row = new Row(mockSheet, 1);
        for (int i = 0; i < 3; i++) {
            String colStr = String.valueOf((char) (colName + 1));
            Cell cell = row.addCell(colStr, Cell.Type.BLANK, "");
            Optional<com.github.batkinson.jxlsform.api.Cell> maybeCell = row.getCell(colStr);
            assertTrue(maybeCell.isPresent());
            assertSame(cell, maybeCell.get());
        }
    }

    @Test
    public void isHeader() {
        Row row = new Row(mockSheet, 1);
        when(mockSheet.getHeader()).thenReturn(row);
        assertTrue(row.isHeader());
    }

    @Test
    public void getCellByName() {
        String colName = "A";
        String headerName = "name";
        when(mockSheet.getHeader()).thenReturn(mockHeader);
        when(mockHeader.getCellByHeader(headerName)).thenReturn(Optional.of(mockHeaderCell));
        when(mockHeaderCell.getCol()).thenReturn(colName);
        Row row = new Row(mockSheet, 1);
        Cell cell = row.addCell(colName, Cell.Type.BLANK, "");
        Optional<com.github.batkinson.jxlsform.api.Cell> maybeCell = row.getCellByHeader(headerName);
        assertTrue(maybeCell.isPresent());
        assertSame(cell, maybeCell.get());
    }

    @Test
    public void testToString() {
        assertEquals("row 1", new Row(mockSheet, 1).toString());
    }
}
