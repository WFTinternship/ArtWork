
package am.aca.wftartproject;

import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;

import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring-root.xml");
//        ItemService itemService = (ItemService)context.getBean("itemService");
//        for(Item element:itemService.getRecentlyAddedItems(20)){
        Artist artist = new Artist();
        System.out.println(artist.getClass() == Artist.class);

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

