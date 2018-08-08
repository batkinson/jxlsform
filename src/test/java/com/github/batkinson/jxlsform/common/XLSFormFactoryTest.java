package com.github.batkinson.jxlsform.common;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.lang.reflect.Field;

import static org.junit.Assert.assertSame;

public class XLSFormFactoryTest {

    @Mock
    com.github.batkinson.jxlsform.api.SurveyItemFactory itemFactory;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void create() {
        new XLSFormFactory();
    }

    @Test
    public void createWithFactory() throws IllegalAccessException, NoSuchFieldException {
        XLSFormFactory formFactory = new XLSFormFactory(itemFactory);
        Field f = formFactory.getClass().getDeclaredField("itemFactory");
        f.setAccessible(true);
        assertSame(itemFactory, f.get(formFactory));
    }
}
