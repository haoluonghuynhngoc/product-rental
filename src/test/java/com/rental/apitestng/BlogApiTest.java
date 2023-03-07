package com.rental.apitestng;

import com.rental.RentalApplication;
import com.rental.domain.Blog;
import com.rental.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.NoSuchElementException;

@SpringBootTest(classes = RentalApplication.class)
public class BlogApiTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private BlogRepository blogRepository;

    @Test
    public void getBlogByIdTestEqualTitle() {
        String actual = "";
        String expected = "TestNG";
        try {
            actual = blogRepository.findById(1L).get().getTitle();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void saveBlogToDataTestEqualNotNull() {
        Blog blog = Blog.builder()
                .author("Ngọc Hảo")
                .description("Công nghệ thông tin là một ngành học được đào " +
                        "tạo để sử dụng máy tính và các phần mềm máy tính để phân phối " +
                        "và xử lý các dữ liệu thông tin, đồng thời dùng để trao đổi, lưu trữ " +
                        "và chuyển đổi các dữ liệu thông tin")
                .title("Đồ công nghệ ")
                .build();
        try {
            blog=  blogRepository.save(blog);
        }catch (Exception e){
            e.printStackTrace();
        }
        Assert.assertNotNull(blog);
    }

}
