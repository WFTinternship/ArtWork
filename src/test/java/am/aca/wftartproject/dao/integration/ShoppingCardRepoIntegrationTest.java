package am.aca.wftartproject.dao.integration;

import am.aca.wftartproject.repository.ShoppingCardRepo;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.dao.NotEnoughMoneyException;
import am.aca.wftartproject.entity.ShoppingCard;
import am.aca.wftartproject.entity.User;
import am.aca.wftartproject.repository.UserRepo;
import am.aca.wftartproject.service.ShoppingCardService;
import am.aca.wftartproject.util.TestObjectTemplate;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.sql.SQLException;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualShoppingCards;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestShoppingCard;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestUser;
import static junit.framework.TestCase.*;


/**
 * Created by Armen on 6/2/2017
 */

public class ShoppingCardRepoIntegrationTest extends BaseDAOIntegrationTest{

    private static Logger LOGGER = Logger.getLogger(ShoppingCardRepoIntegrationTest.class);

    private User testUser;
    private ShoppingCard testShoppingCard;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ShoppingCardRepo shoppingCardRepo;

    @Autowired
    private ShoppingCardService shoppingCardService;

    public ShoppingCardRepoIntegrationTest() throws SQLException, ClassNotFoundException {
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
        userRepo.saveAndFlush(testUser);
        testShoppingCard = createTestShoppingCard();
        testShoppingCard.setAbstractUser(testUser);

//        // Print busy connections quantity
//        if (jdbcTemplate.getDataSource() instanceof ComboPooledDataSource) {
//            LOGGER.info(String.format("Number of busy connections Start: %s",
//                    ((ComboPooledDataSource) jdbcTemplate.getDataSource()).getNumBusyConnections()));
//        }
    }

    /**
     * Deletes all users and shoppingCards created during the tests
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @After
    public void tearDown() throws SQLException, ClassNotFoundException {
        // Delete inserted test users,shoppingCards from db
        if ( testShoppingCard != null && testShoppingCard.getId() != null)
            shoppingCardRepo.delete(testShoppingCard);
        if (testUser.getId() != null)
            userRepo.delete(testUser);

        // Set temp instance refs to null
        testShoppingCard = null;
        testUser = null;

//        // Print busy connections quantity
//        if (jdbcTemplate.getDataSource() instanceof ComboPooledDataSource) {
//            LOGGER.info(String.format("Number of busy connections End: %s",
//                    ((ComboPooledDataSource) jdbcTemplate.getDataSource()).getNumBusyConnections()));
//        }
    }

    // region<TEST CASE>

    /**
     * @see ShoppingCardRepo#saveAndFlush(ShoppingCard)
     */
    @Test
    public void addShoppingCard_Success() {
        // Check testUser id and testShoppingCard for null
        assertNotNull(testUser.getId());
        assertNotNull(testShoppingCard);

        // Set testShoppingCard object into DB
        shoppingCardRepo.saveAndFlush(testShoppingCard);

        // Get shoppingCard from DB
        ShoppingCard added = shoppingCardRepo.findOne(testShoppingCard.getId());

        // Check for equals
        assertEqualShoppingCards(added, testShoppingCard);
    }

    /**
     * @see ShoppingCardRepo#saveAndFlush(ShoppingCard)
     */
    @Test(expected = DAOException.class)
    public void addShoppingCard_Failure() {
        // Check testUser id and testShoppingCard for null
        assertNotNull(testUser.getId());
        assertNotNull(testShoppingCard);
        testShoppingCard.setAbstractUser(null);
        // Try to add shoppingCard with out of range id
        try{
            shoppingCardRepo.saveAndFlush(testShoppingCard);
        }
        catch (Exception e){
            throw new DAOException(e.getMessage());
        }

    }

    /**
     * @see ShoppingCardRepo#findOne(Serializable)
     */
    @Test
    public void findShoppingCard_Success() {
        // Check testUser id for null
        assertNotNull(testUser.getId());

        // Set testShoppingCard object into DB
        shoppingCardRepo.saveAndFlush(testShoppingCard);

        // Check testShoppingCard object and testShoppingCard id for null
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());

        // Get shoppingCard from DB
        ShoppingCard shoppingCard = shoppingCardRepo.findOne(testShoppingCard.getId());

        // Check for equals
        assertEqualShoppingCards(shoppingCard, testShoppingCard);
    }

    /**
     * @see ShoppingCardRepo#findOne(Serializable)
     */
    @Test(expected = DAOException.class)
    public void findShoppingCard_Failure() {
        // Check testUser id for null
        assertNotNull(testUser.getId());

        // Add shopping card into DB and check its id for null
        shoppingCardRepo.saveAndFlush(testShoppingCard);
        assertNotNull(testShoppingCard.getId());
        testShoppingCard.setId(null);

        // Try to get shopping card with very large id
        try {
            shoppingCardRepo.findOne(testShoppingCard.getId());
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }


    /**
     * @see ShoppingCardRepo#findOne(Serializable)
     */
    @Test
    public void findShoppingCardByBuyerId_Success() {
        // Check testUser id for null
        assertNotNull(testUser.getId());

        // Add shopping card into DB
        shoppingCardRepo.saveAndFlush(testShoppingCard);

        // Check testShoppingCard and its id for null
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());

        // Get added shopping card from DB and check sameness
        ShoppingCard foundedCard = shoppingCardRepo.findByAbstractUser_Id(testShoppingCard.getAbstractUser().getId());
        assertEqualShoppingCards(foundedCard, testShoppingCard);
    }

    /**
     * @see ShoppingCardRepo#findOne(Serializable)
     */
    @Test
    public void findShoppingCardByBuyerId_Failure() {
        // Check testUser id for null
        assertNotNull(testUser.getId());

        // Add shopping card into DB
        shoppingCardRepo.saveAndFlush(testShoppingCard);
        assertNotNull(testShoppingCard.getId());

        // Test method
           assertNull(shoppingCardRepo.findByAbstractUser_Id(65435845L));
    }

    /**
     * @see ShoppingCardRepo#saveAndFlush(ShoppingCard)
     */
    @Test
    public void updateShoppingCard_Success() {
        // Check testUser for not null and add shoppingCard into DB
        assertNotNull(testUser.getId());
        shoppingCardRepo.saveAndFlush(testShoppingCard);

        // Check shoppingCard for not null
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());

        // Try to update shoppingCard
        testShoppingCard.setBalance(TestObjectTemplate.getRandomNumber() + 1.1);
        shoppingCardRepo.saveAndFlush(testShoppingCard);

        // Find and get updated shoppingCard from DB and check its sameness with testShoppingCard
        ShoppingCard updatedShoppingCard = shoppingCardRepo.findOne(testShoppingCard.getId());
        assertEquals(updatedShoppingCard.getBalance(), testShoppingCard.getBalance());
    }

    /**
     * @see ShoppingCardRepo#saveAndFlush( ShoppingCard)
     */
    @Test(expected = DAOException.class)
    public void updateShoppingCard_failure() {
        // Check testUser for not null and add shoppingCard into DB
        assertNotNull(testUser.getId());
        shoppingCardRepo.saveAndFlush(testShoppingCard);

        // Check shoppingCard for not null
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());

        // Try to update shoppingCard
        testShoppingCard.setBalance(TestObjectTemplate.getRandomNumber() + 1.1);
        testShoppingCard.setAbstractUser(null);
        try{
            shoppingCardRepo.saveAndFlush( testShoppingCard);
        }
        catch (Exception e){
            throw new DAOException(e.getMessage());
        }

    }

    /**
     * @see ShoppingCardRepo#delete(ShoppingCard)
     */
    @Test
    public void deleteShoppingCard_Success() {
        // Check all components for null and check delete result for true
        assertNotNull(testUser.getId());
        shoppingCardRepo.saveAndFlush(testShoppingCard);
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());
        shoppingCardRepo.delete(testShoppingCard);
        testShoppingCard.setId(null);
    }

    /**
     * @see ShoppingCardRepo#delete(ShoppingCard)
     */
    @Test(expected = DAOException.class)
    public void deleteShoppingCard_Failure() {
        // Check all components for null and check delete result for true
        assertNotNull(testUser.getId());
        shoppingCardRepo.saveAndFlush(testShoppingCard);
        assertNotNull(testShoppingCard);
        testShoppingCard = null;
        try{
           shoppingCardRepo.delete(testShoppingCard);
        }
        catch (Exception e){
            throw new DAOException(e.getMessage());
        }

    }

//    /**
//     * @see ShoppingCardRepo#deleteByBuyerId(Long)
//     */
//    @Test
//    public void deleteByBuyerId_Success() {
//        // Check all components for null and check delete result for true
//        assertNotNull(testUser.getId());
//        shoppingCardRepo.saveAndFlush(testUser.getId(), testShoppingCard);
//        assertNotNull(testShoppingCard);
//        assertNotNull(testShoppingCard.getId());
//        assertTrue(shoppingCardRepo.deleteByBuyerId(testUser.getId()));
//        testShoppingCard.setId(null);
//    }
//
//    /**
//     * @see ShoppingCardRepo#deleteByBuyerId(Long)
//     */
//    @Test(expected = DAOException.class)
//    public void deleteByBuyerId_Failure() {
//        // Check all components for null and check delete result for true
//        assertNotNull(testUser.getId());
//        shoppingCardRepo.saveAndFlush(testUser.getId(), testShoppingCard);
//        assertNotNull(testShoppingCard);
//        assertNotNull(testShoppingCard.getId());
//        assertTrue(shoppingCardRepo.deleteByBuyerId(68466L));
//    }

    // endregion
}
