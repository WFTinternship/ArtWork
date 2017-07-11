package am.aca.wftartproject.service.unit;

import am.aca.wftartproject.exception.dao.NotEnoughMoneyException;
import am.aca.wftartproject.exception.service.DuplicateEntryException;
import am.aca.wftartproject.service.BaseUnitTest;
import am.aca.wftartproject.dao.impl.ShoppingCardDaoImpl;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.ShoppingCard;
import am.aca.wftartproject.service.ShoppingCardService;
import am.aca.wftartproject.service.impl.ShoppingCardServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualShoppingCards;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestShoppingCard;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

/**
 * @author surik
 */
public class ShoppingCardServiceUnitTest extends BaseUnitTest {

    private ShoppingCard testShoppingCard;

    @InjectMocks
    @Autowired
    private ShoppingCardService shoppingCardService;

    @Mock
    private ShoppingCardDaoImpl shoppingCardDaoMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(shoppingCardService, "shoppingCardDao", shoppingCardDaoMock);
    }

    @After
    public void tearDown() {
    }

    // region <TEST CASE>

    /**
     * @see ShoppingCardServiceImpl#addShoppingCard(java.lang.Long, am.aca.wftartproject.model.ShoppingCard)
     */
    @Test
    public void addShoppingCard_userIdNullOrNegative() {
        // Create shopping card and userId
        Long userId;
        testShoppingCard = createTestShoppingCard();

        // Try to add shopping card
        try {
            shoppingCardService.addShoppingCard(null, testShoppingCard);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change userId to negative value
        userId = -5L;

        // Try to add shopping card
        try {
            shoppingCardService.addShoppingCard(userId, testShoppingCard);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ShoppingCardServiceImpl#addShoppingCard(java.lang.Long, am.aca.wftartproject.model.ShoppingCard)
     */
    @Test
    public void addShoppingCard_invalidShoppingCard() {
        // Create userId
        Long userId = 5L;

        // Add null shopping card
        try {
            shoppingCardService.addShoppingCard(userId, null);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Create invalid shopping card
        testShoppingCard = createTestShoppingCard();
        testShoppingCard.setId(5L);
        testShoppingCard.setShoppingCardType(null);

        // Add shopping card
        try {
            shoppingCardService.addShoppingCard(userId, testShoppingCard);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ShoppingCardServiceImpl#addShoppingCard(java.lang.Long, am.aca.wftartproject.model.ShoppingCard)
     */
    @Test
    public void addShoppingCard_addSuccess() {
        //Create argument capture
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<ShoppingCard> argumentCaptor1 = ArgumentCaptor.forClass(ShoppingCard.class);

        // Create userId and testShoppingCard
        Long userId = 5L;
        testShoppingCard = createTestShoppingCard();
        testShoppingCard.setId(userId);

        // Setup mocks
        doNothing().when(shoppingCardDaoMock).addShoppingCard(argumentCaptor.capture(), argumentCaptor1.capture());

        // Test method
        shoppingCardService.addShoppingCard(userId, testShoppingCard);

        // Check input argument
        assertEquals(userId, argumentCaptor.getValue());
        assertEquals(testShoppingCard, argumentCaptor1.getValue());
    }


    /**
     * @see ShoppingCardServiceImpl#addShoppingCard(java.lang.Long, am.aca.wftartproject.model.ShoppingCard)
     */
    @Test(expected = ServiceException.class)
    public void addShoppingCard_addFailure() {
        // Create userId and testShoppingCard
        Long userId = 5L;
        testShoppingCard = createTestShoppingCard();
        testShoppingCard.setId(userId);

        // Setup mocks
        doThrow(DAOException.class).when(shoppingCardDaoMock).addShoppingCard(anyLong(), any(ShoppingCard.class));

        // Test method
        shoppingCardService.addShoppingCard(userId, testShoppingCard);
    }


    /**
     * @see ShoppingCardServiceImpl#getShoppingCard(java.lang.Long)
     */
    @Test
    public void getShoppingCard_idNullOrNegative() {
        // Create id
        Long id;

        // Try to get shopping card
        try {
            shoppingCardService.getShoppingCard(null);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change id to negative value
        id = -5L;

        // Try to get shopping card
        try {
            shoppingCardService.getShoppingCard(id);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ShoppingCardServiceImpl#getShoppingCard(java.lang.Long)
     */
    @Test
    public void getShoppingCard_getSuccess() {
        //Create argument capture
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        // Create id and testShoppingCard
        Long id = 5L;
        testShoppingCard = createTestShoppingCard();

        // Setup mocks
        doReturn(testShoppingCard).when(shoppingCardDaoMock).getShoppingCard(argumentCaptor.capture());

        // Test method
        assertEqualShoppingCards(testShoppingCard, shoppingCardService.getShoppingCard(id));

        // Check input argument
        assertEquals(id, argumentCaptor.getValue());
    }


    /**
     * @see ShoppingCardServiceImpl#getShoppingCard(java.lang.Long)
     */
    @Test(expected = ServiceException.class)
    public void getShoppingCard_getFailure() {
        // Create id
        Long id = 5L;

        // Setup mock
        doThrow(DAOException.class).when(shoppingCardDaoMock).getShoppingCard(anyLong());

        // Test method
        shoppingCardService.getShoppingCard(id);
    }

    /**
     * @see ShoppingCardServiceImpl#getShoppingCardByBuyerId(Long)
     */
    @Test
    public void getShoppingCardByBuyerId_buyerIdNullOrNegative() {
        Long buyerId = null;

        try {
            shoppingCardService.getShoppingCardByBuyerId(buyerId);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }

        buyerId = -5L;

        try {
            shoppingCardService.getShoppingCardByBuyerId(buyerId);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }

    /**
     * @see ShoppingCardServiceImpl#getShoppingCardByBuyerId(Long)
     */
    @Test
    public void getShoppingCardByBuyerId_getSuccess() {
        //Create argument capture
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        Long buyerId = 5L;
        testShoppingCard = createTestShoppingCard();

        // Setup mock
        doReturn(testShoppingCard).when(shoppingCardDaoMock).getShoppingCardByBuyerId(argumentCaptor.capture());

        // Test method
        assertEqualShoppingCards(testShoppingCard, shoppingCardService.getShoppingCardByBuyerId(buyerId));

        assertEquals(buyerId, argumentCaptor.getValue());
    }

    /**
     * @see ShoppingCardServiceImpl#getShoppingCardByBuyerId(Long)
     */
    @Test(expected = ServiceException.class)
    public void getShoppingCardByBuyerId_getFailed() {
        // Setup mock
        doThrow(DAOException.class).when(shoppingCardDaoMock).getShoppingCardByBuyerId(anyLong());

        // Test method
        shoppingCardService.getShoppingCardByBuyerId(5L);
    }

    /**
     * @see ShoppingCardServiceImpl#updateShoppingCard(java.lang.Long, am.aca.wftartproject.model.ShoppingCard)
     */
    @Test
    public void updateShoppingCard_idNullOrNegative() {
        // Create testShoppingCard and null id
        Long id;
        testShoppingCard = createTestShoppingCard();

        // Test method
        try {
            shoppingCardService.updateShoppingCard(null, testShoppingCard);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }

        // Change id to negative
        id = -5L;

        // Test method
        try {
            shoppingCardService.updateShoppingCard(id, testShoppingCard);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }


    /**
     * @see ShoppingCardServiceImpl#updateShoppingCard(java.lang.Long, am.aca.wftartproject.model.ShoppingCard)
     */
    @Test
    public void updateShoppingCard_shoppingCardNullOrInvalid() {
        // Create id and null shopping card
        Long id = 5L;

        // Try to update null shopping card
        try {
            shoppingCardService.updateShoppingCard(id, null);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Create invalid shopping card
        testShoppingCard = createTestShoppingCard();
        testShoppingCard.setId(id);
        testShoppingCard.setShoppingCardType(null);

        // Try to update shopping card
        try {
            shoppingCardService.updateShoppingCard(id, testShoppingCard);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }


    /**
     * @see ShoppingCardServiceImpl#updateShoppingCard(java.lang.Long, am.aca.wftartproject.model.ShoppingCard)
     */
    @Test
    public void updateShoppingCard_updateSuccess() {
        //Create argument capture
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<ShoppingCard> argumentCaptor1 = ArgumentCaptor.forClass(ShoppingCard.class);

        // Create id and valid shopping card
        Long id = 5L;
        testShoppingCard = createTestShoppingCard();
        testShoppingCard.setId(id);

        // Setup mocks
        doReturn(true).when(shoppingCardDaoMock).updateShoppingCard(argumentCaptor.capture(), argumentCaptor1.capture());

        // Test method
        shoppingCardService.updateShoppingCard(id, testShoppingCard);

        // Check input argument
        assertEquals(id, argumentCaptor.getValue());
        assertEquals(testShoppingCard, argumentCaptor1.getValue());
    }


    /**
     * @see ShoppingCardServiceImpl#updateShoppingCard(java.lang.Long, am.aca.wftartproject.model.ShoppingCard)
     */
    @Test(expected = ServiceException.class)
    public void updateShoppingCard_updateFailed() {
        // Create id and valid shopping card
        Long id = 5L;
        testShoppingCard = createTestShoppingCard();
        testShoppingCard.setId(id);

        // Setup mocks
        doThrow(DAOException.class).when(shoppingCardDaoMock).updateShoppingCard(anyLong(), any(ShoppingCard.class));

        // Test method
        shoppingCardService.updateShoppingCard(id, testShoppingCard);
    }

    /**
     * @see ShoppingCardServiceImpl#debitBalanceForItemBuying(Long, Double)
     */
    @Test
    public void debitBalanceForItemBuying_buyerIdAndItemPriceNullOrNegative() {
        Long buyerId = null;
        Double itemPrice = null;

        try {
            shoppingCardService.debitBalanceForItemBuying(buyerId, itemPrice);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }

        buyerId = -5L;
        itemPrice = -5.0;

        try {
            shoppingCardService.debitBalanceForItemBuying(buyerId, itemPrice);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }

    /**
     * @see ShoppingCardServiceImpl#debitBalanceForItemBuying(Long, Double)
     */
    @Test
    public void debitBalanceForItemBuying_debitSuccess() {
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Double> argumentCaptor1 = ArgumentCaptor.forClass(Double.class);

        Long buyerId = 5L;
        Double itemPrice = 5.0;

        doReturn(true).when(shoppingCardDaoMock).debitBalanceForItemBuying(argumentCaptor.capture(), argumentCaptor1.capture());

        shoppingCardService.debitBalanceForItemBuying(buyerId, itemPrice);

        assertEquals(buyerId, argumentCaptor.getValue());
        assertEquals(itemPrice, argumentCaptor1.getValue());
    }

    /**
     * @see ShoppingCardServiceImpl#debitBalanceForItemBuying(Long, Double)
     */
    @Test(expected = ServiceException.class)
    public void debitBalanceForItemBuying_debitFailed() {
        // Setup mock
        doThrow(NotEnoughMoneyException.class).when(shoppingCardDaoMock).debitBalanceForItemBuying(anyLong(), anyDouble());

        shoppingCardService.debitBalanceForItemBuying(5L, 5.0);
    }

    /**
     * @see ShoppingCardServiceImpl#deleteShoppingCard(java.lang.Long)
     */
    @Test
    public void deleteShoppingCard_idNullOrNegative() {
        // Create id
        Long id;

        // Try to delete shopping card
        try {
            shoppingCardService.deleteShoppingCard(null);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }

        // Change id to negative value
        id = -5L;

        // Try to delete shopping card
        try {
            shoppingCardService.deleteShoppingCard(id);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }


    /**
     * @see ShoppingCardServiceImpl#deleteShoppingCard(java.lang.Long)
     */
    @Test
    public void deleteShoppingCard_deleteSuccess() {
        //Create argument capture
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        // Create id
        Long id = 5L;

        // Setup mocks
        doReturn(true).when(shoppingCardDaoMock).deleteShoppingCard(argumentCaptor.capture());

        // Test method
        shoppingCardService.deleteShoppingCard(id);

        // Check input argument
        assertEquals(id, argumentCaptor.getValue());
    }


    /**
     * @see ShoppingCardServiceImpl#deleteShoppingCard(java.lang.Long)
     */
    @Test(expected = ServiceException.class)
    public void deleteShoppingCard_deleteFailure() {
        // Create id
        Long id = 5L;

        // Setup mocks
        doThrow(DAOException.class).when(shoppingCardDaoMock).deleteShoppingCard(anyLong());

        // Test method
        shoppingCardService.deleteShoppingCard(id);
    }

    /**
     * @see ShoppingCardServiceImpl#deleteShoppingCardByBuyerId(Long)
     */
    @Test
    public void deleteShoppingCardByBuyerId_buyerIdNullOrNegative() {
        Long buyerId = null;

        try {
            shoppingCardService.deleteShoppingCardByBuyerId(buyerId);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }

        buyerId = -5L;

        try {
            shoppingCardService.deleteShoppingCardByBuyerId(buyerId);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }

    /**
     * @see ShoppingCardServiceImpl#deleteShoppingCardByBuyerId(Long)
     */
    @Test
    public void deleteShoppingCardByBuyerId_deleteSuccess() {
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        Long buyerId = 5L;

        // Setup mock
        doReturn(true).when(shoppingCardDaoMock).deleteShoppingCardByBuyerId(argumentCaptor.capture());

        // Test method
        shoppingCardService.deleteShoppingCardByBuyerId(buyerId);

        assertEquals(buyerId, argumentCaptor.getValue());
    }

    /**
     * @see ShoppingCardServiceImpl#deleteShoppingCardByBuyerId(Long)
     */
    @Test(expected = ServiceException.class)
    public void deleteShoppingCardByBuyerId_deleteFailed() {
        // Setup mock
        doReturn(false).when(shoppingCardDaoMock).deleteShoppingCardByBuyerId(anyLong());

        // Test method
        shoppingCardService.deleteShoppingCardByBuyerId(5L);
    }

    // endregion
}
