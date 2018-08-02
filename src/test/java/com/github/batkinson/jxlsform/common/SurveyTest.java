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

public class SurveyTest {

    @Mock
    private com.github.batkinson.jxlsform.api.Sheet mockSheet;

    @Mock
    private com.github.batkinson.jxlsform.api.XLSForm mockForm;

    @Mock
    private com.github.batkinson.jxlsform.api.SurveyItem item1, item2, item3;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void createNoForm() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("form is required");
        new Survey(null, mockSheet);
    }

    @Test
    public void createNoSheet() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("sheet is required");
        new Survey(mockForm, null);
    }

    @Test
    public void create() {
        Survey survey = new Survey(mockForm, mockSheet);
        assertSame(mockForm, survey.getForm());
        assertSame(mockSheet, survey.getSheet());
    }

    @Test
    public void iterator() {
        Survey survey = new Survey(mockForm, mockSheet);
        List<com.github.batkinson.jxlsform.api.SurveyItem> items = new ArrayList<>(asList(item1, item2, item3));
        assertFalse(items.isEmpty());
        items.forEach(survey::add);
        survey.forEach(items::remove);
        assertTrue(items.isEmpty());
    }
}
