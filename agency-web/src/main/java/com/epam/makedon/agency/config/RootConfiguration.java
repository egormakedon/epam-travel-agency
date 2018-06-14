package com.epam.makedon.agency.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MainHibernateConfiguration.class)
public class RootConfiguration {
}