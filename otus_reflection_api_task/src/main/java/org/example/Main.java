package org.example;

import org.example.annotations.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        Class cl = TestClass.class;
        Method[] methods = cl.getDeclaredMethods();
        List<Method> methodBeforeTestList = new ArrayList<>();
        List<Method> methodTestList = new ArrayList<>();
        List<Method> methodAfterTestList = new ArrayList<>();
        int beforeSuiteCount = 0;
        int methodTestsWithOutDisabledCount = 0;
        int afterSuiteSuiteCount = 0;
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class) && beforeSuiteCount < 1) {
                    method.invoke(null);
                    beforeSuiteCount++;
            }

            if (method.isAnnotationPresent(Before.class)) {
                methodBeforeTestList.add(method);
            }

            if (method.isAnnotationPresent(Test.class)) {
                methodTestList.add(method);
            }

            if (method.isAnnotationPresent(After.class)) {
                methodAfterTestList.add(method);
            }
        }

        for (Method methodBeforeTest : methodBeforeTestList.stream().sorted(Comparator.comparing(method->method.getAnnotation(Before.class).priority())).toList().reversed()) {
            methodBeforeTest.invoke(null);
        }

        for (Method methodTest : methodTestList.stream().sorted(Comparator.comparing(method->method.getAnnotation(Test.class).priority())).toList().reversed()) {
            if (!methodTest.isAnnotationPresent(Disabled.class)) {
                methodTest.invoke(null);
                methodTestsWithOutDisabledCount++;
            }
        }

        for (Method methodAfterTest : methodAfterTestList.stream().sorted(Comparator.comparing(method->method.getAnnotation(After.class).priority())).toList().reversed()) {
            methodAfterTest.invoke(null);
        }

        for (Method method : methods) {
            if (method.isAnnotationPresent(AfterSuite.class) && afterSuiteSuiteCount < 1) {
                method.invoke(null);
                afterSuiteSuiteCount++;
            }
        }

        System.out.println("сколько было всего " + Arrays.stream(methods).count());
        System.out.println("сколько прошло успешно " + (methodTestsWithOutDisabledCount + beforeSuiteCount + afterSuiteSuiteCount + methodBeforeTestList.size() + methodAfterTestList.size()));
        System.out.println("сколько упало " + (Arrays.stream(methods).count() - methodTestsWithOutDisabledCount - beforeSuiteCount - afterSuiteSuiteCount - methodBeforeTestList.size() - methodAfterTestList.size()));
    }
}