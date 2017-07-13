package am.aca.wftartproject.dao.integration;

import am.aca.wftartproject.entity.ItemType;
import am.aca.wftartproject.repository.ArtistRepo;
import am.aca.wftartproject.repository.ItemRepo;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.entity.Artist;
import am.aca.wftartproject.entity.Item;
import am.aca.wftartproject.util.TestObjectTemplate;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualItems;
import static am.aca.wftartproject.util.TestObjectTemplate.*;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestArtist;
import static junit.framework.TestCase.*;


/**
 * Created by Armen on 6/1/2017
 */

public class ItemRepoIntegrationTest extends BaseDAOIntegrationTest {

    private static Logger LOGGER = Logger.getLogger(ItemRepoIntegrationTest.class);

    @Autowired
    private ArtistRepo artistRepo;

    @Autowired
    private ItemRepo itemDao;

    private Item testItem, tempItem;
    private Artist testArtist;

    public ItemRepoIntegrationTest() throws SQLException, ClassNotFoundException {
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
        artistRepo.saveAndFlush(testArtist);

        testItem.setArtist(testArtist);
        tempItem.setArtist(testArtist);

        // Add item into db and get generated id
        itemDao.saveAndFlush(testItem);



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
            itemDao.delete(testItem);

        if (tempItem.getId() != null)
            itemDao.delete(tempItem);

        if (testArtist.getId() != null)
            artistRepo.delete(testArtist);

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
     * @see ItemRepo#saveAndFlush(Object)
     */
    @Test
    public void saveAndFlush_Success() {
        // Check testItem for null
        assertNotNull(testItem.getId());

        // Find added item from db
        Item item = itemDao.findOne(testItem.getId());

        // Check for sameness
        assertEqualItems(testItem, item);
    }


    /**
     * @see ItemRepo#saveAndFlush(Object) (Item)
     */
    @Test(expected = DAOException.class)
    public void saveAndFlush_Failure() {
        // Check testItem for null
        Item testitem2 = TestObjectTemplate.createTestItem();
        testitem2.setDescription(null);
        try{
            itemDao.saveAndFlush(testitem2);
        }catch (Exception e){
            throw new DAOException(e.getMessage());
        }

    }

    /**
     * @see ItemRepo#findOne(Serializable)
     */
    @Test
    public void findItem_Success() {

        // Find and get item from db
        Item expectedItem = itemDao.findOne(testItem.getId());

        // Check for null testItem and expectedItem
        assertNotNull(testItem);
        assertEqualItems(expectedItem, testItem);
    }

    /**
     * @see ItemRepo#findOne(Serializable)
     */
    @Test
    public void findItem_Failure() {
        // Try to find and get item by id
        Item foundItem = itemDao.findOne(135984984651L);

        // Check foundItem for null
        assertNull(foundItem);
    }


    /**
     * @see ItemRepo#findFirst10ByOrderByAdditionDate()
     */
    @Test
    public void getRecentlyAddedItems_Success() {
        // Add items into DB
        itemDao.saveAndFlush(tempItem);
        // Get items list and check for not empty when limit is positive
        List<Item> itemList = itemDao.findFirst10ByOrderByAdditionDate();

        assertEqualItems(tempItem, itemList.get(itemList.size()-1));
    }


    /**
     * @see ItemRepo#findFirst10ByOrderByAdditionDate()
     */
    @Test
    public void getRecentlyAddedItems_Failure() {
        itemDao.deleteAll();
        // Get recently added items with 0 limit and check list for empty
        assertTrue(itemDao.findFirst10ByOrderByAdditionDate().isEmpty());
    }

    /**
     * @see ItemRepo#getAllByTitle(String)
     */
    @Test
    public void getItemsByTitle_NotEmptyList() {

        // Get items by title from DB and check for not empty
        assertFalse(itemDao.getAllByTitle(testItem.getTitle()).isEmpty());
    }

    /**
     * @see ItemRepo#getAllByTitle(String) (String)
     */
    @Test
    public void getItemsByTitle_EmptyList() {

        // Get items by title from DB and check list for empty
        assertTrue(itemDao.getAllByTitle("fake title").isEmpty());
    }

    /**
     * @see ItemRepo#getAllByItemType(ItemType) (am.aca.wftartproject.entity.ItemType)
     */
    @Test
    public void getItemsByType_NotEmptyList() {

        // Get items by type from DB and check list for not empty
        assertFalse(itemDao.getAllByItemType(testItem.getItemType()).isEmpty());
    }

//    /**
//     * @see ItemRepo#getItemsByType(String)
//     */
//    @Test
//    public void getItemsByType_EmptyList() {
//
//        // Get items by type from DB and check list for empty
//        assertTrue(itemDao.getItemsByType("fake type").isEmpty());
//    }

    /**
     * @see ItemRepo#getAllByPriceBetween(Double, Double)
     */
    @Test
    public void getItemsForGivenPriceRange_NotEmptyList() {
        // Add item into DB
        itemDao.saveAndFlush(tempItem);

        // Try to get items for given price range
        assertEqualItems(itemDao.getAllByPriceBetween(tempItem.getPrice() - 10, tempItem.getPrice() + 10).get(0), tempItem);
    }

    /**
     * @see ItemRepo#getAllByPriceBetween(Double, Double) (Double, Double)
     */
    @Test
    public void getItemsForGivenPriceRange_EmptyList() {

        // Try to get items for given price range
        assertTrue(itemDao.getAllByPriceBetween(-200.0, -100.0).isEmpty());
    }

    /**
     * @see ItemRepo#getAllByArtistId(Long)
     */
    @Test
    public void getArtistItems_NotEmptyList() {
        // Add testArtist's 2 items into DB

        itemDao.saveAndFlush(tempItem);

        // Get testArtist's items and check list for not empty
        assertFalse(itemDao.getAllByArtistId(testArtist.getId()).isEmpty());
    }

    /**
     * @see ItemRepo#getAllByArtistId(Long) (Long)
     */
    @Test
    public void getArtistItems_EmptyList() {
        // Add testArtist's 2 items into DB
        itemDao.saveAndFlush(tempItem);

        // Get testArtist's items and check list for not empty
        assertTrue(itemDao.getAllByArtistId(684531L).isEmpty());
    }

    /**
     * @see ItemRepo#saveAndFlush(Object)
     */
    @Test
    public void updateItem_Success() {
        // Check testItem for null
        assertNotNull(testItem);

        // Add item into db and get generated id
        testItem.setTitle("test_item");
        itemDao.saveAndFlush(testItem);

        // Find added item from db
        Item item = itemDao.findOne(testItem.getId());

        // Check for sameness
        assertEquals(testItem.getTitle(), item.getTitle());
    }

    /**
     * @see ItemRepo#saveAndFlush(Object)
     */
    @Test(expected = DAOException.class)
    public void updateItem_Failure() {
        // Check testItem for null
        assertNotNull(testItem);
        testItem.setArtist(testArtist);

        testItem.setTitle(null);
        try{
            itemDao.saveAndFlush(testItem);
        }catch (Exception e){
            throw new DAOException(e.getMessage());
        }

    }

    /**
     * @see ItemRepo#delete(Object)
     */

    @Test
    public void delete_Success() {
        // Add item into db
        // Check item in db for null
        assertNotNull(itemDao.findOne(testItem.getId()));

        // Delete item from db
        itemDao.delete(testItem);
        testItem.setId(null);
    }

    /**
     * @see ItemRepo#delete(Object)
     */
    @Test
    public void delete_Failure() {

        // Check item in db for null
        assertNotNull(itemDao.findOne(testItem.getId()));
        // Delete item from db
        itemDao.delete(tempItem);

        // Get deleted item from db
        Item deletedItem = itemDao.findOne(testItem.getId());

        // Check for null
        assertNotNull(deletedItem.getId());
    }

    // endregion
}
