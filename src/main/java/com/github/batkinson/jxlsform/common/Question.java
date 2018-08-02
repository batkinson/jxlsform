package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Row;
import com.github.batkinson.jxlsform.api.Survey;
import com.github.batkinson.jxlsform.api.SurveyItemContainer;

public class Question extends SurveyItem {
    Question(Survey survey, SurveyItemContainer parent, Row row) {
        super(survey, parent, row);
    }
}
