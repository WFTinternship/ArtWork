package am.aca.wftartproject.service.integration;

import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.PurchaseHistory;
import am.aca.wftartproject.model.ShoppingCard;
import am.aca.wftartproject.service.BaseIntegrationTest;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import am.aca.wftartproject.service.impl.ItemServiceImpl;
import am.aca.wftartproject.service.impl.ShoppingCardServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static am.aca.wftartproject.util.TestObjectTemplate.*;
import static junit.framework.TestCase.assertTrue;

/**
 * @author surik
 */
public class ItemServiceIntegrationTest extends BaseIntegrationTest{
    private Artist testArtist;
    private Item testItem;
    private Item tempItem;
    private ShoppingCard testShoppingCard;
    private PurchaseHistory testPurchaseHistory;

    @Autowired
    private ArtistServiceImpl artistService;
    @Autowired
    private ItemServiceImpl itemService;
    @Autowired
    private ShoppingCardServiceImpl shoppingCardService;

    /**
     * Creates testArtist and testItem for tests
     */
    @Before
    public void setUp() {
        testArtist = createTestArtist();
        testItem = createTestItem();
        testShoppingCard = createTestShoppingCard();
        testArtist.setShoppingCard(testShoppingCard);
        artistService.addArtist(testArtist);
        tempItem = createTestItem();
    }

    /**
     * Deletes all items and artist created during the tests
     */
    @After
    public void tearDown() {
        if (tempItem.getId() != null) {
            itemService.deleteItem(tempItem.getId());
        }

        if (testItem.getId() != null) {
            itemService.deleteItem(testItem.getId());
        }

        if (testShoppingCard.getId() != null) {
            shoppingCardService.deleteShoppingCard(testShoppingCard.getId());
        }

        if (testArtist.getId() != null) {
            artistService.deleteArtist(testArtist.getId());
        }

        testItem = null;
        testArtist = null;
        tempItem = null;
    }

    // region<TEST CASE>

    /**
     * @see ItemServiceImpl#itemBuying(am.aca.wftartproject.model.Item, java.lang.Long)
     */
    @Test
    public void itemBuying_Success() {
        // Create testShoppingCard and TestPurchaseHistory
        Long buyerId = 5L;
        testPurchaseHistory = createTestPurchaseHistory();
        testItem.setPrice(1000.0);
        testShoppingCard.setBalance(10000.0);

        // Add testItem, shoppingCard into DB
        itemService.addItem(testArtist.getId(), testItem);

        // Test method
        itemService.itemBuying(testItem, buyerId);

        List<Item> purchaseItems = itemService.getItemsByTitle(testItem.getTitle());

        assertTrue(purchaseItems.contains(testItem));
    }

    /**
     * @see ItemServiceImpl#itemBuying(am.aca.wftartproject.model.Item, java.lang.Long)
     */
    @Test
    public void itemBuying_Failure() {

    }


    // endregion

}
