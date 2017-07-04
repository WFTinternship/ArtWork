package am.aca.wftartproject.dao.integration;

import am.aca.wftartproject.dao.ArtistSpecializationLkpDao;
import am.aca.wftartproject.dao.ItemDao;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.dao.impl.ArtistSpecializationLkpDaoImpl;
import am.aca.wftartproject.dao.impl.ItemDaoImpl;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.Item;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.SQLException;
import java.util.List;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualItems;
import static am.aca.wftartproject.util.TestObjectTemplate.*;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestArtist;
import static junit.framework.TestCase.*;


/**
 * Created by Armen on 6/1/2017
 */

public class ItemDaoIntegrationTest extends BaseDAOIntegrationTest{

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
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        // Create artistSpecialization
        ArtistSpecializationLkpDao artistSpecialization = new ArtistSpecializationLkpDaoImpl(jdbcTemplate);

        if (artistSpecialization.getArtistSpecialization(1) == null) {
            artistSpecialization.addArtistSpecialization();
        }

        // Create test artist and user
        testArtist = createTestArtist();
        testItem = createTestItem();
        tempItem = createTestItem();

        // Insert test Artist into db, get generated id
        artistDao.addArtist(testArtist);

        // Print busy connections quantity
        if (jdbcTemplate.getDataSource() instanceof ComboPooledDataSource) {
            LOGGER.info(String.format("Number of busy connections Start: %s",
                    ((ComboPooledDataSource) jdbcTemplate.getDataSource()).getNumBusyConnections()));
        }
    }

    /**
     * Deletes all artists and items created during the tests
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
            artistDao.deleteArtist(testArtist.getId());

        // Set testArtist and testItem to null
        testArtist = null;
        tempItem = null;
        testItem = null;

        // Print busy connections quantity
        if (jdbcTemplate.getDataSource() instanceof ComboPooledDataSource) {
            LOGGER.info(String.format("Number of busy connections End: %s",
                    ((ComboPooledDataSource) jdbcTemplate.getDataSource()).getNumBusyConnections()));
        }
    }

    // region<TEST CASE>

    /**
     * @see ItemDao#addItem(Long, Item)
     */
    @Test
    public void addItem_Success() {
        // Check testItem for null
        assertNotNull(testItem);

        // Add item into db and get generated id
        itemDao.addItem(testArtist.getId(), testItem);

        // Find added item from db
        Item item = itemDao.findItem(testItem.getId());

        // Check for sameness
        assertEqualItems(testItem, item);
    }


    /**
     * @see ItemDao#addItem(Long, Item)
     */
    @Test(expected = DAOException.class)
    public void addItem_Failure() {
        // Check testItem for null
        assertNotNull(testItem);
        testItem.setTitle(null);

        // Add item into db and get generated id
        itemDao.addItem(testArtist.getId(), testItem);

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
        // Add item into db
        itemDao.addItem(testArtist.getId(), testItem);

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
    public void findItem_Failure(){
        // Add item into DB
        itemDao.addItem(testArtist.getId(), testItem);

        // Try to find and get item by id
        Item foundItem = itemDao.findItem(135984984651L);

        // Check foundItem for null
        assertNull(foundItem);
    }


    /**
     * @see ItemDao#getRecentlyAddedItems(int)
     */
    @Test
    public void getRecentlyAddedItems_Success(){
        // Add items into DB
        itemDao.addItem(testArtist.getId(), tempItem);
        itemDao.addItem(testArtist.getId(), testItem);

        // Get items list and check for not empty when limit is positive
        List<Item> itemList = itemDao.getRecentlyAddedItems(1);

        assertEqualItems(testItem, itemList.get(0));
    }


    /**
     * @see ItemDao#getRecentlyAddedItems(int)
     */
    @Test
    public void getRecentlyAddedItems_Failure(){
        // Add item into DB
        itemDao.addItem(testArtist.getId(), testItem);

        // Get recently added items with 0 limit and check list for empty
        assertTrue(itemDao.getRecentlyAddedItems(0).isEmpty());
    }

    /**
     * @see ItemDao#getItemsByTitle(String)
     */
    @Test
    public void getItemsByTitle_NotEmptyList(){
        // Add item into DB
        itemDao.addItem(testArtist.getId(), testItem);

        // Get items by title from DB and check for not empty
        assertFalse(itemDao.getItemsByTitle(testItem.getTitle()).isEmpty());
    }

    /**
     * @see ItemDao#getItemsByTitle(String)
     */
    @Test
    public void getItemsByTitle_EmptyList(){
        // Add item into DB
        itemDao.addItem(testArtist.getId(), testItem);

        // Get items by title from DB and check list for empty
        assertTrue(itemDao.getItemsByTitle("fake title").isEmpty());
    }

    /**
     * @see ItemDao#getItemsByType(String)
     */
    @Test
    public void getItemsByType_NotEmptyList(){
        // Add item into DB
        itemDao.addItem(testArtist.getId(), testItem);

        // Get items by type from DB and check list for not empty
        assertFalse(itemDao.getItemsByType(testItem.getItemType().getType()).isEmpty());
    }

    /**
     * @see ItemDao#getItemsByType(String)
     */
    @Test
    public void getItemsByType_EmptyList(){
        // Add item into DB
        itemDao.addItem(testArtist.getId(), testItem);

        // Get items by type from DB and check list for empty
        assertTrue(itemDao.getItemsByType("fake type").isEmpty());
    }

    /**
     * @see ItemDao#getItemsForGivenPriceRange(Double, Double)
     */
    @Test
    public void getItemsForGivenPriceRange_NotEmptyList(){
        // Add item into DB
        itemDao.addItem(testArtist.getId(), tempItem);

        // Try to get items for given price range
        assertEqualItems(itemDao.getItemsForGivenPriceRange(tempItem.getPrice() - 10, tempItem.getPrice() + 10).get(0), tempItem);
    }

    /**
     * @see ItemDao#getItemsForGivenPriceRange(Double, Double)
     */
    @Test
    public void getItemsForGivenPriceRange_EmptyList(){
        // Add item into DB
        itemDao.addItem(testArtist.getId(), testItem);

        // Try to get items for given price range
        assertTrue(itemDao.getItemsForGivenPriceRange(-200.0, -100.0).isEmpty());
    }

    /**
     * @see ItemDao#getArtistItems(Long, Long, Long)
     */
    @Test
    public void getArtistItems_NotEmptyList(){
        // Add testArtist's 2 items into DB
        itemDao.addItem(testArtist.getId(), testItem);
        itemDao.addItem(testArtist.getId(), tempItem);

        // Get testArtist's items and check list for not empty
        assertFalse(itemDao.getArtistItems(testArtist.getId(), testItem.getId(), 3L).isEmpty());
    }

    /**
     * @see ItemDao#getArtistItems(Long, Long, Long)
     */
    @Test
    public void getArtistItems_EmptyList(){
        // Add testArtist's 2 items into DB
        itemDao.addItem(testArtist.getId(), testItem);
        itemDao.addItem(testArtist.getId(), tempItem);

        // Get testArtist's items and check list for not empty
        assertTrue(itemDao.getArtistItems(684531L, testItem.getId(), 3L).isEmpty());
    }

    /**
     * @see ItemDao#getAvailableItemsForGivenArtist(Long)
     */
    @Test
    public void getAvailableItemsForGivenArtist_NotEmptyList() {
        // Add testArtist's 2 items into DB
        itemDao.addItem(testArtist.getId(), testItem);
        itemDao.addItem(testArtist.getId(), tempItem);

        // Get available items list and check not empty
        List<Item> availableItems = itemDao.getAvailableItemsForGivenArtist(testArtist.getId());
        assertEqualItems(testItem, availableItems.get(0));
        assertEqualItems(tempItem, availableItems.get(1));
    }

    /**
     * @see ItemDao#getAvailableItemsForGivenArtist(Long)
     */
    @Test
    public void getAvailableItemsForGivenArtist_EmptyList() {
        testItem.setStatus(true);
        tempItem.setStatus(true);

        // Add testArtist's 2 items into DB
        itemDao.addItem(testArtist.getId(), testItem);
        itemDao.addItem(testArtist.getId(), tempItem);

        List availableItems = itemDao.getAvailableItemsForGivenArtist(testArtist.getId());
        assertTrue(availableItems.isEmpty());
    }


    /**
     * @see ItemDao#updateItem(Long, Item)
     */
    @Test
    public void updateItem_Success() {
        // Check testItem for null
        assertNotNull(testItem);

        // Add item into db and get generated id
        itemDao.addItem(testArtist.getId(), testItem);
        testItem.setTitle("ankap item");
        itemDao.updateItem(testItem.getId(), testItem);

        // Find added item from db
        Item item = itemDao.findItem(testItem.getId());

        // Check for sameness
        assertEquals(testItem.getTitle(), item.getTitle());
    }

    /**
     * @see ItemDao#updateItem(Long, Item)
     */
    @Test(expected = DAOException.class)
    public void updateItem_Failure() {
        // Check testItem for null
        assertNotNull(testItem);

        // Add item into db and get generated id
        itemDao.addItem(testArtist.getId(), testItem);
        testItem.setTitle(null);
        itemDao.updateItem(testItem.getId(), testItem);

        // Find added item from db
        Item item = itemDao.findItem(testItem.getId());

        // Check for sameness
        assertEquals(testItem.getTitle(), item.getTitle());
    }

    /**
     * @see ItemDao#deleteItem(Long)
     */

    @Test
    public void deleteItem_Success() {
        // Add item into db
        itemDao.addItem(testArtist.getId(), testItem);

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
        // Add item into db
        itemDao.addItem(testArtist.getId(), testItem);

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
