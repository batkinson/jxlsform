package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Sheet;
import com.github.batkinson.jxlsform.api.XLSFormException;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import static com.github.batkinson.jxlsform.api.XLSForm.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

public class XLSFormTest {

    @Mock
    Sheet mockSheet1, mockSheet2, mockSheet3;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private List<Sheet> sheets;

    @Before
    public void initLists() {
        sheets = asList(mockSheet1, mockSheet2, mockSheet3);
    }

    @Test
    public void creation() {
        when(mockSheet1.getName()).thenReturn(SURVEY);
        when(mockSheet2.getName()).thenReturn(CHOICES);
        when(mockSheet3.getName()).thenReturn(SETTINGS);
        XLSForm f = new XLSForm(sheets);
        assertThat("form iterates same sheets given at creation time", sheets,
                Matchers.is(stream(f.spliterator(), false).collect(toList())));
        assertSame(mockSheet1, f.getSurvey());
        assertSame(mockSheet2, f.getChoices());
        assertSame(mockSheet3, f.getSettings());
    }

    @Test(expected = XLSFormException.class)
    public void getMissingSheet() {
        when(mockSheet1.getName()).thenReturn(SURVEY);
        when(mockSheet2.getName()).thenReturn(CHOICES);
        when(mockSheet3.getName()).thenReturn(SETTINGS);
        XLSForm f = new XLSForm(sheets);
        f.getSheet("unknown sheet");
    }
}
