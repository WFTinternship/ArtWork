package integration.service;

import am.aca.wftartproject.model.*;

import java.util.Random;

/**
 * @author surik
 */
public class TestObjectTemplate {

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
        item.setTitle("test_Photo");
        item.setDescription("test item");
        item.setItemType(ItemType.PAINTING);
        item.setPhotoURL("test");
        item.setPrice(getRandomNumber()+1.1);
        item.setStatus(true);
        return item;
    }

    public static Artist createTestArtist() {
        Artist testArtist = new Artist();
        testArtist.setFirstName("Test FirstName" + getRandomNumber());
        testArtist.setLastName("Test LastName");
        testArtist.setAge(26);
        testArtist.setEmail("test" + getRandomNumber() + "@test.com");
        testArtist.setPassword("testPassword");
        testArtist.setSpecialization(ArtistSpecialization.SCULPTOR);
        testArtist.setArtistPhoto(new byte[10]);
        return testArtist;
    }


    public static int getRandomNumber() {
        Random rand = new Random();
        return rand.nextInt(100000) + 1;
    }
}

