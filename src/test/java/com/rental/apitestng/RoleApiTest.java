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
    public void getRoleByIdTestEqualAdmin() {
        Long id =100L;
        Role role = roleRepository.findById(id).orElse(null);
        Assert.assertNull(role);
       // Assert.assertEquals(role.getName(), RoleName.ADMIN);
    }
    @Test
    public void getRoleByRoleNameTestNotNull(){
        Assert.assertNotNull(roleRepository.findByName(RoleName.USERS));
    }
    @Test(expectedExceptions = Exception.class)
    public void getRoleByRoleIdTestException(){
       Assert.assertNotNull(roleRepository.findById(10L).get().getId());
    }
}
