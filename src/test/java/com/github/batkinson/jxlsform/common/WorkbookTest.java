package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.XLSFormException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

public class WorkbookTest {

    @Mock
    private com.github.batkinson.jxlsform.api.Workbook mockWb;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void create() {
        new Workbook();
    }

    @Test
    public void addSheetNoName() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("sheet name is required");
        Workbook workbook = new Workbook();
        workbook.addSheet(null);
    }

    @Test
    public void addSheetWithNameConflict() {
        String name = "sheet1";
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("a sheet named '" + name + "' already exists");
        Workbook workbook = new Workbook();
        workbook.addSheet(name);
        workbook.addSheet(name);
    }

    @Test
    public void addSheet() {
        String name = "sample";
        Workbook workbook = new Workbook();
        Sheet sheet = workbook.addSheet(name);
        assertNotNull(sheet);
        assertSame(sheet, workbook.getSheet(name).orElseThrow(() -> new XLSFormException("expected sample sheet")));
        assertEquals(name, sheet.getName());
    }

    @Test
    public void iterator() {
        List<String> names = asList("sheet1", "sheet2", "sheet3");
        Workbook workbook = new Workbook();
        workbook.forEach((s) -> fail("should not iterate, no sheets"));
        List<Sheet> added = new ArrayList<>();
        for (String name : names) {
            added.add(workbook.addSheet(name));
            int index = 0;
            for (com.github.batkinson.jxlsform.api.Sheet s : workbook) {
                assertSame(added.get(index), s);
                index++;
            }
        }
    }
}
