import com.epam.makedon.agency.config.HibernateConfiguration;
import com.epam.makedon.agency.domain.impl.User;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class Test {

    @PersistenceContext
    private EntityManager entityManager;

    @org.junit.Test
    public void test1() {
        User h = new User();
        h.setId(1);
        h = entityManager.find(User.class, h.getId());
        System.out.println(h.getReviewList());
//        Session s = sessionFactory.getCurrentSession();
//        s.beginTransaction();
//        User u = new User();
//        u.setId(1L);
//        u = s.get(User.class, u.getId());
//        s.getTransaction().commit();
//        System.out.println(u);
    }
}
