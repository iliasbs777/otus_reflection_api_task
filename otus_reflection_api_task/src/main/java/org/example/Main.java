package org.example;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws Exception {

        Class cl = TestClass.class;
        Method[] methods = cl.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                method.invoke(null);
            }
        }
    }
}