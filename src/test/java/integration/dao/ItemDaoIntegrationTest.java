package integration.dao;

import am.aca.wftartproject.dao.*;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.dao.impl.ItemDaoImpl;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.util.DBConnection;
import integration.service.AssertTemplates;
import integration.service.TestObjectTemplate;

import static integration.service.AssertTemplates.assertEqualItems;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by Armen on 6/1/2017
 */
public class ItemDaoIntegrationTest {
    private DBConnection connection;
    private ArtistDaoImpl artistDao;
    private ItemDaoImpl itemDao;
    private Item testitem;
    private Artist testArtist;


    public ItemDaoIntegrationTest() throws SQLException, ClassNotFoundException {
        connection = null;
    }

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        //creating new DB connection, artistDao and itemDao implementations

        connection = new DBConnection();
        artistDao = new ArtistDaoImpl(connection.getDBConnection(DBConnection.DBType.TEST));
        itemDao = new ItemDaoImpl(connection.getDBConnection(DBConnection.DBType.TEST));

        //create test artist and user

        testArtist = TestObjectTemplate.createTestArtist();
        testitem = TestObjectTemplate.createTestItem();

        //insert test Artist into db, get generated id

        artistDao.addArtist(testArtist);

    }

    /**
     * @see ItemDao#addItem(Long, Item)
     */
    @Test
    public void addItem() {
        //check testItem for null

        assertNotNull(testitem);

        //add item into db and get generated id

        itemDao.addItem(testArtist.getId(), testitem);

        //find added item from db

        Item item = itemDao.findItem(testitem.getId());

        //check for sameness

        AssertTemplates.assertEqualItems(testitem, item);

    }

    /**
     * @see ItemDao#addItem(Long, Item)
     */
    @Test(expected = DAOException.class)
    public void addItem_failure() {

        //check testItem for null

        assertNotNull(testitem);
        testitem.setTitle(null);

        //add item into db and get generated id

        itemDao.addItem(testArtist.getId(), testitem);

        //find added item from db

        Item item = itemDao.findItem(testitem.getId());

        //check for sameness
        AssertTemplates.assertEqualItems(testitem, item);

    }


    /**
     * @see ItemDao#updateItem(Long, Item)
     */
    @Test
    public void updateItem() {
        //check testItem for null

        assertNotNull(testitem);

        //add item into db and get generated id

        itemDao.addItem(testArtist.getId(), testitem);
        System.out.println(testitem.getId());
        testitem.setTitle("ankap item");
        itemDao.updateItem(testitem.getId(), testitem);
        System.out.println(itemDao.findItem(testitem.getId()));

        //find added item from db

        Item item = itemDao.findItem(testitem.getId());
        System.out.println(testitem.getTitle());
        System.out.println(item.getTitle());

        //check for sameness

        assertEquals(testitem.getTitle(), item.getTitle());

    }

    /**
     * @see ItemDao#updateItem(Long, Item)
     */
    @Test(expected = DAOException.class)
    public void updateItem_failure() {
        //check testItem for null

        assertNotNull(testitem);

        //add item into db and get generated id

        itemDao.addItem(testArtist.getId(), testitem);
        System.out.println(testitem.getId());
        testitem.setTitle(null);
        itemDao.updateItem(testitem.getId(), testitem);
        System.out.println(itemDao.findItem(testitem.getId()));

        //find added item from db

        Item item = itemDao.findItem(testitem.getId());
        System.out.println(testitem.getTitle());
        System.out.println(item.getTitle());

        //check for sameness

        assertEquals(testitem.getTitle(), item.getTitle());

    }

    /**
     * @see ItemDao#deleteItem(Long)
     */
    @Test
    public void deleteItem_success() {
        //add item into db

        itemDao.addItem(testArtist.getId(), testitem);

        //check item in db for null

        assertNotNull(itemDao.findItem(testitem.getId()));

        //delete item from db

        itemDao.deleteItem(testitem.getId());

        //get deleted item from db

        Item deletedItem = itemDao.findItem(testitem.getId());

        //check for null

        assertNull(deletedItem.getId());
    }

    /**
     * @see ItemDao#deleteItem(Long)
     */
    @Test
    public void deleteItem_failure() {

        //add item into db

        itemDao.addItem(testArtist.getId(), testitem);
        //check item in db for null

        assertNotNull(itemDao.findItem(testitem.getId()));

        //delete item from db

        itemDao.deleteItem(-7L);


        //get deleted item from db

        Item deletedItem = itemDao.findItem(testitem.getId());

        //check for null

        assertNotNull(deletedItem.getId());
    }

    /**
     * @see ItemDao#findItem(Long)
     */
    @Test
    public void findItem_success() {
        //add item into db

        itemDao.addItem(testArtist.getId(), testitem);

        //find and get item from db

        Item expectedItem = itemDao.findItem(testitem.getId());

        //check for null testItem and expectedItem

        assertNotNull(testitem);
        assertEqualItems(expectedItem, testitem);
    }

    @After
    public void tearDown() {

        //delete inserted test users,artists and items  from db

        if (testitem.getId() != null)
            itemDao.deleteItem(testitem.getId());

        if (testArtist.getId() != null)
            artistDao.deleteArtist(testArtist.getId());

        // set testArtist and testItem to null

        testArtist = null;

        testitem = null;
    }
}
