package integration_tests.dao;
import am.aca.wftartproject.dao.*;
import am.aca.wftartproject.dao.impl.ItemDaoImpl;
import am.aca.wftartproject.dao.impl.PurchaseHistoryDaoImpl;
import am.aca.wftartproject.dao.impl.UserDaoImpl;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.*;
import static integration_tests.service.AssertTemplates.assertEqualPurchaseHistory;
import static junit.framework.TestCase.*;
import am.aca.wftartproject.util.dbconnection.ConnectionFactory;
import am.aca.wftartproject.util.dbconnection.ConnectionModel;
import integration_tests.service.TestObjectTemplate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * Created by Armen on 6/2/2017.
 */
public class PurchaseHistoryDaoIntegrationTest {
    private UserDao userDao;
    private ItemDao itemDao;
    private PurchaseHistoryDao purchaseHistoryDao;
    private User testUser;
    private Item testItem;
    private PurchaseHistory purchaseHistory;

    public PurchaseHistoryDaoIntegrationTest() throws SQLException, ClassNotFoundException, SQLException {
    }

    @Before
    public void setUp() throws SQLException, ClassNotFoundException
    {

        //create db connection

        Connection conn = new ConnectionFactory()
                .getConnection(ConnectionModel.POOL)
                .getTestDBConnection();
        userDao = new UserDaoImpl(conn);
        itemDao = new ItemDaoImpl(conn);
        purchaseHistoryDao = new PurchaseHistoryDaoImpl(conn);

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
    @Test
    public void getPurchase_Failure() {

        purchaseHistoryDao.addPurchase(purchaseHistory);

        //find and get purchase item from DB

        PurchaseHistory added = purchaseHistoryDao.getPurchase(123456789101112131L, 123456789101112L);

        //check for sameness with purchaseHistory

        assertNull(added);
    }

    @Test
    public void deletePurcaseHistory_success() {

        //check for null purchasehistory

        assertNotNull(purchaseHistory);

        //add purchasehistory into db

        purchaseHistoryDao.addPurchase(purchaseHistory);

        assertNotNull(purchaseHistoryDao.getPurchase(testUser.getId(), testItem.getId()));



        assertTrue(purchaseHistoryDao.deletePurchase(testUser.getId(), testItem.getId()));

        purchaseHistory.setUserId(null);


    }

    @Test
    public void deletePurcaseHistory_failure() {

        //check for null purchasehistory;

        assertNotNull(purchaseHistory);

        //add purchasehistory into db get and check for null

        purchaseHistoryDao.addPurchase(purchaseHistory);
        assertNotNull(purchaseHistoryDao.getPurchase(testUser.getId(), testItem.getId()));

        assertFalse(purchaseHistoryDao.deletePurchase(1456325896615358332L, testItem.getId()));



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
