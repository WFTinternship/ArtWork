package integration.dao;

import am.aca.wftartproject.dao.ShoppingCardDao;
import am.aca.wftartproject.dao.impl.ShoppingCardDaoImpl;
import am.aca.wftartproject.dao.impl.UserDaoImpl;
import am.aca.wftartproject.model.ShoppingCard;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.util.DBConnection;
import integration.service.TestObjectTemplate;

import static integration.service.AssertTemplates.assertEqualShoppingCards;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNotSame;
import static junit.framework.TestCase.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by Armen on 6/2/2017
 */
public class ShoppingCardDaoIntegrationTest {
    private DBConnection connection = new DBConnection();
    private UserDaoImpl userDao = new UserDaoImpl(connection.getDBConnection(DBConnection.DBType.TEST));
    private ShoppingCardDaoImpl shoppingCardDao = new ShoppingCardDaoImpl(connection.getDBConnection(DBConnection.DBType.TEST));
    private User testUser;
    private ShoppingCard testShoppingCard;

    public ShoppingCardDaoIntegrationTest() throws SQLException, ClassNotFoundException {
    }

    @Before
    public void setUp() {
        //create test user and shoppingCard
        testUser = TestObjectTemplate.createTestUser();
        testShoppingCard = new ShoppingCard();
        testShoppingCard.setBalance(TestObjectTemplate.getRandomNumber() + 1.1);
        //insert test user into db, get generated id

        //set id to test user
    }

    /**
     * @see ShoppingCardDao#addShoppingCard(Long, ShoppingCard)
     */
    @Test
    public void addShoppingCard_Success() {

        userDao.addUser(testUser);
        assertNotNull(testUser.getId());
        assertNotNull(testShoppingCard);
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);
        ShoppingCard added = shoppingCardDao.getShoppingCard(testShoppingCard.getId());
        assertEqualShoppingCards(added, testShoppingCard);

    }

    /**
     * @see ShoppingCardDao#getShoppingCard(Long)
     */
    @Test
    public void getShoppingCard_Success(){

        userDao.addUser(testUser);
        assertNotNull(testUser.getId());
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());
        ShoppingCard shoppingCard = shoppingCardDao.getShoppingCard(testShoppingCard.getId());
        assertEqualShoppingCards(shoppingCard, testShoppingCard);
    }

    /**
     * @see ShoppingCardDao#updateShoppingCard(Long, ShoppingCard)
     */
    @Test
    public void updateShoppingCard_Success(){

        userDao.addUser(testUser);
        assertNotNull(testUser.getId());
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());
        testShoppingCard.setBalance(TestObjectTemplate.getRandomNumber() + 1.1);
        shoppingCardDao.updateShoppingCard(testShoppingCard.getId(), testShoppingCard);
        ShoppingCard updatedShoppingCard = shoppingCardDao.getShoppingCard(testShoppingCard.getId());
        assertNotSame(updatedShoppingCard.getBalance(), testShoppingCard.getBalance());

    }

    /**
     * @see ShoppingCardDao#deleteShoppingCard(Long)
     */
    @Test
    public void deleteShoppingCard_Success(){
        userDao.addUser(testUser);
        assertNotNull(testUser.getId());
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());
        shoppingCardDao.deleteShoppingCard(testShoppingCard.getId());
        ShoppingCard deletedShoppingCard = shoppingCardDao.getShoppingCard(testShoppingCard.getId());
        assertNull(deletedShoppingCard);

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
