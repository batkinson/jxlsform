package com.github.batkinson.jxlsform.api;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Arrays.asList;

public interface XLSForm {

    String SURVEY = "survey", CHOICES = "choices", SETTINGS = "settings";
    Set<String> REQUIRED_SHEETS = new LinkedHashSet<>(asList(SURVEY, CHOICES));
    Set<String> STANDARD_SHEETS = new LinkedHashSet<>(asList(SURVEY, CHOICES, SETTINGS));

    Workbook getWorkbook();

    Survey getSurvey();

    Choices getChoices();

    Optional<Settings> getSettings();
}
