package integration.dao;

import am.aca.wftartproject.dao.*;
import am.aca.wftartproject.dao.impl.*;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.PurchaseHistory;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.util.dbconnection.ConnectionFactory;
import am.aca.wftartproject.util.dbconnection.ConnectionModel;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.TestObjectTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

import static junit.framework.TestCase.*;
import static util.AssertTemplates.assertEqualPurchaseHistory;


/**
 * Created by Armen on 6/2/2017
 */
public class PurchaseHistoryDaoIntegrationTest {

    private static Logger LOGGER = Logger.getLogger(ArtistDaoIntegrationTest.class);
    private DataSource conn;

    private UserDao userDao;
    private ItemDao itemDao;
    private ArtistDao artistDao;
    private PurchaseHistoryDao purchaseHistoryDao;
    private Artist testArtist;
    private User testUser;
    private Item testItem;
    private PurchaseHistory purchaseHistory;
    private ArtistSpecializationLkpDao artistSpecialization;

    public PurchaseHistoryDaoIntegrationTest() throws ClassNotFoundException, SQLException {
    }

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {

        //create db connection
        conn = new ConnectionFactory()
                .getConnection(ConnectionModel.POOL)
                .getTestDBConnection();

        artistSpecialization = new ArtistSpecializationLkpDaoImpl(conn);
        if (artistSpecialization.getArtistSpecialization(1) == null) {
            artistSpecialization.addArtistSpecialization();
        }
        userDao = new UserDaoImpl(conn);
        itemDao = new ItemDaoImpl(conn);
        artistDao = new ArtistDaoImpl(conn);
        purchaseHistoryDao = new PurchaseHistoryDaoImpl(conn);

        //create test Item,User, purchaseHistory and set them into db
        testItem = TestObjectTemplate.createTestItem();
        purchaseHistory = new PurchaseHistory();
        testUser = TestObjectTemplate.createTestUser();
        testArtist = TestObjectTemplate.createTestArtist();
        userDao.addUser(testUser);
        testArtist.setId(testUser.getId());
        artistDao.addArtist(testArtist);

        itemDao.addItem(testArtist.getId(), testItem);
        purchaseHistory.setItemId(testItem.getId());
        purchaseHistory.setUserId(testUser.getId());

        //Prints busy connections quantity
        if (conn instanceof ComboPooledDataSource) {
            LOGGER.info(((ComboPooledDataSource) conn).getNumBusyConnections());
        }
    }

    /**
     * @see PurchaseHistoryDao#addPurchase(PurchaseHistory)
     */
    @Test
    public void addPurchase_Success() {

        //check for null purchasehistory
        assertNotNull(purchaseHistory);

        //add purchasehistory into db
        purchaseHistoryDao.addPurchase(purchaseHistory);

        //get added purchasehistory from db and check for sameness with origin
        PurchaseHistory added = purchaseHistoryDao.getPurchase(testUser.getId(), testItem.getId());
        assertEqualPurchaseHistory(added, purchaseHistory);
    }

    /**
     * @see PurchaseHistoryDao#addPurchase(PurchaseHistory)
     */
    @Test(expected = DAOException.class)
    public void addPurchase_Failure() {

        //check for null purchasehistory
        assertNotNull(purchaseHistory);

        //set out of range value for itemid column and add purchasehistory into db
        purchaseHistory.setItemId(1234567891011L);
        purchaseHistoryDao.addPurchase(purchaseHistory);
    }

    /**
     * @see PurchaseHistoryDao#getPurchase(Long, Long)
     */
    @Test
    public void getPurchaseHistory_Success() {

        purchaseHistoryDao.addPurchase(purchaseHistory);

        //find and get purchase item from DB
        PurchaseHistory purchaseItem = purchaseHistoryDao.
                getPurchase(purchaseHistory.getUserId(), purchaseHistory.getItemId());

        //check for sameness with purchaseHistory
        assertEqualPurchaseHistory(purchaseItem, purchaseHistory);
    }

    /**
     * @see PurchaseHistoryDao#getPurchase(Long, Long)
     */
    @Test(expected = DAOException.class)
    public void getPurchase_Failure() {

        purchaseHistoryDao.addPurchase(purchaseHistory);

        //find and get purchase item from DB
        assertNull(purchaseHistoryDao.getPurchase(123456789101112131L, 1234567891889901112L));
    }

    @Test
    public void deletePurchaseHistory_Success() {

        //check for null purchasehistory
        assertNotNull(purchaseHistory);

        //add purchasehistory into db
        purchaseHistoryDao.addPurchase(purchaseHistory);

        assertNotNull(purchaseHistoryDao.getPurchase(testUser.getId(), testItem.getId()));
        assertTrue(purchaseHistoryDao.deletePurchase(testUser.getId(), testItem.getId()));

        purchaseHistory.setUserId(null);
    }

    @Test(expected = DAOException.class)
    public void deletePurchaseHistory_Failure() {

        //check for null purchaseHistory;
        assertNotNull(purchaseHistory);

        //add purchaseHistory into db get and check for null
        purchaseHistoryDao.addPurchase(purchaseHistory);

        assertNotNull(purchaseHistoryDao.getPurchase(testUser.getId(), testItem.getId()));
        assertFalse(purchaseHistoryDao.deletePurchase(1456325896615358332L, testItem.getId()));
    }

    @After
    public void tearDown() throws SQLException {

        //delete inserted test users,artists and items  from db
        if (purchaseHistory.getUserId() != null) {
            purchaseHistoryDao.deletePurchase(testUser.getId(), testItem.getId());
        }
        if (testItem.getId() != null) {
            itemDao.deleteItem(testItem.getId());
        }
        if (testUser.getId() != null) {
            userDao.deleteUser(testUser.getId());
        }

        //set temp objects ref  to null
        purchaseHistoryDao = null;
        testUser = null;
        testItem = null;

        //Prints busy connections quantity
        if (conn instanceof ComboPooledDataSource) {
            LOGGER.info(((ComboPooledDataSource) conn).getNumBusyConnections());
        }
    }
}
