package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.XLSFormException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Optional;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

public class SelectMultipleTest {

    @Mock
    private com.github.batkinson.jxlsform.api.XLSForm form;

    @Mock
    private com.github.batkinson.jxlsform.api.Choices choices;

    @Mock
    private com.github.batkinson.jxlsform.api.ChoiceList choiceList;

    @Mock
    private com.github.batkinson.jxlsform.api.Survey survey;

    @Mock
    private com.github.batkinson.jxlsform.api.SurveyItemContainer container;

    @Mock
    private com.github.batkinson.jxlsform.api.Row row;

    @Mock
    private com.github.batkinson.jxlsform.api.Cell cell;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void createNoSurvey() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("survey is required");
        new SelectMultiple(null, container, row);
    }

    @Test
    public void createNoParent() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("parent is required");
        new SelectMultiple(survey, null, row);
    }

    @Test
    public void createNoRow() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("row is required");
        new SelectMultiple(survey, container, null);
    }

    @Test
    public void create() {
        when(row.getCellByHeader("type")).thenReturn(Optional.of(cell));
        when(cell.getValue()).thenReturn("select_one list1");
        when(survey.getForm()).thenReturn(form);
        when(form.getChoices()).thenReturn(choices);
        when(choices.getChoiceList("list1")).thenReturn(Optional.of(choiceList));
        Select s = new SelectMultiple(survey, container, row);
        assertSame(survey, s.getSurvey());
        assertSame(container, s.getParent());
        assertSame(row, s.getRow());
        assertSame(choiceList, s.getChoiceList());
    }
}
