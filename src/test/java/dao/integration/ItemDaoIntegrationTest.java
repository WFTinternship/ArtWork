package dao.integration;

import am.aca.wftartproject.dao.ItemDao;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.dao.impl.ItemDaoImpl;
import am.aca.wftartproject.exception.DAOFailException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.util.dbconnection.ConnectionModel;
import am.aca.wftartproject.util.dbconnection.ConnectionFactory;
import util.TestObjectTemplate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static util.AssertTemplates.assertEqualItems;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by Armen on 6/1/2017
 */


public class ItemDaoIntegrationTest {

    private ArtistDaoImpl artistDao;
    private ItemDaoImpl itemDao;
    private Item testItem;
    private Artist testArtist;


    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        //creating new DB connection, artistDao and itemDao implementations

        Connection conn = new ConnectionFactory().getConnection(ConnectionModel.SINGLETON).getTestDBConnection();
        artistDao = new ArtistDaoImpl(conn);
        itemDao = new ItemDaoImpl(conn);

        //create test artist and user

        testArtist = TestObjectTemplate.createTestArtist();
        testItem = TestObjectTemplate.createTestItem();

        //insert test Artist into db, get generated id

        artistDao.addArtist(testArtist);

    }

    /**
     * @see ItemDao#addItem(Long, Item)
     */
    @Test
    public void addItem() {
        //check testItem for null

        assertNotNull(testItem);

        //add item into db and get generated id

        itemDao.addItem(testArtist.getId(), testItem);

        //find added item from db

        Item item = itemDao.findItem(testItem.getId());

        //check for sameness

        assertEqualItems(testItem, item);

    }

    /**
     * @see ItemDao#addItem(Long, Item)
     */
    @Test(expected = DAOFailException.class)
    public void addItem_failure() {

        //check testItem for null

        assertNotNull(testItem);
        testItem.setTitle(null);

        //add item into db and get generated id

        itemDao.addItem(testArtist.getId(), testItem);

        //find added item from db

        Item item = itemDao.findItem(testItem.getId());

        //check for sameness
        assertEqualItems(testItem, item);

    }


    /**
     * @see ItemDao#updateItem(Long, Item)
     */
    @Test
    public void updateitem() {
        //check testItem for null

        assertNotNull(testItem);

        //add item into db and get generated id

        itemDao.addItem(testArtist.getId(), testItem);

        testItem.setTitle("ankap item");
        itemDao.updateItem(testItem.getId(), testItem);


        //find added item from db

        Item item = itemDao.findItem(testItem.getId());

        //check for sameness

        assertEquals(testItem.getTitle(), item.getTitle());

    }

    /**
     * @see ItemDao#updateItem(Long, Item)
     */
    @Test(expected = DAOFailException.class)
    public void updateitem_failure() {
        //check testItem for null

        assertNotNull(testItem);

        //add item into db and get generated id

        itemDao.addItem(testArtist.getId(), testItem);
        testItem.setTitle(null);
        itemDao.updateItem(testItem.getId(), testItem);

        //find added item from db

        Item item = itemDao.findItem(testItem.getId());

        //check for sameness

        assertEquals(testItem.getTitle(), item.getTitle());

    }

    /**
     * @see ItemDao#deleteItem(Long)
     */
    @Test
    public void deleteItem_success() {
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
    public void deleteItem_failure() {

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
    public void findItem_success() {
        //add item into db

        itemDao.addItem(testArtist.getId(), testItem);

        //find and get item from db

        Item expectedItem = itemDao.findItem(testItem.getId());

        //check for null testItem and expecteditem

        assertNotNull(testItem);
        assertEqualItems(expectedItem, testItem);
    }

    @After
    public void tearDown() {

        //delete inserted test users,artists and items  from db

        if (testItem.getId() != null)
            itemDao.deleteItem(testItem.getId());

        if (testArtist.getId() != null)
            artistDao.deleteArtist(testArtist.getId());

        // set testArtist and testItem to null

        testArtist = null;

        testItem = null;
    }
}