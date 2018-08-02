package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.XLSFormException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class SurveyItemTest {

    @Mock
    private com.github.batkinson.jxlsform.api.XLSForm mockForm;

    @Mock
    private com.github.batkinson.jxlsform.api.Sheet mockSheet;

    @Mock
    private com.github.batkinson.jxlsform.api.Survey mockSurvey;

    @Mock
    private com.github.batkinson.jxlsform.api.SurveyItemContainer mockParent;

    @Mock
    private com.github.batkinson.jxlsform.api.Row mockRow;

    @Mock
    private com.github.batkinson.jxlsform.api.Cell mockCell1;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void createNoSurvey() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("survey is required");
        new SurveyItem(null, mockParent, mockRow);
    }

    @Test
    public void createNoParent() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("parent is required");
        new SurveyItem(mockSurvey, null, mockRow);
    }

    @Test
    public void createNoRow() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("row is required");
        new SurveyItem(mockSurvey, mockParent, null);
    }

    @Test
    public void create() {
        SurveyItem item = new SurveyItem(mockSurvey, mockParent, mockRow);
        assertSame(mockSurvey, item.getSurvey());
        assertSame(mockParent, item.getParent());
        assertSame(mockRow, item.getRow());
    }

    @Test
    public void getForm() {
        SurveyItem item = new SurveyItem(mockSurvey, mockParent, mockRow);
        when(mockSurvey.getForm()).thenReturn(mockForm);
        assertSame(mockForm, item.getForm());
    }

    @Test
    public void getTypeWhenEmpty() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("type is required");
        when(mockRow.getCellByHeader("type")).thenReturn(Optional.empty());
        new SurveyItem(mockSurvey, mockParent, mockRow).getType();
    }

    @Test
    public void getType() {
        String type = "text";
        when(mockRow.getCellByHeader("type")).thenReturn(Optional.of(mockCell1));
        when(mockCell1.getValue()).thenReturn(type);
        SurveyItem item = new SurveyItem(mockSurvey, mockParent, mockRow);
        assertEquals(type, item.getType());
    }

    @Test
    public void getLabelWhenEmpty() {
        when(mockRow.getCellByHeader("label")).thenReturn(Optional.empty());
        SurveyItem item = new SurveyItem(mockSurvey, mockParent, mockRow);
        assertEquals(Optional.empty(), item.getLabel());
    }

    @Test
    public void getLabel() {
        String label = "a label";
        when(mockRow.getCellByHeader("label")).thenReturn(Optional.of(mockCell1));
        when(mockCell1.getValue()).thenReturn(label);
        SurveyItem item = new SurveyItem(mockSurvey, mockParent, mockRow);
        Optional<String> maybeLabel = item.getLabel();
        assertTrue(maybeLabel.isPresent());
        assertEquals(label, maybeLabel.get());
    }

    @Test
    public void getName() {
        String name = "itemName";
        when(mockRow.getCellByHeader("name")).thenReturn(Optional.of(mockCell1));
        when(mockCell1.getValue()).thenReturn(name);
        SurveyItem item = new SurveyItem(mockSurvey, mockParent, mockRow);
        Optional<String> maybeName = item.getName();
        assertTrue(maybeName.isPresent());
        assertEquals(name, maybeName.get());
    }

    @Test
    public void toStringValueWithName() {
        String itemName = "itemName", sheetName = "survey";
        int rowNum = 42;
        when(mockRow.toString()).thenReturn("row " + rowNum);
        when(mockRow.getCellByHeader("name")).thenReturn(Optional.of(mockCell1));
        when(mockCell1.getValue()).thenReturn(itemName);
        when(mockSurvey.getSheet()).thenReturn(mockSheet);
        when(mockSheet.toString()).thenReturn("sheet " + sheetName);
        SurveyItem item = new SurveyItem(mockSurvey, mockParent, mockRow);
        assertEquals(String.format("'%s', (sheet %s, row %s)", itemName, sheetName, rowNum), item.toString());
    }
}
