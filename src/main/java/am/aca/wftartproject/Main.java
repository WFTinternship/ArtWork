package am.aca.wftartproject;

import am.aca.wftartproject.model.ArtistSpecialization;

import java.sql.SQLException;

/**
 * Created by ASUS on 24-May-17
 */
public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring-root.xml");
//        ItemService itemService = context.getBean("itemService",ItemServiceImpl.class);
//        System.out.println(itemService);


        ArtistSpecialization[] art = ArtistSpecialization.values();
        for(ArtistSpecialization artElement:art){
            System.out.println(artElement.getType());
        }

    }
}
