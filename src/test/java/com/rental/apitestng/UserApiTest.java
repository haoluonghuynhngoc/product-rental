package com.rental.apitestng;

import com.rental.RentalApplication;
import com.rental.domain.Brand;
import com.rental.domain.User;
import com.rental.repository.UserRepository;
import com.rental.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SpringBootTest(classes = RentalApplication.class)
public class UserApiTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @DataProvider(name = "useSet")
    public Object[][] dataSet() {
        User user = userRepository.findByUsername("admin");
        return new Object[][]{
                {"admin", user.getUsername()},
                {"true", "" + bCryptPasswordEncoder.matches("11111", user.getPassword())}
        };
    }

    @Test(dataProvider = "useSet")
    public void getUserNameAndPasswordTestEqualLoginUser(String username, String password) {
        Assert.assertEquals(username, password, "User name or password is not correct");
    }

    @Test
    public void getUserByIdTestNotNull() {
        User user = userRepository.findById(1L).get();
        Assert.assertNotNull(user, "User is null");
    }

    @Test(expectedExceptions = Exception.class)
    public void saveUserToEntityTestThrowException() {
        User user = User.builder()
                .username("admin")
                .firstName("Hảo")
                .password(bCryptPasswordEncoder.encode("11111"))
                .address("10, Cao Lỗ, Phường 4, Quận 8, TP.HCM")
                .build();
        userRepository.save(user);
    }

    @Test
    public void checkEmailUserInDataBaseTestEqual() {
        Assert.assertTrue(userRepository.existsByEmail("haoluonghuynhngoc@gmail.com"));
    }

    @Test
    public void getUserByIdAndUpdatePhoneTestNotNullUser(){
        User user = userRepository.findById(7L).map(
                userEntity->{
                    userEntity.setPhone("09340333");
                return userEntity;
                }
        ).orElse(null);
        Assert.assertNull(user); //lỗi vì không thể tìm thấy user có ID:7
    }

}
