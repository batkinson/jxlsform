package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Sheet;
import com.github.batkinson.jxlsform.api.XLSForm;
import com.github.batkinson.jxlsform.api.XLSFormException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class ChoicesTest {

    @Mock
    private XLSForm mockForm;

    @Mock
    private Sheet mockSheet;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void createNoForm() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("form is required");
        new Choices(null, mockSheet);
    }

    @Test
    public void createNoSheet() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("sheet is required");
        new Choices(mockForm, null);
    }

    @Test
    public void create() {
        Choices c = new Choices(mockForm, mockSheet);
        assertSame(mockForm, c.getForm());
        assertSame(mockSheet, c.getSheet());
    }

    @Test
    public void getChoiceList() {
        String listName = "alist";
        Choices c = new Choices(mockForm, mockSheet);
        assertFalse(c.getChoiceList(listName).isPresent());
        c.addList(new ChoiceList(c, listName));
        assertTrue(c.getChoiceList(listName).isPresent());
    }

    @Test
    public void iterator() {
        Choices c = new Choices(mockForm, mockSheet);
        List<ChoiceList> lists = Stream.of("list1", "list2")
                .map(l -> new ChoiceList(c, l))
                .collect(Collectors.toList());
        lists.forEach(c::addList);
        assertFalse(lists.isEmpty());
        c.forEach(lists::remove);
        assertTrue(lists.isEmpty());
    }
}
