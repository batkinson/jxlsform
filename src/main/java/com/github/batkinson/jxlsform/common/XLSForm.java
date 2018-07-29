package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.XLSFormException;

import java.util.Optional;

public class XLSForm implements com.github.batkinson.jxlsform.api.XLSForm {

    private final com.github.batkinson.jxlsform.api.Workbook workbook;

    public XLSForm(com.github.batkinson.jxlsform.api.Workbook workbook) {
        if (workbook == null) {
            throw new XLSFormException("workbook is required");
        }
        this.workbook = workbook;
        REQUIRED_SHEETS.forEach(sheetName -> {
            if (!workbook.getSheet(sheetName).isPresent()) {
                throw new XLSFormException(sheetName + " sheet is required");
            }
        });
    }

    @Override
    public com.github.batkinson.jxlsform.api.Workbook getWorkbook() {
        return workbook;
    }

    @Override
    public com.github.batkinson.jxlsform.api.Sheet getSurvey() {
        return workbook.getSheet(SURVEY).orElseThrow(() -> new XLSFormException("no survey sheet"));
    }

    @Override
    public com.github.batkinson.jxlsform.api.Sheet getChoices() {
        return workbook.getSheet(CHOICES).orElseThrow(() -> new XLSFormException("no choices sheet"));
    }

    @Override
    public Optional<com.github.batkinson.jxlsform.api.Sheet> getSettings() {
        return workbook.getSheet(SETTINGS);
    }
}
