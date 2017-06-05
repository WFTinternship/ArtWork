package dao.integration;

import am.aca.wftartproject.dao.ShoppingCardDao;
import am.aca.wftartproject.dao.UserDao;
import am.aca.wftartproject.dao.impl.ShoppingCardDaoImpl;
import am.aca.wftartproject.dao.impl.UserDaoImpl;
import am.aca.wftartproject.model.ShoppingCard;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.util.dbconnection.ConnectionModel;
import am.aca.wftartproject.util.dbconnection.ConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.TestObjectTemplate;

import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.TestCase.*;
import static util.AssertTemplates.assertEqualShoppingCards;

/**
 * Created by Armen on 6/2/2017
 */
public class ShoppingCardDaoIntegrationTest {


    private ShoppingCardDao shoppingCardDao;
    private ShoppingCard testShoppingCard;
    private UserDao userDao;
    private User testUser;

    /**
     * Creates Test user and shoppingCard, adds user info to the database.
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {

        //create test user and shoppingCard, add user into db

        Connection conn = new ConnectionFactory().getConnection(ConnectionModel.SINGLETON).getTestDBConnection();
        userDao = new UserDaoImpl(conn);
        shoppingCardDao = new ShoppingCardDaoImpl(conn);

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
    @Test(expected = NullPointerException.class)
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
    @Test(expected = NullPointerException.class)
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
    public void update_ShoppingCard_Success() throws SQLException {

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
    @Test(expected = NullPointerException.class)
    public void update_ShoppingCard_Failure() throws SQLException {

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
    @Test(expected = NullPointerException.class)
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