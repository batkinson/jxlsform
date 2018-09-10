package com.github.batkinson.jxlsform.xform;

import com.github.batkinson.jxlsform.api.XLSForm;

import java.io.IOException;
import java.io.Writer;

public interface Generator {
    void generateXForm(XLSForm form, Writer writer) throws IOException;
}
