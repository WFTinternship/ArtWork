package dao.integration;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.dao.ItemDao;
import am.aca.wftartproject.dao.PurchaseHistoryDao;
import am.aca.wftartproject.dao.UserDao;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.dao.impl.ItemDaoImpl;
import am.aca.wftartproject.dao.impl.PurchaseHistoryDaoImpl;
import am.aca.wftartproject.dao.impl.UserDaoImpl;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.PurchaseHistory;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.util.dbconnection.ConnectionModel;
import am.aca.wftartproject.util.dbconnection.ConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.TestObjectTemplate;

import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static util.AssertTemplates.assertEqualPurchaseHistory;

/**
 * Created by Armen on 6/2/2017
 */
public class PurchaseHistoryDaoIntegrationTest {

    private UserDao userDao;
    private ItemDao itemDao;
    private ArtistDao artistDao;
    private PurchaseHistoryDao purchaseHistoryDao;
    private User testUser;
    private Item testItem;
    private PurchaseHistory purchaseHistory;
    private Artist testArtist;


    /**
     * Creates and adds user and item to the database.
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        Connection conn = new ConnectionFactory()
                .getConnection(ConnectionModel.BASIC)
                .getTestDBConnection();

        userDao = new UserDaoImpl(conn);
        artistDao = new ArtistDaoImpl(conn);
        itemDao = new ItemDaoImpl(conn);
        purchaseHistoryDao = new PurchaseHistoryDaoImpl(conn);

        //create test Item,User, purchaseHistory and set them into db

        testUser = TestObjectTemplate.createTestUser();
//        testArtist = TestObjectTemplate.createTestArtist();
        userDao.addUser(testUser);
//        testArtist.setId(testUser.getId());

        artistDao.addArtist(testArtist);
        testItem = TestObjectTemplate.createTestItem();
        purchaseHistory = new PurchaseHistory();
        System.out.println(testUser.getId());
        System.out.println(testItem);

        itemDao.addItem(testUser.getId(), testItem);
        purchaseHistory.setItemId(testItem.getId());
        purchaseHistory.setUserId(testUser.getId());
    }


    /**
     * @see PurchaseHistoryDao#addPurchase(PurchaseHistory)
     */
    @Test
    public void addPurchase() {

        //check for null purchasehistory

        assertNotNull(purchaseHistory);

        //add purchasehistory into db

        purchaseHistoryDao.addPurchase(purchaseHistory);

        //get added purchasehistory from db and check for sameness with origin

        PurchaseHistory added = purchaseHistoryDao.getPurchase(testUser.getId(), testItem.getId());
        assertEqualPurchaseHistory(added, purchaseHistory);
        System.out.println(added);
        System.out.println(purchaseHistory);


    }


    /**
     * @see PurchaseHistoryDao#addPurchase(PurchaseHistory)
     */
    @Test(expected = DAOException.class)
    public void addPurchase_failure() {

        //check for null purchasehistory

        assertNotNull(purchaseHistory);

        //add purchasehistory into db
        purchaseHistory.setUserId(null);
        purchaseHistoryDao.addPurchase(purchaseHistory);

        //get added purchasehistory from db and check for sameness with origin

        PurchaseHistory added = purchaseHistoryDao.getPurchase(testUser.getId(), testItem.getId());
        assertEqualPurchaseHistory(added, purchaseHistory);
        System.out.println(added);
        System.out.println(purchaseHistory);


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

        PurchaseHistory added = purchaseHistoryDao.getPurchase(null, testItem.getId());

        //check for sameness with purchaseHistory

        assertNull(added.getItemId());
    }


    @Test
    public void deletePurcaseHistory() {

        //check for null purchasehistory

        assertNotNull(purchaseHistory);
        purchaseHistoryDao.addPurchase(purchaseHistory);
        assertNotNull(purchaseHistoryDao.getPurchase(testUser.getId(), testItem.getId()));

        //add purchasehistory into db

        purchaseHistoryDao.deletePurchase(testUser.getId(), testItem.getId());

        //get added purchasehistory from db and check for sameness with origin

        PurchaseHistory deleteded = purchaseHistoryDao.getPurchase(testUser.getId(), testItem.getId());
        assertNull(deleteded.getItemId());
        System.out.println(deleteded);
        System.out.println(purchaseHistory);


    }


    @Test(expected = DAOException.class)
    public void deletePurcaseHistory_failure() {

        //check for null purchasehistory;

        assertNotNull(purchaseHistory);

        //add purchasehistory into db get and check for null

        purchaseHistoryDao.addPurchase(purchaseHistory);
        assertNotNull(purchaseHistoryDao.getPurchase(testUser.getId(), testItem.getId()));

        purchaseHistoryDao.deletePurchase(null, testItem.getId());

        //get deleted purchasehistory from db and check fields for null

        PurchaseHistory deleteded = purchaseHistoryDao.getPurchase(testUser.getId(), testItem.getId());
        assertNull(deleteded.getItemId());


    }


    @After
    public void tearDown() {
        //delete inserted test users,artists and items  from db

        if (purchaseHistory.getUserId() != null)
            purchaseHistoryDao.deletePurchase(testUser.getId(), testItem.getId());

        if (testItem.getId() != null)
            itemDao.deleteItem(testItem.getId());

        if (testUser.getId() != null)
            userDao.deleteUser(testUser.getId());


        //set temp objects ref  to null

        purchaseHistoryDao = null;
        testUser = null;
        testItem = null;
    }
}
