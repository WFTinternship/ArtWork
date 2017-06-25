package am.aca.wftartproject.service.unit;

import am.aca.wftartproject.BaseUnitTest;
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
import org.springframework.beans.factory.annotation.Autowired;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualShoppingCards;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestShoppingCard;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.mockito.Matchers.any;
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
    public void setUp(){
    }

    @After
    public void tearDown(){
    }

    // region <TEST CASE>

    /**
     * @see ShoppingCardServiceImpl#addShoppingCard(java.lang.Long, am.aca.wftartproject.model.ShoppingCard)
     */
    @Test
    public void addShoppingCard_userIdNullOrNegative() {
        // Create shopping card and null userId
        Long userId;
        testShoppingCard = createTestShoppingCard();

        // Try to add shopping card
        try {
            shoppingCardService.addShoppingCard(null, testShoppingCard);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Create negative userId
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

        // Try to add null shopping card
        try {
            shoppingCardService.addShoppingCard(userId, null);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Create invalid shopping card
        testShoppingCard = createTestShoppingCard();
        testShoppingCard.setId(5L);
        testShoppingCard.setBalance(0.0);

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
    @Test(expected = ServiceException.class)
    public void addShoppingCard_addFailure() {
        Long userId = 5L;
        testShoppingCard = createTestShoppingCard();
        testShoppingCard.setId(userId);

        // Setup mocks
        doThrow(DAOException.class).when(shoppingCardDaoMock).addShoppingCard(anyLong(), any(ShoppingCard.class));

        // Test method
        shoppingCardService.addShoppingCard(userId, testShoppingCard);

    }

    /**
     * @see ShoppingCardServiceImpl#addShoppingCard(java.lang.Long, am.aca.wftartproject.model.ShoppingCard)
     */
    @Test
    public void addShoppingCard_addSuccess() {
        //Create argument capture
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<ShoppingCard> argumentCaptor1 = ArgumentCaptor.forClass(ShoppingCard.class);

        Long userId = 5L;
        testShoppingCard = createTestShoppingCard();
        testShoppingCard.setId(userId);

        // Setup mocks
        doNothing().when(shoppingCardDaoMock).addShoppingCard(argumentCaptor.capture(), argumentCaptor1.capture());

        // Test method
        shoppingCardService.addShoppingCard(userId, testShoppingCard);

        assertEquals(userId, argumentCaptor.getValue());
        assertEquals(testShoppingCard, argumentCaptor1.getValue());
    }

    /**
     * @see ShoppingCardServiceImpl#getShoppingCard(java.lang.Long)
     */
    @Test
    public void getShoppingCard_idNullOrNegative() {
        // Create null id
        Long id;

        // Try to get shopping card
        try {
            shoppingCardService.getShoppingCard(null);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Create negative id
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
    @Test(expected = ServiceException.class)
    public void getShoppingCard_getFailure() {
        Long id = 5L;

        doThrow(DAOException.class).when(shoppingCardDaoMock).getShoppingCard(anyLong());

        shoppingCardService.getShoppingCard(id);

    }

    /**
     * @see ShoppingCardServiceImpl#getShoppingCard(java.lang.Long)
     */
    @Test
    public void getShoppingCard_getSuccess() {
        //Create argument capture
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        Long id = 5L;
        testShoppingCard = createTestShoppingCard();

        // Setup mocks
        doReturn(testShoppingCard).when(shoppingCardDaoMock).getShoppingCard(argumentCaptor.capture());

        // Test method
        assertEqualShoppingCards(testShoppingCard, shoppingCardService.getShoppingCard(id));

        assertEquals(id, argumentCaptor.getValue());
    }

    /**
     * @see ShoppingCardServiceImpl#updateShoppingCard(java.lang.Long, am.aca.wftartproject.model.ShoppingCard)
     */
    @Test
    public void updateShoppingCard_idNullOrNegative() {
        // Create testShoppingCard and null id
        Long id;
        testShoppingCard = createTestShoppingCard();

        // Try to update shopping card
        try {
            shoppingCardService.updateShoppingCard(null, testShoppingCard);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }

        // Create negative id
        id = -5L;

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
        testShoppingCard.setBalance(0.0);

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

        shoppingCardService.updateShoppingCard(id, testShoppingCard);

        assertEquals(id, argumentCaptor.getValue());
        assertEquals(testShoppingCard, argumentCaptor1.getValue());
    }

    /**
     * @see ShoppingCardServiceImpl#deleteShoppingCard(java.lang.Long)
     */
    @Test
    public void deleteShoppingCard_idNullOrNegative() {

        // Create null id
        Long id;

        // Try to delete shopping card
        try {
            shoppingCardService.deleteShoppingCard(null);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }

        // Create negative id
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
    @Test(expected = ServiceException.class)
    public void deleteShoppingCard_deleteFailure() {

        Long id = 5L;

        // Setup mocks
        doThrow(DAOException.class).when(shoppingCardDaoMock).deleteShoppingCard(anyLong());

        // Test method
        shoppingCardService.deleteShoppingCard(id);
    }

    /**
     * @see ShoppingCardServiceImpl#deleteShoppingCard(java.lang.Long)
     */
    @Test
    public void deleteShoppingCard_deleteSuccess() {
        //Create argument capture
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        Long id = 5L;

        // Setup mocks
        doReturn(true).when(shoppingCardDaoMock).deleteShoppingCard(argumentCaptor.capture());

        // Test method
        shoppingCardService.deleteShoppingCard(id);

        assertEquals(id, argumentCaptor.getValue());
    }


    // endregion
}
