package com.github.batkinson.jxlsform.xform;

import com.github.batkinson.jxlsform.api.SurveyItem;

import java.util.Optional;

public class Item {

    private final SurveyItem item;

    Item(SurveyItem item) {
        this.item = item;
    }

    public Element getElement() {
        return item.getName().map(name -> new Element(this, name)).orElse(null);
    }

    public Binding getBinding() {
        return item.getName().map(name -> new Binding(this, name)).orElse(null);
    }

    public Control getControl() {
        return item.getName().map(name -> new Control(this, name)).orElse(null);
    }

    public Optional<String> getType() {
        return Optional.ofNullable(xlsToXformType(item.getType()));
    }

    private static String xlsToXformType(String xlsType) {
        switch (xlsType) {
            case "integer":
            case "range":
                return "int";
            case "select_one":
                return "select1";
            case "select_multiple":
                return "select";
            case "geopoint":
            case "geotrace":
            case "geoshape":
            case "date":
            case "time":
            case "dateTime":
            case "barcode":
            case "decimal":
                return xlsType;
            case "image":
            case "audio":
            case "video":
            case "file":
                return "binary";
            case "xml-external":
                return null;
            case "text":
            case "note":
            case "calculate":
            case "hidden":
            case "acknowledge":
            default:
                return "string";
        }
    }
}
