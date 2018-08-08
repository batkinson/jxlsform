package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Sheet;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
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
    @SuppressWarnings("unchecked")
    public void getCellByHeaderCaches() throws NoSuchFieldException, IllegalAccessException {

        CacheTestHeader header = new CacheTestHeader(mockSheet, 1);

        Map<String, Cell> headerCache = header.getCache();

        when(mockSheet.getHeader()).thenReturn(header);
        header.addCell("A", Cell.Type.STRING, "happy");
        header.addCell("B", Cell.Type.STRING, "go");
        header.addCell("D", Cell.Type.STRING, "lucky");

        // ensure the items aren't in cache prior to lookup
        header.forEach(c -> assertFalse(headerCache.containsKey(c.getName())));

        // lookup the existing cells by header name
        header.forEach(c -> assertTrue(header.getCellByHeader(c.getName()).isPresent()));

        // ensure existing items are in cache after lookup
        header.forEach(c -> assertTrue(headerCache.containsKey(c.getName())));

        // ensure the cache items are used
        header.forEach(c -> {
            header.blockIteration = true;
            assertTrue(header.getCellByHeader(c.getName()).isPresent());
            header.blockIteration = false;
        });
    }

    @Test
    public void testToString() {
        assertEquals("header, row 1", new Header(mockSheet, 1).toString());
    }
}


class CacheTestHeader extends Header {

    boolean blockIteration = false;

    CacheTestHeader(Sheet sheet, int rowNumber) {
        super(sheet, rowNumber);
    }

    @SuppressWarnings("unchecked")
    Map<String, Cell> getCache() throws NoSuchFieldException, IllegalAccessException {
        Field ncf = Header.class.getDeclaredField("namedCells");
        ncf.setAccessible(true);
        return (Map<String, Cell>) ncf.get(this);
    }

    @Override
    public Iterator<com.github.batkinson.jxlsform.api.Cell> iterator() {
        if (blockIteration) {
            throw new RuntimeException("iteration should not occur");
        } else {
            return super.iterator();
        }
    }
}
