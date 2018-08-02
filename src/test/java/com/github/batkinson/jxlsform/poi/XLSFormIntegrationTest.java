package com.github.batkinson.jxlsform.poi;

import com.github.batkinson.jxlsform.api.Workbook;
import com.github.batkinson.jxlsform.api.XLSForm;
import com.github.batkinson.jxlsform.api.XLSFormException;
import com.github.batkinson.jxlsform.common.XLSFormFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.github.batkinson.jxlsform.api.XLSForm.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class XLSFormIntegrationTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test(expected = XLSFormException.class)
    public void createWithBadStream() {
        new WorkbookFactory().create(new ByteArrayInputStream(new byte[]{}));
    }

    @Test
    public void testSimpleXls() throws IOException {
        assertMinimal(form("simplest.xls"));
    }

    @Test
    public void testSimpleXlsx() throws IOException {
        assertMinimal(form("simplest.xlsx"));
    }

    @Test
    public void testAllSheetsXls() throws IOException {
        assertAllSheets(form("allsheets.xls"));
    }

    @Test
    public void testAllSheetsXlsx() throws IOException {
        assertAllSheets(form("allsheets.xlsx"));
    }

    @Test
    public void testGroupsXlsx() throws IOException {
        assertMinimal(form("groups.xlsx"));
    }

    @Test
    public void testRepeatsXlsx() throws IOException {
        assertMinimal(form("repeats.xlsx"));
    }

    @Test
    public void testGroupsAndRepeatsXlsx() throws IOException {
        assertMinimal(form("groupsandrepeats.xlsx"));
    }

    @Test
    public void testUnclosedGroupXlsx() throws IOException {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("unclosed group or repeat 'group1', (survey sheet, row 2)");
        form("unclosedgroup.xlsx");
    }

    @Test
    public void testEarlyEndGroupXlsx() throws IOException {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("unexpected 'end group', row 3");
        form("earlyendgroup.xlsx");
    }

    @Test
    public void testEarlyEndRepeatXlsx() throws IOException {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("unexpected 'end repeat', row 3");
        form("earlyendrepeat.xlsx");
    }

    private void assertMinimal(XLSForm form) {
        assertNotNull(form.getSurvey());
        assertNotNull(form.getChoices());
        Workbook workbook = form.getWorkbook();
        assertFalse(workbook.getSheet(SETTINGS).isPresent());
        REQUIRED_SHEETS.forEach(s -> assertNotNull(workbook.getSheet(s)));
        assertMinimalHeaders(form);
    }

    private void assertAllSheets(XLSForm form) {
        assertNotNull(form.getSurvey());
        assertNotNull(form.getChoices());
        assertNotNull(form.getSettings());
        STANDARD_SHEETS.forEach(s -> assertNotNull(form.getWorkbook().getSheet(s)));
        assertAllHeaders(form);
    }

    private void assertMinimalHeaders(XLSForm form) {
        assertNotNull(form.getSurvey().getSheet().getHeader());
        assertNotNull(form.getChoices().getHeader());
    }

    private void assertAllHeaders(XLSForm form) {
        assertMinimalHeaders(form);
        assertNotNull(form.getSettings().orElseThrow(
                () -> new RuntimeException("expected settings sheet to exist")).getHeader());
    }

    private XLSForm form(String fileName) throws IOException {
        String dir = fileName.endsWith(".xls") ? "/xls" : "/xlsx";
        try (InputStream stream = XLSFormIntegrationTest.class.getResourceAsStream(String.join("/", dir, fileName))) {
            return new XLSFormFactory().create(new WorkbookFactory().create(stream));
        }
    }
}
