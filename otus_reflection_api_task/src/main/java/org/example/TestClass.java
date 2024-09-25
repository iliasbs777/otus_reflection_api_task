package org.example;

import org.example.annotations.AfterSuite;
import org.example.annotations.BeforeSuite;
import org.example.annotations.Test;

public class TestClass {

    @BeforeSuite
    public static void method1() {
        System.out.println(1);
    }

    @Test(priority = 10)
    public static void method2() {
        System.out.println(2);
    }

    @Test
    public static void method3() {
        System.out.println(3);
    }

    @Test(priority = 3)
    public static void method4() {
        System.out.println(4);
    }

    @AfterSuite
    public static void method5() {
        System.out.println(5);
    }

    @AfterSuite
    public static void method6() {
        System.out.println(6);
    }
}
