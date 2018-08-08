package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.XLSFormException;

public class Select extends Question implements com.github.batkinson.jxlsform.api.Select {

    private final com.github.batkinson.jxlsform.api.ChoiceList choices;

    Select(com.github.batkinson.jxlsform.api.Survey survey,
           com.github.batkinson.jxlsform.api.SurveyItemContainer parent,
           com.github.batkinson.jxlsform.api.Row row) {
        super(survey, parent, row);

        String listName = getListName();
        choices = getForm().getChoices().getChoiceList(listName)
                .orElseThrow(() -> new XLSFormException("choice list " + listName + " does not exist"));

    }

    private String[] getSplitType() {
        return super.getType().split("\\s+");
    }

    @Override
    public String getType() {
        return getSplitType()[0];
    }

    @Override
    public String getListName() {
        String[] type = getSplitType();
        if (type.length < 2) {
            throw new XLSFormException("choice list not specified: " + getRow());
        }
        return type[1];
    }

    @Override
    public com.github.batkinson.jxlsform.api.ChoiceList getChoiceList() {
        return choices;
    }
}
