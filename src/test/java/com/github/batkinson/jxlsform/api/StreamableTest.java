package com.github.batkinson.jxlsform.api;

import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static junit.framework.TestCase.assertTrue;

public class StreamableTest {

    @Test
    public void test() {
        List<String> items = range(1, 10)
                .boxed()
                .map(Object::toString)
                .collect(toList());
        new TestStreamable<>(items)
                .stream()
                .forEach(s -> assertTrue(items.contains(s)));
    }
}

class TestStreamable<T> implements Streamable<T> {

    private Collection<T> items;

    TestStreamable(Collection<T> items) {
        this.items = items;
    }

    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }
}
