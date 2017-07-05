package am.aca.wftartproject.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ASUS on 19-Jun-17
 */
public class SpringBean {

    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("WEB-INF/spring-config/spring-root.xml");

    public static <T> T getBeanFromSpring(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

}
