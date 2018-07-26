package com.github.batkinson.jxlsform.poi;

import com.github.batkinson.jxlsform.api.XLSForm;
import com.github.batkinson.jxlsform.api.XLSFormException;
import org.apache.poi.EmptyFileException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XLSFormFactory implements com.github.batkinson.jxlsform.api.XLSFormFactory {

    public XLSForm create(InputStream stream) throws XLSFormException {
        try {
            Workbook workbook = WorkbookFactory.create(stream);
            List<com.github.batkinson.jxlsform.api.Sheet> sheets = new ArrayList<>();
            for (Sheet sheet : workbook) {
                List<com.github.batkinson.jxlsform.api.Row> rows = new ArrayList<>();
                for (Row row : sheet) {
                    List<com.github.batkinson.jxlsform.api.Cell> cells = new ArrayList<>();
                    for (Cell cell : row) {
                        cells.add(new com.github.batkinson.jxlsform.common.Cell(cell.getColumnIndex(),
                                translateType(cell.getCellTypeEnum()),
                                translateValue(cell)));
                    }
                    rows.add(new com.github.batkinson.jxlsform.common.Row(row.getRowNum(), cells));
                }
                sheets.add(new com.github.batkinson.jxlsform.common.Sheet(sheet.getSheetName(), rows));
            }
            return new com.github.batkinson.jxlsform.common.XLSForm(sheets);
        } catch (IOException | EmptyFileException | InvalidFormatException e) {
            throw new XLSFormException("failed to create workbook from stream", e);
        }
    }

    private static com.github.batkinson.jxlsform.api.Cell.Type translateType(CellType type) {
        return com.github.batkinson.jxlsform.api.Cell.Type.valueOf(type.name());
    }

    private static String translateValue(Cell cell) {
        return cell.toString();
    }
}
