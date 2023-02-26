package com.rental.apitestng;

import com.rental.RentalApplication;
import com.rental.domain.Notification;
import com.rental.domain.User;
import com.rental.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SpringBootTest(classes = RentalApplication.class)
public class NotificationApiTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private NotificationRepository notificationRepository;

    @DataProvider(name = "notificationSet")
    public Object[][] dataSet() {
        Notification notification = notificationRepository.findById(1L).get();
        return new Object[][]{
                {"Spring Boot", notification.getTitle()},
                {"TestNg", notification.getSortDescription()}
        };
    }

    @Test(dataProvider = "notificationSet")
    public void getNotificationByIdTestEqualTitleAndSortDescription(String test1, String test2) {
        Assert.assertEquals(test1, test2, "Test1 And test2 is not Equal");
    }

    @Test
    public void getNotificationByIdTestNotNull() {
        Assert.assertNotNull(notificationRepository.findById(1L).get());
    }
    @Test
    public void getAllNotificationTestSizeNotificationMoreThanEight(){
        Assert.assertFalse(notificationRepository.findAll().size()>8);
    }
}
