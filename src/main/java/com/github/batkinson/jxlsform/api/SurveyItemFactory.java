package com.github.batkinson.jxlsform.api;

import java.util.Optional;

public interface SurveyItemFactory {
    Optional<SurveyItem> create(Survey survey, SurveyItemContainer parent, Row row);
}
