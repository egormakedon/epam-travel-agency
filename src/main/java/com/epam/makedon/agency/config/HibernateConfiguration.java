package com.epam.makedon.agency.config;

import com.epam.makedon.agency.domain.impl.Hotel;
import com.epam.makedon.agency.domain.impl.Review;
import com.epam.makedon.agency.domain.impl.Tour;
import com.epam.makedon.agency.domain.impl.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan("com.epam.makedon.agency.repository.hibernateimpl")
@ComponentScan("com.epam.makedon.agency.service")
@PropertySource(value = "classpath:/property/persistence.properties")
public class HibernateConfiguration {

    @Value("persistence.unit.name")
    private String hibernatePersistenceUnitName;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean(name = "sessionFactory", destroyMethod = "close")
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Hotel.class)
                .addAnnotatedClass(Review.class)
                .addAnnotatedClass(Tour.class)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    @Bean(name = "entityManagerFactory", destroyMethod = "close")
    public EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory(hibernatePersistenceUnitName);
    }

    @Scope("prototype")
    @Bean(name = "session")
    public Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Scope("prototype")
    @Bean(name = "entityManager")
    public EntityManager entityManager() {
        return entityManagerFactory.createEntityManager();
    }
}