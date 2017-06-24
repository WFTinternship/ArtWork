package am.aca.wftartproject.service;

import am.aca.wftartproject.BaseUnitTest;
import am.aca.wftartproject.dao.impl.ItemDaoImpl;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualItems;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestItem;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

/**
 * Created by ASUS on 24-Jun-17
 */
public class ItemServiceUnitTest extends BaseUnitTest {


    private Item testItem;

    @InjectMocks
    @Autowired
    private ItemService itemService;

    @Mock
    private ItemDaoImpl itemDaoMock;


    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void afterTest() {

    }


    /**
     * @see ItemService#addItem(Long, Item)
     */
    @Test
    public void addItem_idIsNullOrNegative() {

        // Create test id and testItem
        Long id = null;
        testItem = createTestItem();


        // Test method
        try {
            itemService.addItem(id, testItem);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change id value to negative
        id = -5L;

        // Test method
        try {
            itemService.addItem(id, testItem);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

    }

    /**
     * @see ItemService#addItem(Long, Item)
     */
    @Test
    public void addItem_itemIsNullOrNotValid() {

        // Create test id and test Item
        Long id = 5L;
        testItem = null;

        // Test method
        try {
            itemService.addItem(id, testItem);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Create test Item
        testItem = createTestItem();
        testItem.setTitle(null);

        // Test method
        try {
            itemService.addItem(id, testItem);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof Exception);
        }
    }


    /**
     * @see ItemService#addItem(Long, Item)
     */
    @Test(expected = ServiceException.class)
    public void addItem_addFail() {

        // Create test id and test Item
        Long id = 5L;
        testItem = createTestItem();

        // Setup mock
        doThrow(DAOException.class).when(itemDaoMock).addItem(id, testItem);

        // Test method
        itemService.addItem(id, testItem);

    }


    /**
     * @see ItemService#addItem(Long, Item)
     */
    @Test
    public void addItem_addSuccess() {

        // Create test id and test item
        Long id = 5L;
        testItem = createTestItem();
        testItem.setArtistId(id);

        // Setup mock
        doNothing().when(itemDaoMock).addItem(id,testItem);


        // Test method
        itemService.addItem(id,testItem);

    }


    /**
     * @see ItemService#findItem(Long)
     */
    @Test
    public void findItem_idIsNullOrNegative(){

        // Create test id
        Long id = null;

        // Test method
        try{
            itemService.findItem(id);
            fail();
        }catch (Exception ex){
            assertTrue(ex instanceof InvalidEntryException);
        }


        // Change id value to negative
        id  = -5L;

        // Test method
        try{
            itemService.findItem(id);
            fail();
        }catch (Exception ex){
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ItemService#findItem(Long)
     */
    @Test(expected = ServiceException.class)
    public void findItem_findFail(){

        // Create test id
        Long id = 5L;

        // Setup mock
        doThrow(DAOException.class).when(itemDaoMock).findItem(anyLong());

        // Test method
        itemService.findItem(id);

    }


    /**
     * @see ItemService#findItem(Long)
     */
    @Test
    public void findItem_findSuccess(){

        // Create test id and test Item
        Long id = 5L;
        testItem = createTestItem();

        // Setup mock
        doReturn(testItem).when(itemDaoMock).findItem(anyLong());

        // Test method
        assertEqualItems(testItem,itemService.findItem(id));

    }

}
