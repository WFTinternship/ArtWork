package util;

import am.aca.wftartproject.model.*;

import java.util.Random;

/**
 * Created by Armen on 5/30/2017.
 */
public class TestObjectTemplate {
    public static Artist createTestArtist(){

        Artist testArtist = new Artist();
        testArtist.setArtistPhoto(new byte[10]);
        testArtist.setSpecialization(ArtistSpecialization.PAINTER);
        testArtist.setAge(26);
        testArtist.setEmail("test" + getRandomNumber() + "@test.com");
        testArtist.setFirstName("Test FirstName" + getRandomNumber());
        testArtist.setLastName("Test LastName" + getRandomNumber());
        testArtist.setPassword("test123");
       // testArtist.setShoppingCard(new ShoppingCard(getRandomNumber() + 1.1));
        return  testArtist;
    }
    public static User createTestUser() {
        User testUser = new User();
        testUser.setFirstName("Test FirstName" + getRandomNumber());
        testUser.setLastName("Test LastName");
        testUser.setAge(26);
        testUser.setEmail("test" + getRandomNumber() + "@test.com");
        testUser.setPassword("testPassword");
        return testUser;
    }
    //title, description, photo_url, price, artist_id, status, item_type
    public static Item createTestItem() {
        Item item = new Item();
        item.setTitle("test_item");
        item.setDescription("test item");
        item.setItemType(ItemType.PAINTING);
        item.setPhotoURL("test");
        item.setPrice(getRandomNumber()+1.1);
        item.setStatus(true);
        return item;
    }


    public static int getRandomNumber() {
        Random rand = new Random();
        int n = rand.nextInt(100000) + 1;
        return n;
    }
}
