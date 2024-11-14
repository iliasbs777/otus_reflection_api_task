package lab.reflection;

import lab.reflection.annotations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestExecutor {

    public static void execute(Class cl) throws Exception {

        Method[] methods = cl.getDeclaredMethods();
        List<Method> methodBeforeTestList = new ArrayList<>();
        List<Method> methodTestList = new ArrayList<>();
        List<Method> methodAfterTestList = new ArrayList<>();
        List<Method> methodBeforeSuiteList = new ArrayList<>();
        List<Method> methodAfterSuiteList = new ArrayList<>();
        int errorCount = 0;
        int successCount = 0;
        for (Method method : methods) {

            if (method.isAnnotationPresent(BeforeSuite.class)) {
                methodBeforeSuiteList.add(method);
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

            if (method.isAnnotationPresent(AfterSuite.class)) {
                methodAfterSuiteList.add(method);
            }
        }


        if (methodBeforeSuiteList.stream().count() > 1) {
            System.out.println("BeforeSuite > 1. Прерываем выполнение всего.");
            return;
        }

        if (methodAfterSuiteList.stream().count() > 1) {
            System.out.println("AfterSuite > 1. Прерываем выполнение всего.");
            return;
        }

        for (Method method : methodBeforeTestList) {
            if (method.isAnnotationPresent(Test.class)) {
                System.out.println("beforeAndTestCount > 1. Прерываем выполнение всего.");
            }
        }

        for (Method method : methodAfterTestList) {
            if (method.isAnnotationPresent(Test.class)) {
                System.out.println("afterAndTestCount > 1. Прерываем выполнение всего.");
            }
        }

        if ((long) methodBeforeSuiteList.size() > 0) {
            methodBeforeSuiteList.getFirst().invoke(null);
        }

        for (Method methodTest : methodTestList.stream().sorted(Comparator.comparing(method->method.getAnnotation(Test.class).priority())).toList().reversed()) {
            if (!methodTest.isAnnotationPresent(Disabled.class)) {
                if (methodTest.getAnnotation(Test.class).priority() >= 1 && methodTest.getAnnotation(Test.class).priority() <= 10) {
                    try {
                        for (Method methodBeforeTest : methodBeforeTestList.stream().sorted(Comparator.comparing(method -> method.getAnnotation(Before.class).priority())).toList().reversed()) {
                            methodBeforeTest.invoke(null);
                        }

                        methodTest.invoke(null);
                        successCount++;

                        for (Method methodAfterTest : methodAfterTestList.stream().sorted(Comparator.comparing(method -> method.getAnnotation(After.class).priority())).toList().reversed()) {
                            methodAfterTest.invoke(null);
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        errorCount++;
                        e.printStackTrace();
                    }
                }
            }
        }

        if ((long) methodAfterSuiteList.size() > 0) {
            methodAfterSuiteList.getFirst().invoke(null);
        }

        System.out.println("сколько было всего " + methodTestList.stream().count());
        System.out.println("сколько прошло успешно " + (successCount));
        System.out.println("сколько упало " + errorCount);
    }
}
