package am.aca.wftartproject.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ASUS on 19-Jun-17
 */
public class SpringBean {

    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-root.xml");

    public static <T> T getBean(String name, Class<T> requiredType) {
        T artistService = applicationContext.getBean(name, requiredType);
        return artistService;
    }

}
