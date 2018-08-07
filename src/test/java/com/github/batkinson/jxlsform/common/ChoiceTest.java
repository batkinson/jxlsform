package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.XLSFormException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

public class ChoiceTest {

    @Mock
    private com.github.batkinson.jxlsform.api.ChoiceList mockList;

    @Mock
    private com.github.batkinson.jxlsform.api.Row mockRow;

    @Mock
    private com.github.batkinson.jxlsform.api.Cell mockCell;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void createNoList() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("list is required");
        new Choice(null, mockRow);
    }

    @Test
    public void createNoRow() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("row is required");
        new Choice(mockList, null);
    }

    @Test
    public void create() {
        Choice c = new Choice(mockList, mockRow);
        assertSame(mockList, c.getList());
        assertSame(mockRow, c.getRow());
    }

    @Test
    public void getName() {
        String value = "nomatter";
        Choice c = new Choice(mockList, mockRow);
        when(mockRow.getCellByHeader("name")).thenReturn(Optional.of(mockCell));
        when(mockCell.getValue()).thenReturn(value);
        assertEquals(value, c.getName());
    }

    @Test
    public void getNameNoValue() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("name is required");
        Choice c = new Choice(mockList, mockRow);
        when(mockRow.getCellByHeader("name")).thenReturn(Optional.empty());
        c.getName();
    }

    @Test
    public void getLabel() {
        String value = "nomatter";
        Choice c = new Choice(mockList, mockRow);
        when(mockRow.getCellByHeader("label")).thenReturn(Optional.of(mockCell));
        when(mockCell.getValue()).thenReturn(value);
        assertEquals(value, c.getLabel().orElse("no label"));
    }

    @Test
    public void getLabelNoValue() {
        Choice c = new Choice(mockList, mockRow);
        when(mockRow.getCellByHeader("label")).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), c.getLabel());
    }
}
