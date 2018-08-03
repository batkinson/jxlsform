package com.github.batkinson.jxlsform.api;

import java.util.Optional;

public interface ChoiceList extends Iterable<Choice> {

    Choices getChoices();

    String getName();

    Optional<Choice> getChoice(String value);

}
