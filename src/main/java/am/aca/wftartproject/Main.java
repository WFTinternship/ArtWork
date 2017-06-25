package am.aca.wftartproject;

import am.aca.wftartproject.model.ArtistSpecialization;

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
            ArtistSpecialization artistSpecialization = ArtistSpecialization.valueOf("SCULPTOR");
          System.out.println(artistSpecialization);
        System.out.println(System.getProperty("java.io.tmpdir"));

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
