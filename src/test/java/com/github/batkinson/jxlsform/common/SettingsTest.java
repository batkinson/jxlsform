package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Row;
import com.github.batkinson.jxlsform.api.XLSFormException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class SettingsTest {

    @Mock
    private XLSForm mockForm;

    @Mock
    private Sheet mockSheet;

    @Mock
    private com.github.batkinson.jxlsform.api.Row row1, row2, row3;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void createNoForm() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("form is required");
        new Settings(null, mockSheet);
    }

    @Test
    public void createNoSheet() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("sheet is required");
        new Settings(mockForm, null);
    }

    @Test
    public void create() {
        Settings s = new Settings(mockForm, mockSheet);
        assertSame(mockForm, s.getForm());
        assertSame(mockSheet, s.getSheet());
    }

    @Test
    public void getRow() {
        when(mockSheet.spliterator()).thenReturn(asList(row1, row2, row3).spliterator());
        when(row1.isHeader()).thenReturn(true);
        when(row2.isHeader()).thenReturn(false);
        when(row3.isHeader()).thenReturn(false);
        Optional<Row> maybeRow = new Settings(mockForm, mockSheet).getRow();
        assertTrue(maybeRow.isPresent());
        assertSame(row2, maybeRow.get());
    }
}
