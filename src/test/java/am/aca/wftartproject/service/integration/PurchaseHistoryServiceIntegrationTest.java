package am.aca.wftartproject.service.integration;

import am.aca.wftartproject.entity.*;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.service.*;
import am.aca.wftartproject.service.impl.*;
import am.aca.wftartproject.util.TestObjectTemplate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualPurchaseHistory;
import static am.aca.wftartproject.util.TestObjectTemplate.*;

/**
 * Created by ASUS on 30-Jun-17
 */
public class PurchaseHistoryServiceIntegrationTest extends BaseIntegrationTest {
    private Artist testArtist;
    private User testUser;
    private Item testItem;
    private PurchaseHistory testPurchaseHistory;
    private ShoppingCard shoppingCard ;

    @Autowired
    private UserService userService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private PurchaseHistoryService purchaseHistoryService;

    @Autowired
    private ShoppingCardService shoppingCardService;

    /**
     * Creates all objects need for tests
     */
    @Before
    public void setUp() {
        testArtist =  createTestArtist();
        shoppingCard = createTestShoppingCard();
        testItem = createTestItem();
        testUser = createTestUser();
        testPurchaseHistory = TestObjectTemplate.createTestPurchaseHistory();
        userService.addUser(testUser);
        artistService.addArtist(testArtist);
        testItem.setArtist_id(testArtist.getId());
        itemService.addItem(testItem);
        testPurchaseHistory.setUserId(testUser.getId());
        testPurchaseHistory.setItem(testItem);

    }

    /**
     * Deletes all objects created during the tests
     */
    @After
    public void tearDown() {
        if (testPurchaseHistory.getUserId() != null)
            purchaseHistoryService.deletePurchase(testPurchaseHistory);

        if (testItem.getId() != null)
            itemService.deleteItem(testItem);

        if (testUser.getId() != null)
            userService.deleteUser(testUser);

        testPurchaseHistory = null;
        testItem = null;
        testUser = null;
    }

    // region<TEST CASE>

    /**
     * @see PurchaseHistoryServiceImpl#addPurchase(am.aca.wftartproject.entity.PurchaseHistory)
     */
    @Test
    public void addPurchase_Success() {
        // Set userId and ItemId
        testPurchaseHistory.setUserId(testUser.getId());
        testPurchaseHistory.setItemId(testItem.getId());

        // Test method
        purchaseHistoryService.addPurchase(testPurchaseHistory);

        // Check sameness testPurchaseHistory and addedPurchaseHistory objects
        PurchaseHistory addedPurchaseHistory = purchaseHistoryService.getPurchase(testItem.getId());
        assertEqualPurchaseHistory(addedPurchaseHistory, testPurchaseHistory);
    }

    /**
     * @see PurchaseHistoryServiceImpl#addPurchase(am.aca.wftartproject.entity.PurchaseHistory)
     */
    @Test(expected = InvalidEntryException.class)
    public void addPurchase_Failure() {
        // Set userId and ItemId
        testPurchaseHistory.setUserId(null);
        testPurchaseHistory.setItemId(testItem.getId());

        // Test method
        purchaseHistoryService.addPurchase(testPurchaseHistory);
    }

    /**
     * @see PurchaseHistoryServiceImpl#getPurchase(Long)
     */
    @Test
    public void getPurchase_Success() {
        // Set userId and ItemId
        testPurchaseHistory.setUserId(testUser.getId());
        testPurchaseHistory.setItemId(testItem.getId());

        // Add purchase item into DB
        purchaseHistoryService.addPurchase(testPurchaseHistory);

        // Test method
        PurchaseHistory foundedPurchaseHistory =
                purchaseHistoryService.getPurchase(testPurchaseHistory.getItemId());
        assertEqualPurchaseHistory(foundedPurchaseHistory, testPurchaseHistory);
    }

    /**
     * @see PurchaseHistoryServiceImpl#getPurchase(Long)
     */
    @Test(expected = InvalidEntryException.class)
    public void getPurchase_Failure() {
        long temp = testPurchaseHistory.getItemId();
        // Set userId and ItemId
        purchaseHistoryService.addPurchase(testPurchaseHistory);
        testPurchaseHistory.setItemId(null);
        try{
            // Test method
            purchaseHistoryService.getPurchase(testPurchaseHistory.getItemId());
        }
        catch (InvalidEntryException e){
            testPurchaseHistory.setItemId(temp);
            throw new InvalidEntryException(e.getMessage());
        }

    }

    /**
     * @see PurchaseHistoryServiceImpl#getPurchase(java.lang.Long)
     */
    @Test
    public void getPurchase_NotEmptyList() {
        // Set userId and ItemId
        testPurchaseHistory.setUserId(testUser.getId());
        testPurchaseHistory.setItemId(testItem.getId());

        // Add purchase history into Db
        purchaseHistoryService.addPurchase(testPurchaseHistory);

        // Test method
        List<PurchaseHistory> purchaseHistories =
                purchaseHistoryService.getPurchaseList(testPurchaseHistory.getUserId());
        assertEqualPurchaseHistory(purchaseHistories.get(0), testPurchaseHistory);

    }

    /**
     * @see PurchaseHistoryServiceImpl#getPurchase(java.lang.Long)
     */
    @Test(expected = InvalidEntryException.class)
    public void getPurchase_EmptyList() {
        // Set userId and ItemId
        testPurchaseHistory.setUserId(null);

        // Test method
        purchaseHistoryService.getPurchaseList(testPurchaseHistory.getUserId());
    }

    /**
     * @see PurchaseHistoryServiceImpl#deletePurchase(PurchaseHistory)
     */
    @Test
    public void deletePurchase_Success() {
        // Set userId and ItemId
        testPurchaseHistory.setUserId(testUser.getId());
        testPurchaseHistory.setItemId(testItem.getId());

        // Add purchase history into DB
        purchaseHistoryService.addPurchase(testPurchaseHistory);

        // Test method
        purchaseHistoryService.deletePurchase(testPurchaseHistory);
        testPurchaseHistory.setUserId(null);
    }

    /**
     * @see PurchaseHistoryServiceImpl#deletePurchase(PurchaseHistory)
     */
    @Test(expected = InvalidEntryException.class)
    public void deletePurchase_Failure() {
        // Set userId and ItemId
        testPurchaseHistory.setUserId(null);
        testPurchaseHistory.setItemId(testItem.getId());

        // Test method
        purchaseHistoryService.deletePurchase(testPurchaseHistory);
    }

    // endregion
}
