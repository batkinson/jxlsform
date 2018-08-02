package com.github.batkinson.jxlsform.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Header extends Row {

    private final Map<String, com.github.batkinson.jxlsform.api.Cell> namedCells = new HashMap<>();

    Header(com.github.batkinson.jxlsform.api.Sheet sheet, int rowNumber) {
        super(sheet, rowNumber);
    }

    @Override
    public Optional<com.github.batkinson.jxlsform.api.Cell> getCellByHeader(String name) {
        if (namedCells.containsKey(name)) {
            return Optional.ofNullable(namedCells.get(name));
        } else {
            // assume non-null cells
            for (com.github.batkinson.jxlsform.api.Cell cell : this) {
                if (name.equals(cell.getName())) {
                    namedCells.put(name, cell);
                    return Optional.of(cell);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "header, " + super.toString();
    }
}
