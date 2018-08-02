package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Cell;
import com.github.batkinson.jxlsform.api.Row;
import com.github.batkinson.jxlsform.api.Survey;
import com.github.batkinson.jxlsform.api.SurveyItemContainer;
import com.github.batkinson.jxlsform.api.XLSForm;
import com.github.batkinson.jxlsform.api.*;

import java.util.Optional;

public class SurveyItem implements com.github.batkinson.jxlsform.api.SurveyItem {

    private com.github.batkinson.jxlsform.api.Row row;
    private final Survey survey;
    private final SurveyItemContainer parent;

    SurveyItem(Survey survey, SurveyItemContainer parent, Row row) {
        if (survey == null) {
            throw new XLSFormException("survey is required");
        }
        if (parent == null) {
            throw new XLSFormException("parent is required");
        }
        if (row == null) {
            throw new XLSFormException("row is required");
        }
        this.survey = survey;
        this.parent = parent;
        this.row = row;
    }

    @Override
    public XLSForm getForm() {
        return survey.getForm();
    }

    @Override
    public Survey getSurvey() {
        return survey;
    }

    @Override
    public Row getRow() {
        return row;
    }

    @Override
    public SurveyItemContainer getParent() {
        return parent;
    }

    @Override
    public String getType() {
        return row.getCellByHeader("type")
                .orElseThrow(() -> new XLSFormException("type is required"))
                .getValue();
    }

    @Override
    public Optional<String> getName() {
        return row.getCellByHeader("name").map(Cell::getValue);
    }

    @Override
    public Optional<String> getLabel() {
        return row.getCellByHeader("label").map(Cell::getValue);
    }

    @Override
    public String toString() {
        return String.format("'%s', (%s, %s)",
                getName().orElse("<unnamed>"),
                getSurvey().getSheet(), row);
    }
}
