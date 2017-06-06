package integration.dao;

import am.aca.wftartproject.dao.*;
import am.aca.wftartproject.dao.impl.ItemDaoImpl;
import am.aca.wftartproject.dao.impl.PurchaseHistoryDaoImpl;
import am.aca.wftartproject.dao.impl.UserDaoImpl;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.*;
import am.aca.wftartproject.util.DBConnection;
import integration.service.TestObjectTemplate;

import static integration.service.AssertTemplates.assertEqualPurchaseHistory;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by Armen on 6/2/2017
 */
public class PurchaseHistoryDaoIntegrationTest {
    private DBConnection connection;
    private UserDao userDao;
    private ItemDao itemDao;
    private PurchaseHistoryDao purchaseHistoryDao;
    private User testUser;
    private Item testItem;
    private PurchaseHistory purchaseHistory;

    public PurchaseHistoryDaoIntegrationTest() throws SQLException, ClassNotFoundException, SQLException {
        connection = null;
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

        //check for null purchaseHistory

        assertNotNull(purchaseHistory);

        //add purchaseHistory into db

        purchaseHistoryDao.addPurchase(purchaseHistory);

        //get added purchaseHistory from db and check for sameness with origin

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

        //check for null purchaseHistory

        assertNotNull(purchaseHistory);

        //add purchaseHistory into db
        purchaseHistory.setUserId(null);
        purchaseHistoryDao.addPurchase(purchaseHistory);

        //get added purchaseHistory from db and check for sameness with origin

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
    public void deletePurchaseHistory() {

        //check for null purchaseHistory

        assertNotNull(purchaseHistory);
        purchaseHistoryDao.addPurchase(purchaseHistory);
        assertNotNull(purchaseHistoryDao.getPurchase(testUser.getId(), testItem.getId()));

        //add purchaseHistory into db

        purchaseHistoryDao.deletePurchase(testUser.getId(), testItem.getId());

        //get added purchaseHistory from db and check for sameness with origin

        PurchaseHistory deleted = purchaseHistoryDao.getPurchase(testUser.getId(), testItem.getId());
        assertNull(deleted.getItemId());
        System.out.println(deleted);
        System.out.println(purchaseHistory);


    }

    @Test(expected = DAOException.class)
    public void deletePurchaseHistory_failure() {

        //check for null purchaseHistory;

        assertNotNull(purchaseHistory);

        //add purchaseHistory into db get and check for null

        purchaseHistoryDao.addPurchase(purchaseHistory);
        assertNotNull(purchaseHistoryDao.getPurchase(testUser.getId(), testItem.getId()));

        purchaseHistoryDao.deletePurchase(null, testItem.getId());

        //get deleted purchaseHistory from db and check fields for null

        PurchaseHistory deleted = purchaseHistoryDao.getPurchase(testUser.getId(), testItem.getId());
        assertNull(deleted.getItemId());


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
