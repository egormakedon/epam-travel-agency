import com.epam.makedon.agency.config.HibernateConfiguration;
import com.epam.makedon.agency.domain.impl.User;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class Test {

    @PersistenceContext
    private EntityManager entityManager;

    @org.junit.Test
    public void test1() {
        Query query = entityManager.createNativeQuery("SELECT * FROM \"user\" WHERE user_id=?", User.class);
        query.setParameter(1, 1);
        User  h = (User)query.getSingleResult();
        System.out.println(h.getReviewList());
    }
}
