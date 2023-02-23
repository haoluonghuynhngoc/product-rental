package com.rental.basictestng;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviderTest {
    @DataProvider(name = "dataSet")
    public static Object[][] dataSet() {
        return new Object[][]{
                {"user3", "user3"},
                {"user1", "user1"},
                {"password2", "password2"},
                {"user3", "user3"}
        };
    }
    @Test(dataProvider = "dataSet")
    public void testDataProvider(String username, String password) {
        System.out.println(username + "============" + password);
        Assert.assertEquals(username, password,"Two results are not equal");
    }
}
