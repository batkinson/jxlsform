package com.github.batkinson.jxlsform.common;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class Choices implements com.github.batkinson.jxlsform.api.Choices {

    private final com.github.batkinson.jxlsform.api.XLSForm form;
    private final com.github.batkinson.jxlsform.api.Sheet sheet;
    private final Map<String, com.github.batkinson.jxlsform.api.ChoiceList> choices = new LinkedHashMap<>();

    Choices(com.github.batkinson.jxlsform.api.XLSForm form, com.github.batkinson.jxlsform.api.Sheet sheet) {
        this.form = form;
        this.sheet = sheet;
    }

    @Override
    public com.github.batkinson.jxlsform.api.XLSForm getForm() {
        return form;
    }

    @Override
    public com.github.batkinson.jxlsform.api.Sheet getSheet() {
        return sheet;
    }

    @Override
    public Optional<com.github.batkinson.jxlsform.api.ChoiceList> getChoiceList(String listName) {
        return Optional.ofNullable(choices.get(listName));
    }

    void addList(com.github.batkinson.jxlsform.api.ChoiceList list) {
        choices.put(list.getName(), list);
    }

    @Override
    public Iterator<com.github.batkinson.jxlsform.api.ChoiceList> iterator() {
        return choices.values().iterator();
    }
}
