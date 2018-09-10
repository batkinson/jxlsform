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
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class SettingsTest {

    @Mock
    private XLSForm mockForm;

    @Mock
    private Sheet mockSheet;

    @Mock
    private com.github.batkinson.jxlsform.api.Row row1, row2, row3;

    @Mock
    com.github.batkinson.jxlsform.api.Cell cell1;

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
        when(mockSheet.stream()).thenReturn(Stream.of(row1, row2, row3));
        when(row1.isHeader()).thenReturn(true);
        when(row2.isHeader()).thenReturn(false);
        Optional<Row> maybeRow = new Settings(mockForm, mockSheet).getRow();
        assertTrue(maybeRow.isPresent());
        assertSame(row2, maybeRow.get());
    }

    @Test
    public void getFormTitleNoCell() {
        when(mockSheet.stream()).thenReturn(Stream.of(row1));
        when(row1.isHeader()).thenReturn(false);
        when(row1.getCellByHeader("form_title")).thenReturn(Optional.empty());
        Settings s = new Settings(mockForm, mockSheet);
        assertEquals(Optional.empty(), s.getFormTitle());
    }

    @Test
    public void getFormTitle() {
        String title = "a title";
        when(mockSheet.stream()).thenReturn(Stream.of(row1));
        when(row1.isHeader()).thenReturn(false);
        when(row1.getCellByHeader("form_title")).thenReturn(Optional.of(cell1));
        when(cell1.getValue()).thenReturn(title);
        Settings s = new Settings(mockForm, mockSheet);
        assertEquals(title, s.getFormTitle().orElse("no title found"));
    }

    @Test
    public void getFormIdNoCell() {
        when(mockSheet.stream()).thenReturn(Stream.of(row1));
        when(row1.isHeader()).thenReturn(false);
        when(row1.getCellByHeader("form_id")).thenReturn(Optional.empty());
        Settings s = new Settings(mockForm, mockSheet);
        assertEquals(Optional.empty(), s.getFormId());
    }

    @Test
    public void getFormId() {
        String id = "form id";
        when(mockSheet.stream()).thenReturn(Stream.of(row1));
        when(row1.isHeader()).thenReturn(false);
        when(row1.getCellByHeader("form_id")).thenReturn(Optional.of(cell1));
        when(cell1.getValue()).thenReturn(id);
        Settings s = new Settings(mockForm, mockSheet);
        assertEquals(id, s.getFormId().orElse("no form id found"));
    }

    @Test
    public void getFormVersionNoCell() {
        when(mockSheet.stream()).thenReturn(Stream.of(row1));
        when(row1.isHeader()).thenReturn(false);
        when(row1.getCellByHeader("version")).thenReturn(Optional.empty());
        Settings s = new Settings(mockForm, mockSheet);
        assertEquals(Optional.empty(), s.getFormVersion());
    }

    @Test
    public void getFormVersion() {
        String version = "form id";
        when(mockSheet.stream()).thenReturn(Stream.of(row1));
        when(row1.isHeader()).thenReturn(false);
        when(row1.getCellByHeader("version")).thenReturn(Optional.of(cell1));
        when(cell1.getValue()).thenReturn(version);
        Settings s = new Settings(mockForm, mockSheet);
        assertEquals(version, s.getFormVersion().orElse("no form version found"));
    }
}
