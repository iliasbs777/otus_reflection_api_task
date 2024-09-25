package org.example;

import org.example.annotations.AfterSuite;
import org.example.annotations.BeforeSuite;
import org.example.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        Class cl = TestClass.class;
        Method[] methods = cl.getDeclaredMethods();
        List<Method> methodTestList = new ArrayList<>();
        int beforeSuiteCount = 0;
        int afterSuiteSuiteCount = 0;
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class) && beforeSuiteCount < 1) {
                    method.invoke(null);
                    beforeSuiteCount++;
            }

            if (method.isAnnotationPresent(Test.class)) {
                methodTestList.add(method);
            }
        }

        for (Method methodTest : methodTestList.stream().sorted(Comparator.comparing(method->method.getAnnotation(Test.class).priority())).toList().reversed()) {
            methodTest.invoke(null);
        }

        for (Method method : methods) {
            if (method.isAnnotationPresent(AfterSuite.class) && afterSuiteSuiteCount < 1) {
                method.invoke(null);
                afterSuiteSuiteCount++;
            }
        }

        System.out.println("сколько было всего " + Arrays.stream(methods).count());
        System.out.println("сколько прошло успешно " + (methodTestList.size() + beforeSuiteCount + afterSuiteSuiteCount));
        System.out.println("сколько упало " + (Arrays.stream(methods).count() - methodTestList.size() - beforeSuiteCount - afterSuiteSuiteCount));
    }
}