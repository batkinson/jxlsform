package com.github.batkinson.jxlsform.api;

import java.util.Optional;

public interface SurveyItemFactory {
    Group createGroup(Survey survey, SurveyItemContainer parent, Row row);
    Repeat createRepeat(Survey survey, SurveyItemContainer parent, Row row);
    Optional<SurveyItem> create(Survey survey, SurveyItemContainer parent, Row row);
}
