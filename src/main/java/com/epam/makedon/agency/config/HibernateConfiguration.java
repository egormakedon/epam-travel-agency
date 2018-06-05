package com.epam.makedon.agency.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan("com.epam.makedon.agency.repository.hibernateimpl")
@ComponentScan("com.epam.makedon.agency.service")
@PropertySource(value = {"classpath:/property/hibernate.properties", "classpath:/property/database.properties"})
public class HibernateConfiguration {

    // Hibernate properties

    @Value("${hibernate.entity.package}")
    private String packagesToScan;

    @Value("${hibernate.show_sql}")
    private String showSQL;

    @Value("${hibernate.dialect}")
    private String dialect;

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

    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    // Database properties

    @Value("${database.password}")
    private String password;

    @Value("${database.schema}")
    private String schema;

    @Value("${database.user}")
    private String user;

    @Value("${database.driverClass}")
    private String driverClass;

    @Value("${database.url}")
    private String url;

    @Value("${database.poolName}")
    private String poolName;

    @Value("${database.poolSize}")
    private int poolSize;

    @Value("${database.idleTimeout}")
    private int idleTimeout;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(name = "localContainerEntityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        hibernateJpaVendorAdapter.setShowSql(Boolean.valueOf(showSQL));

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(hikariDataSource());
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

    @Bean(name = "hikariDataSource", destroyMethod = "close")
    public HikariDataSource hikariDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPassword(password);
        hikariConfig.setSchema(schema);
        hikariConfig.setDriverClassName(driverClass);
        hikariConfig.setUsername(user);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setPoolName(poolName);
        hikariConfig.setMaximumPoolSize(poolSize);
        hikariConfig.setIdleTimeout(idleTimeout);

        return new HikariDataSource(hikariConfig);
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.show_sql", showSQL);
        hibernateProperties.setProperty("hibernate.dialect", dialect);
        hibernateProperties.setProperty("hibernate.format_sql", formatSQL);
        hibernateProperties.setProperty("hibernate.use_sql_comments", useSQLComments);
        hibernateProperties.setProperty("hibernate.connection.autocommit", connectionAutocommit);
        hibernateProperties.setProperty("hibernate.current_session_context_class", currentSessionContextClass);
        hibernateProperties.setProperty("hibernate.enable_lazy_load_no_trans", enableLazyLoadNoTrans);
//        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", hbm2ddlAuto);
        return hibernateProperties;
    }
}