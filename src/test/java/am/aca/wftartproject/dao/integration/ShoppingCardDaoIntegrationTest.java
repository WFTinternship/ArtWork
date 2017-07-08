package am.aca.wftartproject.dao.integration;

import am.aca.wftartproject.dao.ShoppingCardDao;
import am.aca.wftartproject.dao.UserDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.dao.NotEnoughMoneyException;
import am.aca.wftartproject.model.ShoppingCard;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.util.TestObjectTemplate;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.function.BooleanSupplier;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualShoppingCards;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestShoppingCard;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestUser;
import static junit.framework.TestCase.*;


/**
 * Created by Armen on 6/2/2017
 */

public class ShoppingCardDaoIntegrationTest extends BaseDAOIntegrationTest{

    private static Logger LOGGER = Logger.getLogger(ArtistDaoIntegrationTest.class);

    private User testUser;
    private ShoppingCard testShoppingCard;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ShoppingCardDao shoppingCardDao;

    public ShoppingCardDaoIntegrationTest() throws SQLException, ClassNotFoundException {
    }

    /**
     * Creates user and shoppingCard for tests
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        // Create test user and shoppingCard, add user into db
        testUser = createTestUser();
        userDao.addUser(testUser);
        testShoppingCard = createTestShoppingCard();

        // Print busy connections quantity
        if (jdbcTemplate.getDataSource() instanceof ComboPooledDataSource) {
            LOGGER.info(String.format("Number of busy connections Start: %s",
                    ((ComboPooledDataSource) jdbcTemplate.getDataSource()).getNumBusyConnections()));
        }
    }

    /**
     * Deletes all users and shoppingCards created during the tests
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @After
    public void tearDown() throws SQLException, ClassNotFoundException {
        // Delete inserted test users,shoppingCards from db
        if (testShoppingCard.getId() != null)
            shoppingCardDao.deleteShoppingCard(testShoppingCard.getId());
        if (testUser.getId() != null)
            userDao.deleteUser(testUser.getId());

        // Set temp instance refs to null
        testShoppingCard = null;
        testUser = null;

        // Print busy connections quantity
        if (jdbcTemplate.getDataSource() instanceof ComboPooledDataSource) {
            LOGGER.info(String.format("Number of busy connections End: %s",
                    ((ComboPooledDataSource) jdbcTemplate.getDataSource()).getNumBusyConnections()));
        }
    }

    // region<TEST CASE>

    /**
     * @see ShoppingCardDao#addShoppingCard(Long, ShoppingCard)
     */
    @Test
    public void addShoppingCard_Success() {
        // Check testUser id and testShoppingCard for null
        assertNotNull(testUser.getId());
        assertNotNull(testShoppingCard);

        // Test method
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);

        // Get shoppingCard from DB
        ShoppingCard added = shoppingCardDao.getShoppingCard(testShoppingCard.getId());

        // Check for equals
        assertEqualShoppingCards(added, testShoppingCard);
    }

    /**
     * @see ShoppingCardDao#addShoppingCard(Long, ShoppingCard)
     */
    @Test(expected = DAOException.class)
    public void addShoppingCard_Failure() {
        // Check testUser id and testShoppingCard for null
        assertNotNull(testUser.getId());
        assertNotNull(testShoppingCard);

        // Test method
        shoppingCardDao.addShoppingCard(124561515415313L, testShoppingCard);
    }

    /**
     * @see ShoppingCardDao#getShoppingCard(Long)
     */
    @Test
    public void getShoppingCard_Success() {
        // Check testUser id for null
        assertNotNull(testUser.getId());

        // Set testShoppingCard object into DB
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);

        // Check testShoppingCard object and testShoppingCard id for null
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());

        // Test method
        ShoppingCard shoppingCard = shoppingCardDao.getShoppingCard(testShoppingCard.getId());

        // Check for equals
        assertEqualShoppingCards(shoppingCard, testShoppingCard);
    }

    /**
     * @see ShoppingCardDao#getShoppingCard(Long)
     */
    @Test
    public void getShoppingCard_Failure() {
        // Check testUser id for null
        assertNotNull(testUser.getId());

        // Add shopping card into DB and check its id for null
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);
        assertNotNull(testShoppingCard.getId());

        // Test method
        shoppingCardDao.getShoppingCard(1515131651654151351L);
    }

    /**
     * @see ShoppingCardDao#getShoppingCardByBuyerId(Long)
     */
    @Test
    public void getShoppingCardByBuyerId_Success() {
        // Check testUser id for null
        assertNotNull(testUser.getId());

        // Add shopping card into DB
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);

        // Check testShoppingCard and its id for null
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());

        // Test method
        ShoppingCard foundedCard = shoppingCardDao.getShoppingCardByBuyerId(testShoppingCard.getBuyerId());

        // Check sameness
        assertEqualShoppingCards(foundedCard, testShoppingCard);
    }

    /**
     * @see ShoppingCardDao#getShoppingCardByBuyerId(Long)
     */
    @Test
    public void getShoppingCardByBuyerId_Failure() {
        // Check testUser id for null
        assertNotNull(testUser.getId());

        // Add shopping card into DB
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);
        assertNotNull(testShoppingCard.getId());

        // Test method
        shoppingCardDao.getShoppingCardByBuyerId(65435845L);
    }

    /**
     * @see ShoppingCardDao#updateShoppingCard(Long, ShoppingCard)
     */
    @Test
    public void updateShoppingCard_Success() {
        // Check testUser for not null and add shoppingCard into DB
        assertNotNull(testUser.getId());
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);

        // Check shoppingCard for not null
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());

        testShoppingCard.setBalance(TestObjectTemplate.getRandomNumber() + 1.1);

        // Test method
        shoppingCardDao.updateShoppingCard(testShoppingCard.getId(), testShoppingCard);

        // Find and get updated shoppingCard from DB and check its sameness with testShoppingCard
        ShoppingCard updatedShoppingCard = shoppingCardDao.getShoppingCard(testShoppingCard.getId());
        assertEquals(updatedShoppingCard.getBalance(), testShoppingCard.getBalance());
    }

    /**
     * @see ShoppingCardDao#updateShoppingCard(Long, ShoppingCard)
     */
    @Test(expected = DAOException.class)
    public void updateShoppingCard_Failure() {
        // Check testUser for not null and add shoppingCard into DB
        assertNotNull(testUser.getId());
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);

        // Check shoppingCard for not null
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());

        testShoppingCard.setBalance(TestObjectTemplate.getRandomNumber() + 1.1);

        // Test method
        Boolean isUpdated = shoppingCardDao.updateShoppingCard(1516516516351635163L, testShoppingCard);

        assertFalse(isUpdated);
    }

    /**
     * @see ShoppingCardDao#debitBalanceForItemBuying(Long, Double)
     */
    @Test
    public void debitBalanceForItemBuying_Success() {
        // Check testUser for not null and add shoppingCard into DB
        assertNotNull(testUser.getId());
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);

        // Test method
        shoppingCardDao.debitBalanceForItemBuying(testUser.getId(), 100.0);

        testShoppingCard.setBalance(testShoppingCard.getBalance() - 100.0);
        ShoppingCard updatedShoppingCard = shoppingCardDao.getShoppingCardByBuyerId(testUser.getId());
        assertEqualShoppingCards(testShoppingCard, updatedShoppingCard);
    }

    /**
     * @see ShoppingCardDao#debitBalanceForItemBuying(Long, Double)
     */
    @Test(expected = NotEnoughMoneyException.class)
    public void debitBalanceForItemBuying_Failure() {
        assertNotNull(testUser.getId());
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);

        // Test method
        shoppingCardDao.debitBalanceForItemBuying(testUser.getId(), testShoppingCard.getBalance() + 100.0);
    }

    /**
     * @see ShoppingCardDao#deleteShoppingCard(Long)
     */
    @Test
    public void deleteShoppingCard_Success() {
        assertNotNull(testUser.getId());

        // Add shoppingCard to DB
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);

        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());

        // Test method
        Boolean isDeleted = shoppingCardDao.deleteShoppingCard(testShoppingCard.getId());

        assertTrue(isDeleted);
        testShoppingCard.setId(null);
    }

    /**
     * @see ShoppingCardDao#deleteShoppingCard(Long)
     */
    @Test(expected = DAOException.class)
    public void deleteShoppingCard_Failure() {
        assertNotNull(testUser.getId());

        // Add testShoppingCard to DB
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());

        // Test method
        Boolean isDeleted = shoppingCardDao.deleteShoppingCard(4154541654564654656L);

        assertFalse(isDeleted);
    }

    /**
     * @see ShoppingCardDao#deleteShoppingCardByBuyerId(Long)
     */
    @Test
    public void deleteShoppingCardByBuyerId_Success() {
        assertNotNull(testUser.getId());

        // Add testShoppingCard to DB
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());

        // Test method
        Boolean isDeleted = shoppingCardDao.deleteShoppingCardByBuyerId(testUser.getId());

        assertTrue(isDeleted);
        testShoppingCard.setId(null);
    }

    /**
     * @see ShoppingCardDao#deleteShoppingCardByBuyerId(Long)
     */
    @Test(expected = DAOException.class)
    public void deleteShoppingCardByBuyerId_Failure() {
        assertNotNull(testUser.getId());

        // Add testShoppingCard to DB
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());

        // Test method
        assertTrue(shoppingCardDao.deleteShoppingCardByBuyerId(68466L));
    }

    // endregion
}
