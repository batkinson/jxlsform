package com.github.batkinson.jxlsform.api;

import java.util.LinkedHashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public interface XLSForm extends Iterable<Sheet> {

    String SURVEY = "survey", CHOICES = "choices", SETTINGS = "settings";
    Set<String> REQUIRED_SHEET_NAMES = new LinkedHashSet<>(asList(SURVEY, CHOICES));
    Set<String> STANDARD_SHEET_NAMES = new LinkedHashSet<>(asList(SURVEY, CHOICES, SETTINGS));

    boolean hasSheet(String name);

    Sheet getSheet(String name);

    Sheet getSurvey();

    Sheet getChoices();

    Sheet getSettings();
}
