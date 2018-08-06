package com.github.batkinson.jxlsform.common;


import com.github.batkinson.jxlsform.api.Cell;
import com.github.batkinson.jxlsform.api.XLSFormException;

import java.util.Optional;
import java.util.stream.StreamSupport;

public class Settings implements com.github.batkinson.jxlsform.api.Settings {

    private final com.github.batkinson.jxlsform.api.XLSForm form;
    private final com.github.batkinson.jxlsform.api.Sheet sheet;

    Settings(com.github.batkinson.jxlsform.api.XLSForm form, com.github.batkinson.jxlsform.api.Sheet sheet) {
        if (form == null) {
            throw new XLSFormException("form is required");
        }
        if (sheet == null) {
            throw new XLSFormException("sheet is required");
        }
        this.form = form;
        this.sheet = sheet;
    }

    @Override
    public com.github.batkinson.jxlsform.api.XLSForm getForm() {
        return form;
    }

    @Override
    public com.github.batkinson.jxlsform.api.Sheet getSheet() {
        return sheet;
    }

    @Override
    public Optional<com.github.batkinson.jxlsform.api.Row> getRow() {
        return StreamSupport.stream(sheet.spliterator(), false)
                .filter(row -> !row.isHeader())
                .findFirst();
    }


    @Override
    public Optional<String> getFormTitle() {
        return getRow()
                .flatMap(row -> row.getCellByHeader("form_title"))
                .map(Cell::getValue);
    }

    @Override
    public Optional<String> getFormId() {
        return getRow()
                .flatMap(row -> row.getCellByHeader("form_id"))
                .map(Cell::getValue);
    }

    @Override
    public Optional<String> getFormVersion() {
        return getRow()
                .flatMap(row -> row.getCellByHeader("form_version"))
                .map(Cell::getValue);
    }
}
