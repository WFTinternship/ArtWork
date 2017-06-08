package am.aca.wftartproject;

import am.aca.wftartproject.model.ArtistSpecialization;

import java.sql.SQLException;

/**
 * Created by ASUS on 24-May-17
 */
public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        AbstractApplicationContext context = new ClassPathXmlApplicationContext("app-config.xml");
//        Item item = (Item)context.getBean("item");
//        System.out.println(item);

        ArtistSpecialization[] art = ArtistSpecialization.values();
        System.out.println(art.length);
        for(ArtistSpecialization artElement:art){
            System.out.println(artElement.getId()+"   "+artElement.getType());
        }
        System.out.println(ArtistSpecialization.values().toString());

    }
}
