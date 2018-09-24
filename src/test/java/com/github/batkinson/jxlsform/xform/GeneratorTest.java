package com.github.batkinson.jxlsform.xform;

import com.github.batkinson.jxlsform.common.XLSFormFactory;
import com.github.batkinson.jxlsform.poi.WorkbookFactory;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import static junit.framework.TestCase.assertTrue;

public class GeneratorTest {

    @Test
    public void generatedFormContainsUuid() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream xlsxIn = GeneratorTest.class.getResourceAsStream("/xlsx/simple_no_translations.xlsx");
        new DefaultGenerator()
                .generateXForm(
                        new XLSFormFactory().create(
                                new WorkbookFactory().create(xlsxIn)),
                        new PrintWriter(out, true));
        String generated = new String(out.toByteArray());
        assertTrue(generated.contains("calculate=\"concat('uuid:', uuid())\""));
    }
}
