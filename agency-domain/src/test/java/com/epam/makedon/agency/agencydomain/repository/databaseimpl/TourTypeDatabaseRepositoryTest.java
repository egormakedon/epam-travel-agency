package com.epam.makedon.agency.agencydomain.repository.databaseimpl;

import com.epam.makedon.agency.agencydomain.config.TestDatabaseConfiguration;
import com.epam.makedon.agency.agencydomain.domain.impl.TourType;
import com.epam.makedon.agency.agencydomain.repository.TourTypeRepository;
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
@ContextConfiguration(classes = TestDatabaseConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TourTypeDatabaseRepositoryTest {

    @Autowired
    private TourTypeRepository repository;

    @Test
    public void addTrueTest() {
        TourType c = TourType.EXCURSION;
        assertTrue(repository.add(c));
    }

    @Test
    public void addExcTest1() {
        TourType c = TourType.SHOPING;
        try {
            repository.add(c);
            fail();
        } catch (DuplicateKeyException e) {
            assertTrue(true);
        }
    }

    @Test
    public void addExcTest2() {
        TourType c = TourType.CHILDREN;
        try {
            repository.add(c);
            fail();
        } catch (DuplicateKeyException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getTrueTest1() {
        assertNotNull(repository.get(1).orElse(null));
    }

    @Test
    public void getTrueTest2() {
        assertNotNull(repository.get(3).orElse(null));
    }

    @Test
    public void getFalseTest() {
        TourType c = TourType.CHILDREN;
        assertNotEquals(c, repository.get(4).orElse(null));
    }

    @Test
    public void getExcTest() {
        try {
            repository.get(100);
            fail();
        } catch (EmptyResultDataAccessException e) {
            assertTrue(true);
        }
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

    @Test
    public void updateTest1() {
        TourType c = TourType.EXCURSION;
        assertNull(repository.update(c).orElse(null));
    }

    @Test
    public void updateTest2() {
        TourType c = TourType.WEEKEND;
        assertEquals(repository.update(c).orElse(null), c);
    }
}
