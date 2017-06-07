package integration_tests.dao;

import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.util.dbconnection.ConnectionFactory;
import am.aca.wftartproject.util.dbconnection.ConnectionModel;
import integration_tests.service.*;
import am.aca.wftartproject.dao.*;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.dao.impl.ItemDaoImpl;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.Item;
import static integration_tests.service.AssertTemplates.assertEqualItems;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Armen on 6/1/2017.
 */
public class ItemDaoIntegrationTest {

    private ArtistDaoImpl artistDao;
    private ItemDaoImpl itemDao;
    private Item testitem;
    private Artist testArtist;


    public ItemDaoIntegrationTest() throws SQLException, ClassNotFoundException {
    }

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        //creating new DB connectio, artisdao and itemdao imlementations

        Connection conn = new ConnectionFactory()
                .getConnection(ConnectionModel.POOL)
                .getTestDBConnection();
        artistDao = new ArtistDaoImpl(conn);
        itemDao = new ItemDaoImpl(conn);

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
        //check testitem for null

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

        //check testitem for null

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
    public void updateitem() {
        //check testitem for null

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
    public void updateitem_failure() {
        //check testitem for null

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

        //check for null testitem and expecteditem

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
