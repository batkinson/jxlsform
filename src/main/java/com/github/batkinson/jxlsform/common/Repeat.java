package com.github.batkinson.jxlsform.common;

public class Repeat extends ContainerItem implements com.github.batkinson.jxlsform.api.Repeat, SurveyItemContainer {
    Repeat(com.github.batkinson.jxlsform.api.Survey survey,
           com.github.batkinson.jxlsform.api.SurveyItemContainer parent,
           com.github.batkinson.jxlsform.api.Row row) {
        super(survey, parent, row);
    }
}
