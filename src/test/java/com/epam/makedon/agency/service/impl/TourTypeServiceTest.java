package com.epam.makedon.agency.service.impl;

import com.epam.makedon.agency.config.TestConfiguration;
import com.epam.makedon.agency.domain.impl.TourType;
import com.epam.makedon.agency.service.ServiceException;
import com.epam.makedon.agency.service.TourTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"databaseRepository", "service"})
@ContextConfiguration(classes = TestConfiguration.class)
public class TourTypeServiceTest {
    @Autowired
    private TourTypeService service;

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

    @Test(expected = ServiceException.class)
    public void exceptionGetTest1() {
        service.get(0);
    }

    @Test(expected = ServiceException.class)
    public void exceptionGetTest2() {
        service.get(-3);
    }

    @Test
    @Transactional
    public void addTrueTest() {
        assertTrue(service.add(TourType.EXCURSION));
    }

    @Test
    public void getTrueTest() {
        Optional<TourType> opt = service.get(1);
        assertNotNull(opt.orElse(null));
    }

    @Test
    @Transactional
    public void removeTrueTest() {
        assertTrue(service.remove(TourType.CHILDREN));
    }
}
