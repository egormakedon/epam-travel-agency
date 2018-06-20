package com.epam.makedon.agency.agencyweb.config;

import com.epam.makedon.agency.agencydomain.config.MainHibernateConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MainHibernateConfiguration.class)
public class RootConfiguration {
}