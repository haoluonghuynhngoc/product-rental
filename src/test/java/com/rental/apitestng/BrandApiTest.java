package com.rental.apitestng;
import com.rental.RentalApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@SpringBootTest(classes = RentalApplication.class)
public class BrandApiTest extends AbstractTestNGSpringContextTests {
//    @Autowired
//    private BrandService brandService;
//    @Autowired
//    private BrandRepository brandRepository;

//    @Test // trùng với dữ liệu người nhập (expectedExceptions = Exception.class)
//    public void brandGetIdTestEqual() {
//        long expected = 1;
//        long actual = (long) brandService.findOne(1L).get().getId();
//        Assert.assertEquals(actual, expected, "Can not find the brand");
//    }
//
//    @Test
//    public void brandGetIdTestExist() {
//        boolean actual = brandRepository.existsById(2L);
//        Assert.assertTrue(actual, "Brand does not exist");
//    }
//
//    @Test
//    public void brandGetIdTestNotNull() {
//        Brand actual = brandRepository.findById(2L).get();
//        Assert.assertNotNull(actual);
//    }
//
//    @Test
//    public void brandGetObjectBrandTestSaveBrandEntity() {
//        Brand brand = Brand.builder()
//                .name("Code")
//                .products(null)
//                .build();
//
//        Assert.assertNotNull(brandRepository.save(brand));
//    }
//    @Test
//    public void brandGetBrandIdTestUpdateBrandEntity() {
//        Brand brand = brandRepository.findById(5L).map(
//                brandEntity->{
//                    brandEntity.setName("TestNG Spring Boot");
//                return brandEntity;
//                }
//        ).get();
//        Assert.assertNotNull(brandRepository.save(brand));
//    }


}
