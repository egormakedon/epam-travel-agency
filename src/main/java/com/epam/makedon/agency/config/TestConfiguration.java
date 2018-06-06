package com.epam.makedon.agency.config;

import com.epam.makedon.agency.aspect.RepositoryLogger;
import com.epam.makedon.agency.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan("com.epam.makedon.agency.repository")
@ComponentScan("com.epam.makedon.agency.service")
@PropertySource(value = {"classpath:/property/hibernate.properties", "classpath:/property/database.properties"})
public class TestConfiguration {

    // Hibernate properties

    @Value("${hibernate.entity.package}")
    private String packagesToScan;

    @Value("${hibernate.show_sql}")
    private String showSQL;

    @Value("${hibernate.format_sql}")
    private String formatSQL;

    @Value("${hibernate.use_sql_comments}")
    private String useSQLComments;

    @Value("${hibernate.connection.autocommit}")
    private String connectionAutocommit;

    @Value("${hibernate.current_session_context_class}")
    private String currentSessionContextClass;

    @Value("${hibernate.enable_lazy_load_no_trans}")
    private String enableLazyLoadNoTrans;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2)
                .addScript("databaseScripts/schema.sql")
                .addScript("databaseScripts/init-data.sql")
                .build();
    }

    @Bean(name = "txManager")
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    // Hibernate

    @Bean(name = "localContainerEntityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.H2);
        hibernateJpaVendorAdapter.setShowSql(Boolean.valueOf(showSQL));

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(packagesToScan);
        entityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        entityManagerFactoryBean.setJpaProperties(hibernateProperties());
        return entityManagerFactoryBean;
    }

    @Bean(name = "platformTransactionManager")
    @Autowired
    public PlatformTransactionManager platformTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }

    @Bean(name = "persistenceExceptionTranslationPostProcessor")
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    // Aspect

    @Bean(name = "repositoryLogger")
    public RepositoryLogger repositoryLogger() {
        return new RepositoryLogger();
    }

    @Bean(name = "logger")
    public Logger logger() {
        return LoggerFactory.getLogger(Repository.class);
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.show_sql", showSQL);
        hibernateProperties.setProperty("hibernate.format_sql", formatSQL);
        hibernateProperties.setProperty("hibernate.use_sql_comments", useSQLComments);
        hibernateProperties.setProperty("hibernate.connection.autocommit", connectionAutocommit);
        hibernateProperties.setProperty("hibernate.current_session_context_class", currentSessionContextClass);
        hibernateProperties.setProperty("hibernate.enable_lazy_load_no_trans", enableLazyLoadNoTrans);
        return hibernateProperties;
    }
}