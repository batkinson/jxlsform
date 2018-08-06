package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Row;
import com.github.batkinson.jxlsform.api.Survey;
import com.github.batkinson.jxlsform.api.SurveyItem;
import com.github.batkinson.jxlsform.api.SurveyItemContainer;

import java.util.Optional;

public class SurveyItemFactory implements com.github.batkinson.jxlsform.api.SurveyItemFactory {

    @Override
    public Optional<SurveyItem> create(Survey survey, SurveyItemContainer parent, Row row) {
        return row.getCellByHeader("type")
                .map(com.github.batkinson.jxlsform.api.Cell::getValue)
                .map(s -> s.split("\\s+"))
                .map(t -> {
                            switch (t[0]) {
                                case "select_one":
                                    return new SelectOne(survey, parent, row);
                                case "select_multiple":
                                    return new SelectMultiple(survey, parent, row);
                                default:
                                    return new Question(survey, parent, row);
                            }
                        }
                );
    }

}
