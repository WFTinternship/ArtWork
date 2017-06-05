package integration_tests.dao;

import am.aca.wftartproject.dao.*;
import am.aca.wftartproject.dao.impl.*;
import am.aca.wftartproject.exception.DAOFailException;
import am.aca.wftartproject.model.*;
import static integration_tests.service.AssertTemplates.assertEqualShoppingCards;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import am.aca.wftartproject.util.DBConnection;
import integration_tests.service.TestObjectTemplate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by Armen on 6/2/2017.
 */
public class ShoppingCardDaoIntegrationTest {
    private DBConnection connection = new DBConnection();
    private UserDao userDao = new UserDaoImpl(connection.getDBConnection(DBConnection.DBType.TEST));
    private ShoppingCardDao shoppingCardDao = new ShoppingCardDaoImpl(connection.getDBConnection(DBConnection.DBType.TEST));
    private User testUser;
    private ShoppingCard testShoppingCard;

    public ShoppingCardDaoIntegrationTest() throws SQLException, ClassNotFoundException {
    }

    @Before
    public void setUp() {

        //create test user and shoppingCard, add user into db

        testUser = TestObjectTemplate.createTestUser();
        userDao.addUser(testUser);
        testShoppingCard = new ShoppingCard();
        testShoppingCard.setBalance(TestObjectTemplate.getRandomNumber() + 1.1);

    }

    /**
     * @see ShoppingCardDao#addShoppingCard(Long, ShoppingCard)
     */
    @Test
    public void addShoppingCard() {

        assertNotNull(testUser.getId());
        assertNotNull(testShoppingCard);
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);
        ShoppingCard added = shoppingCardDao.getShoppingCard(testShoppingCard.getId());
        assertEqualShoppingCards(added, testShoppingCard);

    }

    /**
     * @see ShoppingCardDao#addShoppingCard(Long, ShoppingCard)
     */
    @Test(expected = DAOFailException.class)
    public void addShoppingCard_failure() {

        assertNotNull(testUser.getId());
        assertNotNull(testShoppingCard);
        shoppingCardDao.addShoppingCard(null, testShoppingCard);
        ShoppingCard added = shoppingCardDao.getShoppingCard(testShoppingCard.getId());
        assertEqualShoppingCards(added, testShoppingCard);

    }

    /**
     * @see ShoppingCardDao#getShoppingCard(Long)
     */
    @Test
    public void getShoppingCard_Success() {


        assertNotNull(testUser.getId());
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());
        ShoppingCard shoppingCard = shoppingCardDao.getShoppingCard(testShoppingCard.getId());
        assertEqualShoppingCards(shoppingCard, testShoppingCard);
    }

    /**
     * @see ShoppingCardDao#getShoppingCard(Long)
     */
    @Test(expected = DAOFailException.class)
    public void getShoppingCard_Failure() {


        assertNotNull(testUser.getId());
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());
        ShoppingCard shoppingCard = shoppingCardDao.getShoppingCard(null);
        assertEqualShoppingCards(shoppingCard, testShoppingCard);
    }

    /**
     * @see ShoppingCardDao#updateShoppingCard(Long, ShoppingCard)
     */
    @Test
    public void update_ShoppingCard_Success() {

        assertNotNull(testUser.getId());
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());
        testShoppingCard.setBalance(TestObjectTemplate.getRandomNumber() + 1.1);
        shoppingCardDao.updateShoppingCard(testShoppingCard.getId(), testShoppingCard);
        ShoppingCard updatedShoppingCard = shoppingCardDao.getShoppingCard(testShoppingCard.getId());
        assertEquals(updatedShoppingCard.getBalance(), testShoppingCard.getBalance());

    }

    /**
     * @see ShoppingCardDao#updateShoppingCard(Long, ShoppingCard)
     */
    @Test(expected = DAOFailException.class)
    public void update_ShoppingCard_Failure() {

        assertNotNull(testUser.getId());
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());
        testShoppingCard.setBalance(TestObjectTemplate.getRandomNumber() + 1.1);
        shoppingCardDao.updateShoppingCard(testShoppingCard.getId(), null);
        ShoppingCard updatedShoppingCard = shoppingCardDao.getShoppingCard(testShoppingCard.getId());
        assertEquals(updatedShoppingCard.getBalance(), testShoppingCard.getBalance());

    }

    /**
     * @see ShoppingCardDao#deleteShoppingCard(Long)
     */
    @Test
    public void deleteShoppingCard_Success() {
        assertNotNull(testUser.getId());
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());
        shoppingCardDao.deleteShoppingCard(testShoppingCard.getId());
        ShoppingCard deletedShoppingCard = shoppingCardDao.getShoppingCard(testShoppingCard.getId());
        assertNull(deletedShoppingCard.getId());

    }

    /**
     * @see ShoppingCardDao#deleteShoppingCard(Long)
     */
    @Test(expected = DAOFailException.class)
    public void deleteShoppingCard_Failure() {
        assertNotNull(testUser.getId());
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());
        shoppingCardDao.deleteShoppingCard(null);
        ShoppingCard deletedShoppingCard = shoppingCardDao.getShoppingCard(testShoppingCard.getId());
        assertNull(deletedShoppingCard.getId());

    }

    @After
    public void tearDown() {
        //delete inserted test users,shoppingCards from db
        if (testShoppingCard.getId() != null)
            shoppingCardDao.deleteShoppingCard(testShoppingCard.getId());
        if (testUser.getId() != null)
            userDao.deleteUser(testUser.getId());

        testShoppingCard = null;

        testUser = null;
    }
}
