package am.aca.wftartproject.dao.integration;

import am.aca.wftartproject.dao.*;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.PurchaseHistory;
import am.aca.wftartproject.model.User;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualPurchaseHistory;
import static am.aca.wftartproject.util.TestObjectTemplate.*;
import static junit.framework.TestCase.*;

/**
 * Created by Armen on 6/2/2017
 */

public class PurchaseHistoryDaoIntegrationTest extends BaseDAOIntegrationTest {

    private static Logger LOGGER = Logger.getLogger(ArtistDaoIntegrationTest.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private ArtistDao artistDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private PurchaseHistoryDao purchaseHistoryDao;

    @Autowired
    private ArtistSpecializationLkpDao artistSpecializationLkpDao;

    private User testUser;
    private Artist testArtist;
    private Item testItem;
    private PurchaseHistory purchaseHistory;

    public PurchaseHistoryDaoIntegrationTest() throws ClassNotFoundException, SQLException {
    }

    /**
     * Creates user, artist, items and purchaseHistory for test
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        testArtist = createTestArtist();

        // Add specialization values, if appropriate table is empty
        if (artistSpecializationLkpDao.getArtistSpecialization(1) == null) {
            artistSpecializationLkpDao.addArtistSpecialization();
        }

        // Create test Item,User, purchaseHistory and set them into db
        testItem = createTestItem();
        purchaseHistory = new PurchaseHistory();
        testUser = createTestUser();
        userDao.addUser(testUser);
        testArtist.setId(testUser.getId());
        artistDao.addArtist(testArtist);

        itemDao.addItem(testArtist.getId(), testItem);
        purchaseHistory.setItemId(testItem.getId());
        purchaseHistory.setUserId(testUser.getId());

        // Print busy connections quantity
        if (jdbcTemplate.getDataSource() instanceof ComboPooledDataSource) {
            LOGGER.info(String.format("Number of busy connections Start: %s",
                    ((ComboPooledDataSource) jdbcTemplate.getDataSource()).getNumBusyConnections()));
        }
    }

    /**
     * Deletes all users, artists, items and purchaseHistory created during the test
     *
     * @throws SQLException
     */
    @After
    public void tearDown() throws SQLException {
        // Delete inserted test users,artists and items  from db
        if (purchaseHistory.getUserId() != null) {
            purchaseHistoryDao.deletePurchase(testUser.getId(), testItem.getId());
        }

        if (testItem.getId() != null) {
            itemDao.deleteItem(testItem.getId());
        }

        if (testArtist.getId() != null) {
            artistDao.deleteArtist(testArtist.getId());
        }

        if (testUser.getId() != null) {
            userDao.deleteUser(testUser.getId());
        }

        // Set temp objects ref  to null
        purchaseHistoryDao = null;
        testUser = null;
        testArtist = null;
        testItem = null;

        // Print busy connections quantity
        if (jdbcTemplate.getDataSource() instanceof ComboPooledDataSource) {
            LOGGER.info(String.format("Number of busy connections End: %s",
                    ((ComboPooledDataSource) jdbcTemplate.getDataSource()).getNumBusyConnections()));
        }
    }

    // region<TEST CASE>

    /**
     * @see PurchaseHistoryDao#addPurchase(PurchaseHistory)
     */
    @Test
    public void addPurchase_Success() {
        // Check for null purchaseHistory
        assertNotNull(purchaseHistory);

        // Test method
        purchaseHistoryDao.addPurchase(purchaseHistory);

        // Get added purchaseHistory from db and check for sameness with origin
        PurchaseHistory added = purchaseHistoryDao.getPurchase(testUser.getId(), testItem.getId());
        assertEqualPurchaseHistory(added, purchaseHistory);
    }

    /**
     * @see PurchaseHistoryDao#addPurchase(PurchaseHistory)
     */
    @Test(expected = DAOException.class)
    public void addPurchase_Failure() {
        // Check for null purchaseHistory
        assertNotNull(purchaseHistory);

        // Set out of range value for itemId column
        purchaseHistory.setItemId(1234567891011L);

        // Test method
        purchaseHistoryDao.addPurchase(purchaseHistory);
    }

    /**
     * @see PurchaseHistoryDao#getPurchase(Long, Long)
     */
    @Test
    public void getPurchaseHistory_Success() {
        // Add purchaseHistory into DB
        purchaseHistoryDao.addPurchase(purchaseHistory);

        // Test method
        PurchaseHistory purchaseItem = purchaseHistoryDao.
                getPurchase(purchaseHistory.getUserId(), purchaseHistory.getItemId());

        // Check for sameness with purchaseHistory
        assertEqualPurchaseHistory(purchaseItem, purchaseHistory);
    }

    /**
     * @see PurchaseHistoryDao#getPurchase(Long, Long)
     */
    @Test
    public void getPurchaseHistory_Failure() {
        // Add purchaseHistory into Db
        purchaseHistoryDao.addPurchase(purchaseHistory);

        // Test method
        assertNull(purchaseHistoryDao.getPurchase(123456789101112131L, 1234567891889901112L));
    }

    /**
     * @see PurchaseHistoryDao#getPurchase(Long)
     */
    @Test
    public void getPurchaseHistory_NotEmptyList(){
        // Add purchaseHistory into DB
        purchaseHistoryDao.addPurchase(purchaseHistory);

        // Test method
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryDao.getPurchase(purchaseHistory.getUserId());

        assertFalse(purchaseHistoryList.isEmpty());
    }

    /**
     * @see PurchaseHistoryDao#getPurchase(Long)
     */
    @Test
    public void getPurchaseHistory_EmptyList(){
        // Add purchaseHistory into DB
        purchaseHistoryDao.addPurchase(purchaseHistory);

        // Test method
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryDao.getPurchase(6598465L);

        assertTrue(purchaseHistoryList.isEmpty());
    }

    /**
     * @see PurchaseHistoryDao#deletePurchase(Long, Long)
     */
    @Test
    public void deletePurchaseHistory_Success() {
        // Check for null purchaseHistory
        assertNotNull(purchaseHistory);

        // Add purchaseHistory into db, get and check it for null
        purchaseHistoryDao.addPurchase(purchaseHistory);
        assertNotNull(purchaseHistoryDao.getPurchase(testUser.getId(), testItem.getId()));

        // Test method
        Boolean isDeleted = purchaseHistoryDao.deletePurchase(testUser.getId(), testItem.getId());

        assertTrue(isDeleted);
        purchaseHistory.setUserId(null);
    }

    /**
     * @see PurchaseHistoryDao#deletePurchase(Long, Long)
     */
    @Test(expected = DAOException.class)
    public void deletePurchaseHistory_Failure() {
        // Check for null purchaseHistory;
        assertNotNull(purchaseHistory);

        // Add purchaseHistory into db, get and check it for null
        purchaseHistoryDao.addPurchase(purchaseHistory);
        assertNotNull(purchaseHistoryDao.getPurchase(testUser.getId(), testItem.getId()));

        // Test method
        Boolean isDeleted = purchaseHistoryDao.deletePurchase(1456325896615358332L, testItem.getId());

        assertFalse(isDeleted);
    }

    // endregion
}
