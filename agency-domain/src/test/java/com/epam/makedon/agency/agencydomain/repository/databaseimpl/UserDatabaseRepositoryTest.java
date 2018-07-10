package com.epam.makedon.agency.agencydomain.repository.databaseimpl;

import com.epam.makedon.agency.agencydomain.config.TestDatabaseConfiguration;
import com.epam.makedon.agency.agencydomain.domain.impl.Role;
import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.domain.impl.User;
import com.epam.makedon.agency.agencydomain.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Test for {@link UserDatabaseRepository} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@ActiveProfiles("databaseRepository")
@ContextConfiguration(classes = TestDatabaseConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class UserDatabaseRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void addTrueTest1() {
        User user = new User();
        user.setLogin("hello1");
        user.setPassword("hello1");
        assertTrue(userRepository.add(user));
    }

    @Test
    public void addTrueTest2() {
        User user = new User();
        user.setLogin("h1");
        user.setPassword("h23");
        assertTrue(userRepository.add(user));
    }

    @Test
    public void getTest() {
        Optional<User> opt = userRepository.get(1);
        User user = opt.orElseThrow(() -> new RuntimeException(""));
        assertEquals(user.getLogin(), "user1");
        assertEquals(user.getPassword(), "user1");
        assertEquals(user.getTourList().get(0).getId(), 1);
        assertEquals(user.getReviewList().get(0).getId(), 1);

        opt = userRepository.get(2);
        user = opt.orElseThrow(() -> new RuntimeException(""));
        assertEquals(user.getLogin(), "user2");
        assertEquals(user.getPassword(), "user2");
        assertEquals(user.getTourList().get(0).getId(), 2);
        assertEquals(user.getReviewList().get(0).getId(), 3);
    }

    @Test
    public void removeTest() {
        User user = new User();
        user.setId(1);
        assertTrue(userRepository.remove(user));
    }

    @Test
    public void removeFalseTest() {
        User user = new User();
        user.setId(100);
        assertFalse(userRepository.remove(user));
    }

    @Test
    public void updateTest1() {
        User user = new User();
        user.setId(6);
        user.setLogin("ss");
        user.setPassword("ss");
        userRepository.add(user);

        user.setLogin("aa");
        user.setPassword("aa");
        Tour tour = new Tour();
        tour.setId(1);
        user.getTourList().add(tour);
        userRepository.update(user);

        assertEquals(userRepository.get(6).orElse(null), user);
    }

    @Test
    public void updateTest2() {
        User user = userRepository.get(5).orElseThrow(() -> new RuntimeException(""));

        user.setLogin("ss");
        user.setPassword("ss");
        Tour tour = new Tour();
        tour.setId(1);
        user.getTourList().add(tour);
        userRepository.update(user);

        user.setTourList(user.getTourList()
                        .stream()
                        .sorted((u1,u2) -> (int)(u1.getId() - u2.getId()))
                        .collect(Collectors.toList()));

        assertEquals(userRepository.get(5).orElse(null), user);
    }

    @Test
    public void findByUsernameTest1() {
        User user = userRepository.findByUsername("user1").orElse(null);
        assertNotNull(user);
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    public void findByUsernameTest2() {
        User user = userRepository.findByUsername("user2").orElse(null);
        assertNotNull(user);
        assertEquals(Role.USER, user.getRole());
    }
}