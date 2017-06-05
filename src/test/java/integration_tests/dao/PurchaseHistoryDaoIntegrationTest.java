package integration_tests.dao;

import am.aca.wftartproject.dao.*;
import am.aca.wftartproject.dao.impl.ItemDaoImpl;
import am.aca.wftartproject.dao.impl.PurchaseHistoryDaoImpl;
import am.aca.wftartproject.dao.impl.UserDaoImpl;
import am.aca.wftartproject.exception.DAOFailException;
import am.aca.wftartproject.model.*;

import static integration_tests.service.AssertTemplates.assertEqualPurchaseHistory;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

import am.aca.wftartproject.util.DBConnection;
import integration_tests.service.AssertTemplates;
import integration_tests.service.TestObjectTemplate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;


/**
 * Created by Armen on 6/2/2017.
 */
public class PurchaseHistoryDaoIntegrationTest {
    private DBConnection connection = null;
    private UserDao userDao;
    private ItemDao itemDao;
    private PurchaseHistoryDao purchaseHistoryDao;
    private User testUser;
    private Item testItem;
    private PurchaseHistory purchaseHistory;

    public PurchaseHistoryDaoIntegrationTest() throws SQLException, ClassNotFoundException, SQLException {
    }

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        connection = new DBConnection();
        userDao = new UserDaoImpl(connection.getDBConnection(DBConnection.DBType.TEST));
        itemDao = new ItemDaoImpl(connection.getDBConnection(DBConnection.DBType.TEST));
        purchaseHistoryDao = new PurchaseHistoryDaoImpl(connection.getDBConnection(DBConnection.DBType.TEST));

        //create test Item,User, purchaseHistory and set them into db

        testItem = TestObjectTemplate.createTestItem();
        purchaseHistory = new PurchaseHistory();
        testUser = TestObjectTemplate.createTestUser();
        userDao.addUser(testUser);

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

        PurchaseHistory added = purchaseHistoryDao.getPurchaseHistory(testUser.getId(), testItem.getId());
        assertEqualPurchaseHistory(added, purchaseHistory);
        System.out.println(added);
        System.out.println(purchaseHistory);


    }

    /**
     * @see PurchaseHistoryDao#addPurchase(PurchaseHistory)
     */
    @Test(expected = DAOFailException.class)
    public void addPurchase_failure() {

        //check for null purchasehistory

        assertNotNull(purchaseHistory);

        //add purchasehistory into db
        purchaseHistory.setUserId(null);
        purchaseHistoryDao.addPurchase(purchaseHistory);

        //get added purchasehistory from db and check for sameness with origin

        PurchaseHistory added = purchaseHistoryDao.getPurchaseHistory(testUser.getId(), testItem.getId());
        assertEqualPurchaseHistory(added, purchaseHistory);
        System.out.println(added);
        System.out.println(purchaseHistory);


    }

    /**
     * @see PurchaseHistoryDao#getPurchaseHistory(Long, Long)
     */
    @Test
    public void getPurchaseHistory_Success() {

        purchaseHistoryDao.addPurchase(purchaseHistory);

        //find and get purchase item from DB

        PurchaseHistory purchaseItem = purchaseHistoryDao.
                getPurchaseHistory(purchaseHistory.getUserId(), purchaseHistory.getItemId());

        //check for sameness with purchaseHistory

        assertEqualPurchaseHistory(purchaseItem, purchaseHistory);
    }

    /**
     * @see PurchaseHistoryDao#getPurchaseHistory(Long, Long)
     */
    @Test(expected = DAOFailException.class)
    public void getPurchase_Failure() {

        purchaseHistoryDao.addPurchase(purchaseHistory);

        //find and get purchase item from DB

        PurchaseHistory added = purchaseHistoryDao.getPurchaseHistory(null, testItem.getId());

        //check for sameness with purchaseHistory

        assertNull(added.getItemId());
    }

    @Test
    public void deletePurcaseHistory() {

        //check for null purchasehistory

        assertNotNull(purchaseHistory);
        purchaseHistoryDao.addPurchase(purchaseHistory);
        assertNotNull(purchaseHistoryDao.getPurchaseHistory(testUser.getId(), testItem.getId()));

        //add purchasehistory into db

        purchaseHistoryDao.deletePurchaseHistory(testUser.getId(), testItem.getId());

        //get added purchasehistory from db and check for sameness with origin

        PurchaseHistory deleteded = purchaseHistoryDao.getPurchaseHistory(testUser.getId(), testItem.getId());
        assertNull(deleteded.getItemId());
        System.out.println(deleteded);
        System.out.println(purchaseHistory);


    }

    @Test(expected = DAOFailException.class)
    public void deletePurcaseHistory_failure() {

        //check for null purchasehistory;

        assertNotNull(purchaseHistory);

        //add purchasehistory into db get and check for null

        purchaseHistoryDao.addPurchase(purchaseHistory);
        assertNotNull(purchaseHistoryDao.getPurchaseHistory(testUser.getId(), testItem.getId()));

        purchaseHistoryDao.deletePurchaseHistory(null, testItem.getId());

        //get deleted purchasehistory from db and check fields for null

        PurchaseHistory deleteded = purchaseHistoryDao.getPurchaseHistory(testUser.getId(), testItem.getId());
        assertNull(deleteded.getItemId());


    }

    @After
    public void tearDown() {
        //delete inserted test users,artists and items  from db

        if (purchaseHistory.getUserId() != null)
            purchaseHistoryDao.deletePurchaseHistory(testUser.getId(), testItem.getId());

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
