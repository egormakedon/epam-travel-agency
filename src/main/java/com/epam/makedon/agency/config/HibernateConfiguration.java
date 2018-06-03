package com.epam.makedon.agency.config;

import com.epam.makedon.agency.domain.impl.Hotel;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan("com.epam.makedon.agency.repository.hibernateimpl")
@ComponentScan("com.epam.makedon.agency.service")
public class HibernateConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Hotel.class)
//                .addAnnotatedClass(Review.class)
//                .addAnnotatedClass(Tour.class)
//                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }
}