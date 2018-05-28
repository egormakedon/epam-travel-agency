package com.epam.makedon.agency.repository.databaseimpl;

import com.epam.makedon.agency.config.TestConfiguration;
import com.epam.makedon.agency.domain.impl.Country;
import com.epam.makedon.agency.repository.CountryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertTrue;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("databaseRepository")
@ContextConfiguration(classes = TestConfiguration.class)
public class CountryDatabaseRepositoryTest {
//    private static AnnotationConfigApplicationContext context;

    @Autowired
//    @Qualifier(value = "countryDatabaseRepository")
    private CountryRepository countryDatabaseRepository;

//    @Before
//    public void init() {
//        context = new AnnotationConfigApplicationContext();
//        context.getEnvironment().setActiveProfiles("databaseRepository");
//        context.register(TestConfiguration.class);
//        context.refresh();
//
//        repository = context.getBean("countryDatabaseRepository", CountryRepository.class);
//    }

//    @After
//    public void destroy() {
////        ((EmbeddedDatabase)context.getBean("dataSource")).shutdown();
//        context = null;
//        repository = null;
//    }

    @Test
    public void addTrueTest() {
        Country c = Country.CHINA;
        assertTrue(countryDatabaseRepository.add(c));
    }

//    @Test(expected = DuplicateKeyException.class)
//    public void addExcTest() {
//        Country s = Country.SPAIN;
//        repository.add(s);
//        fail();
//    }
//
//    @Test
//    public void getTrueTest() {
//        Country s = Country.SPAIN;
//        Optional<Country> opt = repository.get(4);
//        assertEquals(s, opt.get());
//    }
//
//    @Test
//    public void getFalseTest() {
//        Country s = Country.SPAIN;
//        Optional<Country> opt = repository.get(5);
//        assertNotEquals(s, opt.get());
//    }
//
//    @Test(expected = EmptyResultDataAccessException.class)
//    public void getExcTest() {
//        Optional<Country> opt = repository.get(100);
//        fail();
//    }
//
//    @Test
//    public void removeTrueTest() {
//        Country s = Country.SPAIN;
//        assertTrue(repository.remove(s));
//    }
//
//    @Test
//    public void removeExcTest() {
//        Country c = Country.CHINA;
//        assertFalse(repository.remove(c));
//    }
}
