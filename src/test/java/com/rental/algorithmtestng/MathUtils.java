package com.rental.algorithmtestng;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MathUtils {
    public static long getFactorial(int n) {
        if (n < 0 || n > 20) {
            throw new IllegalArgumentException("Invalid agrument n must be than 0 and n low 20");
        }
        if (n == 0 || n == 1) {
            return 1;
        }
        long product = 1;
        for (int i = 2; i <= n; i++) {
            product *= i;
        }
        return product;
    }

    @Test
    public void testFactorialWithNumberSix() {
        long expected = 720;
        long actual = MathUtils.getFactorial(6);
        Assert.assertEquals(expected, actual, "The result is not same ");
    }

    @Test
    public void testFactorialWithNumberNegative() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                MathUtils.getFactorial(-6));
    }

    @Test
    public void testFactorialWithNumberZero() {
        long expected = 1;
        long actual = MathUtils.getFactorial(1);
        Assert.assertEquals(expected,actual,"Two result is not equal");
    }

}

