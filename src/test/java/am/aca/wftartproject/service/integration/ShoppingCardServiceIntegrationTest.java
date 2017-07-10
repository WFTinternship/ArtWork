package am.aca.wftartproject.service.integration;

import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.entity.ShoppingCard;
import am.aca.wftartproject.entity.User;
import am.aca.wftartproject.service.BaseIntegrationTest;
import am.aca.wftartproject.service.ShoppingCardService;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.service.impl.ShoppingCardServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualShoppingCards;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestShoppingCard;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestUser;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by ASUS on 30-Jun-17
 */
public class ShoppingCardServiceIntegrationTest extends BaseIntegrationTest {

    private User testUser;
    private ShoppingCard testShoppingCard;

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCardService shoppingCardService;

    /**
     * Creates testUser and testShoppingCard for tests
     */
    @Before
    public void setUp() {
        testShoppingCard = createTestShoppingCard();
        testUser = createTestUser();
        userService.addUser(testUser);
        testShoppingCard.setBuyer_id(testUser.getId());
    }

    /**
     * Deletes all objects created during the tests
     */
    @After
    public void tearDown() {
        // delete inserted test users,shoppingCards from db
        if (testShoppingCard.getId() != null)
            shoppingCardService.deleteShoppingCard(testShoppingCard);

        if (testUser.getId() != null)
            userService.deleteUser(testUser);

        // set temp instance refs to null
        testShoppingCard = null;
        testUser = null;

    }

    // region<TEST CASE>

    /**
     * @see ShoppingCardServiceImpl#addShoppingCard(ShoppingCard)
     */
    @Test
    public void addShoppingCard_Success() {
        // Check testUser and its id for null
        assertNotNull(testUser);
        assertNotNull(testUser.getId());

        // Test method
        shoppingCardService.addShoppingCard(testShoppingCard);

        // Get added shoppingCard and check its sameness with testShoppingCard
        ShoppingCard addedCard = shoppingCardService.getShoppingCard(testShoppingCard.getBuyer_id());

        assertEqualShoppingCards(addedCard, testShoppingCard);
    }

    /**
     * @see ShoppingCardServiceImpl#addShoppingCard(ShoppingCard)
     */
    @Test(expected = InvalidEntryException.class)
    public void addShoppingCard_Failure() {
        // Test method
        shoppingCardService.addShoppingCard(null);
    }

    /**
     * @see ShoppingCardServiceImpl#getShoppingCard(java.lang.Long)
     */
    @Test
    public void getShoppingCard_Success() {
        // Add shoppingCard into DB
        shoppingCardService.addShoppingCard(testShoppingCard);

        // Test method
        ShoppingCard foundedShoppingCard = shoppingCardService.getShoppingCard(testShoppingCard.getBuyer_id());
        assertEqualShoppingCards(foundedShoppingCard, testShoppingCard);
    }

    /**
     * @see ShoppingCardServiceImpl#getShoppingCard(java.lang.Long)
     */
    @Test(expected = InvalidEntryException.class)
    public void getShoppingCard_Failure() {
        // Test method
        shoppingCardService.getShoppingCard(null);
    }

    /**
     * @see ShoppingCardServiceImpl#getShoppingCard(Long) (Long)
     */
    @Test
    public void getShoppingCardByBuyerId_Success() {
//TODO
    }

    /**
     * @see ShoppingCardServiceImpl#getShoppingCard(Long)
     */
    @Test
    public void getShoppingCardByBuyerId_Failure() {
//TODO
    }

    /**
     * @see ShoppingCardServiceImpl#updateShoppingCard(ShoppingCard)
     */
    @Test
    public void updateShoppingCard_Success() {
        // Add shoppingCard into DB
        shoppingCardService.addShoppingCard(testShoppingCard);

        testShoppingCard.setBalance(1001.0);

        // Test method
        shoppingCardService.updateShoppingCard(testShoppingCard);
    }

    /**
     * @see ShoppingCardServiceImpl#updateShoppingCard(ShoppingCard)
     */
    @Test(expected = InvalidEntryException.class)
    public void updateShoppingCard_Failure() {
        // Test method
        long temp = testShoppingCard.getBuyer_id();
        testShoppingCard.setBuyer_id(null);
        try{
            shoppingCardService.updateShoppingCard(testShoppingCard);
        }
        catch (InvalidEntryException e){
            testShoppingCard.setBuyer_id(temp);
            throw new InvalidEntryException(e.getMessage());
        }
    }

    /**
     * @see ShoppingCardServiceImpl#debitBalanceForItemBuying(Long, Double)
     */
    @Test
    public void debitBalanceForItemBuying_NotEmptyList() {}

    /**
     * @see ShoppingCardServiceImpl#debitBalanceForItemBuying(Long, Double)
     */
    @Test
    public void debitBalanceForItemBuying_EmptyList() {}


    /**
     * @see ShoppingCardServiceImpl#deleteShoppingCard(ShoppingCard)
     */
    @Test
    public void deleteShoppingCard_Success() {
        // Add shoppingCard into DB
        shoppingCardService.addShoppingCard(testShoppingCard);

        // Test method
        shoppingCardService.deleteShoppingCard(testShoppingCard);
        testShoppingCard.setId(null);
    }

    /**
     * @see ShoppingCardServiceImpl#deleteShoppingCard(ShoppingCard)
     */
    @Test(expected = InvalidEntryException.class)
    public void deleteShoppingCard_Failure() {
        // Test method
        long temp = testShoppingCard.getBuyer_id();
        testShoppingCard.setBuyer_id(-8L);
        try{
            shoppingCardService.deleteShoppingCard(testShoppingCard);
        }
        catch (InvalidEntryException e){
            testShoppingCard.setBuyer_id(temp);
            throw new InvalidEntryException(e.getMessage());
        }

    }

    /**
     * @see ShoppingCardServiceImpl#deleteShoppingCard(ShoppingCard) (Long)
     */
//    @Test
//    public void deleteShoppingCardByBuyerId_Success() {
//        //TODO
//    }
//
//    /**
//     * @see ShoppingCardServiceImpl#deleteShoppingCard(ShoppingCard) (Long)
//     */
//    @Test
//    public void deleteShoppingCardByBuyerId_Failure() {
//        //TODO
//    }


    // endregion
}

