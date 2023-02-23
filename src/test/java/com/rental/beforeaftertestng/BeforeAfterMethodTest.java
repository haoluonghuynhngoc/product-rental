package com.rental.beforeaftertestng;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BeforeAfterMethodTest {

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("\n================================Before Test========================================");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("=================================After Test========================================\n");
    }


    @Test
    public void test1() {
        System.out.println("test 1 ");
    }

    @Test
    public void test2() {
        System.out.println("test 2 ");
    }

    @Test
    public void test3() {
        System.out.println("test 3 ");
    }

    @Test
    public void test4() {
        System.out.println("test 4 ");
    }
}
