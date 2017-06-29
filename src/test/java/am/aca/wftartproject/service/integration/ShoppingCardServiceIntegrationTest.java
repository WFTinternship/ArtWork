package am.aca.wftartproject.service.integration;

import am.aca.wftartproject.BaseIntegrationTest;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.model.ShoppingCard;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.ShoppingCardService;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.service.impl.ShoppingCardServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualShoppingCards;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestShoppingCard;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestUser;
import static junit.framework.TestCase.assertNotNull;

/**
 * @author surik
 */
@Transactional
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
        testUser = createTestUser();
        userService.addUser(testUser);
        testShoppingCard = createTestShoppingCard();
    }

    /**
     * Deletes all objects created during the tests
     */
    @After
    public void tearDown() {
        // delete inserted test users,shoppingCards from db
        if (testShoppingCard.getId() != null)
            shoppingCardService.deleteShoppingCard(testShoppingCard.getId());
        if (testUser.getId() != null)
            userService.deleteUser(testUser.getId());

        // set temp instance refs to null
        testShoppingCard = null;
        testUser = null;

    }

    // region<TEST CASE>

    /**
     * @see ShoppingCardServiceImpl#addShoppingCard(java.lang.Long, am.aca.wftartproject.model.ShoppingCard)
     */
    @Test
    public void addShoppingCard_Success() {
        // Check testUser and its id for null
        assertNotNull(testUser);
        assertNotNull(testUser.getId());

        // Test method
        shoppingCardService.addShoppingCard(testUser.getId(), testShoppingCard);

        // Get added shoppingCard and check its sameness with testShoppingCard
        ShoppingCard addedCard = shoppingCardService.getShoppingCard(testShoppingCard.getId());

        assertEqualShoppingCards(addedCard, testShoppingCard);
    }

    /**
     * @see ShoppingCardServiceImpl#addShoppingCard(java.lang.Long, am.aca.wftartproject.model.ShoppingCard)
     */
    @Test(expected = InvalidEntryException.class)
    public void addShoppingCard_Failure() {
        // Test method
        shoppingCardService.addShoppingCard(testUser.getId(), null);
    }

    /**
     * @see ShoppingCardServiceImpl#getShoppingCard(java.lang.Long)
     */
    @Test
    public void getShoppingCard_Success() {
        // Add shoppingCard into DB
        shoppingCardService.addShoppingCard(testUser.getId(), testShoppingCard);

        // Test method
        ShoppingCard foundedShoppingCard = shoppingCardService.getShoppingCard(testShoppingCard.getId());
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
     * @see ShoppingCardServiceImpl#updateShoppingCard(java.lang.Long, am.aca.wftartproject.model.ShoppingCard)
     */
    @Test
    public void updateShoppingCard_Success() {
        // Add shoppingCard into DB
        shoppingCardService.addShoppingCard(testUser.getId(), testShoppingCard);

        testShoppingCard.setBalance(1001.0);

        // Test method
        shoppingCardService.updateShoppingCard(testShoppingCard.getId(), testShoppingCard);
    }

    /**
     * @see ShoppingCardServiceImpl#updateShoppingCard(java.lang.Long, am.aca.wftartproject.model.ShoppingCard)
     */
    @Test(expected = InvalidEntryException.class)
    public void updateShoppingCard_Failure() {
        // Test method
        shoppingCardService.updateShoppingCard(-5L, testShoppingCard);
    }

    /**
     * @see ShoppingCardServiceImpl#deleteShoppingCard(java.lang.Long)
     */
    @Test
    public void deleteShoppingCard_Success() {
        // Add shoppingCard into DB
        shoppingCardService.addShoppingCard(testUser.getId(), testShoppingCard);

        // Test method
        shoppingCardService.deleteShoppingCard(testShoppingCard.getId());
        testShoppingCard.setId(null);
    }

    /**
     * @see ShoppingCardServiceImpl#deleteShoppingCard(java.lang.Long)
     */
    @Test(expected = InvalidEntryException.class)
    public void deleteShoppingCard_Failure() {
        // Test method
        shoppingCardService.deleteShoppingCard(-5L);
    }

    // endregion
}
