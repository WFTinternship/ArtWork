package am.aca.wftartproject.service.integration;

import am.aca.wftartproject.entity.*;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.dao.NotEnoughMoneyException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.service.BaseIntegrationTest;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.PurchaseHistoryService;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import am.aca.wftartproject.service.impl.ItemServiceImpl;
import am.aca.wftartproject.service.impl.ShoppingCardServiceImpl;
import am.aca.wftartproject.util.TestObjectTemplate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualItems;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestArtist;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestItem;
import static junit.framework.TestCase.*;

/**
 * Created by ASUS on 30-Jun-17
 */
public class ItemServiceIntegrationTest extends BaseIntegrationTest {

    private Artist testArtist;
    private Item testItem;
    private Item tempItem;
    private ShoppingCard testShoppingCard;
    private PurchaseHistory testPurchaseHistory;

    @Autowired
    private ArtistServiceImpl artistService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ShoppingCardServiceImpl shoppingCardService;

    @Autowired
    private PurchaseHistoryService purchaseHistoryService;

    /**
     * Creates testArtist and testItem for tests
     */
    @Before
    public void setUp() {
        testArtist = createTestArtist();
        testItem = createTestItem();
        /*testShoppingCard = createTestShoppingCard();
        testArtist.setShoppingCard(testShoppingCard);*/
        testShoppingCard = TestObjectTemplate.createTestShoppingCard();
        testArtist.setShoppingCard(testShoppingCard);
        artistService.addArtist(testArtist);
        testItem.setArtist_id(testArtist.getId());
        tempItem = createTestItem();
        tempItem.setArtist_id(testArtist.getId());
        testShoppingCard.setBuyer_id(testArtist.getId());
    }

    /**
     * Deletes all items and artist created during the tests
     */
    @After
    public void tearDown() {
        if (tempItem.getId() != null) {
            itemService.deleteItem(tempItem);
        }

        if (testItem.getId() != null) {
            itemService.deleteItem(testItem);
        }

        if (testShoppingCard.getId() != null) {
            shoppingCardService.deleteShoppingCard(testShoppingCard);
        }

        if (testArtist.getId() != null) {
            artistService.deleteArtist(testArtist);
        }

        testItem = null;
        testArtist = null;
        tempItem = null;
    }

    // region<TEST CASE>

    /**
     * @see ItemServiceImpl#addItem(Item)
     */
    @Test
    public void addItem_Success() {
        // Check testArtist and its id for null
        assertNotNull(testArtist);
        assertNotNull(testArtist.getId());

        // Add item into DB
        itemService.addItem(testItem);

        // Find added item from DB
        Item addedItem = itemService.findItem(testItem.getId());

        // Check sameness items
        assertEqualItems(addedItem, testItem);
    }

    /**
     * @see ItemServiceImpl#addItem(Item)
     */
    @Test(expected = InvalidEntryException.class)
    public void addItem_Failure() {
        // Test method
        itemService.addItem(null);
    }

    /**
     * @see ItemServiceImpl#findItem(java.lang.Long)
     */
    @Test
    public void findItem_Success() {
        // Add item into DB
        itemService.addItem(testItem);

        // Find added item from DB
        Item foundedItem = itemService.findItem(testItem.getId());

        // Check sameness items
        assertNotNull(testItem);
        assertEqualItems(foundedItem, testItem);
    }

    /**
     * @see ItemServiceImpl#findItem(java.lang.Long)
     */
    @Test(expected = InvalidEntryException.class)
    public void findItem_Failure() {
        // Test method
        itemService.findItem(-5L);
    }

    /**
     * @see ItemServiceImpl#getRecentlyAddedItems(int)
     */
    @Test
    public void getRecentlyAddedItems_NotEmptyList() {
        // Add items into Db
        itemService.addItem(testItem);
        itemService.addItem(tempItem);

        // Test method and check
        List<Item> recentlyAddedItems = itemService.getRecentlyAddedItems(10);
        assertEqualItems(tempItem, recentlyAddedItems.get(recentlyAddedItems.size()-1));
    }

    /**
     * @see ItemServiceImpl#getRecentlyAddedItems(int)
     */
    @Test(expected = InvalidEntryException.class)
    public void getRecentlyAddedItems_EmptyList() {
        // Test method
        itemService.getRecentlyAddedItems(-1);
    }

    /**
     * @see ItemServiceImpl#getItemsByTitle(java.lang.String)
     */
    @Test
    public void getItemsByTitle_NotEmptyList() {
        testItem.setTitle("itemTitle");
        // Add item into DB
        itemService.addItem(testItem);

        // Test method
        List<Item> items = itemService.getItemsByTitle(testItem.getTitle());
        assertEqualItems(testItem, items.get(items.size() - 1));
    }

    /**
     * @see ItemServiceImpl#getItemsByTitle(java.lang.String)
     */
    @Test
    public void getItemsByTitle_EmptyList() {
        // Test method
        List<Item> items = itemService.getItemsByTitle("fake title");
        assertTrue(items.isEmpty());
    }

    /**
     * @see ItemServiceImpl#getItemsByType(ItemType)
     */
    @Test
    public void getItemsByType_NotEmptyList() {
        // Add item into Db
        itemService.addItem(testItem);

        // Test method
        List<Item> items = itemService.getItemsByType(testItem.getItemType());
        /*Item recentlyAddedItem = null;
        for (Item item : items) {
            if (item.equals(testItem)) {
                recentlyAddedItem = item;
                break;
            }
        }
        assertNotNull(recentlyAddedItem);
        */
        assertEqualItems(testItem, items.get(items.size() - 1));
    }

    /**
     * @see ItemServiceImpl#getItemsByType(ItemType)
     */
    @Test(expected = InvalidEntryException.class)
    public void getItemsByType_EmptyList() {
        // Test method
        List<Item> items = itemService.getItemsByType(null);
        assertTrue(items.isEmpty());
    }

    /**
     * @see ItemServiceImpl#getItemsForGivenPriceRange(java.lang.Double, java.lang.Double)
     */
    @Test
    public void getItemsForGivenPriceRange_NotEmptyList() {
        // Add testIem into DB
        itemService.addItem(testItem);

        // Test method
        List<Item> items = itemService.getItemsForGivenPriceRange(testItem.getPrice() - 100.0, testItem.getPrice() + 100.0);
        assertFalse(items.isEmpty());
    }

    /**
     * @see ItemServiceImpl#getItemsForGivenPriceRange(java.lang.Double, java.lang.Double)
     */
    @Test
    public void getItemsForGivenPriceRange_EmptyList() {
        // Test method
        List<Item> items = itemService.getItemsForGivenPriceRange(0.0, 1.0);
        assertTrue(items.isEmpty());
    }

    /**
     * @see ItemServiceImpl#getArtistItems(Long)
     */
    @Test
    public void getArtistItems_NotEmptyList() {
        // Add test items into DB
        itemService.addItem(testItem);
        itemService.addItem(tempItem);

        // Test method
        List<Item> items = itemService.getArtistItems(testArtist.getId());

        assertFalse(items.isEmpty());
    }

    /**
     * @see ItemServiceImpl#getArtistItems(Long)
     */
    @Test
    public void getArtistItems_EmptyList() {

        // Test method
        List<Item> items = itemService.getArtistItems(testArtist.getId());
        assertTrue(items.isEmpty());
    }

//    /**
//     * @see ItemServiceImpl#getAvailableItemsForGivenArtist(Long)
//     */
//    @Test
//    public void getAvailableItemsForGivenArtist_NotEmptyLIst() {
//        assertNotNull(testArtist.getId());
//        itemService.addItem(testArtist.getId(), testItem);
//
//        assertNotNull(testItem.getId());
//
//        // Test method
//        List<Item> availableItems = itemService.getAvailableItemsForGivenArtist(testArtist.getId());
//        assertEqualItems(testItem, availableItems.get(0));
//    }
//
//    /**
//     * @see ItemServiceImpl#getAvailableItemsForGivenArtist(Long)
//     */
//    @Test
//    public void getAvailableItemsForGivenArtist_EmptyList() {
//        assertNotNull(testArtist.getId());
//        testItem.setStatus(true);
//        itemService.addItem(testArtist.getId(), testItem);
//
//        assertNotNull(testItem.getId());
//
//        // Test method
//        List<Item> availableItems = itemService.getAvailableItemsForGivenArtist(testArtist.getId());
//        assertTrue(availableItems.isEmpty());
//    }

    /**
     * @see ItemServiceImpl#updateItem(Item)
     */
    @Test
    public void updateItem_Success() {
        // Add test item into DB
        itemService.addItem(testItem);

        // Change testItem for update
        testItem.setDescription("new description");

        // Test method
        itemService.updateItem(testItem);
    }

    /**
     * @see ItemServiceImpl#updateItem(Item)
     */

    @Test(expected = InvalidEntryException.class)
    public void updateItem_Failure() {
        // Test method

       long temp = testItem.getArtist_id();
       testItem.setArtist_id(null);
        try{
            itemService.updateItem(testItem);
        }
        catch (InvalidEntryException e){
            testItem.setArtist_id(temp);
            throw new InvalidEntryException("");
        }

    }

    /**
     * @see ItemServiceImpl#deleteItem(Item)
     */
    @Test
    public void deleteItem_Success() {
        // Add item into DB
        itemService.addItem(testItem);

        // Test method
        itemService.deleteItem(testItem);
        testItem.setId(null);
    }

    /**
     * @see ItemServiceImpl#deleteItem(Item)
     */
    @Test(expected = InvalidEntryException.class)
    public void deleteItem_Failure() {
        // Test method
        testItem.setTitle(null);
        itemService.deleteItem(testItem);
    }

    /**
     * @see ItemServiceImpl#itemBuying(am.aca.wftartproject.entity.Item, java.lang.Long)
     */
    @Test
    public void itemBuying_Success() {
        // Create testShoppingCard and testPurchaseHistory
        testItem.setPrice(1000.0);
        testItem.setArtist_id(testArtist.getId());
        testShoppingCard.setBalance(10000.0);
        shoppingCardService.addShoppingCard(testShoppingCard);

        // Add testItem, shoppingCard into DB
        itemService.addItem(testItem);

        // Test method
        itemService.itemBuying(testItem, testArtist.getId());

        testPurchaseHistory = purchaseHistoryService.getPurchase(testItem.getId());

        List<Item> purchaseItems = itemService.getItemsByTitle(testItem.getTitle());

        assertEqualItems(testItem, purchaseItems.get(purchaseItems.size() - 1));

        if (testPurchaseHistory.getItemId() != null)
            purchaseHistoryService.deletePurchase(testPurchaseHistory);

        testPurchaseHistory = null;
    }

    /**
     * @see ItemServiceImpl#itemBuying(am.aca.wftartproject.entity.Item, java.lang.Long)
     */
    @Test(expected = NotEnoughMoneyException.class)
    public void itemBuying_Failure() {
        // Create testShoppingCard and testPurchaseHistory
        testItem.setPrice(1000000000.0);
        testItem.setArtist_id(testArtist.getId());

        shoppingCardService.addShoppingCard(testShoppingCard);

        // Add testItem, shoppingCard into DB
        itemService.addItem(testItem);

        // Test method
        itemService.itemBuying(testItem, testArtist.getId());
    }

    // endregion
}

