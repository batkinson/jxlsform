package com.github.batkinson.jxlsform.api;

import java.util.Optional;

public interface Choice {

    ChoiceList getList();

    Row getRow();

    String getName();

    Optional<String> getLabel();

}
