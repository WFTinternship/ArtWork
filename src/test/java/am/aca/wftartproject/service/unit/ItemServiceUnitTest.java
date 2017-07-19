package am.aca.wftartproject.service.unit;

import am.aca.wftartproject.repository.ItemRepo;
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
import org.springframework.test.util.ReflectionTestUtils;
import java.util.ArrayList;
import java.util.List;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualItems;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualPurchaseHistory;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualShoppingCards;
import static am.aca.wftartproject.util.TestObjectTemplate.*;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;


public class ItemServiceUnitTest extends BaseUnitTest {

    private Item testItem;
    private Artist testArtist;
    private ShoppingCard shoppingCard;
    private PurchaseHistory purchaseHistory;


    @InjectMocks
    private ItemService itemService = new ItemServiceImpl();

    @Mock
    private ItemRepo itemRepoMock;

    @Mock
    private ShoppingCardService shoppingCardServiceMock;

    @Mock
    private PurchaseHistoryService purchaseHistoryServiceMock;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(itemService, "itemRepo", itemRepoMock);
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
        testArtist = createTestArtist();
        testItem.setArtist(testArtist);

        // Setup mock
        doThrow(DAOException.class).when(itemRepoMock).saveAndFlush(testItem);

        // Test method
        itemService.addItem(testItem);
    }


    /**
     * @see ItemService#addItem(Item)
     */
    @Test
    public void addItem_addSuccess() {
        // Create test id and test item
        testArtist = createTestArtist();
        testItem = createTestItem();
        testItem.setArtist(testArtist);

        // Setup mock
        doReturn(testItem).when(itemRepoMock).saveAndFlush(testItem);

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
        doThrow(DAOException.class).when(itemRepoMock).findOne(anyLong());

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
        doReturn(testItem).when(itemRepoMock).findOne(argument.capture());

        // Test method
        assertEqualItems(testItem, itemService.findItem(id));

        // Check input argument
        assertEquals(id, argument.getValue());
    }


//    /**
//     * @see ItemService#getRecentlyAddedItems
//     */
//    @Test
//    public void getRecentlyAddedItems_limitIsNegative() {
//
//        // Test method
//        try {
//            itemService.getRecentlyAddedItems();
//            fail();
//        } catch (Exception ex) {
//            assertTrue(ex instanceof InvalidEntryException);
//        }
//    }


    /**
     * @see ItemService#getRecentlyAddedItems()
     */
    @Test(expected = ServiceException.class)
    public void getRecentlyAddedItems_getFail() {
        // Create test limit
        int testLimit = 20;

        // Setup mock
        doThrow(DAOException.class).when(itemRepoMock).findTop20By();

        // Test method
        itemService.getRecentlyAddedItems();
    }


    /**
     * @see ItemService#getRecentlyAddedItems
     */
    @Test
    public void getRecentlyAddedItems_getSuccess() {

        // Create test limit and itemList
        testItem = createTestItem();
        List<Item> itemList = new ArrayList<>();
        itemList.add(testItem);

        // Setup mock
        doReturn(itemList).when(itemRepoMock).findTop20By();

        // Test method
        itemService.getRecentlyAddedItems();

        // Check input argument
        assertEquals(itemList.get(0), testItem);
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
        doThrow(DAOException.class).when(itemRepoMock).getAllByTitle(anyString());

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
        doReturn(itemList).when(itemRepoMock).getAllByTitle(argument.capture());

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
        doThrow(DAOException.class).when(itemRepoMock).getAllByItemType(any(ItemType.class));

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
        doReturn(itemList).when(itemRepoMock).getAllByItemType(argument.capture());

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
        doReturn(null).when(itemRepoMock).getAllByPriceBetween(argument.capture(),argument.capture());

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
        doReturn(itemList).when(itemRepoMock).getAllByPriceBetween(argument.capture(),argument.capture());

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


        // Setup mock
        doReturn(null).when(itemRepoMock).getAllByArtistId(argument.capture());

        // Test method
        assertEquals(null,itemService.getArtistItems(testArtistId));

        // Check input argument
        assertEquals(testArtistId,argument.getAllValues().get(0));
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

        List<Item> itemList = new ArrayList<>();

        // Setup mock
        doReturn(itemList).when(itemRepoMock).getAllByArtistId(argument.capture());

        // Test method
        assertEquals(itemList,itemService.getArtistItems(testArtistId));

        // Check input argument
        assertEquals(testArtistId,argument.getAllValues().get(0));

    }
    /**
     * @see ItemService#updateItem(Item)
     */
    @Test
    public void updateItem_idIsNullOrNegative() {
        // Create test id and testItem
        Long id = null;
        testArtist = createTestArtist();
        testItem = createTestItem();
        testItem.setArtist(testArtist);


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
        testArtist = createTestArtist();
        testItem = createTestItem();
        testItem.setId(55L);
        testItem.setArtist(testArtist);

        // Setup mock
        doThrow(DAOException.class).when(itemRepoMock).saveAndFlush(argument.capture());

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
        testArtist = createTestArtist();
        testItem = createTestItem();
        testItem.setArtist(testArtist);
        testItem.setId(444L);

        // Setup mock
        doReturn(true).when(itemRepoMock).saveAndFlush(argument.capture());

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
        testArtist = createTestArtist();
        testItem.setArtist(testArtist);

        // Setup mock
        doThrow(DAOException.class).when(itemRepoMock).delete(any(Item.class));

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
        testItem = createTestItem();
        testArtist = createTestArtist();
        testItem.setId(id);
        testItem.setArtist(testArtist);

        // Setup mock
        doNothing().when(itemRepoMock).delete(argument.capture());

        // Test method
        itemService.deleteItem(testItem);

        // Check input argument
        assertEquals(testItem,argument.getValue());
    }


    /**
     * @see ItemService#itemBuying(Item, AbstractUser)
     */
    @Test
    public void itemBuying_idIsNotValid(){
        // Create test item and test buyerId with null value
        testArtist = createTestArtist();
        testItem = createTestItem();

        // Test method
        try{
            itemService.itemBuying(testItem, testArtist);
            fail();
        }catch (Exception ex){
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change id value to negative
        testArtist.setFirstName(null);

        // Test method
        try {
            itemService.itemBuying(testItem,testArtist);
            fail();
        }catch (Exception ex){
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ItemService#itemBuying(Item, AbstractUser)
     */
    @Test
    public void itemBuying_itemIsNotValid(){
        // Create test buyerId and test item with null value
        testArtist = createTestArtist();
        testItem = null;

        // Test method
        try{
            itemService.itemBuying(testItem,testArtist);
            fail();
        }catch (Exception ex){
            assertTrue(ex instanceof InvalidEntryException || ex instanceof NotEnoughMoneyException);
        }

        // Change buyerId value to negative
        testItem = createTestItem();
        testItem.setTitle(null);

        // Test method
        try {
            itemService.itemBuying(testItem,testArtist);
            fail();
        }catch (Exception ex){
            assertTrue(ex instanceof InvalidEntryException);
        }
    }

    /**
     * @see ItemService#itemBuying(Item, AbstractUser)
     */
    @Test(expected = ServiceException.class)
    public void itemBuying_getShoppingCardFailed() {
        testArtist = createTestArtist();
        testItem = createTestItem();
        testItem.setArtist(testArtist);
        testItem.setId(777L);

        doThrow(DAOException.class).when(shoppingCardServiceMock).getShoppingCard(anyLong());

        // Test method
        itemService.itemBuying(testItem, testArtist);
    }

    /**
     * @see ItemService#itemBuying(Item, AbstractUser)
     */
    @Test(expected = NotEnoughMoneyException.class)
    public void itemBuying_notEnoughMoneyOnAccount(){
        // Create test item, shoppingCard and test buyerId
        testArtist = createTestArtist();
        shoppingCard = createTestShoppingCard();
        shoppingCard.setBalance(0);
        shoppingCard.setAbstractUser(testArtist);
        testItem = createTestItem();
        testItem.setArtist(testArtist);
        testItem.setId(777L);

        // Setup mock
        doReturn(shoppingCard).when(shoppingCardServiceMock).getShoppingCard(anyLong());

        // Test method
        itemService.itemBuying(testItem, testArtist);
    }


    /**
     * @see ItemService#itemBuying(Item, AbstractUser)
     */
    @Test(expected = ServiceException.class)
    public void itemBuying_withdrawFail(){
        // Create test shoppingCard and test item
        Long id = 5L;
        shoppingCard = createTestShoppingCard();
        shoppingCard.setBalance(1000);
        testArtist = createTestArtist();
        testArtist.setId(id);
        testItem = createTestItem();
        testItem.setId(id);
        testItem.setArtist(testArtist);
        testItem.setPrice(100.0);

        // Setup mock
        doReturn(shoppingCard).when(shoppingCardServiceMock).getShoppingCard(anyLong());
        doThrow(DAOException.class).when(shoppingCardServiceMock).updateShoppingCard(any(ShoppingCard.class));

        // Test method
        itemService.itemBuying(testItem, testArtist);
    }


    /**
     * @see ItemService#itemBuying(Item, AbstractUser)
     */
    @Test
    public void itemBuying_withdrawSuccess(){
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);

        // Create test shoppingCard and test item
        Long id = 5L;
        testArtist = createTestArtist();
        testArtist.setId(id);
        shoppingCard = createTestShoppingCard();
        shoppingCard.setBalance(100000);
        shoppingCard.setAbstractUser(testArtist);
        testArtist.setShoppingCard(shoppingCard);
        testItem = createTestItem();
        testItem.setId(777L);
        testItem.setArtist(testArtist);
        testItem.setPrice(100.0);

        // SetUp mocks
        doReturn(shoppingCard).when(shoppingCardServiceMock).getShoppingCard(argument.capture());

        // Test method
        itemService.itemBuying(testItem,testArtist);

        verify(shoppingCardServiceMock).updateShoppingCard(shoppingCard);

        assertEquals(id, argument.getValue());
    }

    /**
     * @see ItemService#itemBuying(Item, AbstractUser)
     */
    @Test(expected = ServiceException.class)
    public void itemBuying_addPurchaseHistoryFail(){
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);

        // Create test item and test shopping card
        Long buyerId = 5L;
        testItem = createTestItem();
        testArtist = createTestArtist();
        shoppingCard = createTestShoppingCard();
        testItem.setPrice(100.0);
        testItem.setArtist(testArtist);
        shoppingCard.setBalance(100000.0);
        shoppingCard.setAbstractUser(testArtist);

        // Setup mocks
        doReturn(shoppingCard).when(shoppingCardServiceMock).getShoppingCard(argument.capture());
        doNothing().when(shoppingCardServiceMock).updateShoppingCard(any(ShoppingCard.class));
        doThrow(DAOException.class).when(purchaseHistoryServiceMock).addPurchase(any(PurchaseHistory.class));

        // Test method
         itemService.itemBuying(testItem, testArtist);

        assertEquals(buyerId, argument.getValue());
    }

    /**
     * @see ItemService#itemBuying(Item, AbstractUser)
     */
    @Test
    public void itemBuying_addPurchaseHistorySuccess() {
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<ShoppingCard> argument1 = ArgumentCaptor.forClass(ShoppingCard.class);
        ArgumentCaptor<PurchaseHistory> argument2 = ArgumentCaptor.forClass(PurchaseHistory.class);

        // Create testItem, testShoppingCard and purchaseHistory
        Long buyerId = 5L;
        testItem = createTestItem();
        testArtist = createTestArtist();
        testArtist.setId(buyerId);
        shoppingCard = createTestShoppingCard();
        shoppingCard.setId(5L);
        testItem.setPrice(100.0);
        testItem.setArtist(testArtist);
        shoppingCard.setBalance(10000.0);
        purchaseHistory = createTestPurchaseHistory();
        purchaseHistory.setItem(testItem);
        purchaseHistory.setAbsUser(testArtist);

        // Setup mock
        doReturn(shoppingCard).when(shoppingCardServiceMock).getShoppingCard(argument.capture());
        doNothing().when(shoppingCardServiceMock).updateShoppingCard(argument1.capture());
        doNothing().when(purchaseHistoryServiceMock).addPurchase(argument2.capture());
        doReturn(true).when(itemRepoMock).saveAndFlush(any(Item.class));

        // Test method
        itemService.itemBuying(testItem, testArtist);

        assertEquals(buyerId, argument.getAllValues().get(0));
        assertEquals(shoppingCard.getId(), argument.getAllValues().get(0));
        assertEqualShoppingCards(shoppingCard, argument1.getValue());
        assertEqualPurchaseHistory(purchaseHistory, argument2.getValue());
    }

    /**
     * @see ItemServiceImpl#itemBuying(Item, AbstractUser)
     */
    @Test(expected = ServiceException.class)
    public void itemBuying_updateItemFailed() {
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<ShoppingCard> argument1 = ArgumentCaptor.forClass(ShoppingCard.class);
        ArgumentCaptor<PurchaseHistory> argument2 = ArgumentCaptor.forClass(PurchaseHistory.class);

        // Create testItem, testShoppingCard and purchaseHistory
        Long buyerId = 5L;
        testItem = createTestItem();
        testArtist = createTestArtist();
        shoppingCard = createTestShoppingCard();
        testItem.setPrice(1000.0);
        testItem.setArtist(testArtist);
        shoppingCard.setBalance(10000.0);
        purchaseHistory = createTestPurchaseHistory();
        purchaseHistory.setItem(testItem);
        purchaseHistory.setAbsUser(testArtist);

        // Setup mock
        doReturn(shoppingCard).when(shoppingCardServiceMock).getShoppingCard(argument.capture());
        doNothing().when(shoppingCardServiceMock).updateShoppingCard(argument1.capture());
        doNothing().when(purchaseHistoryServiceMock).addPurchase(argument2.capture());
        doThrow(DAOException.class).when(itemRepoMock).saveAndFlush(any(Item.class));

        // Test method
        itemService.itemBuying(testItem, testArtist);

        assertEquals(buyerId, argument.getValue());
        assertEqualShoppingCards(shoppingCard, argument1.getValue());
        assertEqualPurchaseHistory(purchaseHistory, argument2.getValue());
    }

    /**
     * @see ItemServiceImpl#itemBuying(Item, AbstractUser)
     */
    @Test
    public void itemBuying_updateItemSuccess() {
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<ShoppingCard> argument1 = ArgumentCaptor.forClass(ShoppingCard.class);
        ArgumentCaptor<PurchaseHistory> argument2 = ArgumentCaptor.forClass(PurchaseHistory.class);
        ArgumentCaptor<Item> argument3 = ArgumentCaptor.forClass(Item.class);

        // Create testItem, testShoppingCard and purchaseHistory
        Long buyerId = 5L;
        Long artist_id = 5L;
        testItem = createTestItem();
        testArtist = createTestArtist();
        testArtist.setId(artist_id);
        shoppingCard = createTestShoppingCard();
        testItem.setPrice(1000.0);
        testItem.setArtist(testArtist);
        shoppingCard.setBalance(10000.0);
        purchaseHistory = createTestPurchaseHistory();
        purchaseHistory.setItem(testItem);
        purchaseHistory.setAbsUser(testArtist);

        // Setup mock
        doReturn(shoppingCard).when(shoppingCardServiceMock).getShoppingCard(argument.capture());
        doNothing().when(shoppingCardServiceMock).updateShoppingCard(argument1.capture());
        doNothing().when(purchaseHistoryServiceMock).addPurchase(argument2.capture());
        doReturn(true).when(itemRepoMock).saveAndFlush(argument3.capture());

        // Test method
        itemService.itemBuying(testItem, testArtist);

        assertEquals(buyerId, argument.getAllValues().get(0));;
        assertEqualShoppingCards(shoppingCard, argument1.getValue());
        assertEqualPurchaseHistory(purchaseHistory, argument2.getValue());
        assertEquals(testItem.getArtist().getId(), argument.getAllValues().get(0));
        assertEqualItems(testItem, argument3.getValue());
        System.out.println(argument.getAllValues());
    }

    // endregion
}
