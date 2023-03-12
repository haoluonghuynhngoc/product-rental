package com.rental.apitestng;

import com.rental.RentalApplication;
import com.rental.domain.Notification;
import com.rental.domain.enums.NotificationStatus;
import com.rental.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;
@SpringBootTest(classes = RentalApplication.class)
public class NotificationApiTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private NotificationRepository notificationRepository;

    @DataProvider(name = "testEqual")
    public Object[][] dataSet() {
        Notification notification =null;
        try {
            notification = notificationRepository.findById(1L).get();
        }catch (NoSuchElementException e){
           notification = new Notification();
           notification.setTitle(" ");
           notification.setDescription("");
        }
        return new Object[][]{
                {"Spring Boo1t", notification.getTitle()},
                {"TestNG", notification.getDescription()}
        };
    }
    @Test(dataProvider = "testEqual")
    public void getNotificationByIdTestEqualTitleAndDescription(String test1, String test2) {
        Assert.assertEquals(test1, test2, "Test1 và test2 không giống nhau");
    }


    @Test
    public void getAllNotificationTestSizeNotificationMoreThanEight(){
        Assert.assertFalse(notificationRepository.findAll().size()>8);
    }
}
