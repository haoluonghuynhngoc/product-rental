package com.rental.apitestng;

import com.rental.RentalApplication;
import com.rental.domain.Blog;
import com.rental.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@SpringBootTest(classes = RentalApplication.class)
public class BlogApiTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private BlogRepository blogRepository;

    @Test
    public void getBlogByIdTestNotNull() {
        Assert.assertNotNull(blogRepository.existsById(1L));
    }

    @Test
    public void getBlogByIdTestEqual() {
        Long actual = blogRepository.findById(1L).get().getId(); // lấy dữ liệu trong bảng blog có ID là 1
        Long expected = 1L;
        Assert.assertEquals(actual, expected); // so sánh xem kết quả có phải là 1 hay không bằng các so sánh Equal
    }
        @Test
    public void saveBlogToDataTestEqualNotNull() {
            Blog blog =Blog.builder()
                    .author("Ngọc Hảo")
                    .description("Công nghệ thông tin là một ngành học được đào tạo để sử dụng máy tính và các phần mềm máy tính để phân phối và xử lý các dữ liệu thông tin, đồng thời dùng để trao đổi, lưu trữ và chuyển đổi các dữ liệu thông tin")
                    .title("Đồ công nghệ ")
                    .build();
        Assert.assertNotNull(blogRepository.save(blog)); // lưu vô database và kiểm tra xem nó có null hay không
            // nếu trả về null thì dự expected không chính xác
    }
}
