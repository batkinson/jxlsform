package com.github.batkinson.jxlsform.poi;

import com.github.batkinson.jxlsform.api.XLSFormException;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static com.github.batkinson.jxlsform.poi.FormLoader.getStream;

public class WorkbookFactoryTest {

    @Test
    public void factoryCreate() {
        new WorkbookFactory();
    }

    @Test(expected = XLSFormException.class)
    public void createWithBadStream() {
        new WorkbookFactory().create(new ByteArrayInputStream(new byte[]{}));
    }

    @Test
    public void create() {
        new WorkbookFactory()
                .create(getStream("simplest.xlsx"));
    }
}
