package integration.service;

import am.aca.wftartproject.model.*;

import static org.junit.Assert.assertEquals;

/**
 * @author surik
 */
public class AssertTemplates {
    public static void assertEqualUsers(User expectedUser, User actualUser) {
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
        assertEquals(expectedUser.getLastName(), actualUser.getLastName());
        assertEquals(expectedUser.getAge(),actualUser.getAge());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
    }
    public static void assertEqualArtists(Artist expectedItem, Artist actualArtist) {
        assertEquals(expectedItem.getId(), actualArtist.getId());
        assertEquals(expectedItem.getFirstName(), actualArtist.getFirstName());
        assertEquals(expectedItem.getLastName(), actualArtist.getLastName());
        assertEquals(expectedItem.getAge(), actualArtist.getAge());
        assertEquals(expectedItem.getEmail(), actualArtist.getEmail());
        assertEquals(expectedItem.getPassword(),actualArtist.getPassword());
        assertEquals(expectedItem.getItemList(), actualArtist.getItemList());
        assertEquals(expectedItem.getSpecialization(), actualArtist.getSpecialization());


    }
    public static void assertEqualItems(Item expectedItem, Item actualItem) {
        assertEquals(expectedItem.getId(), actualItem.getId());
        assertEquals(expectedItem.getTitle(), actualItem.getTitle());
        assertEquals(expectedItem.getDescription(), actualItem.getDescription());
        assertEquals(expectedItem.getPhotoURL(), actualItem.getPhotoURL());
        assertEquals(expectedItem.getPrice(), actualItem.getPrice());
        assertEquals(expectedItem.getItemType(), actualItem.getItemType());
    }
    public static void assertEqualShoppingCards(ShoppingCard expectedShoppingCard, ShoppingCard actualShoppingCard) {
        assertEquals(expectedShoppingCard.getId(), actualShoppingCard.getId());
        assertEquals(expectedShoppingCard.getBalance(), actualShoppingCard.getBalance(), 0.0001);
    }

    public static void assertEqualPurchaseHistory(PurchaseHistory expectedPurchaseHistory, PurchaseHistory actualPurchaseHistory) {
        assertEquals(expectedPurchaseHistory.getItemId(), actualPurchaseHistory.getItemId());
        assertEquals(expectedPurchaseHistory.getUserId(), actualPurchaseHistory.getUserId());
        assertEquals(expectedPurchaseHistory.getPurchaseDate().toString(), actualPurchaseHistory.getPurchaseDate().toString());
    }
}
