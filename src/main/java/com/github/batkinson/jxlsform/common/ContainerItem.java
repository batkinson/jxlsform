package com.github.batkinson.jxlsform.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContainerItem extends SurveyItem implements SurveyItemContainer {

    private final List<com.github.batkinson.jxlsform.api.SurveyItem> items = new ArrayList<>();

    ContainerItem(com.github.batkinson.jxlsform.api.Survey survey,
                  com.github.batkinson.jxlsform.api.SurveyItemContainer parent,
                  com.github.batkinson.jxlsform.api.Row row) {
        super(survey, parent, row);
    }

    @Override
    public void add(com.github.batkinson.jxlsform.api.SurveyItem item) {
        items.add(item);
    }

    @Override
    public Iterator<com.github.batkinson.jxlsform.api.SurveyItem> iterator() {
        return items.iterator();
    }

}
