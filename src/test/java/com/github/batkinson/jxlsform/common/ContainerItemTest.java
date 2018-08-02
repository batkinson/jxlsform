package com.github.batkinson.jxlsform.common;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

public class ContainerItemTest {

    @Mock
    private com.github.batkinson.jxlsform.api.Survey survey;

    @Mock
    private com.github.batkinson.jxlsform.api.SurveyItemContainer parent;

    @Mock
    private com.github.batkinson.jxlsform.api.Row row;

    @Mock
    private com.github.batkinson.jxlsform.api.SurveyItem item1, item2, item3;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void create() {
        ContainerItem container = new ContainerItem(survey, parent, row);
        assertSame(survey, container.getSurvey());
        assertSame(parent, container.getParent());
        assertSame(row, container.getRow());
    }

    @Test
    public void iterator() {
        List<com.github.batkinson.jxlsform.api.SurveyItem> items = new ArrayList<>(asList(item1, item2, item3));
        ContainerItem container = new ContainerItem(survey, parent, row);
        assertFalse(items.isEmpty());
        items.forEach(container::add);
        container.forEach(items::remove);
        assertTrue(items.isEmpty());
    }
}
