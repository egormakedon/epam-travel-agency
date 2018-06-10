package com.epam.makedon.agency.repository.hibernateimpl;

import com.epam.makedon.agency.config.TestDatabaseConfiguration;
import com.epam.makedon.agency.domain.impl.Hotel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestDatabaseConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CriteriaQueryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void test() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Hotel> criteriaQuery = builder.createQuery(Hotel.class);
        Root<Hotel> hotelRoot = criteriaQuery.from(Hotel.class);
        criteriaQuery.select(hotelRoot);
        criteriaQuery.where(builder.equal(hotelRoot.get("id"), 1));
        Hotel h = entityManager.createQuery(criteriaQuery).getSingleResult();

        assertEquals(1, h.getId());
        assertEquals("1234567", h.getPhone());
        assertEquals(5, h.getStars());
     }
}
