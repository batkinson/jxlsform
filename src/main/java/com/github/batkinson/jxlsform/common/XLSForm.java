package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.XLSFormException;

import java.util.Optional;

public class XLSForm implements com.github.batkinson.jxlsform.api.XLSForm {

    private final com.github.batkinson.jxlsform.api.Workbook workbook;
    private final Survey survey;
    private final Choices choices;

    public XLSForm(com.github.batkinson.jxlsform.api.Workbook workbook) {
        if (workbook == null) {
            throw new XLSFormException("workbook is required");
        }
        this.workbook = workbook;
        survey = new Survey(this,
                workbook.getSheet(SURVEY)
                        .orElseThrow(() -> new XLSFormException("survey sheet is required")));
        choices = new Choices(this,
                workbook.getSheet(CHOICES)
                        .orElseThrow(() -> new XLSFormException("choices sheet is required")));
    }

    @Override
    public com.github.batkinson.jxlsform.api.Workbook getWorkbook() {
        return workbook;
    }

    @Override
    public Survey getSurvey() {
        return survey;
    }

    @Override
    public Choices getChoices() {
        return choices;
    }

    @Override
    public Optional<com.github.batkinson.jxlsform.api.Sheet> getSettings() {
        return workbook.getSheet(SETTINGS);
    }
}
