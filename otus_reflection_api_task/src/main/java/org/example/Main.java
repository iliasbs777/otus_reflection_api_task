package org.example;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws Exception {

        Class cl = TestClass.class;
        Method[] methods = cl.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                //for (int i = 0; i < method.getAnnotation(BeforeSuite.class).count(); i++) {
                    method.invoke(null);
                //}
            }

            if (method.isAnnotationPresent(AfterSuite.class)) {
                method.invoke(null);
            }
        }
    }
}