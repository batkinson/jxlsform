package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Sheet;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class HeaderTest {

    @Mock
    private Sheet mockSheet;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void create() {
        int rowNum = 1;
        Header row = new Header(mockSheet, rowNum);
        assertSame(mockSheet, row.getSheet());
        assertEquals(rowNum, row.getRowNumber());
    }

    @Test
    public void getCellByHeader() {
        Header header = new Header(mockSheet, 1);
        when(mockSheet.getHeader()).thenReturn(header);
        header.addCell("A", Cell.Type.STRING, "happy");
        header.addCell("B", Cell.Type.STRING, "go");
        header.addCell("D", Cell.Type.STRING, "lucky");
        for (com.github.batkinson.jxlsform.api.Cell cell : header) {
            Optional<com.github.batkinson.jxlsform.api.Cell> maybeCell = header.getCellByHeader(cell.getValue());
            assertTrue(maybeCell.isPresent());
            assertEquals(cell.getValue(), maybeCell.get().getValue());
        }
        assertFalse(header.getCellByHeader("pfft").isPresent());
    }

    @Test
    public void testToString() {
        assertEquals("header, row 1", new Header(mockSheet, 1).toString());
    }
}
