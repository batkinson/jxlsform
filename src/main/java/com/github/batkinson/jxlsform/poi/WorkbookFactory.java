package com.github.batkinson.jxlsform.poi;

import com.github.batkinson.jxlsform.api.XLSFormException;
import org.apache.poi.EmptyFileException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;

import static org.apache.poi.ss.util.CellReference.convertNumToColString;

public class WorkbookFactory implements com.github.batkinson.jxlsform.api.WorkbookFactory {

    public com.github.batkinson.jxlsform.api.Workbook create(InputStream stream) throws XLSFormException {
        try {
            com.github.batkinson.jxlsform.common.Workbook workbook = new com.github.batkinson.jxlsform.common.Workbook();
            Workbook excelWorkbook = org.apache.poi.ss.usermodel.WorkbookFactory.create(stream);
            for (Sheet excelSheet : excelWorkbook) {
                com.github.batkinson.jxlsform.common.Sheet sheet = workbook.addSheet(excelSheet.getSheetName());
                for (Row excelRow : excelSheet) {
                    com.github.batkinson.jxlsform.common.Row row = sheet.addRow(excelRow.getRowNum() + 1);
                    for (Cell excelCell : excelRow) {
                        row.addCell(
                                convertNumToColString(excelCell.getColumnIndex()),
                                translateType(excelCell.getCellTypeEnum()),
                                translateValue(excelCell));
                    }
                }
            }
            return workbook;
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
