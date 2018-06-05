//import com.epam.makedon.agency.config.TestConfiguration;
//import com.epam.makedon.agency.domain.impl.Hotel;
//import com.epam.makedon.agency.repository.HotelRepository;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ActiveProfiles("hibernateRepository")
//@ContextConfiguration(classes = TestConfiguration.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//public class Test {
//
//    @Autowired
//    private HotelRepository repository;
//
//    @org.junit.Test
//    public void test1() {
//        Hotel h = repository.get(1).get();
//        System.out.println(h);
//    }
//}
