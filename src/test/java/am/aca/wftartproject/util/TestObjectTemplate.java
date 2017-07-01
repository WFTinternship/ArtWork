package am.aca.wftartproject.util;

import am.aca.wftartproject.model.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Armen on 5/30/2017
 */
public class TestObjectTemplate {
    public static Artist createTestArtist() {
        Artist testArtist = new Artist();
        testArtist.setArtistPhoto(null)
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
           //     .setPhotoURL("../../resources/images/product/images (1).jpg")
                .setPrice(getRandomNumber() + 1.1)
                .setStatus(false)
                .setAdditionDate(getDate());
        return item;
    }

    public static ShoppingCard createTestShoppingCard() {
        ShoppingCard shoppingCard = new ShoppingCard();

        shoppingCard.setBalance(getRandomNumber() + 100.0)
        .setShoppingCardType(ShoppingCardType.PAYPAL);

        return shoppingCard;
    }

    public static PurchaseHistory createTestPurchaseHistory() {
        PurchaseHistory purchaseHistory = new PurchaseHistory();

        purchaseHistory.setItemId(getRandomNumber() + 1L)
                .setUserId(getRandomNumber() + 1L)
                .setPurchaseDate(getDate());

        return purchaseHistory;
    }

    public static int getRandomNumber() {
        Random rand = new Random();
        return rand.nextInt(100000) + 1;
    }

    public static Date getDate() {
        return new Date(System.currentTimeMillis());
    }
}
