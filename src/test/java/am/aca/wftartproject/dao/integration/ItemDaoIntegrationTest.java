package am.aca.wftartproject.dao.integration;

import am.aca.wftartproject.dao.ItemDao;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.dao.impl.ItemDaoImpl;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.Item;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualItems;
import static am.aca.wftartproject.util.TestObjectTemplate.*;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestArtist;
import static junit.framework.TestCase.*;


/**
 * Created by Armen on 6/1/2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:BeanLocations.xml"})
@EnableTransactionManagement
@Transactional
public class ItemDaoIntegrationTest extends BaseDAOIntegrationTest {

    private static Logger LOGGER = Logger.getLogger(ArtistDaoIntegrationTest.class);

    @Autowired
    private ArtistDaoImpl artistDao;

    @Autowired
    private ItemDaoImpl itemDao;

    private Item testItem, tempItem;
    private Artist testArtist;

    public ItemDaoIntegrationTest() throws SQLException, ClassNotFoundException {
    }

    /**
     * Creates artist and item for tests
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {

        // Create test artist and user
        testArtist = createTestArtist();
        testItem = createTestItem();
        tempItem = createTestItem();

        // Insert test Artist into db, get generated id
        artistDao.addArtist(testArtist);

        testItem.setArtist_id(testArtist.getId());
        tempItem.setArtist_id(testArtist.getId());

        // Add item into db and get generated id
        itemDao.addItem(testItem);



        // Print busy connections quantity
//        if (jdbcTemplate.getDataSource() instanceof ComboPooledDataSource) {
//            LOGGER.info(String.format("Number of busy connections Start: %s",
//                    ((ComboPooledDataSource) jdbcTemplate.getDataSource()).getNumBusyConnections()));
//        }
    }

    /**
     * Deletes all artists and items created during the tests
     *
     * @throws SQLException
     */
    @After
    public void tearDown() throws SQLException {
        // Delete inserted test users,artists and items  from db
        if (testItem.getId() != null)
            itemDao.deleteItem(testItem.getId());

        if (tempItem.getId() != null)
            itemDao.deleteItem(tempItem.getId());

        if (testArtist.getId() != null)
            artistDao.deleteArtist(testArtist);

        // Set testArtist and testItem to null
        testArtist = null;
        tempItem = null;
        testItem = null;

        // Print busy connections quantity
//        if (jdbcTemplate.getDataSource() instanceof ComboPooledDataSource) {
//            LOGGER.info(String.format("Number of busy connections End: %s",
//                    ((ComboPooledDataSource) jdbcTemplate.getDataSource()).getNumBusyConnections()));
//        }
    }

    // region<TEST CASE>

    /**
     * @see ItemDao#addItem(Item)
     */
    @Test
    public void addItem_Success() {
        // Check testItem for null
        assertNotNull(testItem);



        // Find added item from db
        Item item = itemDao.findItem(testItem.getId());

        // Check for sameness
        assertEqualItems(testItem, item);
    }


    /**
     * @see ItemDao#addItem(Item)
     */
    @Test(expected = DAOException.class)
    public void addItem_Failure() {
        // Check testItem for null
        assertNotNull(testItem);
        testItem.setTitle(null);


        // Find added item from db
        Item item = itemDao.findItem(testItem.getId());

        // Check for sameness
        assertEqualItems(testItem, item);
    }

    /**
     * @see ItemDao#findItem(Long)
     */
    @Test
    public void findItem_Success() {

        // Find and get item from db
        Item expectedItem = itemDao.findItem(testItem.getId());

        // Check for null testItem and expectedItem
        assertNotNull(testItem);
        assertEqualItems(expectedItem, testItem);
    }

    /**
     * @see ItemDao#findItem(Long)
     */
    @Test
    public void findItem_Failure() {
        // Try to find and get item by id
        Item foundItem = itemDao.findItem(135984984651L);

        // Check foundItem for null
        assertNull(foundItem);
    }


    /**
     * @see ItemDao#getRecentlyAddedItems(int)
     */
    @Test
    public void getRecentlyAddedItems_Success() {
        // Add items into DB
        itemDao.addItem(tempItem);
        // Get items list and check for not empty when limit is positive
        List<Item> itemList = itemDao.getRecentlyAddedItems(1);

        assertEqualItems(testItem, itemList.get(0));
    }


    /**
     * @see ItemDao#getRecentlyAddedItems(int)
     */
    @Test
    public void getRecentlyAddedItems_Failure() {

        // Get recently added items with 0 limit and check list for empty
        assertTrue(itemDao.getRecentlyAddedItems(0).isEmpty());
    }

    /**
     * @see ItemDao#getItemsByTitle(String)
     */
    @Test
    public void getItemsByTitle_NotEmptyList() {

        // Get items by title from DB and check for not empty
        assertFalse(itemDao.getItemsByTitle(testItem.getTitle()).isEmpty());
    }

    /**
     * @see ItemDao#getItemsByTitle(String)
     */
    @Test
    public void getItemsByTitle_EmptyList() {

        // Get items by title from DB and check list for empty
        assertTrue(itemDao.getItemsByTitle("fake title").isEmpty());
    }

    /**
     * @see ItemDao#getItemsByType(String)
     */
    @Test
    public void getItemsByType_NotEmptyList() {

        // Get items by type from DB and check list for not empty
        assertFalse(itemDao.getItemsByType(testItem.getItemType().getType()).isEmpty());
    }

    /**
     * @see ItemDao#getItemsByType(String)
     */
    @Test
    public void getItemsByType_EmptyList() {

        // Get items by type from DB and check list for empty
        assertTrue(itemDao.getItemsByType("fake type").isEmpty());
    }

    /**
     * @see ItemDao#getItemsForGivenPriceRange(Double, Double)
     */
    @Test
    public void getItemsForGivenPriceRange_NotEmptyList() {
        // Add item into DB
        itemDao.addItem(tempItem);

        // Try to get items for given price range
        assertEqualItems(itemDao.getItemsForGivenPriceRange(tempItem.getPrice() - 10, tempItem.getPrice() + 10).get(0), tempItem);
    }

    /**
     * @see ItemDao#getItemsForGivenPriceRange(Double, Double)
     */
    @Test
    public void getItemsForGivenPriceRange_EmptyList() {

        // Try to get items for given price range
        assertTrue(itemDao.getItemsForGivenPriceRange(-200.0, -100.0).isEmpty());
    }

    /**
     * @see ItemDao#getArtistItems(Long)
     */
    @Test
    public void getArtistItems_NotEmptyList() {
        // Add testArtist's 2 items into DB

        itemDao.addItem(tempItem);

        // Get testArtist's items and check list for not empty
        assertFalse(itemDao.getArtistItems(testArtist.getId()).isEmpty());
    }

    /**
     * @see ItemDao#getArtistItems(Long)
     */
    @Test
    public void getArtistItems_EmptyList() {
        // Add testArtist's 2 items into DB
        itemDao.addItem(tempItem);

        // Get testArtist's items and check list for not empty
        assertTrue(itemDao.getArtistItems(684531L).isEmpty());
    }

    /**
     * @see ItemDao#updateItem(Item)
     */
    @Test
    public void updateItem_Success() {
        // Check testItem for null
        assertNotNull(testItem);

        // Add item into db and get generated id
        testItem.setTitle("test_item");
        itemDao.updateItem(testItem);

        // Find added item from db
        Item item = itemDao.findItem(testItem.getId());

        // Check for sameness
        assertEquals(testItem.getTitle(), item.getTitle());
    }

    /**
     * @see ItemDao#updateItem(Item)
     */
    @Test(expected = DAOException.class)
    public void updateItem_Failure() {
        // Check testItem for null
        assertNotNull(testItem);
        testItem.setArtist_id(testArtist.getId());

        testItem.setTitle(null);
        assertFalse(itemDao.updateItem(testItem));
    }

    /**
     * @see ItemDao#deleteItem(Long)
     */

    @Test
    public void deleteItem_Success() {
        // Add item into db
        // Check item in db for null
        assertNotNull(itemDao.findItem(testItem.getId()));

        // Delete item from db
        assertTrue(itemDao.deleteItem(testItem.getId()));
        testItem.setId(null);
    }

    /**
     * @see ItemDao#deleteItem(Long)
     */
    @Test(expected = DAOException.class)
    public void deleteItem_Failure() {

        // Check item in db for null
        assertNotNull(itemDao.findItem(testItem.getId()));

        // Delete item from db
        itemDao.deleteItem(-7L);

        // Get deleted item from db
        Item deletedItem = itemDao.findItem(testItem.getId());

        // Check for null
        assertNotNull(deletedItem.getId());
    }

    // endregion
}
