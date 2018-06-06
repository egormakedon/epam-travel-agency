package com.epam.makedon.agency.repository.hibernateimpl;

import com.epam.makedon.agency.config.TestConfiguration;
import com.epam.makedon.agency.domain.impl.Tour;
import com.epam.makedon.agency.domain.impl.User;
import com.epam.makedon.agency.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("hibernateRepository")
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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

    @Test
    public void updateTest1() {
        User user = new User();
        user.setId(6);
        user.setLogin("ss");
        user.setPassword("ss");
        repository.add(user);

        user.setLogin("aa");
        user.setPassword("aa");
        Tour tour = new Tour();
        tour.setId(1);
        user.getTourList().add(tour);
        repository.update(user);

        assertEquals(repository.get(6).orElse(null), user);
    }

    @Test
    public void updateTest2() {
        User user = repository.get(5).orElseThrow(() -> new RuntimeException(""));

        user.setLogin("ss");
        user.setPassword("ss");
        Tour tour = new Tour();
        tour.setId(1);
        user.getTourList().add(tour);
        repository.update(user);

        user.setTourList(user.getTourList()
                        .stream()
                        .sorted((u1,u2) -> (int)(u1.getId() - u2.getId()))
                        .collect(Collectors.toList()));

        assertEquals(repository.get(5).orElse(null), user);
    }
}
