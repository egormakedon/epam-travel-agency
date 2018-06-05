package com.epam.makedon.agency.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan("com.epam.makedon.agency.repository.hibernateimpl")
@ComponentScan("com.epam.makedon.agency.service")
@PropertySource(value = "classpath:/property/hibernate.properties")
public class HibernateConfiguration {

}