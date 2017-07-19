package am.aca.wftartproject.service.unit;

import am.aca.wftartproject.exception.dao.NotEnoughMoneyException;
import am.aca.wftartproject.repository.ShoppingCardRepo;
import am.aca.wftartproject.service.BaseUnitTest;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.entity.ShoppingCard;
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
import static am.aca.wftartproject.util.TestObjectTemplate.createTestUser;
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
    private ShoppingCardService shoppingCardServiceRepoMock = new ShoppingCardServiceImpl();

    @Mock
    private ShoppingCardRepo shoppingCardRepoMock;

    @Before
    public void setUp(){
        ReflectionTestUtils.setField(shoppingCardServiceRepoMock, "shoppingCardRepo", shoppingCardRepoMock);
    }

    @After
    public void tearDown(){
    }

    // region <TEST CASE>

    /**
     * @see ShoppingCardServiceImpl#addShoppingCard( am.aca.wftartproject.entity.ShoppingCard)
     */
    @Test
    public void addShoppingCard_userIdNullOrNegative() {
        // Create shopping card and userId
        Long userId;
        testShoppingCard = createTestShoppingCard();

        // Try to add shopping card
        try {
            shoppingCardServiceRepoMock.addShoppingCard(testShoppingCard);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change userId to negative value
        userId = -5L;

        // Try to add shopping card
        try {
            shoppingCardServiceRepoMock.addShoppingCard(testShoppingCard);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ShoppingCardServiceImpl#addShoppingCard(am.aca.wftartproject.entity.ShoppingCard)
     */
    @Test
    public void addShoppingCard_invalidShoppingCard() {
        // Create userId
        Long userId = 5L;

        // Add null shopping card
        try {
            shoppingCardServiceRepoMock.addShoppingCard( null);
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
            shoppingCardServiceRepoMock.addShoppingCard(testShoppingCard);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ShoppingCardServiceImpl#addShoppingCard(am.aca.wftartproject.entity.ShoppingCard)
     */
    @Test(expected = ServiceException.class)
    public void addShoppingCard_addFailure() {
        // Create userId and testShoppingCard
        testShoppingCard = createTestShoppingCard();
        testShoppingCard.setId(5L);
        testShoppingCard.setAbstractUser(createTestUser());

        // Setup mocks
        doThrow(DAOException.class).when(shoppingCardRepoMock).saveAndFlush( any(ShoppingCard.class));

        // Test method
        shoppingCardServiceRepoMock.addShoppingCard(testShoppingCard);
    }


    /**
     * @see ShoppingCardServiceImpl#addShoppingCard(am.aca.wftartproject.entity.ShoppingCard)
     */
    @Test
    public void addShoppingCard_addSuccess() {
        //Create argument capture
        ArgumentCaptor<ShoppingCard> argumentCaptor = ArgumentCaptor.forClass(ShoppingCard.class);

        // Create userId and testShoppingCard
        Long userId = 5L;
        testShoppingCard = createTestShoppingCard();
        testShoppingCard.setId(userId);
        testShoppingCard.setAbstractUser(createTestUser());

        // Setup mocks
        doReturn(testShoppingCard).when(shoppingCardRepoMock).saveAndFlush( argumentCaptor.capture());

        // Test method
        shoppingCardServiceRepoMock.addShoppingCard(testShoppingCard);

        // Check input argument
        assertEquals(testShoppingCard, argumentCaptor.getValue());
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
            shoppingCardServiceRepoMock.getShoppingCard(null);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change id to negative value
        id = -5L;

        // Try to get shopping card
        try {
            shoppingCardServiceRepoMock.getShoppingCard(id);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ShoppingCardServiceImpl#getShoppingCard(java.lang.Long)
     */
    @Test(expected = ServiceException.class)
    public void getShoppingCard_getFailure() {
        // Create id
        Long id = 5L;

        // Setup mock
        doThrow(DAOException.class).when(shoppingCardRepoMock).findByAbstractUser_Id(anyLong());

        // Test method
        shoppingCardServiceRepoMock.getShoppingCard(id);
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
        doReturn(testShoppingCard).when(shoppingCardRepoMock).findByAbstractUser_Id(argumentCaptor.capture());

        // Test method
        assertEqualShoppingCards(testShoppingCard, shoppingCardServiceRepoMock.getShoppingCard(id));

        // Check input argument
        assertEquals(id, argumentCaptor.getValue());
    }

    /**
     * @see ShoppingCardServiceImpl#getShoppingCard)
     */
    @Test
    public void getShoppingCard_buyerIdNullOrNegative() {
        Long buyerId = null;

        try {
            shoppingCardServiceRepoMock.getShoppingCard(buyerId);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }

        buyerId = -5L;

        try {
            shoppingCardServiceRepoMock.getShoppingCard(buyerId);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }

    /**
     * @see ShoppingCardServiceImpl#getShoppingCard(Long)
     */
    @Test(expected = ServiceException.class)
    public void getShoppingCard_getFailed() {
        // Setup mock
        doThrow(DAOException.class).when(shoppingCardRepoMock).findByAbstractUser_Id(anyLong());

        // Test method
        shoppingCardServiceRepoMock.getShoppingCard(5L);
    }

//    /**
//     * @see ShoppingCardServiceImpl#getShoppingCard(Long)
//     */
//    @Test
//    public void getShoppingCard_getSuccess() {
//        //Create argument capture
//        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
//
//        Long buyerId = 5L;
//        testShoppingCard = createTestShoppingCard();
//
//        // Setup mock
//        doReturn(testShoppingCard).when(shoppingCardRepoMock).getShoppingCard(argumentCaptor.capture());
//
//        // Test method
//        assertEqualShoppingCards(testShoppingCard, shoppingCardServiceRepoMock.getShoppingCard(buyerId));
//
//        assertEquals(buyerId, argumentCaptor.getValue());
//    }

    /**
     * @see ShoppingCardServiceImpl#updateShoppingCard(am.aca.wftartproject.entity.ShoppingCard)
     */
    @Test
    public void updateShoppingCard_idNullOrNegative() {
        // Create testShoppingCard and null id
        Long id;
        testShoppingCard = createTestShoppingCard();

        // Test method
        try {
            shoppingCardServiceRepoMock.updateShoppingCard(testShoppingCard);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }

        // Change id to negative
        id = -5L;

        // Test method
        try {
            shoppingCardServiceRepoMock.updateShoppingCard(testShoppingCard);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }


    /**
     * @see ShoppingCardServiceImpl#updateShoppingCard(am.aca.wftartproject.entity.ShoppingCard)
     */
    @Test
    public void updateShoppingCard_shoppingCardNullOrInvalid() {
        // Create id and null shopping card
        Long id = 5L;

        // Try to update null shopping card
        try {
            shoppingCardServiceRepoMock.updateShoppingCard(null);
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
            shoppingCardServiceRepoMock.updateShoppingCard(testShoppingCard);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }


    /**
     * @see ShoppingCardServiceImpl#updateShoppingCard( am.aca.wftartproject.entity.ShoppingCard)
     */
    @Test(expected = ServiceException.class)
    public void updateShoppingCard_updateFailed() {
        // Create id and valid shopping card
        Long id = 5L;
        testShoppingCard = createTestShoppingCard();
        testShoppingCard.setId(id);
        testShoppingCard.setAbstractUser(createTestUser());

        // Setup mocks
        doThrow(DAOException.class).when(shoppingCardRepoMock).saveAndFlush( any(ShoppingCard.class));

        // Test method
        shoppingCardServiceRepoMock.updateShoppingCard(testShoppingCard);
    }


    /**
     * @see ShoppingCardServiceImpl#updateShoppingCard(ShoppingCard)
     */
    @Test
    public void updateShoppingCard_updateSuccess() {
        //Create argument capture
        ArgumentCaptor<ShoppingCard> argumentCaptor = ArgumentCaptor.forClass(ShoppingCard.class);

        // Create id and valid shopping card
        Long id = 5L;
        testShoppingCard = createTestShoppingCard();
        testShoppingCard.setId(id);
        testShoppingCard.setAbstractUser(createTestUser());

        // Setup mocks
        doReturn(true).when(shoppingCardRepoMock).saveAndFlush(argumentCaptor.capture());

        // Test method
        shoppingCardServiceRepoMock.updateShoppingCard(testShoppingCard);

        // Check input argument
        assertEquals(testShoppingCard, argumentCaptor.getValue());
    }

    /**
     * @see ShoppingCardServiceImpl#debitBalanceForItemBuying(Long, Double)
     */
    @Test
    public void debitBalanceForItemBuying_buyerIdAndItemPriceNullOrNegative() {
        Long buyerId = null;
        Double itemPrice = null;

        try {
            shoppingCardServiceRepoMock.debitBalanceForItemBuying(buyerId, itemPrice);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }

        buyerId = -5L;
        itemPrice = -5.0;

        try {
            shoppingCardServiceRepoMock.debitBalanceForItemBuying(buyerId, itemPrice);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }

    /**
     * @see ShoppingCardServiceImpl#debitBalanceForItemBuying(Long, Double)
     */
    @Test(expected = ServiceException.class)
    public void debitBalanceForItemBuying_debitFailed() {
        // Setup mock

        shoppingCardServiceRepoMock.debitBalanceForItemBuying(5L, 5.0);
    }

//    /**
//     * @see ShoppingCardServiceImpl#debitBalanceForItemBuying(Long, Double)
//     */
//    @Test
//    public void debitBalanceForItemBuying_debitSuccess() {
//        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
//        ArgumentCaptor<Double> argumentCaptor1 = ArgumentCaptor.forClass(Double.class);
//
//        Long buyerId = 5L;
//        Double itemPrice = 5.0;
//
//       doReturn(true).when(shoppingCardServiceRepoMock).debitBalanceForItemBuying(argumentCaptor.capture(), argumentCaptor1.capture());
//
//        shoppingCardServiceRepoMock.debitBalanceForItemBuying(buyerId, itemPrice);
//
//        assertEquals(buyerId, argumentCaptor.getValue());
//        assertEquals(itemPrice, argumentCaptor1.getValue());
//    }

    /**
     * @see ShoppingCardServiceImpl#deleteShoppingCard(ShoppingCard)
     */
    @Test
    public void deleteShoppingCard_idNullOrNegative() {
        // Create id
        Long id;

        // Try to delete shopping card
        try {
            shoppingCardServiceRepoMock.deleteShoppingCard(null);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }

        // Change id to negative value
        id = -5L;

        // Try to delete shopping card
        try {
            shoppingCardServiceRepoMock.deleteShoppingCard(testShoppingCard);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }


    /**
     * @see ShoppingCardServiceImpl#deleteShoppingCard(ShoppingCard)
     */
    @Test(expected = ServiceException.class)
    public void deleteShoppingCard_deleteFailure() {
        // Create id
        Long id = 5L;
        testShoppingCard = createTestShoppingCard();
        testShoppingCard.setAbstractUser(createTestUser());
        testShoppingCard.setId(55L);

        // Setup mocks
        doThrow(DAOException.class).when(shoppingCardRepoMock).delete(any(ShoppingCard.class));

        // Test method
        shoppingCardServiceRepoMock.deleteShoppingCard(testShoppingCard);
    }


    /**
     * @see ShoppingCardServiceImpl#deleteShoppingCard(ShoppingCard)
     */
    @Test
    public void deleteShoppingCard_deleteSuccess() {
        //Create argument capture
        ArgumentCaptor<ShoppingCard> argumentCaptor = ArgumentCaptor.forClass(ShoppingCard.class);

        // Create id
        Long id = 5L;
        testShoppingCard = createTestShoppingCard();
        testShoppingCard.setAbstractUser(createTestUser());
        testShoppingCard.setId(55L);
        // Setup mocks
        doNothing().when(shoppingCardRepoMock).delete(argumentCaptor.capture());

        // Test method
        shoppingCardServiceRepoMock.deleteShoppingCard(testShoppingCard);

        // Check input argument
        assertEquals(testShoppingCard, argumentCaptor.getValue());
    }

    /**
     * @see ShoppingCardServiceImpl#deleteShoppingCard(ShoppingCard)
     */
    @Test
    public void deleteShoppingCardByBuyerId_buyerIdNullOrNegative() {
        Long buyerId = null;

        try {
            shoppingCardServiceRepoMock.deleteShoppingCard(testShoppingCard);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }

        buyerId = -5L;

        try {
            shoppingCardServiceRepoMock.deleteShoppingCard(testShoppingCard);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }

    /**
     * @see ShoppingCardServiceImpl#deleteShoppingCard(ShoppingCard)
     */
    @Test(expected = ServiceException.class)
    public void deleteShoppingCardByBuyerId_deleteFailed() {
        // Setup mock
        doThrow(ServiceException.class).when(shoppingCardRepoMock).delete(any(ShoppingCard.class));
        testShoppingCard = createTestShoppingCard();
        testShoppingCard.setId(7L);
        testShoppingCard.setAbstractUser(createTestUser());
        // Test method
        shoppingCardServiceRepoMock.deleteShoppingCard(testShoppingCard);
    }

    /**
     * @see ShoppingCardServiceImpl#deleteShoppingCard(ShoppingCard)
     */
    @Test
    public void deleteShoppingCardByBuyerId_deleteSuccess() {
        ArgumentCaptor<ShoppingCard> argumentCaptor = ArgumentCaptor.forClass(ShoppingCard.class);

        testShoppingCard = createTestShoppingCard();
        testShoppingCard.setId(7L);
        testShoppingCard.setAbstractUser(createTestUser());
        // Setup mock
        doNothing().when(shoppingCardRepoMock).delete(argumentCaptor.capture());

        // Test method
        shoppingCardServiceRepoMock.deleteShoppingCard(testShoppingCard);

        assertEquals(testShoppingCard, argumentCaptor.getValue());
    }

    // endregion
}
