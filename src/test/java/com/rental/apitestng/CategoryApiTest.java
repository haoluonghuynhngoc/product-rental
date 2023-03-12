package com.rental.apitestng;

import com.rental.RentalApplication;
import com.rental.domain.Category;
import com.rental.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
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
            actual = categoryRepository.findByName("Bóng Chuyền");
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }
        Assert.assertNotNull(actual,"Không Có tên Bóng Chuyền trong bảng thư mục");
        if (actual!=null){
            System.out.println(actual.toString());
        }
    }

    @Test
    public void getAllCategoryTestSizeCategory() {
        boolean actual = categoryRepository.findAll().size() > 3;
        Assert.assertTrue(actual, "Số lượng phần tử nhỏ hơn 3 ");
        if (actual==true){
            List<Category> list = categoryRepository.findAll();
            list.forEach(x-> System.out.println(x.toString()));
        }
    }

    @Test
    public void getCategoryByIdTestNotNull() {
        Category actual =categoryRepository.findById(1L).orElse(null);
        Assert.assertNotNull(actual,"Category is null because cant not find the category have id is 1");
    }
}
