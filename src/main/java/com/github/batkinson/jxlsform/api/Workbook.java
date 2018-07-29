package com.github.batkinson.jxlsform.api;

import java.util.Optional;

public interface Workbook extends Iterable<Sheet> {

    Optional<Sheet> getSheet(String name);

}
