package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Sheet;
import com.github.batkinson.jxlsform.api.XLSFormException;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class XLSForm implements com.github.batkinson.jxlsform.api.XLSForm {

    private final LinkedHashMap<String, Sheet> sheets;

    public XLSForm(List<Sheet> sheets) {
        this.sheets = new LinkedHashMap<>();
        for (Sheet sheet : sheets) {
            this.sheets.put(sheet.getName(), sheet);
            sheet.setForm(this);
        }
    }

    @Override
    public boolean hasSheet(String name) {
        return sheets.containsKey(name);
    }

    @Override
    public Sheet getSheet(String name) {
        if (sheets.containsKey(name)) {
            return sheets.get(name);
        }
        throw new XLSFormException("no sheet named " + name);
    }

    @Override
    public Sheet getSurvey() {
        return getSheet(SURVEY);
    }

    @Override
    public Sheet getChoices() {
        return getSheet(CHOICES);
    }

    @Override
    public Sheet getSettings() {
        return getSheet(SETTINGS);
    }

    @Override
    public Iterator<Sheet> iterator() {
        return sheets.values().iterator();
    }
}
