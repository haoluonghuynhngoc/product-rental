package com.rental.apitestng;

import com.rental.RentalApplication;
import com.rental.domain.Voucher;
import com.rental.domain.enums.VorcherStatus;
import com.rental.repository.VoucherRepository;
import com.rental.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@SpringBootTest(classes = RentalApplication.class)
public class VoucherApiTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private VoucherService voucherService;

    @Test
    public void getVoucherByIdTestEqualStatus() {
        Voucher voucher = voucherRepository.findById(1L).get();
        Assert.assertEquals(voucher.getStatus(), VorcherStatus.DISCOUNT, "Tow result is not correct ");
    }

    @Test
    public void getVoucherByIdTestDiscountMoreThanFive() {
        Double discount = 5.0;
        Assert.assertTrue(voucherRepository.findById(1L).get().getDiscount() > discount
                , "The discount in database more than 5");
    }
    @Test(expectedExceptions = Exception.class)
    public void getVoucherByIdTestVoucherNullPointerException(){
        Voucher voucher = voucherRepository.findById(20L).get();
    }
}
