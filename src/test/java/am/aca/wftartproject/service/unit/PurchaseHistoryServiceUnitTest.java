package am.aca.wftartproject.service.unit;

import am.aca.wftartproject.service.BaseUnitTest;
import am.aca.wftartproject.dao.impl.PurchaseHistoryDaoImpl;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.entity.PurchaseHistory;
import am.aca.wftartproject.service.PurchaseHistoryService;
import am.aca.wftartproject.service.impl.PurchaseHistoryServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualPurchaseHistory;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestPurchaseHistory;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author surik
 */
public class PurchaseHistoryServiceUnitTest extends BaseUnitTest {

    private PurchaseHistory testPurchaseHistory;
    @InjectMocks
    private PurchaseHistoryService purchaseHistoryService = new PurchaseHistoryServiceImpl();

    @Mock
    private PurchaseHistoryDaoImpl purchaseHistoryDaoMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(purchaseHistoryService, "purchaseHistoryDao", purchaseHistoryDaoMock);
    }

    @After
    public void tearDown() {
    }

    // region <TEST CASE>

    /**
     * @see PurchaseHistoryServiceImpl#addPurchase(am.aca.wftartproject.entity.PurchaseHistory)
     */
    @Test
    public void addPurchase_purchaseHistoryNullOrInvalid() {
        // Try to add purchase history
        try {
            purchaseHistoryService.addPurchase(null);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }

        // Create invalid purchase history
        testPurchaseHistory = createTestPurchaseHistory();
        testPurchaseHistory.setUserId(null);

        // Try to add purchase history
        try {
            purchaseHistoryService.addPurchase(testPurchaseHistory);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }


    /**
     * @see PurchaseHistoryServiceImpl#addPurchase(am.aca.wftartproject.entity.PurchaseHistory)
     */
    @Test(expected = ServiceException.class)
    public void addPurchase_addFailed() {
        // Create testPurchaseHistory
        testPurchaseHistory = createTestPurchaseHistory();

        // Setup mocks
        doThrow(DAOException.class).when(purchaseHistoryDaoMock).addPurchase(any(PurchaseHistory.class));

        // Test method
        purchaseHistoryService.addPurchase(testPurchaseHistory);
    }

    /**
     * @see PurchaseHistoryServiceImpl#addPurchase(am.aca.wftartproject.entity.PurchaseHistory)
     */
    @Test
    public void addPurchase_addSuccess() {
        //Create argument capture
        ArgumentCaptor<PurchaseHistory> argumentCaptor = ArgumentCaptor.forClass(PurchaseHistory.class);

        // Create testPurchaseHistory
        testPurchaseHistory = createTestPurchaseHistory();

        // Setup mock
        doNothing().when(purchaseHistoryDaoMock).addPurchase(argumentCaptor.capture());

        // Test method
        purchaseHistoryService.addPurchase(testPurchaseHistory);

        // Check input argument
        assertEqualPurchaseHistory(testPurchaseHistory, argumentCaptor.getValue());
    }


    /**
     * @see PurchaseHistoryServiceImpl#getPurchase(java.lang.Long)
     */
    @Test
    public void getPurchase_userIdNullOrNegative() {
        // Create itemId and null userId
        Long itemId = -7L;

        // Try to get Purchase item
        try {
            purchaseHistoryService.getPurchase(itemId);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }

        // Change id to negative value
       Long userId = -5L;

        // Try to get Purchase item
        try {
            purchaseHistoryService.getPurchase(itemId);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }


    /**
     * @see PurchaseHistoryServiceImpl#getPurchase(java.lang.Long)
     */
    @Test
    public void getPurchase_itemIdNullOrNegative() {
        // Create userId and itemId
        Long userId = 5L, itemId;

        // Try to get Purchase item
        try {
            purchaseHistoryService.getPurchase(null);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }

        // Create negative itemId
        itemId = -5L;

        // Try to get Purchase item
        try {
            purchaseHistoryService.getPurchase(itemId);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }


    /**
     * @see PurchaseHistoryServiceImpl#getPurchase(java.lang.Long)
     */
    @Test(expected = ServiceException.class)
    public void getPurchase_getFailed() {
        // Create userId and itemId
        Long userId = 5L, itemId = 5L;

        // Setup mocks
        doThrow(DAOException.class).when(purchaseHistoryDaoMock).getPurchase(itemId);

        // Test method
        purchaseHistoryService.getPurchase(itemId);
    }

    /**
     * @see PurchaseHistoryServiceImpl#getPurchase(java.lang.Long)
     */
    @Test
    public void getPurchase_getSuccess() {
        //Create argument capture
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        //Create not null result object
        PurchaseHistory result = new PurchaseHistory();

        // Create userId and itemId
        Long itemId = 5L;

        // Setup mock
        doReturn(result).when(purchaseHistoryDaoMock).getPurchase(argumentCaptor.capture());

        //Test method
        purchaseHistoryService.getPurchase(itemId);

        // Check input arguments
        assertEquals(itemId, argumentCaptor.getAllValues().get(0));
    }


    /**
     * @see PurchaseHistoryServiceImpl#getPurchase(java.lang.Long)
     */
    @Test
    public void getPurchaseHistory_userIdNullOrNegative() {
        // Create null userId
        Long userId;

        // Try to get purchase history
        try {
            purchaseHistoryService.getPurchase(null);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }

        // Create negative userId
        userId = -5L;

        // Try to get purchase history
        try {
            purchaseHistoryService.getPurchase(userId);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }


    /**
     * @see PurchaseHistoryServiceImpl#getPurchase(java.lang.Long)
     */
    @Test
    public void getPurchaseHistory_gottenListIsNull() {
        // Create userId
        Long userId = 5L;

        // Setup mocks
        doReturn(null).when(purchaseHistoryDaoMock).getPurchase(anyLong());

        // Test method
        assertEquals(null, purchaseHistoryService.getPurchase(userId));
    }


    /**
     * @see PurchaseHistoryServiceImpl#getPurchase(java.lang.Long)
     */
    @Test
    public void getPurchaseHistory_getSuccess() {
        //Create argument capture
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        // Create userId and purchaseHistoryList
        Long userId = 5L;
        List<PurchaseHistory> purchaseHistoryList = new ArrayList<>();

        // Setup mock
        doReturn(purchaseHistoryList).when(purchaseHistoryDaoMock).getPurchaseList(argumentCaptor.capture());

        // Test method
        assertEquals(purchaseHistoryList, purchaseHistoryService.getPurchaseList(userId));

        // Check input argument
        assertEquals(userId, argumentCaptor.getValue());
    }


    /**
     * @see PurchaseHistoryServiceImpl#deletePurchase(PurchaseHistory)
     */
    @Test
    public void deletePurchase_userIdNullOrNegative() {
        // Create itemId and null userId
        Long itemId = 5L, userId;

        // Try to delete purchase item
        try {
            purchaseHistoryService.deletePurchase(testPurchaseHistory);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
        // Change id to negative value
        userId = -5L;

        // Try to delete purchase item
        try {
            purchaseHistoryService.deletePurchase(testPurchaseHistory);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }


    /**
     * @see PurchaseHistoryServiceImpl#deletePurchase(PurchaseHistory)
     */
    @Test
    public void deletePurchase_itemIdNullOrNegative() {
        // Create userId and null itemId
        Long userId = 5L, itemId;

        // Try to delete purchase item
        try {
            purchaseHistoryService.deletePurchase(null);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
        // Create negative itemId
        itemId = -5L;

        // Try to delete purchase item
        try {
            purchaseHistoryService.deletePurchase(testPurchaseHistory);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }


    /**
     * @see PurchaseHistoryServiceImpl#deletePurchase(PurchaseHistory)
     */
    @Test(expected = ServiceException.class)
    public void deletePurchase_deleteFailed() {
        // Create userId and itemId
        testPurchaseHistory = createTestPurchaseHistory();
        Long  itemId = 5L;
        testPurchaseHistory.setItemId(5L);

        // Setup mocks
        doThrow(DAOException.class).when(purchaseHistoryDaoMock).deletePurchase(any(PurchaseHistory.class));

        // Test method
        purchaseHistoryService.deletePurchase(testPurchaseHistory);
    }


    /**
     * @see PurchaseHistoryServiceImpl#deletePurchase(PurchaseHistory)
     */
    @Test
    public void deletePurchase_deleteSuccess() {
        //Create argument capture
        ArgumentCaptor<PurchaseHistory> argumentCaptor = ArgumentCaptor.forClass(PurchaseHistory.class);
        testPurchaseHistory = createTestPurchaseHistory();
        // Create userId and itemId
        Long itemId = 5L;
        testPurchaseHistory.setItemId(5L);

        // Setup mock
        doReturn(true).when(purchaseHistoryDaoMock).deletePurchase(argumentCaptor.capture());

        // Test method
        purchaseHistoryService.deletePurchase(testPurchaseHistory);

        // Check input arguments
        assertEquals(itemId, argumentCaptor.getAllValues().get(0).getItemId());
    }

    // endregion
}
