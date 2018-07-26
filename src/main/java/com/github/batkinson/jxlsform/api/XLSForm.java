package com.github.batkinson.jxlsform.api;

public interface XLSForm extends Iterable<Sheet> {

    String SURVEY = "survey", CHOICES = "choices", SETTINGS = "settings";

    boolean hasSheet(String name);

    Sheet getSheet(String name);

    Sheet getSurvey();

    Sheet getChoices();

    Sheet getSettings();
}
