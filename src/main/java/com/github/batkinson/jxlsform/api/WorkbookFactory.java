package com.github.batkinson.jxlsform.api;

import java.io.InputStream;

public interface WorkbookFactory {
    Workbook create(InputStream stream) throws XLSFormException;
}
