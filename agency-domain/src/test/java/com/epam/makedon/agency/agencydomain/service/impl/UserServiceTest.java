package com.epam.makedon.agency.agencydomain.service.impl;

import com.epam.makedon.agency.agencydomain.config.TestDatabaseConfiguration;
import com.epam.makedon.agency.agencydomain.service.ServiceException;
import com.epam.makedon.agency.agencydomain.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test for {@link UserServiceImpl} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@ActiveProfiles({"databaseRepository",
        "service"})
@ContextConfiguration(classes = TestDatabaseConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test(expected = ServiceException.class)
    public void exceptionAddTest() {
        userService.add(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionRemoveTest() {
        userService.remove(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionUpdateTest() {
        userService.update(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionGetTest() {
        userService.get(-50);
    }
}