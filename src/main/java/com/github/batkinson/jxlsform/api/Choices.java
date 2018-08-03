package com.github.batkinson.jxlsform.api;

import java.util.Optional;

public interface Choices extends Iterable<ChoiceList> {

    XLSForm getForm();

    Sheet getSheet();

    Optional<ChoiceList> getChoiceList(String listName);
}
