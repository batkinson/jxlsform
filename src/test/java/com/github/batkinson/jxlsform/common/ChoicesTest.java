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

import static org.junit.Assert.assertSame;

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
    public void testCreateNoForm() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("form is required");
        new Choices(null, mockSheet);
    }

    @Test
    public void testCreateNoSheet() {
        exceptionRule.expect(XLSFormException.class);
        exceptionRule.expectMessage("sheet is required");
        new Choices(mockForm, null);
    }

    @Test
    public void testCreate() {
        Choices c = new Choices(mockForm, mockSheet);
        assertSame(mockForm, c.getForm());
        assertSame(mockSheet, c.getSheet());
    }
}
