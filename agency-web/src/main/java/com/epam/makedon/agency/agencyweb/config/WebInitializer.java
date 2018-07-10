package com.epam.makedon.agency.agencyweb.config;

import com.epam.makedon.agency.agencydomain.config.MainHibernateConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Web initializer configuration,
 * extends {@link AbstractAnnotationConfigDispatcherServletInitializer} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Configuration

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";
    private static final String ACTIVE_PROFILES = "hibernateRepository, service";

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{MainHibernateConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ServletConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.setInitParameter(SPRING_PROFILES_ACTIVE, ACTIVE_PROFILES);
    }
}