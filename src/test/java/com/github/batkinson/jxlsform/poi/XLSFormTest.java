package com.github.batkinson.jxlsform.poi;

import com.github.batkinson.jxlsform.api.XLSForm;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static com.github.batkinson.jxlsform.api.XLSForm.SETTINGS;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class XLSFormTest {

    @Test
    public void testSimpleXls() throws IOException {
        assertMinimalSheets(loadTestForm("simplest.xls"));
    }

    @Test
    public void testSimpleXlsx() throws IOException {
        assertMinimalSheets(loadTestForm("simplest.xlsx"));
    }

    @Test
    public void testAllSheetsXls() throws IOException {
        assertAllSheets(loadTestForm("allsheets.xls"));
    }

    @Test
    public void testAllSheetsXlsx() throws IOException {
        assertAllSheets(loadTestForm("allsheets.xlsx"));
    }

    private void assertMinimalSheets(XLSForm form) {
        assertNotNull(form.getSurvey());
        assertNotNull(form.getChoices());
        assertFalse(form.hasSheet(SETTINGS));
    }

    private void assertAllSheets(XLSForm form) {
        assertNotNull(form.getSurvey());
        assertNotNull(form.getChoices());
        assertNotNull(form.getSettings());
    }

    private XLSForm loadTestForm(String fileName) throws IOException {
        String dir = fileName.endsWith(".xls") ? "/xls" : "/xlsx";
        try (InputStream stream = XLSFormTest.class.getResourceAsStream(String.join("/", dir, fileName))) {
            return new XLSFormFactory().create(stream);
        }
    }
}
