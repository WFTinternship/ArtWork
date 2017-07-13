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
        testItem.setArtist(testArtist);
        itemService.addItem(testItem);
        testPurchaseHistory.setAbsUser(testUser);
        testPurchaseHistory.setItem(testItem);

    }

    /**
     * Deletes all objects created during the tests
     */
    @After
    public void tearDown() {
        if (testPurchaseHistory.getAbsUser() != null)
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
        testPurchaseHistory.setAbsUser(testUser);
        testPurchaseHistory.setItem(testItem);

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
        testPurchaseHistory.setAbsUser(null);
        testPurchaseHistory.setItem(testItem);

        // Test method
        purchaseHistoryService.addPurchase(testPurchaseHistory);
    }

    /**
     * @see PurchaseHistoryServiceImpl#getPurchase(Long)
     */
    @Test
    public void getPurchase_Success() {
        // Set userId and ItemId
        testPurchaseHistory.setAbsUser(testUser);
        testPurchaseHistory.setItem(testItem);

        // Add purchase item into DB
        purchaseHistoryService.addPurchase(testPurchaseHistory);

        // Test method
        PurchaseHistory foundedPurchaseHistory =
                purchaseHistoryService.getPurchase(testPurchaseHistory.getId());
        assertEqualPurchaseHistory(foundedPurchaseHistory, testPurchaseHistory);
    }

    /**
     * @see PurchaseHistoryServiceImpl#getPurchase(Long)
     */
    @Test(expected = InvalidEntryException.class)
    public void getPurchase_Failure() {
        // Set userId and ItemId
        purchaseHistoryService.addPurchase(testPurchaseHistory);
        try{
            // Test method
            purchaseHistoryService.getPurchase(testPurchaseHistory.getId());
        }
        catch (Exception e){
            throw new InvalidEntryException(e.getMessage());
        }

    }

    /**
     * @see PurchaseHistoryServiceImpl#getPurchase(java.lang.Long)
     */
    @Test
    public void getPurchase_NotEmptyList() {
        // Set userId and ItemId
        testPurchaseHistory.setAbsUser(testUser);
        testPurchaseHistory.setItem(testItem);

        // Add purchase history into Db
        purchaseHistoryService.addPurchase(testPurchaseHistory);

        // Test method
        List<PurchaseHistory> purchaseHistories =
                purchaseHistoryService.getPurchaseList(testPurchaseHistory.getAbsUser().getId());
        assertEqualPurchaseHistory(purchaseHistories.get(0), testPurchaseHistory);

    }

    /**
     * @see PurchaseHistoryServiceImpl#getPurchase(java.lang.Long)
     */
    @Test(expected = InvalidEntryException.class)
    public void getPurchase_EmptyList() {
        // Set userId and ItemId
        testPurchaseHistory.setAbsUser(null);

        // Test method
        purchaseHistoryService.getPurchaseList(testPurchaseHistory.getAbsUser().getId());
    }

    /**
     * @see PurchaseHistoryServiceImpl#deletePurchase(PurchaseHistory)
     */
    @Test
    public void deletePurchase_Success() {
        // Set userId and ItemId
        testPurchaseHistory.setAbsUser(testUser);
        testPurchaseHistory.setItem(testItem);

        // Add purchase history into DB
        purchaseHistoryService.addPurchase(testPurchaseHistory);

        // Test method
        purchaseHistoryService.deletePurchase(testPurchaseHistory);
        testPurchaseHistory.setAbsUser(null);
    }

    /**
     * @see PurchaseHistoryServiceImpl#deletePurchase(PurchaseHistory)
     */
    @Test(expected = InvalidEntryException.class)
    public void deletePurchase_Failure() {
        // Set userId and ItemId
        testPurchaseHistory.setAbsUser(null);
        testPurchaseHistory.setItem(testItem);

        // Test method
        purchaseHistoryService.deletePurchase(testPurchaseHistory);
    }

    // endregion
}
