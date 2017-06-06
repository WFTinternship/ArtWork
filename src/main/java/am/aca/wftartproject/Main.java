package am.aca.wftartproject;

import am.aca.wftartproject.model.Item;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

/**
 * Created by ASUS on 24-May-17
 */
public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("app-config.xml");
        Item item = (Item)context.getBean("item");
        System.out.println(item);

    }
}
