package com.github.batkinson.jxlsform.poi;

import com.github.batkinson.jxlsform.api.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static com.github.batkinson.jxlsform.api.XLSForm.*;
import static com.github.batkinson.jxlsform.poi.FormLoader.getForm;
import static org.junit.Assert.*;

public class XLSFormIntegrationTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testSimpleXls() throws IOException {
        assertMinimal(getForm("simplest.xls"));
    }

    @Test
    public void testSimpleXlsx() throws IOException {
        assertMinimal(getForm("simplest.xlsx"));
    }

    @Test
    public void testAllSheetsXls() throws IOException {
        assertAllSheets(getForm("allsheets.xls"));
    }

    @Test
    public void testAllSheetsXlsx() throws IOException {
        assertAllSheets(getForm("allsheets.xlsx"));
    }

    @Test
    public void testGroupsXlsx() throws IOException {
        assertMinimal(getForm("groups.xlsx"));
    }

    @Test
    public void testRepeatsXlsx() throws IOException {
        assertMinimal(getForm("repeats.xlsx"));
    }

    @Test
    public void testGroupsAndRepeatsXlsx() throws IOException {
        assertMinimal(getForm("groupsandrepeats.xlsx"));
    }

    @Test
    public void testUnclosedGroupXlsx() throws IOException {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("unclosed group or repeat 'group1', (survey sheet, row 2)");
        getForm("unclosedgroup.xlsx");
    }

    @Test
    public void testEarlyEndGroupXlsx() throws IOException {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("unexpected 'end group', row 3");
        getForm("earlyendgroup.xlsx");
    }

    @Test
    public void testEarlyEndRepeatXlsx() throws IOException {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("unexpected 'end repeat', row 3");
        getForm("earlyendrepeat.xlsx");
    }

    @Test
    public void testChoicesXlsx() throws IOException {
        assertEquals("label", getForm("choices.xlsx")
                .getChoices()
                .getChoiceList("list1")
                .flatMap(list -> list.getChoice("value"))
                .orElseThrow(() -> new XLSFormException("expected choice list1->value"))
                .getLabel()
                .orElse("choice label not present"));
    }

    @Test
    public void testUsedChoicesXlsx() throws IOException {
        for (SurveyItem item : getForm("usedchoices.xlsx").getSurvey()) {
            assertTrue(item instanceof Select);
            Select select = (Select) item;
            assertTrue(select.getChoiceList().iterator().hasNext());
        }
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
        assertNotNull(form.getChoices().getSheet().getHeader());
    }

    private void assertAllHeaders(XLSForm form) {
        assertMinimalHeaders(form);
        assertNotNull(form.getSettings().map(Settings::getSheet).orElseThrow(
                () -> new RuntimeException("expected settings sheet to exist")).getHeader());
    }
}
