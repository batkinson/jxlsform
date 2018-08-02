package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.XLSFormException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

public class SheetTest {

    @Mock
    private com.github.batkinson.jxlsform.api.Workbook mockWb;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void createWithNullWorkbook() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("workbook is required");
        new Sheet(null, "no matter");
    }

    @Test
    public void createWithNullSheetName() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("sheet name is required");
        new Sheet(mockWb, null);
    }

    @Test
    public void create() {
        String name = "test-create";
        Sheet sheet = new Sheet(mockWb, name);
        assertSame(mockWb, sheet.getWorkbook());
        assertEquals(name, sheet.getName());
    }

    @Test
    public void addHeader() {
        Sheet sheet = new Sheet(mockWb, "don't care");
        Row row = sheet.addRow(1);
        assertSame(row, sheet.getHeader());
        Iterator<com.github.batkinson.jxlsform.api.Row> iter = sheet.iterator();
        assertSame(row, iter.next());
        assertFalse("expected only the row added", iter.hasNext());
        StreamSupport.stream(sheet.spliterator(), false)
                .filter(r -> !r.isHeader())
                .forEach(r -> fail("data should be empty"));
    }

    @Test
    public void addHeaderAndData() {
        Sheet sheet = new Sheet(mockWb, "don't care");
        Row row1 = sheet.addRow(1), row2 = sheet.addRow(2);
        assertSame(row1, sheet.getHeader());
        Iterator<com.github.batkinson.jxlsform.api.Row> iter = sheet.iterator();
        assertSame(row1, iter.next());
        assertSame(row2, iter.next());
        assertFalse("expected only the rows added", iter.hasNext());
    }

    @Test
    public void getRow() {
        Sheet sheet = new Sheet(mockWb, "don't care");
        for (int i = 1; i <= 10; i++) {
            Row added = sheet.addRow(i);
            Optional<com.github.batkinson.jxlsform.api.Row> maybeRow = sheet.getRow(i);
            assertTrue(maybeRow.isPresent());
            assertSame(added, maybeRow.get());
        }
    }

    @Test
    public void getCellExists() {
        List<Integer> rows = asList(1, 2, 3);
        List<String> cols = asList("A", "B", "D");
        Sheet sheet = new Sheet(mockWb, "don't care");

        // load cells with their addresses C1 has value 'C1'
        for (Integer rowNum : rows) {
            Row row = sheet.addRow(rowNum);
            for (String col : cols) {
                row.addCell(col, Cell.Type.STRING, col + rowNum);
            }
        }

        // verify all cells have appropriate contents when fetched
        for (Integer row : rows) {
            for (String col : cols) {
                Optional<com.github.batkinson.jxlsform.api.Cell> maybeCell = sheet.getCell(col, row);
                assertTrue(maybeCell.isPresent());
                assertEquals(col + row, maybeCell.get().getValue());
            }
        }
    }

    @Test
    public void getCellNotExists() {
        assertFalse(
                new Sheet(mockWb, "don't care")
                        .getCell("A", 1)
                        .isPresent());
    }
}
