package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Row;
import com.github.batkinson.jxlsform.api.Survey;
import com.github.batkinson.jxlsform.api.SurveyItemContainer;

class SelectMultiple extends Select {
    SelectMultiple(Survey survey, SurveyItemContainer parent, Row row) {
        super(survey, parent, row);
    }
}
