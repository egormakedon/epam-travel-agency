package com.epam.makedon.agency.repository.database;

import com.epam.makedon.agency.entity.impl.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class UserDatabaseRepositoryTest {
    private static ApplicationContext context;
    private static UserDatabaseRepository repository;

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("test.xml");
        repository = context.getBean("userDatabaseRepository", UserDatabaseRepository.class);
    }

    @After
    public void destroy() {
        DataSource dataSource = context.getBean("dataSource", DataSource.class);
        try {
            dataSource.getConnection().createStatement().execute("SHUTDOWN");
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
