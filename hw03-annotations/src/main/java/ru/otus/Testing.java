package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.assertions.Assertions;

import java.util.ArrayList;
import java.util.List;

public class Testing {
    List<String> strings;

    @Test
    public void testMethodOne() {
        System.out.println("Test Method One");
        Assertions.assertEquals(3, strings.size());
        strings.add("Four");
        Assertions.assertEquals("Four", strings.get(3));
    }

    @Test
    public void testMethodTwo() {
        System.out.println("Test Method Two");
        Assertions.assertNotNull(strings);
        Assertions.assertEquals(3, strings.size());
    }

    @Test
    public void testMethodThree() {
        System.out.println("Test Method Three");
        Assertions.assertEquals(2.0, Math.sqrt(4.0));
    }

    @Test
    public void testMethodFour() {
        System.out.println("Test Method Four");
        Assertions.assertNotNull(null);
    }

    @Before
    public void beforeMethod() {
        System.out.println("Before Method");
        strings = new ArrayList<>();
        strings.add("One");
        strings.add("Two");
        strings.add("Three");
    }

    @After
    public void afterMethod() {
        System.out.println("After Method");
        strings.clear();
    }
}
