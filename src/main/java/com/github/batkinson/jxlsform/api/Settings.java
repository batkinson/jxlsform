package com.github.batkinson.jxlsform.api;

import java.util.Optional;

public interface Settings {

    XLSForm getForm();

    Sheet getSheet();

    Optional<Row> getRow();

    Optional<String> getFormTitle();

    Optional<String> getFormId();

    Optional<String> getFormVersion();

}
