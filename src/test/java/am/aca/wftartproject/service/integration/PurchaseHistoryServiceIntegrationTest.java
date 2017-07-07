//package am.aca.wftartproject.service.integration;
//
//import am.aca.wftartproject.exception.service.InvalidEntryException;
//import am.aca.wftartproject.entity.Artist;
//import am.aca.wftartproject.entity.Item;
//import am.aca.wftartproject.entity.PurchaseHistory;
//import am.aca.wftartproject.entity.User;
//import am.aca.wftartproject.service.BaseIntegrationTest;
//import am.aca.wftartproject.service.impl.*;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static am.aca.wftartproject.util.AssertTemplates.assertEqualPurchaseHistory;
//import static am.aca.wftartproject.util.TestObjectTemplate.*;
//
///**
// * Created by ASUS on 30-Jun-17
// */
//public class PurchaseHistoryServiceIntegrationTest extends BaseIntegrationTest {
//
//    private User testUser;
//    private Item testItem;
//    private PurchaseHistory testPurchaseHistory;
//
//    @Autowired
//    private UserServiceImpl userService;
//
//    @Autowired
//    private ArtistServiceImpl artistService;
//
//    @Autowired
//    private ItemServiceImpl itemService;
//
//    @Autowired
//    private PurchaseHistoryServiceImpl purchaseHistoryService;
//
//    @Autowired
//    private ShoppingCardServiceImpl shoppingCardService;
//
//    /**
//     * Creates all objects need for tests
//     */
//    @Before
//    public void setUp() {
//        Artist testArtist = createTestArtist();
//        testItem = createTestItem();
//        testUser = createTestUser();
//        testPurchaseHistory = new PurchaseHistory();
//        userService.addUser(testUser);
//        artistService.addArtist(testArtist);
//        itemService.addItem(testArtist.getId(), testItem);
//        testPurchaseHistory.setPurchaseDate(LocalDateTime.now());
//    }
//
//    /**
//     * Deletes all objects created during the tests
//     */
//    @After
//    public void tearDown() {
//        if (testPurchaseHistory.getUserId() != null)
//            purchaseHistoryService.deletePurchase(testUser.getId(), testItem.getId());
//
//        if (testItem.getId() != null)
//            itemService.deleteItem(testItem.getId());
//
//        if (testUser.getShoppingCard() != null) {
//            shoppingCardService.deleteShoppingCardByBuyerId(testUser.getId());
//        }
//        if (testUser.getId() != null)
//            userService.deleteUser(testUser.getId());
//
//        testPurchaseHistory = null;
//        testItem = null;
//        testUser = null;
//    }
//
//    // region<TEST CASE>
//
//    /**
//     * @see PurchaseHistoryServiceImpl#addPurchase(am.aca.wftartproject.entity.PurchaseHistory)
//     */
//    @Test
//    public void addPurchase_Success() {
//        // Set userId and ItemId
//        testPurchaseHistory.setUserId(testUser.getId());
//        testPurchaseHistory.setItemId(testItem.getId());
//
//        // Test method
//        purchaseHistoryService.addPurchase(testPurchaseHistory);
//
//        // Check sameness testPurchaseHistory and addedPurchaseHistory objects
//        PurchaseHistory addedPurchaseHistory = purchaseHistoryService.getPurchase(testUser.getId(), testItem.getId());
//        assertEqualPurchaseHistory(addedPurchaseHistory, testPurchaseHistory);
//    }
//
//    /**
//     * @see PurchaseHistoryServiceImpl#addPurchase(am.aca.wftartproject.entity.PurchaseHistory)
//     */
//    @Test(expected = InvalidEntryException.class)
//    public void addPurchase_Failure() {
//        // Set userId and ItemId
//        testPurchaseHistory.setUserId(null);
//        testPurchaseHistory.setItemId(testItem.getId());
//
//        // Test method
//        purchaseHistoryService.addPurchase(testPurchaseHistory);
//    }
//
//    /**
//     * @see PurchaseHistoryServiceImpl#getPurchase(java.lang.Long, java.lang.Long)
//     */
//    @Test
//    public void getPurchase_Success() {
//        // Set userId and ItemId
//        testPurchaseHistory.setUserId(testUser.getId());
//        testPurchaseHistory.setItemId(testItem.getId());
//
//        // Add purchase item into DB
//        purchaseHistoryService.addPurchase(testPurchaseHistory);
//
//        // Test method
//        PurchaseHistory foundedPurchaseHistory =
//                purchaseHistoryService.getPurchase(testPurchaseHistory.getUserId(), testPurchaseHistory.getItemId());
//        assertEqualPurchaseHistory(foundedPurchaseHistory, testPurchaseHistory);
//    }
//
//    /**
//     * @see PurchaseHistoryServiceImpl#getPurchase(java.lang.Long, java.lang.Long)
//     */
//    @Test(expected = InvalidEntryException.class)
//    public void getPurchase_Failure() {
//        // Set userId and ItemId
//        testPurchaseHistory.setUserId(null);
//        testPurchaseHistory.setItemId(testItem.getId());
//
//        // Test method
//        purchaseHistoryService.getPurchase(testPurchaseHistory.getUserId(), testPurchaseHistory.getItemId());
//    }
//
//    /**
//     * @see PurchaseHistoryServiceImpl#getPurchase(java.lang.Long)
//     */
//    @Test
//    public void getPurchase_NotEmptyList() {
//        // Set userId and ItemId
//        testPurchaseHistory.setUserId(testUser.getId());
//        testPurchaseHistory.setItemId(testItem.getId());
//
//        // Add purchase history into Db
//        purchaseHistoryService.addPurchase(testPurchaseHistory);
//
//        // Test method
//        List<PurchaseHistory> purchaseHistories =
//                purchaseHistoryService.getPurchase(testPurchaseHistory.getUserId());
//        assertEqualPurchaseHistory(purchaseHistories.get(0), testPurchaseHistory);
//
//    }
//
//    /**
//     * @see PurchaseHistoryServiceImpl#getPurchase(java.lang.Long)
//     */
//    @Test(expected = InvalidEntryException.class)
//    public void getPurchase_EmptyList() {
//        // Set userId and ItemId
//        testPurchaseHistory.setUserId(null);
//
//        // Test method
//        purchaseHistoryService.getPurchase(testPurchaseHistory.getUserId());
//    }
//
//    /**
//     * @see PurchaseHistoryServiceImpl#deletePurchase(java.lang.Long, java.lang.Long)
//     */
//    @Test
//    public void deletePurchase_Success() {
//        // Set userId and ItemId
//        testPurchaseHistory.setUserId(testUser.getId());
//        testPurchaseHistory.setItemId(testItem.getId());
//
//        // Add purchase history into DB
//        purchaseHistoryService.addPurchase(testPurchaseHistory);
//
//        // Test method
//        purchaseHistoryService.deletePurchase(testPurchaseHistory.getUserId(), testPurchaseHistory.getItemId());
//        testPurchaseHistory.setUserId(null);
//    }
//
//    /**
//     * @see PurchaseHistoryServiceImpl#deletePurchase(java.lang.Long, java.lang.Long)
//     */
//    @Test(expected = InvalidEntryException.class)
//    public void deletePurchase_Failure() {
//        // Set userId and ItemId
//        testPurchaseHistory.setUserId(null);
//        testPurchaseHistory.setItemId(testItem.getId());
//
//        // Test method
//        purchaseHistoryService.deletePurchase(testPurchaseHistory.getUserId(), testPurchaseHistory.getItemId());
//    }
//
//    // endregion
//}
