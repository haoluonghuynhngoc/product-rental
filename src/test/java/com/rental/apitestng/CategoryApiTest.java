package com.rental.apitestng;

import com.rental.RentalApplication;
import com.rental.domain.Category;
import com.rental.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.NoSuchElementException;

@SpringBootTest(classes = RentalApplication.class)
public class CategoryApiTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test(expectedExceptions = Exception.class)
    public void getCategoryByIdTestNullPointerException() {
        Category category = categoryRepository.findById(100L).get();
    }

    @Test
    public void getCategoryByNameTestUpdateNameCategoryEntity() {
        Category actual = null;
        try {
            actual = categoryRepository.findByName("Spring Boot");
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }
        Assert.assertNotNull(actual,"Không Có tên Spring Boot trong bảng Category");
    }

    @Test
    public void getAllCategoryTestSizeCategory() {
        boolean Actual = categoryRepository.findAll().size() >= 6;
        Assert.assertTrue(Actual, "Số lượng phần tử nhỏ hơn 6 ");
    }

    @Test
    public void getCategoryByIdTestNotNull() {
        Category actual =categoryRepository.findById(1L).orElse(null);
        Assert.assertNotNull(actual,"Category is null because cant not find the category have id is 1");
    }
}
