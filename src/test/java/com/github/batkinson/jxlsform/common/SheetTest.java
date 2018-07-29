package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.XLSFormException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Iterator;

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
        Row row = sheet.addRow(0);
        assertSame(row, sheet.getHeader());
        Iterator<com.github.batkinson.jxlsform.api.Row> iter = sheet.iterator();
        assertSame(row, iter.next());
        assertFalse("expected only the row added", iter.hasNext());
        sheet.getData().forEach((r) -> fail("data should be empty"));
    }

    @Test
    public void addHeaderAndData() {
        Sheet sheet = new Sheet(mockWb, "don't care");
        Row row1 = sheet.addRow(0), row2 = sheet.addRow(1);
        assertSame(row1, sheet.getHeader());
        Iterator<com.github.batkinson.jxlsform.api.Row> iter = sheet.iterator(), dataIter = sheet.getData().iterator();
        assertSame(row1, iter.next());
        assertSame(row2, iter.next());
        assertFalse("expected only the rows added", iter.hasNext());
        assertSame(row2, dataIter.next());
        assertFalse("expected only second row", dataIter.hasNext());

    }

    @Test
    public void getRow() {
        Sheet sheet = new Sheet(mockWb, "don't care");
        for (int i = 0; i < 10; i++) {
            Row added = sheet.addRow(i);
            assertSame(added, sheet.getRow(i));
        }
    }
}
