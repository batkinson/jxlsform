package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Cell;
import com.github.batkinson.jxlsform.api.Sheet;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class RowTest {

    @Mock
    private com.github.batkinson.jxlsform.api.Row mockRow;

    @Mock
    private Sheet mockSheet;

    @Mock
    private Cell mockCell1, mockCell2;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private List<Cell> cells;

    @Before
    public void setupList() {
        cells = asList(mockCell1, mockCell2);
    }

    @Test
    public void creation() {
        int rowNum = 0;
        Row r = new Row(rowNum, cells);
        assertEquals(rowNum, r.getRowNumber());
        assertSame(mockCell1, r.getCell(0));
        assertSame(mockCell2, r.getCell(1));
    }

    @Test
    public void setSheet() {
        int rowNum = 0;
        Row r = new Row(rowNum, cells);
        r.setSheet(mockSheet);
        assertSame(mockSheet, r.getSheet());
    }

    @Test
    public void isHeader() {
        int rowNum = 0;
        Row r = new Row(rowNum, cells);
        r.setSheet(mockSheet);
        when(mockSheet.getHeader()).thenReturn(r);
        assertTrue("row is header, but reports otherwise", r.isHeader());
        when(mockSheet.getHeader()).thenReturn(mockRow);
        assertFalse("row is not header, but reports as one", r.isHeader());
    }

    @Test
    public void iterator() {
        int rowNum = 0;
        Row r = new Row(rowNum, cells);
        assertThat("lists expected to be equivalent", cells,
                is(stream(r.spliterator(), false).collect(toList())));
    }

    @Test
    public void getCellByName() {
        int rowNum = 0;
        String first = "first", second = "second";
        Row r = new Row(rowNum, cells);
        r.setSheet(mockSheet);
        when(mockSheet.getHeader()).thenReturn(mockRow);
        when(mockRow.getCell(first)).thenReturn(mockCell1);
        when(mockRow.getCell(second)).thenReturn(mockCell2);
        when(mockCell1.getCellNumber()).thenReturn(0);
        when(mockCell2.getCellNumber()).thenReturn(1);
        assertSame(mockCell1, r.getCell(first));
        assertSame(mockCell2, r.getCell(second));
    }
}
