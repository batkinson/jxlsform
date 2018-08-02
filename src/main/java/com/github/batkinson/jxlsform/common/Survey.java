package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.XLSFormException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Survey implements com.github.batkinson.jxlsform.api.Survey, SurveyItemContainer {

    private final com.github.batkinson.jxlsform.api.XLSForm form;
    private final com.github.batkinson.jxlsform.api.Sheet sheet;
    private final List<com.github.batkinson.jxlsform.api.SurveyItem> items = new ArrayList<>();

    Survey(com.github.batkinson.jxlsform.api.XLSForm form, com.github.batkinson.jxlsform.api.Sheet sheet) {
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
    public Iterator<com.github.batkinson.jxlsform.api.SurveyItem> iterator() {
        return items.iterator();
    }

    @Override
    public void add(com.github.batkinson.jxlsform.api.SurveyItem item) {
        items.add(item);
    }
}
