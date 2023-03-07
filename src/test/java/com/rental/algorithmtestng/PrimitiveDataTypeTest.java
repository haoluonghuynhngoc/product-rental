package com.rental.algorithmtestng;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PrimitiveDataTypeTest {
    @Test
    public void towIntegerTestEqual() {
        int expected = 1;
        int actual = 1;
        Assert.assertEquals(expected, actual, "Two results are not equal");
    }

    @Test
    public void towIntegerTestEqualAndDataType() {
        int expected = 1;
        int actual = 1;
        Assert.assertSame(expected, actual,"Two results are not the same");
    }

    @Test
    public void stringIsEmptyTest() {
        String testNG = "TestNG with Spring Boot";
        boolean expected= false;
        boolean actual=testNG.isEmpty();
        Assert.assertFalse(actual,"The returned result is true");
    }

}
