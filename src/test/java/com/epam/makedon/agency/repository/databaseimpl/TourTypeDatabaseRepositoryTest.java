package com.epam.makedon.agency.repository.databaseimpl;

import com.epam.makedon.agency.config.TestConfiguration;
import com.epam.makedon.agency.domain.impl.TourType;
import com.epam.makedon.agency.repository.TourTypeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("databaseRepository")
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TourTypeDatabaseRepositoryTest {

    @Autowired
    private TourTypeRepository repository;

    @Test
    public void addTrueTest() {
        TourType c = TourType.EXCURSION;
        assertTrue(repository.add(c));
    }

    @Test(expected = DuplicateKeyException.class)
    public void addExcTest() {
        TourType c = TourType.SHOPING;
        repository.add(c);
    }

    @Test
    public void getTrueTest() {
        assertNotNull(repository.get(1).orElse(null));
    }

    @Test
    public void getFalseTest() {
        TourType c = TourType.CHILDREN;
        assertNotEquals(c, repository.get(4).orElse(null));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getExcTest() {
        repository.get(100);
    }

    @Test
    public void removeTrueTest() {
        TourType c = TourType.CHILDREN;
        assertTrue(repository.remove(c));
    }

    @Test
    public void removeExcTest() {
        TourType c = TourType.EXCURSION;
        assertFalse(repository.remove(c));
    }
}
