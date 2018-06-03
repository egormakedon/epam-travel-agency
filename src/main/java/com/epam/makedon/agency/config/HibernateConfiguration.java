package com.epam.makedon.agency.config;

import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan("com.epam.makedon.agency.repository.hibernateimpl")
@ComponentScan("com.epam.makedon.agency.service")
public class HibernateConfiguration {

//    @Bean
//    public org.hibernate.cfg.Configuration configuration() {
//        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
//
//    }
}