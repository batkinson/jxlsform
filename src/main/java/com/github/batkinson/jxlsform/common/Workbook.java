package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.XLSFormException;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class Workbook implements com.github.batkinson.jxlsform.api.Workbook {

    private Map<String, com.github.batkinson.jxlsform.api.Sheet> sheets = new LinkedHashMap<>();

    @Override
    public Optional<com.github.batkinson.jxlsform.api.Sheet> getSheet(String name) {
        return Optional.ofNullable(sheets.get(name));
    }

    public Sheet addSheet(String name) {
        if (name == null) {
            throw new XLSFormException("sheet name is required");
        }
        if (getSheet(name).isPresent()) {
            throw new XLSFormException("a sheet named '" + name + "' already exists");
        }
        Sheet newSheet = new Sheet(this, name);
        sheets.put(name, newSheet);
        return newSheet;
    }

    @Override
    public Iterator<com.github.batkinson.jxlsform.api.Sheet> iterator() {
        return sheets.values().iterator();
    }
}
