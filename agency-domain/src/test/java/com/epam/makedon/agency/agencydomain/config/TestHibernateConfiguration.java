package com.epam.makedon.agency.agencydomain.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Spring java configuration for testing hibernate profile
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan({"com.epam.makedon.agency.agencydomain.repository.hibernateimpl",
        "com.epam.makedon.agency.agencydomain.service"})

public class TestHibernateConfiguration {

    private static final String DIALECT = "hibernate.dialect";
    private static final String SHOW_SQL = "hibernate.show_sql";
    private static final String FORMAT_SQL = "hibernate.format_sql";
    private static final String USE_SQL_COMMENTS = "hibernate.use_sql_comments";
    private static final String PACKAGE_TO_SCAN_PATH = "com.epam.makedon.agency.agencydomain.domain";

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2)
                .addScript("databaseScripts/schema.sql")
                .addScript("databaseScripts/init-data.sql")
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource());
        emfb.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        emfb.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty(DIALECT, "org.hibernate.dialect.H2Dialect");
        jpaProperties.setProperty(SHOW_SQL, "true");
        jpaProperties.setProperty(FORMAT_SQL, "true");
        jpaProperties.setProperty(USE_SQL_COMMENTS, "true");
        emfb.setPackagesToScan(PACKAGE_TO_SCAN_PATH);
        emfb.setJpaProperties(jpaProperties);
        return emfb;
    }

    @Bean
    public JpaTransactionManager jpaTransactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean().getObject());
        return jpaTransactionManager;
    }
}