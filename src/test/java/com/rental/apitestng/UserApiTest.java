package com.rental.apitestng;

import com.rental.RentalApplication;
import com.rental.domain.User;
import com.rental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;

@SpringBootTest(classes = RentalApplication.class)
public class UserApiTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @DataProvider(name = "useSet")
    public Object[][] dataSet() {
        User user = userRepository.findByUsername("userone");
        return new Object[][]{
                {"userone", user.getUsername()},
                {"true", "" + bCryptPasswordEncoder.matches("11111", user.getPassword())}
        };
    }

    @Test(dataProvider = "useSet")
    public void getUserNameAndPasswordTestEqualLoginUser(String test1, String test2) {
        Assert.assertEquals(test1, test2, "User name or password is not correct");
    }

    @Test
    public void getUserByIdTestNotNull() {
        User actual = userRepository.findById(1L).orElse(null);
        Assert.assertNotNull(actual, "User is null");
    }

    @Test(expectedExceptions = Exception.class)
    public void saveUserToEntityTestThrowException() {
        User user = User.builder()
                .username("admin") // quăn lỗi vì trùng useName
                .firstName("Hảo")
                .password(bCryptPasswordEncoder.encode("11111"))
                .address("10, Cao Lỗ, Phường 4, Quận 8, TP.HCM")
                .build();
        userRepository.save(user);

    }

    @Test
    public void checkEmailUserInDataBaseTestBoolean() {
        Assert.assertTrue(userRepository.existsByEmail("haoluong@gmail.com"));
    }

    @Test
    public void getUserByIdAndUpdatePhoneTestNotNullUser() {
        User user = new User();
        try {
            user=  userRepository.findById(2L).map(
                    userEntity -> {
                        userEntity.setId(12L);
                        return userEntity;
                    }
            ).get();
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }
        Long expeted =12L;
        Long actual =user.getId();
        Assert.assertSame(actual,expeted,"Two result is not same");
    }

}
