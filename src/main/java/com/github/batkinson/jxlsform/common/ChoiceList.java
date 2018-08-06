package com.github.batkinson.jxlsform.common;


import com.github.batkinson.jxlsform.api.XLSFormException;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class ChoiceList implements com.github.batkinson.jxlsform.api.ChoiceList {

    private final com.github.batkinson.jxlsform.api.Choices choices;
    private final String name;
    private final Map<String, com.github.batkinson.jxlsform.api.Choice> choiceMap = new LinkedHashMap<>();

    ChoiceList(com.github.batkinson.jxlsform.api.Choices choices, String name) {
        if (choices == null) {
            throw new XLSFormException("choices is required");
        }
        if (name == null) {
            throw new XLSFormException("name is required");
        }
        this.choices = choices;
        this.name = name;
    }

    @Override
    public com.github.batkinson.jxlsform.api.Choices getChoices() {
        return choices;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<com.github.batkinson.jxlsform.api.Choice> getChoice(String value) {
        return Optional.ofNullable(choiceMap.get(value));
    }

    void add(Choice choice) {
        choiceMap.put(choice.getName(), choice);
    }

    @Override
    public Iterator<com.github.batkinson.jxlsform.api.Choice> iterator() {
        return choiceMap.values().iterator();
    }
}
