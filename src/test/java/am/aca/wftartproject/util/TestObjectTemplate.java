package am.aca.wftartproject.util;

import am.aca.wftartproject.entity.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Armen on 5/30/2017
 */
public class TestObjectTemplate {

    public static Artist createTestArtist() {
        Artist testArtist = new Artist();
        testArtist.setArtistPhoto(new byte[]{0})
                .setSpecialization(ArtistSpecialization.PAINTER)
                .setAge(26)
                .setEmail("test" + getRandomNumber() + "@test.com")
                .setFirstName("Test FirstName" + getRandomNumber())
                .setLastName("Test LastName" + getRandomNumber())
                .setPassword("test123").setUserPasswordRepeat("test123");
         testArtist.setShoppingCard(new ShoppingCard().setShoppingCardType(ShoppingCardType.PAYPAL));
        return testArtist;
    }

    public static User createTestUser() {
        User testUser = new User();
        testUser.setFirstName("Test FirstName" + getRandomNumber())
                .setLastName("Test LastName")
                .setAge(26)
                .setEmail("test" + getRandomNumber() + "@test.com")
                .setPassword("testPassword").setUserPasswordRepeat("testPassword");
        testUser.setShoppingCard(new ShoppingCard().setShoppingCardType(ShoppingCardType.PAYPAL));
        return testUser;
    }

    //title, description, photo_url, price, artist_id, status, item_type
    public static Item createTestItem() {
        Item item = new Item();
        List<String> list = new ArrayList<>();
        list.add("../../resources/images/product/images (1).jpg");
        item.setTitle("test_item")
                .setDescription("test item")
                .setItemType(ItemType.PAINTING)
                .setPhotoURL(list)
                .setPrice(getRandomNumber() + 1.1)
                .setStatus(false)
                .setAdditionDate(getCurrentDateTime());
        return item;
    }

    public static ShoppingCard createTestShoppingCard() {
        ShoppingCard shoppingCard = new ShoppingCard();

        shoppingCard.setBalance(getRandomNumber() + 100)
        .setShoppingCardType(ShoppingCardType.PAYPAL);

        return shoppingCard;
    }

    public static PurchaseHistory createTestPurchaseHistory() {
        PurchaseHistory purchaseHistory = new PurchaseHistory();

        purchaseHistory.setItemId(getRandomNumber() + 1L)
                .setUserId(getRandomNumber() + 1L)
                .setPurchaseDate(getCurrentDateTime());

        return purchaseHistory;
    }

    public static int getRandomNumber() {
        Random rand = new Random();
        return rand.nextInt(100000) + 1;
    }

    private static Date getCurrentDateTime() {
        Calendar cal = Calendar.getInstance();
        return new Date(cal.getTimeInMillis());
    }
}
