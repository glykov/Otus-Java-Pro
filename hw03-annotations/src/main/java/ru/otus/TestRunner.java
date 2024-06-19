package ru.otus;


import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.exceptions.TestFailException;

import java.lang.reflect.Method;
import java.util.*;

public class TestRunner {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void run(String testClassName) {
        Map<String, String> failedTests = new HashMap<>();
        long testsRun = 0;
        long failures = 0;

        try {
            Class<?> testClass = Class.forName(testClassName);
            List<Method> testMethods = findTestMethods(testClass);
            Method before = findBeforeMethod(testClass);
            Method after = findAfterMethod(testClass);

            for (Method m : testMethods) {
                var t = testClass.getConstructor().newInstance();
                try {
                    testsRun++;
                    if (before != null) {
                        before.invoke(t);
                    }
                    m.invoke(t);
                } catch (Exception e) {
                    if (e.getCause() instanceof TestFailException) {
                        System.out.println("Test Fail Exception is caught");
                        failedTests.put(m.getName(), e.getCause().getMessage());
                        failures++;
                    } else {
                        throw new RuntimeException(e);
                    }
                } finally {
                    if (after != null) {
                        after.invoke(t);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        showResults(testsRun, failures, failedTests);
    }

    private static List<Method> findTestMethods(Class<?> testClass) {
        List<Method> result = new ArrayList<>();

        Objects.requireNonNull(testClass);
        for (Method m : testClass.getDeclaredMethods()) {
            if (m.getAnnotation(Test.class) != null) {
                result.add(m);
            }
        }

        return result;
    }

    private static Method findBeforeMethod(Class<?> testClass) {
        Method before = null;

        Objects.requireNonNull(testClass);
        for (Method m : testClass.getDeclaredMethods()) {
            if (before == null && m.getAnnotation(Before.class) != null) {
                before = m;
            }
        }

        return before;
    }

    private static Method findAfterMethod(Class<?> testClass) {
        Method after = null;

        Objects.requireNonNull(testClass);
        for (Method m : testClass.getDeclaredMethods()) {
            if (after == null && m.getAnnotation(After.class) != null) {
                after = m;
            }
        }

        return after;
    }

    private static void showResults(long testsRun, long failures, Map<String, String> failedTests) {
        System.out.println("\n\nTotal of " + testsRun + " tests was run");
        System.out.println(ANSI_GREEN + "OK (" + (testsRun - failures) + " tests)" + ANSI_RESET);
        if (failures != 0) {
            System.out.println(ANSI_RED + "\n>>> " + failures + " tests failed <<<" + ANSI_RESET);
            for (var e : failedTests.entrySet()) {
                System.out.println(e.getKey() + " failed because " + e.getValue());
            }
        }
    }
}
