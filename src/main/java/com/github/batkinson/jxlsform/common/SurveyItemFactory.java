package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Row;
import com.github.batkinson.jxlsform.api.Survey;
import com.github.batkinson.jxlsform.api.SurveyItem;
import com.github.batkinson.jxlsform.api.SurveyItemContainer;
import com.github.batkinson.jxlsform.api.*;

public class SurveyItemFactory implements com.github.batkinson.jxlsform.api.SurveyItemFactory {

    @Override
    public com.github.batkinson.jxlsform.api.Group createGroup(Survey survey, SurveyItemContainer parent, Row row) {
        return new Group(survey, parent, row);
    }

    @Override
    public com.github.batkinson.jxlsform.api.Repeat createRepeat(Survey survey, SurveyItemContainer parent, Row row) {
        return new Repeat(survey, parent, row);
    }

    @Override
    public SurveyItem create(Survey survey, SurveyItemContainer parent, Row row) {
        if (survey == null) {
            throw new XLSFormException("survey is required");
        }
        if (parent == null) {
            throw new XLSFormException("parent is required");
        }
        if (row == null) {
            throw new XLSFormException("row is required");
        }
        return row.getCellByHeader("type")
                .map(com.github.batkinson.jxlsform.api.Cell::getValue)
                .map(s -> s.matches("begin (?:group|repeat)") ? new String[]{s} : s.split("\\s+"))
                .map(t -> {
                            switch (t[0]) {
                                case "select_one":
                                    return new SelectOne(survey, parent, row);
                                case "select_multiple":
                                    return new SelectMultiple(survey, parent, row);
                                case "begin group":
                                    return createGroup(survey, parent, row);
                                case "begin repeat":
                                    return createRepeat(survey, parent, row);
                                default:
                                    return new Question(survey, parent, row);
                            }
                        }
                )
                .orElseThrow(() -> new XLSFormException("type is required"));
    }

}
