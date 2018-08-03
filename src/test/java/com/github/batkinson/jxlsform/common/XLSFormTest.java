package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.XLSFormException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Optional;

import static com.github.batkinson.jxlsform.api.XLSForm.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class XLSFormTest {

    @Mock
    private com.github.batkinson.jxlsform.api.Workbook mockWb;

    @Mock
    private com.github.batkinson.jxlsform.api.Sheet mockSurveySheet, mockChoicesSheet, mockSettingsSheet;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test(expected = XLSFormException.class)
    public void createWithNull() {
        new XLSForm(null);
    }

    @Test
    public void createWithoutSheets() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("survey sheet is required");
        new XLSForm(mockWb);
    }

    @Test
    public void createWithoutChoices() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("choices sheet is required");
        when(mockWb.getSheet(SURVEY)).thenReturn(Optional.of(mockSurveySheet));
        new XLSForm(mockWb);
    }

    @Test
    public void createWithRequiredSheets() {
        when(mockWb.getSheet(SURVEY)).thenReturn(Optional.of(mockSurveySheet));
        when(mockWb.getSheet(CHOICES)).thenReturn(Optional.of(mockChoicesSheet));
        XLSForm form = new XLSForm(mockWb);
        assertSame(mockWb, form.getWorkbook());
        assertNotNull(form.getSurvey());
        assertNotNull(form.getChoices());
        assertFalse(form.getSettings().isPresent());
    }

    @Test
    public void createWithStandardSheets() {
        when(mockWb.getSheet(SURVEY)).thenReturn(Optional.of(mockSurveySheet));
        when(mockWb.getSheet(CHOICES)).thenReturn(Optional.of(mockChoicesSheet));
        when(mockWb.getSheet(SETTINGS)).thenReturn(Optional.of(mockSettingsSheet));
        XLSForm form = new XLSForm(mockWb);
        assertSame(mockWb, form.getWorkbook());
        assertNotNull(form.getSurvey());
        assertNotNull(form.getChoices());
        assertNotNull(form.getSettings());
    }
}
