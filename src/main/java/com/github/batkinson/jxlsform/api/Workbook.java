package com.github.batkinson.jxlsform.api;

import java.util.Optional;

public interface Workbook extends Streamable<Sheet> {

    Optional<Sheet> getSheet(String name);

}
