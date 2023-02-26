package com.rental.apitestng;

import com.rental.RentalApplication;
import com.rental.domain.Category;
import com.rental.repository.CategoryRepository;
import com.rental.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@SpringBootTest(classes = RentalApplication.class)
public class CategoryApiTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test(expectedExceptions = Exception.class)
    public void getCategoryByIdTestNullPointerException() {
        Category category = categoryRepository.findById(10L).get();
    }

    @Test
    public void getCategoryByNameTestNotNull() {
        Assert.assertNotNull(categoryRepository.findByName("Thể Thao"));
    }

    @Test
    public void getCategoryByNameTestUpdateNameCategoryEntity() {
        Category category = categoryRepository.findByName("TestNG");
        category.setName("Spring Boot");
        Assert.assertNotNull(categoryRepository.save(category));
    }

    @Test
    public void getAllCategoryTestSizeCategory() {
        Assert.assertTrue(categoryRepository.findAll().size() >= 4, "Size Category less than 4 ");
    }

    @Test
    public void getCategoryByIdTestNotNull() {
        Assert.assertNotNull(categoryRepository.findById(1L));
    }
}
