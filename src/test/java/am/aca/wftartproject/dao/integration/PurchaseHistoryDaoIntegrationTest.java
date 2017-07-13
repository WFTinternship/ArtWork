package am.aca.wftartproject.dao.integration;

import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.entity.Artist;
import am.aca.wftartproject.entity.Item;
import am.aca.wftartproject.entity.PurchaseHistory;
import am.aca.wftartproject.entity.User;
import am.aca.wftartproject.repository.ArtistRepo;
import am.aca.wftartproject.repository.ItemRepo;
import am.aca.wftartproject.repository.PurchaseHistoryRepo;
import am.aca.wftartproject.repository.UserRepo;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.SQLException;
import java.util.Calendar;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualPurchaseHistory;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestArtist;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestItem;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestUser;
import static junit.framework.TestCase.*;



/**
 * Created by Armen on 6/2/2017
 */

public class PurchaseHistoryDaoIntegrationTest extends BaseDAOIntegrationTest{

    private static Logger LOGGER = Logger.getLogger(ArtistRepoIntegrationTest.class);

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ArtistRepo artistRepo;
    @Autowired
    private ItemRepo itemDao;
    @Autowired
    private PurchaseHistoryRepo purchaseHistoryRepo;
    private User testUser;
    private Item testItem;
    private PurchaseHistory purchaseHistory;

    public PurchaseHistoryDaoIntegrationTest() throws ClassNotFoundException, SQLException {
    }

    /**
     * Creates user, artist, items and purchaseHistory for tests
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        Artist testArtist = createTestArtist();

        // Create test Item,User, purchaseHistory and set them into db
        testItem = createTestItem();
        purchaseHistory = new PurchaseHistory();
        testUser = createTestUser();
        userRepo.saveAndFlush(testUser);
        artistRepo.saveAndFlush(testArtist);
        testItem.setArtist(testArtist);
        itemDao.saveAndFlush(testItem);
        purchaseHistory.setItem(testItem);
        purchaseHistory.setAbsUser(testUser);
        purchaseHistory.setPurchaseDate(Calendar.getInstance().getTime());

//        // Print busy connections quantity
//        if (jdbcTemplate.getDataSource() instanceof ComboPooledDataSource) {
//            LOGGER.info(String.format("Number of busy connections Start: %s",
//                    ((ComboPooledDataSource) jdbcTemplate.getDataSource()).getNumBusyConnections()));
//        }
    }

    /**
     * Deletes all users, artists, items and purchaseHistory created during the tests
     * @throws SQLException
     */
    @After
    public void tearDown() throws SQLException {
        // Delete inserted test users,artists and items  from db
        if (purchaseHistory != null && purchaseHistory.getId() != null) {
            purchaseHistoryRepo.delete(purchaseHistory);
        }
        if (testItem.getId() != null) {
            itemDao.delete(testItem);
        }
        if (testUser.getId() != null) {
            userRepo.delete(testUser);
        }

        // Set temp objects ref  to null
        purchaseHistoryRepo = null;
        testUser = null;
        testItem = null;

//        // Print busy connections quantity
//        if (jdbcTemplate.getDataSource() instanceof ComboPooledDataSource) {
//            LOGGER.info(String.format("Number of busy connections End: %s",
//                    ((ComboPooledDataSource) jdbcTemplate.getDataSource()).getNumBusyConnections()));
//        }
    }

    // region<TEST CASE>

    /**
     * @see PurchaseHistoryRepo#saveAndFlush(Object)
     */
    @Test
    public void saveAndFlush_Success() {
        // Check for null purchaseHistory
        assertNotNull(purchaseHistory);

        // Add purchaseHistory into db
        purchaseHistoryRepo.saveAndFlush(purchaseHistory);

        // Get added purchaseHistory from db and check for sameness with origin
        PurchaseHistory added = purchaseHistoryRepo.findByPurchaseItem_Id(testItem.getId());
        assertEqualPurchaseHistory(added, purchaseHistory);
    }

    /**
     * @see PurchaseHistoryRepo#saveAndFlush(Object)
     */
    @Test(expected = DAOException.class)
    public void saveAndFlush_Failure() {
        // Check for null purchaseHistory
        assertNotNull(purchaseHistory);

        // Set out of range value for itemId column and add purchaseHistory into db
        purchaseHistory.setItem(null);
        try{
            purchaseHistoryRepo.saveAndFlush(purchaseHistory);
        }
          catch (Exception e){
            // to prevent exceptions in tearDown
            purchaseHistory.setAbsUser(null);
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * @see PurchaseHistoryRepo#findByPurchaseItem_Id(Long) (Long)
     */
    @Test
    public void getPurchaseHistory_Success() {
        // Add purchaseHistory into DB
        purchaseHistoryRepo.saveAndFlush(purchaseHistory);

        // Find and get purchase item from DB
        PurchaseHistory purchaseItem = purchaseHistoryRepo.
                findByPurchaseItem_Id(purchaseHistory.getItem().getId());

        // Check for sameness with purchaseHistory
        assertEqualPurchaseHistory(purchaseItem, purchaseHistory);
    }

    /**
     * @see PurchaseHistoryRepo#findByPurchaseItem_Id(Long) 
     */
    @Test
    public void getPurchaseHistory_Failure() {
        // Add purchaseHistory into Db
        purchaseHistoryRepo.saveAndFlush(purchaseHistory);

        // Find and get purchase item from DB
        assertNull(purchaseHistoryRepo.findByPurchaseItem_Id(-7L));
    }

    /**
     * @see PurchaseHistoryRepo#findByPurchaseItem_Id(Long)
     */
    @Test
    public void getPurchaseHistoryNotEmptyList(){
        // Add purchaseHistory into DB
        purchaseHistoryRepo.saveAndFlush(purchaseHistory);

        // Find and get user's purchaseHistory from DB
        assertFalse(purchaseHistoryRepo.getAllByAbsUser_Id(purchaseHistory.getAbsUser().getId()).isEmpty());
    }

    /**
     * @see PurchaseHistoryRepo#findByPurchaseItem_Id(Long)
     */
    @Test
    public void getPurchaseHistoryEmptyList(){
        // Add purchaseHistory into DB
        purchaseHistoryRepo.saveAndFlush(purchaseHistory);

        // Find and get user's purchaseHistory from DB
        assertTrue(purchaseHistoryRepo.getAllByAbsUser_Id(6598465L).isEmpty());
    }

    /**
     * @see PurchaseHistoryRepo#delete(Object)
     */
    @Test
    public void deletePurchaseHistory_Success() {
        // Check for null purchaseHistory
        assertNotNull(purchaseHistory);

        // Add purchaseHistory into db, get and check it for null
        purchaseHistoryRepo.saveAndFlush(purchaseHistory);
        assertNotNull(purchaseHistoryRepo.findByPurchaseItem_Id(testItem.getId()));

        // Try to delete purchaseHistory
        purchaseHistoryRepo.delete(purchaseHistory);
        purchaseHistory.setId(null);
    }

    /**
     * @see PurchaseHistoryRepo#delete(Object)
     */
    @Test(expected = DAOException.class)
    public void deletePurchaseHistory_Failure() {
        // Check for null purchaseHistory;
        assertNotNull(purchaseHistory);

        // Add purchaseHistory into db, get and check it for null
        purchaseHistoryRepo.saveAndFlush(purchaseHistory);
        assertNotNull(purchaseHistoryRepo.findByPurchaseItem_Id(testItem.getId()));
        purchaseHistory = null;
        // Try to delete purchaseHistory
        try{
            purchaseHistoryRepo.delete(purchaseHistory);
        }
        catch (Exception e){
            // to prevent exceptions in tearDown
            throw new DAOException(e.getMessage());
        }

    }

    // endregion
}
