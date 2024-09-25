package org.example;

public class TestClass {

    @BeforeSuite
    public static void method1() {
        System.out.println(1);
    }

    public static void method2() {
        System.out.println(2);
    }

    @BeforeSuite
    public static void method3() {
        System.out.println(3);
    }

    @BeforeSuite
    public static void method4() {
        System.out.println(4);
    }
}
