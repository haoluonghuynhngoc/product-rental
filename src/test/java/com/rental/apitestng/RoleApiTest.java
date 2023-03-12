package com.rental.apitestng;

import com.rental.RentalApplication;
import com.rental.domain.Role;
import com.rental.domain.User;
import com.rental.domain.enums.RoleName;
import com.rental.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SpringBootTest(classes = RentalApplication.class)
public class RoleApiTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void getRoleByIdTestNullRole() {
        Role role = roleRepository.findById(100L).orElse(null);
        Assert.assertNull(role);
    }

    @Test
    public void getRoleByRoleNameTestNotNull() {
        Role actual = null;
        try {
            actual = roleRepository.findByName(RoleName.USERS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(actual);
        if (actual!=null){
            System.out.println("Giá trị khi lấy ra trong dữ liệu ");
            System.out.println(actual.toString());
        }
    }



}
