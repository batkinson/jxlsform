package com.github.batkinson.jxlsform.poi;

import com.github.batkinson.jxlsform.api.XLSForm;
import com.github.batkinson.jxlsform.common.XLSFormFactory;

import java.io.IOException;
import java.io.InputStream;

class FormLoader {

    private static String getDirForFileName(String fileName) {
        return fileName.endsWith(".xls") ? "/xls" : "/xlsx";
    }

    static InputStream getStream(String fileName) {
        return FormLoader.class.getResourceAsStream(String.join("/", getDirForFileName(fileName), fileName));
    }

    static XLSForm getForm(String fileName) throws IOException {
        try (InputStream stream = getStream(fileName)) {
            return new XLSFormFactory().create(new WorkbookFactory().create(stream));
        }
    }

}
