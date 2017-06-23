package am.aca.wftartproject.dao.integration;

import am.aca.wftartproject.dao.*;
import am.aca.wftartproject.dao.impl.ArtistSpecializationLkpDaoImpl;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.PurchaseHistory;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.util.TestObjectTemplate;
import am.aca.wftartproject.util.dbconnection.ConnectionFactory;
import am.aca.wftartproject.util.dbconnection.ConnectionModel;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualPurchaseHistory;
import static junit.framework.TestCase.*;



/**
 * Created by Armen on 6/2/2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:springconfig/daointegration/spring-dao-integration.xml",
        "classpath:springconfig/database/spring-database.xml"})
public class PurchaseHistoryDaoIntegrationTest extends BaseDAOIntegrationTest{

    private static Logger LOGGER = Logger.getLogger(ArtistDaoIntegrationTest.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private ArtistDao artistDao;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private PurchaseHistoryDao purchaseHistoryDao;
    private User testUser;
    private Item testItem;
    private PurchaseHistory purchaseHistory;

    public PurchaseHistoryDaoIntegrationTest() throws ClassNotFoundException, SQLException {
    }

    /**
     * Creates connection, user, artist, items and purchaseHistory for tests
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {

        // create db connection
        dataSource = new ConnectionFactory()
                .getConnection(ConnectionModel.POOL)
                .getTestDBConnection();

        Artist testArtist = TestObjectTemplate.createTestArtist();

        ArtistSpecializationLkpDao artistSpecialization = new ArtistSpecializationLkpDaoImpl(dataSource);
        if (artistSpecialization.getArtistSpecialization(1) == null) {
            artistSpecialization.addArtistSpecialization();
        }

        // create test Item,User, purchaseHistory and set them into db
        testItem = TestObjectTemplate.createTestItem();
        purchaseHistory = new PurchaseHistory();
        testUser = TestObjectTemplate.createTestUser();
        userDao.addUser(testUser);
        testArtist.setId(testUser.getId());
        artistDao.addArtist(testArtist);

        itemDao.addItem(testArtist.getId(), testItem);
        purchaseHistory.setItemId(testItem.getId());
        purchaseHistory.setUserId(testUser.getId());

        // print busy connections quantity
        if (dataSource instanceof ComboPooledDataSource) {
            LOGGER.info(String.format("Number of busy connections Start: %s", ((ComboPooledDataSource) dataSource).getNumBusyConnections()));
        }
    }

    /**
     * Deletes all users, artists, items and purchaseHistory created during the tests
     * @throws SQLException
     */
    @After
    public void tearDown() throws SQLException {

        // delete inserted test users,artists and items  from db
        if (purchaseHistory.getUserId() != null) {
            purchaseHistoryDao.deletePurchase(testUser.getId(), testItem.getId());
        }
        if (testItem.getId() != null) {
            itemDao.deleteItem(testItem.getId());
        }
        if (testUser.getId() != null) {
            userDao.deleteUser(testUser.getId());
        }

        // set temp objects ref  to null
        purchaseHistoryDao = null;
        testUser = null;
        testItem = null;

        // print busy connections quantity
        if (dataSource instanceof ComboPooledDataSource) {
            LOGGER.info(String.format("Number of busy connections End: %s", ((ComboPooledDataSource) dataSource).getNumBusyConnections()));        }
    }

    //region(TEST_CASE)

    /**
     * @see PurchaseHistoryDao#addPurchase(PurchaseHistory)
     */
    @Test
    public void addPurchase_Success() {

        // check for null purchaseHistory
        assertNotNull(purchaseHistory);

        // add purchaseHistory into db
        purchaseHistoryDao.addPurchase(purchaseHistory);

        // get added purchaseHistory from db and check for sameness with origin
        PurchaseHistory added = purchaseHistoryDao.getPurchase(testUser.getId(), testItem.getId());
        assertEqualPurchaseHistory(added, purchaseHistory);
    }

    /**
     * @see PurchaseHistoryDao#addPurchase(PurchaseHistory)
     */
    @Test(expected = DAOException.class)
    public void addPurchase_Failure() {

        // check for null purchaseHistory
        assertNotNull(purchaseHistory);

        // set out of range value for itemId column and add purchaseHistory into db
        purchaseHistory.setItemId(1234567891011L);
        purchaseHistoryDao.addPurchase(purchaseHistory);
    }

    /**
     * @see PurchaseHistoryDao#getPurchase(Long, Long)
     */
    @Test
    public void getPurchaseHistory_Success() {

        // add purchaseHistory into DB
        purchaseHistoryDao.addPurchase(purchaseHistory);

        // find and get purchase item from DB
        PurchaseHistory purchaseItem = purchaseHistoryDao.
                getPurchase(purchaseHistory.getUserId(), purchaseHistory.getItemId());

        // check for sameness with purchaseHistory
        assertEqualPurchaseHistory(purchaseItem, purchaseHistory);
    }

    /**
     * @see PurchaseHistoryDao#getPurchase(Long, Long)
     */
    @Test
    public void getPurchaseHistory_Failure() {

        // add purchaseHistory into Db
        purchaseHistoryDao.addPurchase(purchaseHistory);

        // find and get purchase item from DB
        assertNull(purchaseHistoryDao.getPurchase(123456789101112131L, 1234567891889901112L));
    }

    /**
     * @see PurchaseHistoryDao#getPurchase(Long)
     */
    @Test
    public void getPurchaseHistoryNotEmptyList(){

        // add purchaseHistory into DB
        purchaseHistoryDao.addPurchase(purchaseHistory);

        // find and get user's purchaseHistory from DB
        assertFalse(purchaseHistoryDao.getPurchase(purchaseHistory.getUserId()).isEmpty());
    }

    /**
     * @see PurchaseHistoryDao#getPurchase(Long)
     */
    @Test
    public void getPurchaseHistoryEmptyList(){

        // add purchaseHistory into DB
        purchaseHistoryDao.addPurchase(purchaseHistory);

        // find and get user's purchaseHistory from DB
        assertTrue(purchaseHistoryDao.getPurchase(6598465L).isEmpty());
    }

    /**
     * @see PurchaseHistoryDao#deletePurchase(Long, Long)
     */
    @Test
    public void deletePurchaseHistory_Success() {

        // check for null purchaseHistory
        assertNotNull(purchaseHistory);

        // add purchaseHistory into db, get and check it for null
        purchaseHistoryDao.addPurchase(purchaseHistory);
        assertNotNull(purchaseHistoryDao.getPurchase(testUser.getId(), testItem.getId()));

        // try to delete purchaseHistory
        assertTrue(purchaseHistoryDao.deletePurchase(testUser.getId(), testItem.getId()));
        purchaseHistory.setUserId(null);
    }

    /**
     * @see PurchaseHistoryDao#deletePurchase(Long, Long)
     */
    @Test(expected = DAOException.class)
    public void deletePurchaseHistory_Failure() {

        // check for null purchaseHistory;
        assertNotNull(purchaseHistory);

        // add purchaseHistory into db, get and check it for null
        purchaseHistoryDao.addPurchase(purchaseHistory);
        assertNotNull(purchaseHistoryDao.getPurchase(testUser.getId(), testItem.getId()));

        // try to delete purchaseHistory
        assertFalse(purchaseHistoryDao.deletePurchase(1456325896615358332L, testItem.getId()));
    }

    //endregion
}