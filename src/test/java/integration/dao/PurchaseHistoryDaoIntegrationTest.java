package integration.dao;

import am.aca.wftartproject.dao.*;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.dao.impl.ItemDaoImpl;
import am.aca.wftartproject.dao.impl.PurchaseHistoryDaoImpl;
import am.aca.wftartproject.dao.impl.UserDaoImpl;
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
import java.util.List;

/**
 * Created by Armen on 6/2/2017
 */
public class PurchaseHistoryDaoIntegrationTest {
    private DBConnection connection = new DBConnection();
    private UserDaoImpl userDao = new UserDaoImpl(connection.getDBConnection(DBConnection.DBType.TEST));
    private ItemDaoImpl itemDao = new ItemDaoImpl(connection.getDBConnection(DBConnection.DBType.TEST));
    private PurchaseHistoryDaoImpl purchaseHistoryDao = new PurchaseHistoryDaoImpl(connection.getDBConnection(DBConnection.DBType.TEST));
    private User testUser;
    private Artist artist;
    private ArtistDaoImpl artistDao = new ArtistDaoImpl(connection.getDBConnection(DBConnection.DBType.TEST));
    private Item item;
    private PurchaseHistory purchaseHistory;
    private ShoppingCard testShoppingCard;

    public PurchaseHistoryDaoIntegrationTest() throws SQLException, ClassNotFoundException {
    }

    @Before
    public void setUp() {
        //create test user
        artist = new Artist();
        item = TestObjectTemplate.createTestItem();
        purchaseHistory = new PurchaseHistory();
        artist = TestObjectTemplate.createTestArtist();
        artistDao.addArtist(artist);
        itemDao.addItem(artist.getId(), item);
        purchaseHistory.setItemId(item.getId());
        purchaseHistory.setUserId(artist.getId());
        purchaseHistory.setPurchaseDate(new java.sql.Date(new java.util.Date().getTime()));
    }

    /**
     * @see PurchaseHistoryDao#addPurchase(PurchaseHistory)
     */
    @Test
    public void addPurchase_Success() {

        //check for null purchaseHistory
        assertNotNull(purchaseHistory);

        //add purchaseHistory into db
        purchaseHistoryDao.addPurchase(purchaseHistory);

        //get added purchaseHistory from db and check for sameness with origin
        PurchaseHistory added = purchaseHistoryDao.
                getPurchase(purchaseHistory.getUserId(), purchaseHistory.getItemId());

        assertEqualPurchaseHistory(added, purchaseHistory);

    }

    /**
     * @see PurchaseHistoryDao#getPurchase(Long, Long)
     */
    @Test
    public void getPurchaseHistory_Success(){

        purchaseHistoryDao.addPurchase(purchaseHistory);

        //find and get purchase item from DB
        PurchaseHistory purchaseItem = purchaseHistoryDao.
                getPurchase(purchaseHistory.getUserId(), purchaseHistory.getItemId());
        //check for sameness with purchaseHistory
        assertEqualPurchaseHistory(purchaseItem, purchaseHistory);
    }

    /**
     * @see PurchaseHistoryDao#getPurchase(Long)
     */
    @Test
    public void getPurchase_Success(){

        purchaseHistoryDao.addPurchase(purchaseHistory);

        //find and get purchase item from DB
        List<PurchaseHistory> purchaseItem = purchaseHistoryDao.getPurchase(purchaseHistory.getUserId());

        //check for sameness with purchaseHistory
        assertEqualPurchaseHistory(purchaseItem.get(0), purchaseHistory);
    }

    /**
     * @see PurchaseHistoryDao#deletePurchase(Long, Long)
     */
    @Test
    public void deletePurchase_Success(){

        //add purchase item into the database
        purchaseHistoryDao.addPurchase(purchaseHistory);

        //delete the purchaseHistory
        purchaseHistoryDao.deletePurchase(purchaseHistory.getUserId(), purchaseHistory.getItemId());

        //check delete
        PurchaseHistory deletedPurchaseHistory =
                purchaseHistoryDao.getPurchase(purchaseHistory.getUserId(), purchaseHistory.getItemId());
        assertNull(deletedPurchaseHistory);

    }



    @After
    public void tearDown() {
        //delete inserted test users,artists and items  from db

        if (purchaseHistory.getItemId() != null)
            purchaseHistoryDao.
                    deletePurchase(purchaseHistory.getUserId(), purchaseHistory.getItemId());

        if (item.getId() != null)
            itemDao.deleteItem(item.getId());

        if (artist.getId() != null)
            artistDao.deleteArtist(artist.getId());

        artist = null;

    }
}
