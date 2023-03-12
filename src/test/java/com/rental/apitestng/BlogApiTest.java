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


//    @Test
//    public void getBlogByIdTestEqualTitle() {
//        String actual = "";
//        String expected = "TestNG";
//        try {
//            actual = blogRepository.findById(1L).get().getTitle();
//        } catch (NoSuchElementException e) {
//            e.printStackTrace();
//        }
//        Assert.assertEquals(actual, expected);
//    }

    @Test
    public void saveBlogToDataTestEqualNotNull() {
        Blog blog = Blog.builder()
                .author("Ngọc Hảo")
                .description("Công nghệ thông tin là một ngành học được đào " +
                        "tạo để sử dụng máy tính và các phần mềm máy tính để phân phối")
                .title("Đồ công nghệ ")
                .build();
        try {
            blog=  blogRepository.save(blog);
        }catch (Exception e){
            blog=null;
        }
        Assert.assertNotNull(blog);
        if (blog!=null){
            System.out.println("Id của Blog khi được thêm trong dữ liệu là " + blog.getId());
        }
    }

}
