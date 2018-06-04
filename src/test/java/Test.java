import com.epam.makedon.agency.config.HibernateConfiguration;
import com.epam.makedon.agency.domain.impl.Hotel;
import com.epam.makedon.agency.domain.impl.Tour;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class Test {

    @Autowired
    private SessionFactory sessionFactory;

    @org.junit.Test
    public void test1() {
        Session s = sessionFactory.getCurrentSession();
        s.beginTransaction();
        Hotel aa = s.get(Hotel.class, 1L);
        Tour h = s.get(Tour.class, 1L);
        h = s.get(Tour.class, h.getId());
        s.getTransaction().commit();
        System.out.println(h);
    }
}
