package com.github.batkinson.jxlsform.poi;

import com.github.batkinson.jxlsform.api.Workbook;
import com.github.batkinson.jxlsform.api.XLSForm;
import com.github.batkinson.jxlsform.api.XLSFormException;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.github.batkinson.jxlsform.api.XLSForm.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class XLSFormIntegrationTest {

    @Test(expected = XLSFormException.class)
    public void createWithBadStream() {
        new XLSFormFactory().create(new ByteArrayInputStream(new byte[]{}));
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
        STANDARD.forEach(s -> assertNotNull(form.getWorkbook().getSheet(s)));
        assertAllHeaders(form);
    }

    private void assertMinimalHeaders(XLSForm form) {
        assertNotNull(form.getSurvey().getHeader());
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
            return new XLSFormFactory().create(stream);
        }
    }
}
