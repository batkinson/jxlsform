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
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ChoiceListTest {

    @Mock
    private Choices mockChoices;

    @Mock
    private Choice mockChoice1, mockChoice2;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void createNoChoices() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("choices is required");
        new ChoiceList(null, "name");
    }

    @Test
    public void createNoName() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("name is required");
        new ChoiceList(mockChoices, null);
    }

    @Test
    public void create() {
        String name = "a name";
        ChoiceList cl = new ChoiceList(mockChoices, name);
        assertSame(mockChoices, cl.getChoices());
        assertEquals(name, cl.getName());
    }

    @Test
    public void getChoice() {
        String choiceName = "choice1";
        ChoiceList cl = new ChoiceList(mockChoices, "doesn't matter");
        when(mockChoice1.getName()).thenReturn(choiceName);
        cl.add(mockChoice1);
        Optional<com.github.batkinson.jxlsform.api.Choice> maybeChoice = cl.getChoice(choiceName);
        assertTrue(maybeChoice.isPresent());
        assertSame(mockChoice1, maybeChoice.get());
    }

    @Test
    public void iterator() {
        List<Choice> choices = new ArrayList<>(asList(mockChoice1, mockChoice2));
        when(mockChoice1.getName()).thenReturn("name1");
        when(mockChoice2.getName()).thenReturn("name2");
        ChoiceList cl = new ChoiceList(mockChoices, "doesn't matter");
        choices.forEach(cl::add);
        cl.forEach(c -> assertTrue(choices.contains(c)));
        cl.forEach(choices::remove);
        assertTrue(choices.isEmpty());
    }
}
