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
import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest(classes = RentalApplication.class)
public class ProductApiTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void getProductByIdTestDepositEqual() {
        boolean actual=false;
        try {
            actual =productRepository.findById(300L).get().getDeposit() == 700000;
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }
        Assert.assertTrue( actual , "The actual is false");
    }

    //    @Test(expectedExceptions = Exception.class)
    @Test
    public void getProductByIdTestNullPointerException() {
        Assert.assertThrows(NoSuchElementException.class, () -> {
            productRepository.findById(100L).get();
        });
    }

//    @Test
//    public void getProductByIdAndUpdateProductTestNotNullPointerException() {
//        Product product =null;
//        try {
//            productRepository.findById(100L).map(
//                    productEntity -> {
//                        productEntity.setQuantity(1);
//                        productEntity.setName("Bông tai ngọc trai hình giọt nước");
//                        return productEntity;
//                    }
//            ).get();
//        }catch (NoSuchElementException e){
//            e.printStackTrace();
//        }
//        Assert.assertNotNull(productRepository.save(product));
//    }

    @Test
    public void getProductByIdAndTestEqualProductName() {
        String expected = "Bông tai ngọc trai hình giọt nước";
        Product product = productRepository.findById(1L).get();
        Assert.assertEquals(product.getName(), expected, "Two result is not correct");
    }

    @Test
    public void getListProductByNameTestSizeProductMoreThan2() {
        List<Product> list = productRepository.findByNameLike("%n%");
        Assert.assertTrue(list.size() > 2, "List less than 3");
    }

    @Test
    public void getProductByIdTestPriceProduct() {
        boolean actual ;
        try {
            actual = productRepository.findById(101L).get().getPrice() == 100000;
        } catch (NoSuchElementException e) {
            actual= true;
        }
        Assert.assertFalse(actual, "The result is not correct");
    }


}
