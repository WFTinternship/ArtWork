package am.aca.wftproject.util;

import am.aca.wftartproject.model.*;

import java.util.Random;

/**
 * Created by Armen on 5/30/2017
 */
public class TestObjectTemplate {

    public static Artist createTestArtist() {
        Artist testArtist = new Artist();
        testArtist.setArtistPhoto(new byte[10])
                .setSpecialization(ArtistSpecialization.PAINTER)
                .setAge(26)
                .setEmail("test" + getRandomNumber() + "@test.com")
                .setFirstName("Test FirstName" + getRandomNumber())
                .setLastName("Test LastName" + getRandomNumber())
                .setPassword("test123");
        // testArtist.setShoppingCard(new ShoppingCard(getRandomNumber() + 1.1));
        return testArtist;
    }

    public static User createTestUser() {
        User testUser = new User();
        testUser.setFirstName("Test FirstName" + getRandomNumber())
                .setLastName("Test LastName")
                .setAge(26)
                .setEmail("test" + getRandomNumber() + "@test.com")
                .setPassword("testPassword");
        return testUser;
    }

    //title, description, photo_url, price, artist_id, status, item_type
    public static Item createTestItem() {
        Item item = new Item();
        item.setTitle("test_item")
                .setDescription("test item")
                .setItemType(ItemType.PAINTING)
                .setPhotoURL("../../resources/images/product/images (1).jpg")
                .setPrice(getRandomNumber() + 1.1)
                .setStatus(false);
        return item;
    }

    public static int getRandomNumber() {
        Random rand = new Random();
        return rand.nextInt(100000) + 1;
    }
}
