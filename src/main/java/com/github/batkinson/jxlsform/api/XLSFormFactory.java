package com.github.batkinson.jxlsform.api;

import java.io.InputStream;

public interface XLSFormFactory {
    XLSForm create(InputStream stream) throws XLSFormException;
}
