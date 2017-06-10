package integration.dao;

import am.aca.wftartproject.dao.ArtistSpecializationLkpDao;
import am.aca.wftartproject.dao.ItemDao;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.dao.impl.ArtistSpecializationLkpDaoImpl;
import am.aca.wftartproject.dao.impl.ItemDaoImpl;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.util.dbconnection.ConnectionFactory;
import am.aca.wftartproject.util.dbconnection.ConnectionModel;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.AssertTemplates;
import util.TestObjectTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

import static junit.framework.TestCase.*;
import static util.AssertTemplates.assertEqualItems;

/**
 * Created by Armen on 6/1/2017
 */
public class ItemDaoIntegrationTest {

    private Logger LOGGER = Logger.getLogger(ArtistDaoIntegrationTest.class);
    private DataSource conn;

    private ArtistDaoImpl artistDao;
    private ItemDaoImpl itemDao;
    private Item testItem;
    private Artist testArtist;
    ArtistSpecializationLkpDao artistSpecialization;

    public ItemDaoIntegrationTest() throws SQLException, ClassNotFoundException {
    }

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        //creating new DB connection, artisdao and itemdao imlementations

        conn = new ConnectionFactory()
                .getConnection(ConnectionModel.POOL)
                .getTestDBConnection();
        artistSpecialization = new ArtistSpecializationLkpDaoImpl(conn);

        if (artistSpecialization.getArtistSpecialization(1) == null) {
            artistSpecialization.addArtistSpecialization();
        }
        artistDao = new ArtistDaoImpl(conn);
        itemDao = new ItemDaoImpl(conn);

        //create test artist and user

        testArtist = TestObjectTemplate.createTestArtist();
        testItem = TestObjectTemplate.createTestItem();

        //insert test Artist into db, get generated id

        artistDao.addArtist(testArtist);

        if (conn instanceof ComboPooledDataSource) {
            LOGGER.info(((ComboPooledDataSource) conn).getNumBusyConnections());
        }
    }

    /**
     * @see ItemDao#addItem(Long, Item)
     */
    @Test
    public void addItem_Success() {
        //check testItem for null

        assertNotNull(testItem);

        //add item into db and get generated id

        itemDao.addItem(testArtist.getId(), testItem);

        //find added item from db

        Item item = itemDao.findItem(testItem.getId());

        //check for sameness

        AssertTemplates.assertEqualItems(testItem, item);

    }

    /**
     * @see ItemDao#addItem(Long, Item)
     */
    @Test(expected = DAOException.class)
    public void addItem_Failure() {

        //check testItem for null

        assertNotNull(testItem);
        testItem.setTitle(null);

        //add item into db and get generated id

        itemDao.addItem(testArtist.getId(), testItem);

        //find added item from db

        Item item = itemDao.findItem(testItem.getId());

        //check for sameness
        AssertTemplates.assertEqualItems(testItem, item);

    }


    /**
     * @see ItemDao#updateItem(Long, Item)
     */
    @Test
    public void updateItem_Success() {
        //check testItem for null

        assertNotNull(testItem);

        //add item into db and get generated id

        itemDao.addItem(testArtist.getId(), testItem);
        System.out.println(testItem.getId());
        testItem.setTitle("ankap item");
        itemDao.updateItem(testItem.getId(), testItem);
        System.out.println(itemDao.findItem(testItem.getId()));

        //find added item from db

        Item item = itemDao.findItem(testItem.getId());
        System.out.println(testItem.getTitle());
        System.out.println(item.getTitle());

        //check for sameness

        assertEquals(testItem.getTitle(), item.getTitle());

    }

    /**
     * @see ItemDao#updateItem(Long, Item)
     */
    @Test(expected = DAOException.class)
    public void updateItem_Failure() {
        //check testItem for null

        assertNotNull(testItem);

        //add item into db and get generated id

        itemDao.addItem(testArtist.getId(), testItem);
        System.out.println(testItem.getId());
        testItem.setTitle(null);
        itemDao.updateItem(testItem.getId(), testItem);
        System.out.println(itemDao.findItem(testItem.getId()));

        //find added item from db

        Item item = itemDao.findItem(testItem.getId());
        System.out.println(testItem.getTitle());
        System.out.println(item.getTitle());

        //check for sameness

        assertEquals(testItem.getTitle(), item.getTitle());

    }

    /**
     * @see ItemDao#deleteItem(Long)
     */
    @Test
    public void deleteItem_Success() {
        //add item into db

        itemDao.addItem(testArtist.getId(), testItem);

        //check item in db for null

        assertNotNull(itemDao.findItem(testItem.getId()));

        //delete item from db

        itemDao.deleteItem(testItem.getId());

        //get deleted item from db

        Item deletedItem = itemDao.findItem(testItem.getId());

        //check for null

        assertNull(deletedItem.getId());
    }

    /**
     * @see ItemDao#deleteItem(Long)
     */
    @Test
    public void deleteItem_Failure() {

        //add item into db

        itemDao.addItem(testArtist.getId(), testItem);
        //check item in db for null

        assertNotNull(itemDao.findItem(testItem.getId()));

        //delete item from db

        itemDao.deleteItem(-7L);


        //get deleted item from db

        Item deletedItem = itemDao.findItem(testItem.getId());

        //check for null

        assertNotNull(deletedItem.getId());
    }

    /**
     * @see ItemDao#findItem(Long)
     */
    @Test
    public void findItem_Success() {
        //add item into db

        itemDao.addItem(testArtist.getId(), testItem);

        //find and get item from db

        Item expectedItem = itemDao.findItem(testItem.getId());

        //check for null testItem and expecteditem

        assertNotNull(testItem);
        assertEqualItems(expectedItem, testItem);
    }

    @After
    public void tearDown() throws SQLException {

        //delete inserted test users,artists and items  from db

        if (testItem.getId() != null)
            itemDao.deleteItem(testItem.getId());

        if (testArtist.getId() != null)
            artistDao.deleteArtist(testArtist.getId());

        // set testArtist and testItem to null

        testArtist = null;

        testItem = null;

        if (conn instanceof ComboPooledDataSource) {
            LOGGER.info(((ComboPooledDataSource) conn).getNumBusyConnections());
        }
    }
}
