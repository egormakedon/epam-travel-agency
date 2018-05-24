//package com.epam.makedon.agency.service.impl;
//
//import com.epam.makedon.agency.repository.collection.CountryCollectionRepository;
//import com.epam.makedon.agency.service.Service;
//import com.epam.makedon.agency.service.ServiceException;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//public class CountryServiceTest {
//    private static Service service;
//
//    @Before
//    public void init() {
//        service = new CountryServiceImpl(CountryCollectionRepository.getInstance());
//    }
//
//    @After
//    public void destroy() {
//        service = null;
//    }
//
//    @Test(expected = ServiceException.class)
//    public void exceptionAddTest() {
//        service.add(null);
//    }
//
//    @Test(expected = ServiceException.class)
//    public void exceptionRemoveTest() {
//        service.remove(null);
//    }
//
//    @Test(expected = ServiceException.class)
//    public void exceptionUpdateTest() {
//        service.update(null);
//    }
//}