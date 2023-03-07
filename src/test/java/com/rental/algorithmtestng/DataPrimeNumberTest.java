package com.rental.algorithmtestng;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataPrimeNumberTest {
    public static boolean isPrimeNumber(int n) {
        if (n < 2) {
            return false;
        }
        int squareRoot = (int) Math.sqrt(n);
        for (int i = 2; i <= squareRoot; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
    // 2 3 5 7 11 13 17 19 23 29 31 37 41 43 47 53 59 61 67 71 73 79 83 89 97
    @Test
    public void binaryConvertTests() {
        boolean actual =isPrimeNumber(13);
        Assert.assertTrue(actual,"Không phải là số nguyên tố ");
    }
}
