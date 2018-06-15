package com.epam.makedon.agency.agencydomain.repository.hibernateimpl;

import com.epam.makedon.agency.agencydomain.config.TestHibernateConfiguration;
import com.epam.makedon.agency.agencydomain.domain.impl.User;
import com.epam.makedon.agency.agencydomain.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("hibernateRepository")
@ContextConfiguration(classes = TestHibernateConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class UserHibernateRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void addTrueTest1() {
        User user = new User();
        user.setLogin("hello1");
        user.setPassword("hello1");
        assertTrue(repository.add(user));
    }

    @Test
    public void addTrueTest2() {
        User user = new User();
        user.setLogin("h1");
        user.setPassword("h23");
        assertTrue(repository.add(user));
    }

    @Test
    public void getTest() {
        Optional<User> opt = repository.get(1);
        User user = opt.orElseThrow(() -> new RuntimeException(""));
        assertEquals(user.getLogin(), "user1");
        assertEquals(user.getPassword(), "user1");
        assertEquals(user.getTourList().get(0).getId(), 1);
        assertEquals(user.getReviewList().get(0).getId(), 1);

        opt = repository.get(2);
        user = opt.orElseThrow(() -> new RuntimeException(""));
        assertEquals(user.getLogin(), "user2");
        assertEquals(user.getPassword(), "user2");
        assertEquals(user.getTourList().get(0).getId(), 1);
        assertEquals(user.getReviewList().get(0).getId(), 2);
    }

    @Test
    public void removeTest() {
        User user = new User();
        user.setId(1);
        assertTrue(repository.remove(user));
    }

    @Test
    public void removeFalseTest() {
        User user = new User();
        user.setId(100);
        assertFalse(repository.remove(user));
    }
}
