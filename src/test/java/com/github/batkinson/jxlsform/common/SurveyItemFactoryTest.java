package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Cell;
import com.github.batkinson.jxlsform.api.ChoiceList;
import com.github.batkinson.jxlsform.api.Choices;
import com.github.batkinson.jxlsform.api.Row;
import com.github.batkinson.jxlsform.api.SurveyItem;
import com.github.batkinson.jxlsform.api.SurveyItemContainer;
import com.github.batkinson.jxlsform.api.XLSForm;
import com.github.batkinson.jxlsform.api.XLSFormException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class SurveyItemFactoryTest {

    private static String LIST_NAME = "list1";

    @Mock
    private XLSForm form;

    @Mock
    private Choices choices;

    @Mock
    private ChoiceList choiceList;

    @Mock
    private Survey survey;

    @Mock
    private SurveyItemContainer container;

    @Mock
    private Row row;

    @Mock
    private Cell cell;

    private SurveyItemFactory factory;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setup() {
        factory = new SurveyItemFactory();
    }

    @Test
    public void createNoSurvey() {
        assertEquals(Optional.empty(), factory.create(null, container, row));
    }

    @Test
    public void createNoParent() {
        assertEquals(Optional.empty(), factory.create(survey, null, row));
    }

    @Test
    public void createNoRow() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("row is required");
        assertEquals(Optional.empty(), factory.create(survey, container, null));
    }

    @Test
    public void createNoType() {
        when(row.getCellByHeader("type")).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), factory.create(survey, container, row));
    }

    @Test
    public void unknownType() {
        when(row.getCellByHeader("type")).thenReturn(Optional.of(cell));
        when(cell.getValue()).thenReturn("unknown");
        Optional<SurveyItem> maybeItem = factory.create(survey, container, row);
        assertTrue(maybeItem.isPresent());
        SurveyItem item = maybeItem.get();
        assertEquals("unknown", item.getType());
    }

    @Test
    public void selectOne() {
        when(row.getCellByHeader("type")).thenReturn(Optional.of(cell));
        when(cell.getValue()).thenReturn("select_one " + LIST_NAME);
        when(survey.getForm()).thenReturn(form);
        when(form.getChoices()).thenReturn(choices);
        when(choices.getChoiceList(LIST_NAME)).thenReturn(Optional.of(choiceList));
        when(choiceList.getName()).thenReturn(LIST_NAME);
        Optional<SurveyItem> maybeItem = factory.create(survey, container, row);
        assertTrue(maybeItem.isPresent());
        SurveyItem item = maybeItem.get();
        assertEquals("select_one", item.getType());
        assertTrue(item instanceof SelectOne);
        SelectOne select = (SelectOne)item;
        assertEquals(LIST_NAME, select.getListName());
        assertEquals(LIST_NAME, select.getChoiceList().getName());
    }

    @Test
    public void selectMultiple() {
        when(row.getCellByHeader("type")).thenReturn(Optional.of(cell));
        when(cell.getValue()).thenReturn("select_multiple " + LIST_NAME);
        when(survey.getForm()).thenReturn(form);
        when(form.getChoices()).thenReturn(choices);
        when(choices.getChoiceList(LIST_NAME)).thenReturn(Optional.of(choiceList));
        when(choiceList.getName()).thenReturn(LIST_NAME);
        Optional<SurveyItem> maybeItem = factory.create(survey, container, row);
        assertTrue(maybeItem.isPresent());
        SurveyItem item = maybeItem.get();
        assertEquals("select_multiple", item.getType());
        assertTrue(item instanceof SelectMultiple);
        SelectMultiple select = (SelectMultiple)item;
        assertEquals(LIST_NAME, select.getListName());
        assertEquals(LIST_NAME, select.getChoiceList().getName());
    }
}
