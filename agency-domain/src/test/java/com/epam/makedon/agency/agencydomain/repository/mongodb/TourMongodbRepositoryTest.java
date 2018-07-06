package com.epam.makedon.agency.agencydomain.repository.mongodb;

import com.epam.makedon.agency.agencydomain.config.MongodbConfiguration;
import com.epam.makedon.agency.agencydomain.domain.impl.Country;
import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.domain.impl.TourType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test for {@link TourMongodbRepository} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MongodbConfiguration.class)

public class TourMongodbRepositoryTest {

    @Autowired
    private TourMongodbRepository tourMongodbRepository;

    @Before
    public void before() {
        tourMongodbRepository.deleteAll();
        inserting();
    }

    @After
    public void tourMongodbRepository() {
        tourMongodbRepository.deleteAll();
    }

    @Test
    public void get() {
        Tour tour1 = tourMongodbRepository.findById(0L).get();
        Tour tour2 = tourMongodbRepository.findById(1L).get();

        assertEquals(Country.BELARUS, tour1.getCountry());
        assertEquals(LocalDate.of(1998, 6, 25), tour1.getDate());

        assertEquals("tour", tour2.getDescription());
        assertEquals(TourType.CHILDREN, tour2.getType());
    }

    @Test
    public void count() {
        assertEquals(2, tourMongodbRepository.count());
    }

    @Test
    public void remove1() {
        assertEquals(Country.BELARUS, tourMongodbRepository.findById(0L).get().getCountry());
        tourMongodbRepository.deleteById(0L);
        assertNull(tourMongodbRepository.findById(0L).orElse(null));

        assertEquals(1, tourMongodbRepository.count());

        assertEquals(TourType.CHILDREN, tourMongodbRepository.findById(1L).get().getType());
        tourMongodbRepository.deleteById(1L);
        assertNull(tourMongodbRepository.findById(1L).orElse(null));

        assertEquals(0, tourMongodbRepository.count());
    }

    private void inserting() {
        Tour tour1 = new Tour();
        tour1.setId(0L);
        tour1.setCountry(Country.BELARUS);
        tour1.setDate(LocalDate.of(1998, 6, 25));

        Tour tour2 = new Tour();
        tour2.setId(1L);
        tour2.setDescription("tour");
        tour2.setType(TourType.CHILDREN);

        tourMongodbRepository.insert(Arrays.asList(tour1, tour2));
    }
}