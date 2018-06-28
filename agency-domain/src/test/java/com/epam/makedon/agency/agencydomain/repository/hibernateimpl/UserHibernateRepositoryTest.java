//package com.epam.makedon.agency.agencydomain.repository.hibernateimpl;
//
//import com.epam.makedon.agency.agencydomain.config.TestHibernateConfiguration;
//import com.epam.makedon.agency.agencydomain.repository.UserRepository;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//@RunWith(SpringRunner.class)
//@ActiveProfiles("hibernateRepository")
//@ContextConfiguration(classes = TestHibernateConfiguration.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@Transactional
//public class UserHibernateRepositoryTest {
//
//    @Autowired
//    private UserRepository repository;
//
//    @HotelMongodbRepositoryTest
//    public void addTrueTest1() {
//        User user = new User();
//        user.setLogin("hello1");
//        user.setPassword("hello1");
//        assertTrue(repository.add(user));
//    }
//
//    @HotelMongodbRepositoryTest
//    public void addTrueTest2() {
//        User user = new User();
//        user.setLogin("h1");
//        user.setPassword("h23");
//        assertTrue(repository.add(user));
//    }
//
//    @HotelMongodbRepositoryTest
//    public void getTest() {
//        Optional<User> opt = repository.get(1);
//        User user = opt.orElseThrow(() -> new RuntimeException(""));
//        assertEquals(user.getLogin(), "user1");
//        assertEquals(user.getPassword(), "user1");
//        assertEquals(user.getTourList().get(0).getId(), 1);
//        assertEquals(user.getReviewList().get(0).getId(), 1);
//
//        opt = repository.get(2);
//        user = opt.orElseThrow(() -> new RuntimeException(""));
//        assertEquals(user.getLogin(), "user2");
//        assertEquals(user.getPassword(), "user2");
//        assertEquals(user.getTourList().get(0).getId(), 1);
//        assertEquals(user.getReviewList().get(0).getId(), 2);
//    }
//
//    @HotelMongodbRepositoryTest
//    public void removeTest() {
//        User user = new User();
//        user.setId(1);
//        assertTrue(repository.remove(user));
//    }
//
//    @HotelMongodbRepositoryTest
//    public void removeFalseTest() {
//        User user = new User();
//        user.setId(100);
//        assertFalse(repository.remove(user));
//    }
//}
