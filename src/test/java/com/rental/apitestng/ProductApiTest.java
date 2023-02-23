package com.rental.apitestng;

import com.rental.RentalApplication;
import com.rental.domain.Product;
import com.rental.repository.ProductRepository;
import com.rental.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@SpringBootTest(classes = RentalApplication.class)
public class ProductApiTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @Test(expectedExceptions = Exception.class)
    public void getProductByIdTestNullPointerException() {
        Product product = productRepository.findById(200L).get();
    }

    @Test
    public void getProductByIdAndUpdateProductTestNotNullPointerException() {
        Product product = productRepository.findById(1L).map(
                productEntity -> {
                    productEntity.setPrice(1.0);
                    productEntity.setQuantity(1);
                    productEntity.setName("Spring Boot");
                    return productEntity;
                }
        ).get();
        Assert.assertNotNull(productRepository.save(product));
    }

    @Test
    public void getProductByIdAndTestEqualProductName() {
        String expected = "Spring Boot";
        Product product = productRepository.findById(1L).get();
        Assert.assertEquals(product.getName(), expected, "Two result is not correct");
    }

}
