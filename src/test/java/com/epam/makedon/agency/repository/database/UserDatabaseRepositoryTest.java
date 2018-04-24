package com.epam.makedon.agency.repository.database;

import com.epam.makedon.agency.config.TestConfiguration;
import com.epam.makedon.agency.entity.impl.User;
import com.epam.makedon.agency.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import java.util.Optional;

import static org.junit.Assert.*;

public class UserDatabaseRepositoryTest {
    private static ApplicationContext context;
    private static UserRepository repository;

    @Before
    public void init() {
        context = new AnnotationConfigApplicationContext(TestConfiguration.class);
        repository = context.getBean("userDatabaseRepository", UserRepository.class);
    }

    @After
    public void destroy() {
        ((EmbeddedDatabase)context.getBean("dataSource")).shutdown();
        context = null;
        repository = null;
    }

    @Test
    public void addTrueTest() {
        User user = new User();
        user.setLogin("hello1");
        user.setPassword("hello1");
        assertTrue(repository.add(user));
    }

    @Test
    public void getTest() {
        Optional<User> opt = repository.get(1);
        User user = opt.get();
        assertEquals(user.getLogin(), "user1");
        assertEquals(user.getPassword(), "user1");
        assertEquals(user.getTourList().get(0).getId(), 1);
        assertEquals(user.getReviewList().get(0).getId(), 1);

        opt = repository.get(2);
        user = opt.get();
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
