package com.rental.apitestng;

import com.rental.RentalApplication;
import com.rental.domain.Product;
import com.rental.repository.ProductRepository;
import com.rental.service.ProductService;
import net.bytebuddy.implementation.ExceptionMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest(classes = RentalApplication.class)
public class ProductApiTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void getProductByIdTestDepositEqual() {
        boolean actual = false;
        try {
            actual = productRepository.findById(300L).get().getDeposit() == 700000;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(actual, "The actual is false");
    }


    @Test
    public void getProductByIdTestNullPointerException() {
        Assert.assertThrows(NoSuchElementException.class, () -> {
            productRepository.findById(100L).get();
        });
    }


    @Test
    public void getProductByIdAndTestEqualProductName() {
        String expected = "Váy 1";
        String actual = "";
        try {
            actual = productRepository.findById(98L).get().getName();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(actual, expected, "Hai kết quả không giống nhau");
    }

    @Test
    public void getListProductByNameTestSizeProductMoreThan2() {
        List<Product> list = new ArrayList<>();
        try {
            list = productRepository.findByNameLikeOrId("%Bông%",1L);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        boolean actual = list.size() >= 2;
        Assert.assertTrue(actual, "Số lượng phần tử có tên váy nhỏ hơn 2");
        if (actual){
            list.forEach(x-> System.out.println(x.getId() +" || "+ x.getDescription()));
        }
    }

    @Test
    public void getProductByIdTestPriceProduct() {
        boolean actual = false;
        try {
            actual = productRepository.findById(98L).get().getPrice() == 200000;
            System.out.println("Giá tiền của sản phảm có ID = 98 là " + productRepository.findById(98L).get().getPrice());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(actual, "Kết quả trả ra false");
    }


}
