package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Row;
import com.github.batkinson.jxlsform.api.Survey;
import com.github.batkinson.jxlsform.api.SurveyItemContainer;
import com.github.batkinson.jxlsform.api.XLSFormException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class GroupTest {

    @Mock
    private Survey survey;

    @Mock
    private SurveyItemContainer container;

    @Mock
    private Row row;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void createNoSurvey() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("survey is required");
        new Group(null, container, row);
    }

    @Test
    public void createNoParent() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("parent is required");
        new Group(survey, null, row);
    }

    @Test
    public void createNoRow() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("row is required");
        new Group(survey, container, null);
    }

    @Test
    public void create() {
        new Group(survey, container, row);
    }
}
