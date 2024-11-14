package lab.reflection;

import lab.reflection.annotations.*;

public class TestClass {

//    @BeforeSuite
//    public static void method0() {
//        System.out.println(0);
//    }

    @BeforeSuite
    public static void method1() {
        System.out.println("BeforeSuite");
    }

    @Test(priority = 11)
    public static void method11() {
        System.out.println(11);
    }

    @Test(priority = 10)
    public static void method2() {
        System.out.println(2);
    }

    @Test
    public static void method3() {
        System.out.println(3);
    }

    @Disabled
    @Test(priority = 3)
    public static void method4() {
        System.out.println(4);
    }

    @AfterSuite
    public static void method5() {
        System.out.println("AfterSuite");
    }

//    @AfterSuite
//    public static void method6() {
//        System.out.println(6);
//    }

    @Before
    public static void method7() {
        System.out.println("before");
    }

    @After
    public static void method8() {
        System.out.println("after");
    }
}
