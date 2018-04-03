package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.repository.collection.UserCollectionRepository;
import com.epam.makedon.agency.service.Service;
import com.epam.makedon.agency.service.ServiceException;
import com.epam.makedon.agency.service.UserServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserServiceTest {
    private static Service service;

    @Before
    public void init() {
        service = new UserServiceImpl(UserCollectionRepository.getInstance());
    }

    @After
    public void destroy() {
        service = null;
    }

    @Test(expected = ServiceException.class)
    public void exceptionAddTest() {
        service.add(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionRemoveTest() {
        service.remove(null);
    }

    @Test(expected = ServiceException.class)
    public void exceptionUpdateTest() {
        service.update(null);
    }
}
