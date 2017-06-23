package am.aca.wftartproject;

import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by ASUS on 24-May-17
 */
public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring-root.xml");
//        ItemService itemService = (ItemService)context.getBean("itemService");
//        for(Item element:itemService.getRecentlyAddedItems(20)){
//            System.out.println(element.toString());
//
//        }


        int num = ThreadLocalRandom.current().nextInt(1000, 100000 + 1);
        System.out.println(num);

//        ArtistSpecialization[] art = ArtistSpecialization.values();
//        for(ArtistSpecialization artElement:art){
//            System.out.println(artElement.getType());
//        }

    }
}
