package com.github.batkinson.jxlsform.poi;

import com.github.batkinson.jxlsform.api.Cell;
import com.github.batkinson.jxlsform.api.Sheet;
import com.github.batkinson.jxlsform.api.Workbook;
import com.github.batkinson.jxlsform.api.XLSFormException;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static com.github.batkinson.jxlsform.api.XLSForm.*;
import static com.github.batkinson.jxlsform.poi.FormLoader.getStream;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class WorkbookFactoryTest {

    @Test
    public void factoryCreate() {
        new WorkbookFactory();
    }

    @Test(expected = XLSFormException.class)
    public void createWithBadStream() {
        new WorkbookFactory().create(new ByteArrayInputStream(new byte[]{}));
    }

    @Test
    public void createSimplest() {

        Workbook workbook = new WorkbookFactory()
                .create(getStream("simplest.xlsx"));

        Sheet survey = workbook.getSheet(SURVEY)
                .orElseThrow(() -> new XLSFormException("survey sheet not found"));

        assertEquals(asList("type", "name", "label"),
                survey.getHeader()
                        .stream()
                        .map(Cell::getValue)
                        .collect(toList()));

        assertEquals(asList("text", "name", "Name"),
                survey.stream()
                        .filter(r -> !r.isHeader())
                        .findFirst()
                        .orElseThrow(() -> new XLSFormException("expected data row"))
                        .stream()
                        .map(Cell::getValue)
                        .collect(toList()));

        Sheet choices = workbook.getSheet(CHOICES)
                .orElseThrow(() -> new XLSFormException("survey sheet not found"));

        assertEquals(asList("list_name", "name", "label"),
                choices.getHeader()
                        .stream()
                        .map(Cell::getValue)
                        .collect(toList()));
    }

    @Test
    public void createAllSheets() {

        Workbook workbook = new WorkbookFactory()
                .create(getStream("allsheets.xlsx"));

        Sheet survey = workbook.getSheet(SURVEY)
                .orElseThrow(() -> new XLSFormException("survey sheet not found"));

        assertEquals(asList("type", "name", "label"),
                survey.getHeader()
                        .stream()
                        .map(Cell::getValue)
                        .collect(toList()));

        assertEquals(asList("text", "name", "Name"),
                survey.stream()
                        .filter(r -> !r.isHeader())
                        .findFirst()
                        .orElseThrow(() -> new XLSFormException("expected data row"))
                        .stream()
                        .map(Cell::getValue)
                        .collect(toList()));

        Sheet choices = workbook.getSheet(CHOICES)
                .orElseThrow(() -> new XLSFormException("survey sheet not found"));

        assertEquals(asList("list_name", "name", "label"),
                choices.getHeader()
                        .stream()
                        .map(Cell::getValue)
                        .collect(toList()));

        Sheet settings = workbook.getSheet(SETTINGS)
                .orElseThrow(() -> new XLSFormException("settings sheet not found"));

        assertEquals(asList("form_title", "form_id", "version"),
                settings.getHeader()
                        .stream()
                        .map(Cell::getValue)
                        .collect(toList()));

        assertEquals(asList("All Sheets", "all_sheets", "1"),
                settings.stream()
                        .filter(r -> !r.isHeader())
                        .findFirst()
                        .orElseThrow(() -> new XLSFormException("expected data row"))
                        .stream()
                        .map(Cell::getValue)
                        .collect(toList()));
    }
}
