package com.github.batkinson.jxlsform.api;

public interface SurveyItemFactory {

    Group createGroup(Survey survey, SurveyItemContainer parent, Row row);

    Repeat createRepeat(Survey survey, SurveyItemContainer parent, Row row);

    SurveyItem create(Survey survey, SurveyItemContainer parent, Row row);

}
