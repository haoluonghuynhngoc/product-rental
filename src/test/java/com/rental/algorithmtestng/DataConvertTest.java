package com.rental.algorithmtestng;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class DataConvertTest {
    public String convertBinary(int num) {
        List<Integer> list = new ArrayList<>();
        String binary = "";
        while (num > 0) {
            list.add(num % 2);
            num = num / 2;
        }
        Collections.reverse(list);
        for (Integer x : list) {
            binary += x;
        }
        return binary;
    }
    @DataProvider(name = "convertSet")
    public Object[][] dataSet() {
        return new Object[][]{
                {"1111100111", convertBinary(9998)},
                {"101101",convertBinary(45)},
 //               {"101101",convertBinary(-1)},
                {"1010",convertBinary(10)}
        };
    }
    // asas
    @Test(dataProvider = "convertSet")
    public void binaryConvertTests(String testOne ,String testTow) {
        Assert.assertEquals(testTow,testOne);
    }
}
