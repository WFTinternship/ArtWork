package integration.dao;

import am.aca.wftartproject.dao.*;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.dao.impl.ItemDaoImpl;
import am.aca.wftartproject.dao.impl.UserDaoImpl;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.util.DBConnection;
import integration.service.TestObjectTemplate;

import static integration.service.AssertTemplates.assertEqualItems;
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
    private DBConnection connection = new DBConnection();
    private ArtistDaoImpl artistDao = new ArtistDaoImpl(connection.getDBConnection(DBConnection.DBType.TEST));
    private ItemDaoImpl itemDao = new ItemDaoImpl(connection.getDBConnection(DBConnection.DBType.TEST));
    private Item testItem;
    private Artist testArtist;
    private User testUser;
    private UserDaoImpl userDao = new UserDaoImpl(connection.getDBConnection(DBConnection.DBType.TEST));


    public ItemDaoIntegrationTest() throws SQLException, ClassNotFoundException {
    }

    @Before
    public void setUp() {
        //create test artist and user

        testItem = TestObjectTemplate.createTestItem();
        testUser = TestObjectTemplate.createTestUser();

        //insert test user into db, get generated id

        userDao.addUser(testUser);

        //create test artist and set fields

        testArtist = new Artist();
        testArtist.setSpecialization(ArtistSpecialization.PHOTOGRAPHER);

        //insert test artist  into db, get generated id

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

        assertEqualItems(item, testItem);
    }

    /**
     * @see ItemDao#deleteItem(Long)
     */
    @Test
    public void deleteItem() {
        //add item into db

        itemDao.addItem(testArtist.getId(), testItem);
        //check item in db for null

        assertNotNull(itemDao.findItem(testItem.getId()));

        //delete item from db

        itemDao.deleteItem(testItem.getId());

        //get deleted item from db

        Item deletedItem = itemDao.findItem(testItem.getId());

        //check for null

        assertNull(deletedItem);
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

        //check for null testitem and expecteditem

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

        if (testUser.getId() != null)
            userDao.deleteUser(testUser.getId());

        testArtist = null;
        testUser = null;
        testItem = null;
    }
}
