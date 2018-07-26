package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Row;
import com.github.batkinson.jxlsform.api.XLSForm;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import static com.github.batkinson.jxlsform.api.XLSForm.SURVEY;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class SheetTest {

    @Mock
    private Row mockRow1, mockRow2, mockHeaderRow;

    @Mock
    private XLSForm mockForm;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private List<Row> rows;
    private List<Row> data;

    @Before
    public void initLists() {
        rows = asList(mockHeaderRow, mockRow1, mockRow2);
        data = asList(mockRow1, mockRow2);
    }

    @Test
    public void creation() {
        Sheet s = new Sheet(SURVEY, rows);
        assertEquals(SURVEY, s.getName());
        assertThat("sheet iterates same rows given at creation time", rows,
                Matchers.is(stream(s.spliterator(), false).collect(toList())));
        assertSame(mockHeaderRow, s.getHeader());
        assertThat("data iterator gives everything but header", data,
                Matchers.is(stream(s.getData().spliterator(), false).collect(toList())));
    }

    @Test
    public void getRow() {
        Sheet s = new Sheet(SURVEY, rows);
        assertSame(mockRow1, s.getRow(1));
    }

    @Test
    public void getForm() {
        Sheet s = new Sheet(SURVEY, rows);
        s.setForm(mockForm);
        assertSame(mockForm, s.getForm());
    }
}
