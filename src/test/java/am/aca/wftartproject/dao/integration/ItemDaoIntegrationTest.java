package am.aca.wftartproject.dao.integration;

import am.aca.wftartproject.dao.ArtistSpecializationLkpDao;
import am.aca.wftartproject.dao.ItemDao;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.dao.impl.ArtistSpecializationLkpDaoImpl;
import am.aca.wftartproject.dao.impl.ItemDaoImpl;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.util.TestObjectTemplate;
import am.aca.wftartproject.util.dbconnection.ConnectionFactory;
import am.aca.wftartproject.util.dbconnection.ConnectionModel;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.List;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualItems;
import static junit.framework.TestCase.*;


/**
 * Created by Armen on 6/1/2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:springconfig/daointegration/spring-dao-integration.xml",
        "classpath:springconfig/database/spring-database.xml"})
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
     * Creates connection, artist and item for tests
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {


        // get database connection from ConnectionPool
        dataSource = new ConnectionFactory()
                .getConnection(ConnectionModel.POOL)
                .getTestDBConnection();

        ArtistSpecializationLkpDao artistSpecialization = new ArtistSpecializationLkpDaoImpl(dataSource);

        if (artistSpecialization.getArtistSpecialization(1) == null) {
            artistSpecialization.addArtistSpecialization();
        }

        // create test artist and user
        testArtist = TestObjectTemplate.createTestArtist();
        testItem = TestObjectTemplate.createTestItem();
        tempItem = TestObjectTemplate.createTestItem();

        // insert test Artist into db, get generated id
        artistDao.addArtist(testArtist);

        // print busy connections quantity
        if (dataSource instanceof ComboPooledDataSource) {
            LOGGER.info(String.format("Number of busy connections Start: %s", ((ComboPooledDataSource) dataSource).getNumBusyConnections()));
        }
    }

    /**
     * Deletes all artists and items created during the tests
     * @throws SQLException
     */
    @After
    public void tearDown() throws SQLException {

        // delete inserted test users,artists and items  from db
        if (testItem.getId() != null)
            itemDao.deleteItem(testItem.getId());

        if (tempItem.getId() != null)
            itemDao.deleteItem(tempItem.getId());

        if (testArtist.getId() != null)
            artistDao.deleteArtist(testArtist.getId());

        // set testArtist and testItem to null
        testArtist = null;
        tempItem = null;
        testItem = null;

        // print busy connections quantity
        if (dataSource instanceof ComboPooledDataSource) {
            LOGGER.info(String.format("Number of busy connections End: %s", ((ComboPooledDataSource) dataSource).getNumBusyConnections()));
        }
    }

    //region(TEST_CASE)

    /**
     * @see ItemDao#addItem(Long, Item)
     */
    @Test
    public void addItem_Success() {

        // check testItem for null
        assertNotNull(testItem);

        // add item into db and get generated id
        itemDao.addItem(testArtist.getId(), testItem);

        // find added item from db
        Item item = itemDao.findItem(testItem.getId());

        // check for sameness
        assertEqualItems(testItem, item);
    }


    /**
     * @see ItemDao#addItem(Long, Item)
     */
    @Test(expected = DAOException.class)
    public void addItem_Failure() {

        // check testItem for null
        assertNotNull(testItem);
        testItem.setTitle(null);

        // add item into db and get generated id
        itemDao.addItem(testArtist.getId(), testItem);

        // find added item from db
        Item item = itemDao.findItem(testItem.getId());

        // check for sameness
        assertEqualItems(testItem, item);
    }

    /**
     * @see ItemDao#findItem(Long)
     */
    @Test
    public void findItem_Success() {

        // add item into db
        itemDao.addItem(testArtist.getId(), testItem);

        // find and get item from db
        Item expectedItem = itemDao.findItem(testItem.getId());

        // check for null testItem and expectedItem
        assertNotNull(testItem);
        assertEqualItems(expectedItem, testItem);
    }

    /**
     * @see ItemDao#findItem(Long)
     */
    @Test
    public void findItem_Failure(){

        // add item into DB
        itemDao.addItem(testArtist.getId(), testItem);

        // try to find and get item by id
        Item foundItem = itemDao.findItem(135984984651L);

        // check foundItem for null
        assertNull(foundItem);

    }


    /**
     * @see ItemDao#getRecentlyAddedItems(int)
     */
    @Test// ConcurrentModificationException
    public void getRecentlyAddedItems_Success(){
        // add items into DB

        itemDao.addItem(testArtist.getId(), tempItem);
        itemDao.addItem(testArtist.getId(), testItem);

        // get items list and check for not empty when limit is positive
        List<Item> itemList = itemDao.getRecentlyAddedItems(1);

        assertEqualItems(testItem, itemList.get(0));

    }


    /**
     * @see ItemDao#getRecentlyAddedItems(int)
     */
    @Test
    public void getRecentlyAddedItems_Failure(){
        // add item into DB
        itemDao.addItem(testArtist.getId(), testItem);

        // get recently added items with 0 limit and check list for empty
        assertTrue(itemDao.getRecentlyAddedItems(0).isEmpty());
    }

    /**
     * @see ItemDao#getItemsByTitle(String)
     */
    @Test // ConcurrentModificationException
    public void getItemsByTitleNotEmptyList(){

        // add item into DB
        itemDao.addItem(testArtist.getId(), testItem);

        // get items by title from DB and check for not empty
        assertFalse(itemDao.getItemsByTitle(testItem.getTitle()).isEmpty());

    }

    /**
     * @see ItemDao#getItemsByTitle(String)
     */
    @Test
    public void getItemsByTitleEmptyList(){
        // add item into DB
        itemDao.addItem(testArtist.getId(), testItem);

        // get items by title from DB and check list for empty
        assertTrue(itemDao.getItemsByTitle("iuerhpu").isEmpty());
    }

    /**
     * @see ItemDao#getItemsByType(String)
     */
    @Test// ConcurrentModificationException
    public void getItemsByTypeNotEmptyList(){

        // add item into DB
        itemDao.addItem(testArtist.getId(), testItem);

        // get items by type from DB and check list for not empty
        assertFalse(itemDao.getItemsByType(testItem.getItemType().getType()).isEmpty());
    }

    /**
     * @see ItemDao#getItemsByType(String)
     */
    @Test
    public void getItemsByTypeEmptyList(){

        // add item into DB
        itemDao.addItem(testArtist.getId(), testItem);

        // get items by type from DB and check list for empty
        assertTrue(itemDao.getItemsByType("osduyrf").isEmpty());
    }

    /**
     * @see ItemDao#getItemsForGivenPriceRange(Double, Double)
     */
    @Test// ConcurrentModificationException
    public void getItemsForGivenPriceRangeNotEmptyList(){

        // add item into DB
        itemDao.addItem(testArtist.getId(), tempItem);

        // try to get items for given price range
        assertEqualItems(itemDao.getItemsForGivenPriceRange(tempItem.getPrice() - 100, tempItem.getPrice() + 100).get(0), tempItem);
    }

    /**
     * @see ItemDao#getItemsForGivenPriceRange(Double, Double)
     */
    @Test
    public void getItemsForGivenPriceRangeEmptyList(){
        // add item into DB
        itemDao.addItem(testArtist.getId(), testItem);

        // try to get items for given price range
        assertTrue(itemDao.getItemsForGivenPriceRange(-200.0, -100.0).isEmpty());
    }

    /**
     * @see ItemDao#getArtistItems(Long, Long, Long)
     */
    @Test// ConcurrentModificationException
    public void getArtistItemsNotEmptyList(){

        // add testArtist's 2 items into DB
        itemDao.addItem(testArtist.getId(), testItem);
        itemDao.addItem(testArtist.getId(), tempItem);

        // get testArtist's items and check list for not empty
        assertFalse(itemDao.getArtistItems(testArtist.getId(), testItem.getId(), 3L).isEmpty());
    }

    /**
     * @see ItemDao#getArtistItems(Long, Long, Long)
     */
    @Test
    public void getArtistItemsEmptyList(){
        // add testArtist's 2 items into DB
        itemDao.addItem(testArtist.getId(), testItem);
        itemDao.addItem(testArtist.getId(), tempItem);

        // get testArtist's items and check list for not empty
        assertTrue(itemDao.getArtistItems(684531L, testItem.getId(), 3L).isEmpty());
    }

    /**
     * @see ItemDao#updateItem(Long, Item)
     */
    @Test
    public void updateItem_Success() {

        // check testItem for null
        assertNotNull(testItem);

        // add item into db and get generated id
        itemDao.addItem(testArtist.getId(), testItem);
        testItem.setTitle("ankap item");
        itemDao.updateItem(testItem.getId(), testItem);

        // find added item from db
        Item item = itemDao.findItem(testItem.getId());

        // check for sameness
        assertEquals(testItem.getTitle(), item.getTitle());
    }

    /**
     * @see ItemDao#updateItem(Long, Item)
     */
    @Test(expected = DAOException.class)
    public void updateItem_Failure() {

        // check testItem for null
        assertNotNull(testItem);

        // add item into db and get generated id
        itemDao.addItem(testArtist.getId(), testItem);
        testItem.setTitle(null);
        itemDao.updateItem(testItem.getId(), testItem);

        // find added item from db
        Item item = itemDao.findItem(testItem.getId());

        // check for sameness
        assertEquals(testItem.getTitle(), item.getTitle());
    }

    /**
     * @see ItemDao#deleteItem(Long)
     */

    @Test
    public void deleteItem_Success() {

        // add item into db
        itemDao.addItem(testArtist.getId(), testItem);

        // check item in db for null
        assertNotNull(itemDao.findItem(testItem.getId()));

        // delete item from db
        assertTrue(itemDao.deleteItem(testItem.getId()));
        testItem.setId(null);
    }

    /**
     * @see ItemDao#deleteItem(Long)
     */
    @Test(expected = DAOException.class)
    public void deleteItem_Failure() {

        // add item into db
        itemDao.addItem(testArtist.getId(), testItem);

        // check item in db for null
        assertNotNull(itemDao.findItem(testItem.getId()));

        // delete item from db
        itemDao.deleteItem(-7L);

        // get deleted item from db
        Item deletedItem = itemDao.findItem(testItem.getId());

        // check for null
        assertNotNull(deletedItem.getId());
    }

    //endregion
}
