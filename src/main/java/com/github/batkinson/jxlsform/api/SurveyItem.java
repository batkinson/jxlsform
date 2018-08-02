package com.github.batkinson.jxlsform.api;

import java.util.Optional;

public interface SurveyItem {

    XLSForm getForm();

    Survey getSurvey();

    Row getRow();

    SurveyItemContainer getParent();

    String getType();

    Optional<String> getName();

    Optional<String> getLabel();

}
