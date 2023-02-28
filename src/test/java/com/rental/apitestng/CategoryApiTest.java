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
        Category category = categoryRepository.findById(10L).get(); // dòng này kiểm tra trong database có phần từ nào có id là 10 hay không
        // nếu không thì hàm sẽ quăn lỗi và đúng với exception
    }

    @Test
    public void getCategoryByNameTestNotNull() {
        Assert.assertNotNull(categoryRepository.findByName("Thể Thao"));
        // hàm này sẽ tìm theo tên chuyên mục nếu có dữ liệu nó sẽ trả về not null
    }

    @Test
    public void getCategoryByNameTestUpdateNameCategoryEntity() {
        Category category = categoryRepository.findByName("TestNG"); // hàm này sẽ tìm trong bảng chuyên mục có tên là TestNG hay không
        category.setName("Spring Boot");
        Assert.assertNotNull(categoryRepository.save(category)); // hàm này sẽ thêm thông tin đã được thay đồi và kiểm tra có null hay không
    }

    @Test
    public void getAllCategoryTestSizeCategory() {
        Assert.assertTrue(categoryRepository.findAll().size() >= 4, "Size Category less than 4 ");
        // hàm nãy sẽ kiểm tra xem tổng số tổng số size có lớn hơn 4 hay không nếu đúng thì trả về true nếu sai trả về False
    }

    @Test
    public void getCategoryByIdTestNotNull() {
        Assert.assertNotNull(categoryRepository.findById(1L).orElse(null));
        // hàm này sẽ tìm xem trong bảng thư mục có category nào có id = 1 hay không
    }
}
