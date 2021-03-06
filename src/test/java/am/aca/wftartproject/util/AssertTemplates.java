package am.aca.wftartproject.util;

import am.aca.wftartproject.model.*;
import junit.framework.TestCase;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import java.util.List;

import static am.aca.wftartproject.util.DateHelper.dateComparison;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Armen on 5/30/2017
 */
public class AssertTemplates {
    public static void assertEqualUsers(User expectedUser, User actualUser) {
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
        assertEquals(expectedUser.getLastName(), actualUser.getLastName());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
    }

    public static void assertEqualArtists(Artist expectedArtist, Artist actualArtist) {
        assertEquals(expectedArtist.getSpecialization(), actualArtist.getSpecialization());
        assertEquals(expectedArtist.getItemList(), actualArtist.getItemList());
        assertEquals(expectedArtist.getFirstName(), actualArtist.getFirstName());
        assertArrayEquals(expectedArtist.getArtistPhoto(), actualArtist.getArtistPhoto());
        assertEquals(expectedArtist.getAge(), actualArtist.getAge());
        assertEquals(expectedArtist.getLastName(), actualArtist.getLastName());

    }

    public static void assertEqualItems(Item expectedItem, Item actualItem) {
        assertEquals(expectedItem.getDescription(), actualItem.getDescription());
        assertEquals(expectedItem.getId(), actualItem.getId());
        assertEquals(expectedItem.getItemType(), actualItem.getItemType());
        assertEquals(expectedItem.getTitle(), actualItem.getTitle());
        assertEquals(expectedItem.getPhotoURL(), actualItem.getPhotoURL());
        assertEquals(expectedItem.getPrice(), actualItem.getPrice());
        assertTrue(dateComparison(expectedItem.getAdditionDate(), actualItem.getAdditionDate()));
//        assertEquals(expectedItem.getAdditionDate().getTime() / 1000000000, actualItem.getAdditionDate().getTime() / 1000000000);
    }

    public static void assertEqualShoppingCards(ShoppingCard expectedShoppingCard, ShoppingCard actualShoppingCard) {
        assertEquals(expectedShoppingCard.getId(), actualShoppingCard.getId());
        assertEquals(expectedShoppingCard.getBalance(), actualShoppingCard.getBalance(), 0.0001);
        assertEquals(expectedShoppingCard.getShoppingCardType(),actualShoppingCard.getShoppingCardType());
    }

    public static void assertEqualPurchaseHistory(PurchaseHistory expectedPurchaseHistory, PurchaseHistory actualPurchaseHistory) {
        assertEquals(expectedPurchaseHistory.getItemId(), actualPurchaseHistory.getItemId());
        assertEquals(expectedPurchaseHistory.getUserId(), actualPurchaseHistory.getUserId());
        assertTrue(dateComparison(expectedPurchaseHistory.getPurchaseDate(), actualPurchaseHistory.getPurchaseDate()));
//        assertEquals(expectedPurchaseHistory.getPurchaseDate().getTime() / 1000000000, actualPurchaseHistory.getPurchaseDate().getTime() / 1000000000);
    }

    public static void assertEqualCookies(Cookie expected, List<Cookie> real) {
        TestCase.assertEquals(expected.getMaxAge(), real.get(0).getMaxAge());
        TestCase.assertEquals(expected.getName(), real.get(0).getName());
        TestCase.assertEquals(expected.getValue(), real.get(0).getValue());
    }

    public static void assertEqualModelAndViews(ModelAndView expected, ModelAndView real) {
        TestCase.assertEquals(expected.getViewName(), real.getViewName());
        TestCase.assertEquals(expected.getView(), real.getView());
        TestCase.assertEquals(expected.getModel(), real.getModel());
        TestCase.assertEquals(expected.getModelMap(), real.getModelMap());
        TestCase.assertEquals(expected.getStatus(), real.getStatus());
    }
}