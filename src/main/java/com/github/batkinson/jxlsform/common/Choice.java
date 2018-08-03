package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.XLSFormException;

import java.util.Optional;

public class Choice implements com.github.batkinson.jxlsform.api.Choice {

    private final com.github.batkinson.jxlsform.api.ChoiceList list;
    private final com.github.batkinson.jxlsform.api.Row row;

    Choice(com.github.batkinson.jxlsform.api.ChoiceList list, com.github.batkinson.jxlsform.api.Row row) {
        this.list = list;
        this.row = row;
    }

    @Override
    public com.github.batkinson.jxlsform.api.ChoiceList getList() {
        return list;
    }

    @Override
    public com.github.batkinson.jxlsform.api.Row getRow() {
        return row;
    }

    @Override
    public String getName() {
        return row.getCellByHeader("name")
                .map(com.github.batkinson.jxlsform.api.Cell::getValue)
                .orElseThrow(() -> new XLSFormException("name is required"));
    }

    @Override
    public Optional<String> getLabel() {
        return row.getCellByHeader("label")
                .map(com.github.batkinson.jxlsform.api.Cell::getValue);
    }
}
