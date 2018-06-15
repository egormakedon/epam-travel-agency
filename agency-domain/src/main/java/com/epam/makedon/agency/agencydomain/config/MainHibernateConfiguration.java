package com.epam.makedon.agency.agencydomain.config;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan({"com.epam.makedon.agency.agencydomain.repository.hibernateimpl", "com.epam.makedon.agency.agencydomain.service"})
@PropertySource("classpath:/property/hibernate.properties")
@Import(AbstractDatabaseConfiguration.class)
public class MainHibernateConfiguration {

    // hibernate

    @Value("${hibernate.entity.package}")
    private String packagesToScan;

    @Value("${hibernate.dialect}")
    private String dialect;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    @Value("${hibernate.show_sql}")
    private String showSQL;

    @Value("${hibernate.format_sql}")
    private String formatSQL;

    @Value("${hibernate.use_sql_comments}")
    private String useSQLComments;

    @Autowired
    private HikariDataSource hikariDataSource;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(hikariDataSource);
        emfb.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        emfb.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emfb.setJpaProperties(jpaProperties());
        emfb.setPackagesToScan(packagesToScan);
        return emfb;
    }

    @Bean
    public JpaTransactionManager jpaTransactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean().getObject());
        return jpaTransactionManager;
    }

    private Properties jpaProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", dialect);
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", hbm2ddlAuto);
        hibernateProperties.setProperty("hibernate.show_sql", showSQL);
        hibernateProperties.setProperty("hibernate.format_sql", formatSQL);
        hibernateProperties.setProperty("hibernate.use_sql_comments", useSQLComments);
        return hibernateProperties;
    }
}