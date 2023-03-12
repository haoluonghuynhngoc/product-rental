package com.rental.algorithmtestng;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

public class DataBinarySearchTest {
    public int binarySearch(int[] inputArray, int givenNumber) {
        if (inputArray.length == 0) {
            System.out.println("Given Array is empty!");
            return -1;
        }
        Arrays.sort(inputArray);
        return performBinarySearch(inputArray, givenNumber, 0, inputArray.length - 1);
    }

    private int performBinarySearch(int[] inputArray, int givenNumber, int low, int max) {
        if (max >= low) {
            int mid = low + (max - low) / 2;
            if (inputArray[mid] == givenNumber)
                return mid;
            else if (inputArray[mid] > givenNumber)
                return performBinarySearch(inputArray, givenNumber, low, mid - 1);
            else {
                return performBinarySearch(inputArray, givenNumber, mid + 1, max);
            }
        }
        return -1;
    }
    @DataProvider(name = "binarySearch")
    public Object[][] dataSet() {
        DataBinarySearchTest bs = new DataBinarySearchTest();
        return new Object[][]{
                {5+"",""+ bs.binarySearch(new int[]{14, 10, 19, 26, 27, 31, 33, 35, 42, 44}, 8)},
                {1+"",""+ bs.binarySearch(new int[]{10, 14, 19, 26, 27, 31, 33, 35, 42, 44}, 14)},
                {9+"",""+ bs.binarySearch(new int[]{10, 14, 19, 26, 27, 31, 33, 35, 42, 44}, 44)}
        };
    }
    @Test(dataProvider = "binarySearch")
    public void binaryConvertTests(String testOne ,String testTow) {
        Assert.assertEquals(testTow,testOne);
    }

}
