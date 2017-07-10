package am.aca.wftartproject.service.unit;

import am.aca.wftartproject.dao.ItemDao;
import am.aca.wftartproject.entity.*;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.dao.NotEnoughMoneyException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.service.BaseUnitTest;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.PurchaseHistoryService;
import am.aca.wftartproject.service.ShoppingCardService;
import am.aca.wftartproject.service.impl.ItemServiceImpl;
import am.aca.wftartproject.util.AssertTemplates;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualItems;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualPurchaseHistory;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualShoppingCards;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestItem;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestPurchaseHistory;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestShoppingCard;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by ASUS on 24-Jun-17
 */
public class ItemServiceUnitTest extends BaseUnitTest {

    private Item testItem;
    private ShoppingCard shoppingCard;
    private PurchaseHistory purchaseHistory;

    @InjectMocks
    @Autowired
    private ItemService itemService;

    @Mock
    private ItemDao itemDaoMock;

    @Mock
    private ShoppingCardService shoppingCardServiceMock;

    @Mock
    private PurchaseHistoryService purchaseHistoryServiceMock;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(itemService, "itemDao", itemDaoMock);
    }

    @After
    public void afterTest() {

    }

    // region <TEST CASES>

    /**
     * @see ItemService#addItem(Item)
     */
    @Test
    public void addItem_idIsNullOrNegative() {
        // Create test id and testItem
        Long id = null;
        testItem = createTestItem();

        // Test method
        try {
            itemService.addItem(testItem);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change id value to negative
        id = -5L;

        // Test method
        try {
            itemService.addItem(testItem);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ItemService#addItem(Item)
     */
    @Test
    public void addItem_itemIsNullOrNotValid() {
        // Create test id and test Item
        Long id = 5L;
        testItem = null;

        // Test method
        try {
            itemService.addItem(testItem);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Create test Item
        testItem = createTestItem();
        testItem.setTitle(null);

        // Test method
        try {
            itemService.addItem(testItem);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ItemService#addItem(Item)
     */
    @Test(expected = ServiceException.class)
    public void addItem_addFail() {
        // Create test id and test Item
        Long id = 5L;
        testItem = createTestItem();
        testItem.setArtist_id(id);

        // Setup mock
        doThrow(DAOException.class).when(itemDaoMock).addItem(testItem);

        // Test method
        itemService.addItem(testItem);
    }


    /**
     * @see ItemService#addItem(Item)
     */
    @Test
    public void addItem_addSuccess() {
        // Create test id and test item
        Long id = 5L;
        testItem = createTestItem();
        testItem.setArtist_id(id);

        // Setup mock
        doNothing().when(itemDaoMock).addItem(testItem);

        // Test method
        itemService.addItem(testItem);
    }


    /**
     * @see ItemService#findItem(Long)
     */
    @Test
    public void findItem_idIsNullOrNegative() {
        // Create test id
        Long id = null;

        // Test method
        try {
            itemService.findItem(id);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change id value to negative
        id = -5L;

        // Test method
        try {
            itemService.findItem(id);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ItemService#findItem(Long)
     */
    @Test(expected = ServiceException.class)
    public void findItem_findFail() {
        // Create test id
        Long id = 5L;

        // Setup mock
        doThrow(DAOException.class).when(itemDaoMock).findItem(anyLong());

        // Test method
        itemService.findItem(id);
    }


    /**
     * @see ItemService#findItem(Long)
     */
    @Test
    public void findItem_findSuccess() {
        // Create Argument Capture
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);

        // Create test id and test Item
        Long id = 5L;
        testItem = createTestItem();

        // Setup mock
        doReturn(testItem).when(itemDaoMock).findItem(argument.capture());

        // Test method
        assertEqualItems(testItem, itemService.findItem(id));

        // Check input argument
        assertEquals(id, argument.getValue());
    }


    /**
     * @see ItemService#getRecentlyAddedItems(int)
     */
    @Test
    public void getRecentlyAddedItems_limitIsNegative() {
        // Create test limit
        int testLimit = -20;

        // Test method
        try {
            itemService.getRecentlyAddedItems(testLimit);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ItemService#getRecentlyAddedItems(int)
     */
    @Test(expected = ServiceException.class)
    public void getRecentlyAddedItems_getFail() {
        // Create test limit
        int testLimit = 20;

        // Setup mock
        doThrow(DAOException.class).when(itemDaoMock).getRecentlyAddedItems(anyInt());

        // Test method
        itemService.getRecentlyAddedItems(testLimit);
    }


    /**
     * @see ItemService#getRecentlyAddedItems(int)
     */
    @Test
    public void getRecentlyAddedItems_getSuccess() {
        // Create Argument Capture
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);

        // Create test limit and itemList
        int testLimit = 20;
        List<Item> itemList = null;

        // Setup mock
        doReturn(itemList).when(itemDaoMock).getRecentlyAddedItems(argument.capture());

        // Test method
        itemService.getRecentlyAddedItems(testLimit);

        // Check input argument
        assertEquals(testLimit, argument.getValue().intValue());
    }


    /**
     * @see ItemService#getItemsByTitle(String)
     */
    @Test
    public void getItemsByTitle_titleIsNotValid() {
        // Create test title
        String title = "";

        // Test method
        try {
            itemService.getItemsByTitle(title);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ItemService#getItemsByTitle(String)
     */
    @Test(expected = ServiceException.class)
    public void getItemsByTitle_getFail() {
        // Create test title
        String title = "Title 1";

        // Setup mock
        doThrow(DAOException.class).when(itemDaoMock).getItemsByTitle(anyString());

        // Test method
        itemService.getItemsByTitle(title);
    }


    /**
     * @see ItemService#getItemsByTitle(String)
     */
    @Test
    public void getItemsByTitle_getSuccess() {
        // Create Argument Capture
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);

        // Create test title and itemList
        String title = "Title 1";
        List<Item> itemList = new ArrayList<>();

        // Setup mock
        doReturn(itemList).when(itemDaoMock).getItemsByTitle(argument.capture());

        // Test method
        itemService.getItemsByTitle(title);

        // Check input argument
        assertEquals(title, argument.getValue());
    }


    /**
     * @see ItemService#getItemsByType(am.aca.wftartproject.entity.ItemType)
     */
    @Test
    public void getItemsByType_typeIsNotValid() {
        // Create test type
        ItemType type = null;

        // Test method
        try {
            itemService.getItemsByType(type);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ItemService#getItemsByType(ItemType)
     */
    @Test(expected = ServiceException.class)
    public void getItemsByType_getFail() {
        // Create test type
        ItemType itemType = ItemType.OTHER;

        // Setup mock
        doThrow(DAOException.class).when(itemDaoMock).getItemsByType(any(ItemType.class));

        //Test method
        itemService.getItemsByType(itemType);
    }


    /**
     * @see ItemService#getItemsByType(ItemType)
     */
    @Test
    public void getItemsByType_getSuccess() {
        // Create Argument Capture
        ArgumentCaptor<ItemType> argument = ArgumentCaptor.forClass(ItemType.class);

        // Create test type and test itemList
        ItemType type = ItemType.OTHER;
        List<Item> itemList = new ArrayList<>();

        // Setup mock
        doReturn(itemList).when(itemDaoMock).getItemsByType(argument.capture());

        //Test method
        itemService.getItemsByType(type);

        // Check input argument
        assertEquals(type, argument.getValue());
    }


    /**
     * @see ItemService#getItemsForGivenPriceRange(Double, Double)
     */
    @Test
    public void getItemsForGivenPriceRange_priceIsNotValid() {
        // Create min and max test price values
        Double testMinPrice = null;
        Double testMaxPrice = null;

        // Test method
        try {
            itemService.getItemsForGivenPriceRange(testMinPrice, testMaxPrice);
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change min price value
        testMinPrice = -100.0;

        // Test method
        try {
            itemService.getItemsForGivenPriceRange(testMinPrice, testMaxPrice);
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change max price value
        testMaxPrice = -100.0;

        // Test method
        try {
            itemService.getItemsForGivenPriceRange(testMinPrice, testMaxPrice);
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change min price to null
        testMinPrice = null;

        // Test method
        try {
            itemService.getItemsForGivenPriceRange(testMinPrice, testMaxPrice);
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ItemService#getItemsForGivenPriceRange(Double, Double)
     */
    @Test
    public void getItemsForGivenPriceRange_gottenListIsNull() {
        // Create Argument Capture
        ArgumentCaptor<Double> argument = ArgumentCaptor.forClass(Double.class);

        // Creates test min and max price values
        Double testMinPrice = 100.0;
        Double testMaxPrice = 1000.0;
        List<Item> itemList = null;

        // Setup mock
        doReturn(null).when(itemDaoMock).getItemsForGivenPriceRange(argument.capture(),argument.capture());

        // Test method
        assertEquals(itemList,itemService.getItemsForGivenPriceRange(testMinPrice,testMaxPrice));

        // Check input argument
        assertEquals(testMinPrice,argument.getAllValues().get(0));
        assertEquals(testMaxPrice,argument.getAllValues().get(1));
    }


    /**
     * @see ItemService#getItemsForGivenPriceRange(Double, Double)
     */
    @Test
    public void getItemsForGivenPriceRange_getSuccess() {
        // Create Argument Capture
        ArgumentCaptor<Double> argument = ArgumentCaptor.forClass(Double.class);

        // Creates test min and max price values
        Double testMinPrice = 100.0;
        Double testMaxPrice = 1000.0;
        List<Item> itemList = new ArrayList<>();

        // Setup mock
        doReturn(itemList).when(itemDaoMock).getItemsForGivenPriceRange(argument.capture(),argument.capture());

        // Test method
        assertEquals(itemList,itemService.getItemsForGivenPriceRange(testMinPrice,testMaxPrice));

        // Check input argument
        assertEquals(testMinPrice,argument.getAllValues().get(0));
        assertEquals(testMaxPrice,argument.getAllValues().get(1));
    }


    /**
     * @see ItemService#getArtistItems(Long)
     */
    @Test
    public void getArtistItems_artistIdOrItemIdOrLimitNotValid(){
        // Create test itemId, artistId, and limit
        Long testItemId = null;
        Long testLimit = null;
        Long testArtistId = null;

        // Test method
        try {
            itemService.getArtistItems(testArtistId);
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change itemId to negative
        testItemId = -100L;

        // Test method
        try {
            itemService.getArtistItems(testArtistId);
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change artistId to negative
        testArtistId = -100L;

        // Test method
        try {
            itemService.getArtistItems(testArtistId);
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change limit to negative
        testLimit = -100L;

        // Test method
        try {
            itemService.getArtistItems(testArtistId);
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change itemId to null
        testItemId = null;

        // Test method
        try {
            itemService.getArtistItems(testArtistId);
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change artistId to null
        testArtistId = null;

        // Test method
        try {
            itemService.getArtistItems(testArtistId);
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change artistId to negative and limit to null
        testArtistId = -100L;
        testLimit = null;

        // Test method
        try {
            itemService.getArtistItems(testArtistId);
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change artistId to null, itemId and limit to negative
        testArtistId = null;
        testItemId = -100L;
        testLimit = -100L;

        // Test method
        try {
            itemService.getArtistItems(testArtistId);
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ItemService#getArtistItems(Long)
     */
    @Test
    public void getArtistItems_gottenListIsNull(){
        // Create Argument Capture
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);

        // Creates test iteId, artistId and limit
        Long testArtistId = 7L;
        Long testItemId = 5L;
        Long testLimit = 3L;

        // Setup mock
        doReturn(null).when(itemDaoMock).getArtistItems(argument.capture());

        // Test method
        assertEquals(null,itemService.getArtistItems(testArtistId));

        // Check input argument
        assertEquals(testArtistId,argument.getAllValues().get(0));
        assertEquals(testItemId,argument.getAllValues().get(1));
        assertEquals(testLimit,argument.getAllValues().get(2));
    }


    /**
     * @see ItemService#getArtistItems(Long)
     */
    @Test
    public void getArtistItems_getSuccess(){
        // Create Argument Capture
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);

        // Creates test iteId, artistId and limit
        Long testArtistId = 7L;
        Long testItemId = 5L;
        Long testLimit = 3L;

        List<Item> itemList = new ArrayList<>();

        // Setup mock
        doReturn(itemList).when(itemDaoMock).getArtistItems(argument.capture());

        // Test method
        assertEquals(itemList,itemService.getArtistItems(testArtistId));

        // Check input argument
        assertEquals(testArtistId,argument.getAllValues().get(0));
        assertEquals(testItemId,argument.getAllValues().get(1));
        assertEquals(testLimit,argument.getAllValues().get(2));
    }

//    /**
//     * @see ItemServiceImpl#getAvailableItemsForGivenArtist(Long)
//     */
//    @Test
//    public void getAvailableItemsForGivenArtist_artistIdNullOrNegative() {
//        Long artistId = null;
//
//        try {
//            itemService.getAvailableItemsForGivenArtist(artistId);
//            fail();
//        } catch (Exception e) {
//            assertTrue(e instanceof InvalidEntryException);
//        }
//
//        artistId = -5L;
//
//        try {
//            itemService.getAvailableItemsForGivenArtist(artistId);
//            fail();
//        } catch (Exception e) {
//            assertTrue(e instanceof InvalidEntryException);
//        }
//    }
//
//    /**
//     * @see ItemServiceImpl#getAvailableItemsForGivenArtist(Long)
//     */
//    @Test
//    public void getAvailableItemsForGivenArtist_gottenListIsNull() {
//        // Setup mock
//        doReturn(null).when(itemDaoMock).getAvailableItemsForGivenArtist(anyLong());
//
//        List<Item> availableItems = itemService.getAvailableItemsForGivenArtist(5L);
//
//        assertNull(availableItems);
//    }
//
//    /**
//     * @see ItemServiceImpl#getAvailableItemsForGivenArtist(Long)
//     */
//    @Test
//    public void getAvailableItemsForGivenArtist_gottenListNotNull() {
//        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
//
//        Long artistId = 5L;
//
//        List<Item> availableItems = new ArrayList<>();
//
//        doReturn(availableItems).when(itemDaoMock).getAvailableItemsForGivenArtist(argumentCaptor.capture());
//
//        assertEquals(availableItems, itemService.getAvailableItemsForGivenArtist(artistId));
//
//        assertEquals(artistId, argumentCaptor.getValue());
//    }


    /**
     * @see ItemService#updateItem(Item)
     */
    @Test
    public void updateItem_idIsNullOrNegative() {
        // Create test id and testItem
        Long id = null;
        testItem = createTestItem();
        testItem.setArtist_id(id);

        // Test method
        try {
            itemService.updateItem(testItem);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change id value to negative
        id = -5L;

        // Test method
        try {
            itemService.updateItem(testItem);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ItemService#updateItem(Item)
     */
    @Test
    public void updateItem_itemIsNullOrNotValid() {
        // Create test id and testItem
        Long id = 5L;
        testItem = null;

        // Test method
        try {
            itemService.updateItem(testItem);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Create item and set to not valid
        testItem = createTestItem();
        testItem.setTitle(null);

        // Test method
        try {
            itemService.updateItem(testItem);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ItemService#updateItem(Item)
     */
    @Test(expected = ServiceException.class)
    public void updateItem_updateFail(){
        // Create Argument Capture
        ArgumentCaptor<Item> argument = ArgumentCaptor.forClass(Item.class);

        // Create test id and test item
        Long id = 343534536L;
        testItem = createTestItem();
        testItem.setArtist_id(id);

        // Setup mock
        doThrow(DAOException.class).when(itemDaoMock).updateItem(argument.capture());

        // Test method
        itemService.updateItem(testItem);
    }


    /**
     * @see ItemService#updateItem(Item)
     */
    @Test
    public void updateItem_updateSuccess(){
        // Create Argument Capture
        ArgumentCaptor<Item> argument = ArgumentCaptor.forClass(Item.class);

        // Create test id and test item
        Long id = 3L;
        testItem = createTestItem();
        testItem.setArtist_id(id);
        testItem.setId(444L);

        // Setup mock
        doNothing().when(itemDaoMock).updateItem(any(Item.class));

        // Test method
        itemService.updateItem(testItem);

        // Check input arguments
        AssertTemplates.assertEqualItems(testItem,argument.getValue());
    }


    /**
     * @see ItemService#deleteItem(Item)
     */
    @Test
    public void deleteItem_idIsNullOrNotValid(){
        // Create test id with null value
        testItem = createTestItem();
        Long id = null;
        testItem.setId(id);
        // Test method
        try {
            itemService.deleteItem(testItem);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change id value to negative
        id = -5L;
        testItem.setId(id);
        // Test method
        try {
            itemService.deleteItem(testItem);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ItemService#deleteItem(Item)
     */
    @Test(expected = ServiceException.class)
    public void deleteItem_deleteFail(){
        // Create test id and test item
        testItem = createTestItem();
        Long id = 343534536L;
        testItem.setId(id);
        testItem.setArtist_id(555L);

        // Setup mock
        doThrow(DAOException.class).when(itemDaoMock).deleteItem(any(Item.class));

        // Test method
        itemService.deleteItem(testItem);
    }


    /**
     * @see ItemService#deleteItem(Item)
     */
    @Test
    public void deleteItem_deleteSuccess(){
        // Create Argument Capture
        ArgumentCaptor<Item> argument = ArgumentCaptor.forClass(Item.class);

        // Create test id
        Long id = 10L;
        testItem.setId(id);

        // Setup mock
        doReturn(true).when(itemDaoMock).deleteItem(argument.capture());

        // Test method
        itemService.deleteItem(testItem);

        // Check input argument
        assertEquals(id,argument.getValue());
    }


    /**
     * @see ItemService#itemBuying(Item, Long)
     */
    @Test
    public void itemBuying_idIsNotValid(){
        // Create test item and test buyerId with null value
        Long buyerId = null;
        testItem = createTestItem();

        // Test method
        try{
            itemService.itemBuying(testItem, buyerId);
            fail();
        }catch (Exception ex){
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change id value to negative
        buyerId = -5L;

        // Test method
        try {
            itemService.itemBuying(testItem,buyerId);
            fail();
        }catch (Exception ex){
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ItemService#itemBuying(Item, Long)
     */
    @Test
    public void itemBuying_itemIsNotValid(){
        // Create test buyerId and test item with null value
        Long buyerId = 5L;
        testItem = null;

        // Test method
        try{
            itemService.itemBuying(testItem,buyerId);
            fail();
        }catch (Exception ex){
            assertTrue(ex instanceof InvalidEntryException || ex instanceof NotEnoughMoneyException);
        }

        // Change buyerId value to negative
        testItem = createTestItem();
        testItem.setTitle(null);

        // Test method
        try {
            itemService.itemBuying(testItem,buyerId);
            fail();
        }catch (Exception ex){
            assertTrue(ex instanceof InvalidEntryException);
        }
    }

    /**
     * @see ItemService#itemBuying(Item, Long)
     */
    @Test(expected = ServiceException.class)
    public void itemBuying_getShoppingCardFailed() {
        Long buyerId = 5L;
        testItem = createTestItem();
        testItem.setArtist_id(buyerId);
        testItem.setId(777L);

        doThrow(DAOException.class).when(shoppingCardServiceMock).getShoppingCard(anyLong());

        // Test method
        itemService.itemBuying(testItem, buyerId);
    }

    /**
     * @see ItemService#itemBuying(Item, Long)
     */
    @Test(expected = NotEnoughMoneyException.class)
    public void itemBuying_notEnoughMoneyOnAccount(){
        // Create test item, shoppingCard and test buyerId
        Long buyerId = 5L;
        shoppingCard = createTestShoppingCard();
        shoppingCard.setBalance(0);
        shoppingCard.setBuyer_id(buyerId);
        testItem = createTestItem();
        testItem.setArtist_id(5L);
        testItem.setId(777L);

        // Setup mock
        doReturn(shoppingCard).when(shoppingCardServiceMock).getShoppingCard(anyLong());

        // Test method
        itemService.itemBuying(testItem, buyerId);
    }


    /**
     * @see ItemService#itemBuying(Item, Long)
     */
    @Test(expected = ServiceException.class)
    public void itemBuying_withdrawFail(){
        // Create test shoppingCard and test item
        Long id = 5L;
        shoppingCard = createTestShoppingCard();
        shoppingCard.setBalance(1000);
        testItem = createTestItem();
        testItem.setArtist_id(5L);
        testItem.setPrice(100.0);

        // Setup mock
        doReturn(shoppingCard).when(shoppingCardServiceMock).getShoppingCard(anyLong());
        doThrow(DAOException.class).when(shoppingCardServiceMock).updateShoppingCard(any(ShoppingCard.class));

        // Test method
        itemService.itemBuying(testItem, id);
    }


    /**
     * @see ItemService#itemBuying(Item, Long)
     */
    @Test
    public void itemBuying_withdrawSuccess(){
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);

        // Create test shoppingCard and test item
        Long id = 5L;
        shoppingCard = createTestShoppingCard();
        shoppingCard.setBalance(100000);
        testItem = createTestItem();
        testItem.setArtist_id(5L);
        testItem.setPrice(100.0);

        // SetUp mocks
        doReturn(shoppingCard).when(shoppingCardServiceMock).getShoppingCard(argument.capture());

        // Test method
        itemService.itemBuying(testItem,id);

        verify(shoppingCardServiceMock).updateShoppingCard(shoppingCard);

        assertEquals(id, argument.getValue());
    }

    /**
     * @see ItemService#itemBuying(Item, Long)
     */
    @Test(expected = ServiceException.class)
    public void itemBuying_addPurchaseHistoryFail(){
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);

        // Create test item and test shopping card
        Long buyerId = 5L;
        testItem = createTestItem();
        shoppingCard = createTestShoppingCard();
        testItem.setPrice(100.0);
        testItem.setArtist_id(buyerId);
        shoppingCard.setBalance(100000.0);
        shoppingCard.setBuyer_id(buyerId);

        // Setup mocks
        doReturn(shoppingCard).when(shoppingCardServiceMock).getShoppingCard(argument.capture());
        doNothing().when(shoppingCardServiceMock).updateShoppingCard(any(ShoppingCard.class));
        doThrow(DAOException.class).when(purchaseHistoryServiceMock).addPurchase(any(PurchaseHistory.class));

        // Test method
        itemService.itemBuying(testItem, buyerId);

        assertEquals(buyerId, argument.getValue());
    }

    /**
     * @see ItemService#itemBuying(Item, Long)
     */
    @Test
    public void itemBuying_addPurchaseHistorySuccess() {
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<ShoppingCard> argument1 = ArgumentCaptor.forClass(ShoppingCard.class);
        ArgumentCaptor<PurchaseHistory> argument2 = ArgumentCaptor.forClass(PurchaseHistory.class);

        // Create testItem, testShoppingCard and purchaseHistory
        Long buyerId = 5L;
        testItem = createTestItem();
        shoppingCard = createTestShoppingCard();
        testItem.setPrice(100.0);
        testItem.setArtist_id(5L);
        shoppingCard.setBalance(10000.0);
        purchaseHistory = createTestPurchaseHistory();
        purchaseHistory.setItemId(testItem.getId());
        purchaseHistory.setUserId(buyerId);

        // Setup mock
        doReturn(shoppingCard).when(shoppingCardServiceMock).getShoppingCard(argument.capture());
        doNothing().when(shoppingCardServiceMock).updateShoppingCard(argument1.capture());
        doNothing().when(purchaseHistoryServiceMock).addPurchase(argument2.capture());
        doNothing().when(itemDaoMock).updateItem(any(Item.class));

        // Test method
        itemService.itemBuying(testItem, buyerId);

        assertEquals(buyerId, argument.getAllValues().get(0));
        assertEquals(shoppingCard.getId(), argument.getAllValues().get(1));
        assertEqualShoppingCards(shoppingCard, argument1.getValue());
        assertEqualPurchaseHistory(purchaseHistory, argument2.getValue());
    }

    /**
     * @see ItemServiceImpl#itemBuying(am.aca.wftartproject.entity.Item, java.lang.Long)
     */
    @Test(expected = ServiceException.class)
    public void itemBuying_updateItemFailed() {
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<ShoppingCard> argument1 = ArgumentCaptor.forClass(ShoppingCard.class);
        ArgumentCaptor<PurchaseHistory> argument2 = ArgumentCaptor.forClass(PurchaseHistory.class);

        // Create testItem, testShoppingCard and purchaseHistory
        Long buyerId = 5L;
        testItem = createTestItem();
        shoppingCard = createTestShoppingCard();
        testItem.setPrice(1000.0);
        testItem.setArtist_id(5L);
        shoppingCard.setBalance(10000.0);
        purchaseHistory = createTestPurchaseHistory();
        purchaseHistory.setItemId(testItem.getId());
        purchaseHistory.setUserId(buyerId);

        // Setup mock
        doReturn(shoppingCard).when(shoppingCardServiceMock).getShoppingCard(argument.capture());
        doNothing().when(shoppingCardServiceMock).updateShoppingCard(argument1.capture());
        doNothing().when(purchaseHistoryServiceMock).addPurchase(argument2.capture());
        doThrow(DAOException.class).when(itemDaoMock).updateItem(any(Item.class));

        // Test method
        itemService.itemBuying(testItem, buyerId);

        assertEquals(buyerId, argument.getValue());
        assertEqualShoppingCards(shoppingCard, argument1.getValue());
        assertEqualPurchaseHistory(purchaseHistory, argument2.getValue());
    }

    /**
     * @see ItemServiceImpl#itemBuying(am.aca.wftartproject.entity.Item, java.lang.Long)
     */
    @Test
    public void itemBuying_updateItemSuccess() {
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<ShoppingCard> argument1 = ArgumentCaptor.forClass(ShoppingCard.class);
        ArgumentCaptor<PurchaseHistory> argument2 = ArgumentCaptor.forClass(PurchaseHistory.class);
        ArgumentCaptor<Item> argument3 = ArgumentCaptor.forClass(Item.class);

        // Create testItem, testShoppingCard and purchaseHistory
        Long buyerId = 5L;
        testItem = createTestItem();
        shoppingCard = createTestShoppingCard();
        testItem.setPrice(1000.0);
        testItem.setArtist_id(5L);
        shoppingCard.setBalance(10000.0);
        purchaseHistory = createTestPurchaseHistory();
        purchaseHistory.setItemId(testItem.getId());
        purchaseHistory.setUserId(buyerId);

        // Setup mock
        doReturn(shoppingCard).when(shoppingCardServiceMock).getShoppingCard(argument.capture());
        doNothing().when(shoppingCardServiceMock).updateShoppingCard(argument1.capture());
        doNothing().when(purchaseHistoryServiceMock).addPurchase(argument2.capture());
        doNothing().when(itemDaoMock).updateItem(argument3.capture());

        // Test method
        itemService.itemBuying(testItem, buyerId);

        assertEquals(buyerId, argument.getAllValues().get(0));
        assertEquals(shoppingCard.getId(), argument.getAllValues().get(1));
        assertEqualShoppingCards(shoppingCard, argument1.getValue());
        assertEqualPurchaseHistory(purchaseHistory, argument2.getValue());
        assertEquals(testItem.getArtist_id(), argument.getAllValues().get(2));
        assertEqualItems(testItem, argument3.getValue());
    }

    // endregion
}
