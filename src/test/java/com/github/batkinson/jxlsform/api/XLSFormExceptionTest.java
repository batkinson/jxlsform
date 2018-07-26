package com.github.batkinson.jxlsform.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XLSFormExceptionTest {

    @Test
    public void messageConstructor() {
        String message = "hi!";
        assertEquals(message, new XLSFormException(message).getMessage());
    }

    @Test
    public void messageAndCauseConstructor() {
        String message = "hi!";
        Throwable cause = new Throwable();
        XLSFormException exception = new XLSFormException(message, cause);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
