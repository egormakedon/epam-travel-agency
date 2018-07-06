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

/**
 * Main hibernate spring java configuration,
 * import {@link AbstractDatabaseConfiguration} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan({"com.epam.makedon.agency.agencydomain.repository.hibernateimpl",
        "com.epam.makedon.agency.agencydomain.service"})
@PropertySource("classpath:/property/hibernate.properties")
@Import(AbstractDatabaseConfiguration.class)

public class MainHibernateConfiguration {

    private static final String DIALECT = "hibernate.dialect";
    private static final String HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String SHOW_SQL = "hibernate.show_sql";
    private static final String FORMAT_SQL = "hibernate.format_sql";
    private static final String USE_SQL_COMMENTS = "hibernate.use_sql_comments";
    private static final String ENABLE_LAZY_LOAD_NO_TRANS = "hibernate.enable_lazy_load_no_trans";
    private static final String CURRENT_SESSION_CONTEXT_CLASS = "hibernate.current_session_context_class";

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

    @Value("${hibernate.enable_lazy_load_no_trans}")
    private String enableLazyLoadNoTrans;

    @Value("${hibernate.current_session_context_class}")
    private String currentSessionContextClass;

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
        hibernateProperties.setProperty(DIALECT, dialect);
        hibernateProperties.setProperty(HBM2DDL_AUTO, hbm2ddlAuto);
        hibernateProperties.setProperty(SHOW_SQL, showSQL);
        hibernateProperties.setProperty(FORMAT_SQL, formatSQL);
        hibernateProperties.setProperty(USE_SQL_COMMENTS, useSQLComments);
        hibernateProperties.setProperty(ENABLE_LAZY_LOAD_NO_TRANS, enableLazyLoadNoTrans);
        hibernateProperties.setProperty(CURRENT_SESSION_CONTEXT_CLASS, currentSessionContextClass);
        return hibernateProperties;
    }
}