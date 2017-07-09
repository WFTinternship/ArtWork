package am.aca.wftartproject.servlet;

import am.aca.wftartproject.service.ArtistSpecializationService;
import am.aca.wftartproject.service.impl.ArtistSpecializationServiceImpl;
import am.aca.wftartproject.util.SpringBeanType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by ASUS on 21-Jun-17
 */
public class CtxListener implements ServletContextListener {

    private static ApplicationContext applicationContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        applicationContext = new ClassPathXmlApplicationContext("spring-mvc-servlet.xml");

        ArtistSpecializationService artistSpecialization =
                getBeanFromSpring(SpringBeanType.ARTISTSPECIALIZATIONSERVICE, ArtistSpecializationServiceImpl.class);
        if (artistSpecialization.getArtistSpecialization(1) == null) {
            artistSpecialization.addArtistSpecialization();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    public static <T> T getBeanFromSpring(SpringBeanType springBeanType, Class<T> requiredType) {
        return applicationContext.getBean(springBeanType.toString(), requiredType);
    }

}

