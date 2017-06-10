package integration.dao;

import am.aca.wftartproject.dao.ShoppingCardDao;
import am.aca.wftartproject.dao.UserDao;
import am.aca.wftartproject.dao.impl.ShoppingCardDaoImpl;
import am.aca.wftartproject.dao.impl.UserDaoImpl;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.ShoppingCard;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.util.dbconnection.ConnectionFactory;
import am.aca.wftartproject.util.dbconnection.ConnectionModel;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.TestObjectTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

import static junit.framework.TestCase.*;
import static util.AssertTemplates.assertEqualShoppingCards;

/**
 * Created by Armen on 6/2/2017
 */
public class ShoppingCardDaoIntegrationTest {

    private static Logger LOGGER = Logger.getLogger(ArtistDaoIntegrationTest.class);
    private DataSource conn;

    private UserDao userDao;
    private ShoppingCardDao shoppingCardDao;
    private User testUser;
    private ShoppingCard testShoppingCard;

    public ShoppingCardDaoIntegrationTest() throws SQLException, ClassNotFoundException {
    }

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        //create db connection
        conn = new ConnectionFactory()
                .getConnection(ConnectionModel.POOL)
                .getTestDBConnection();

        userDao = new UserDaoImpl(conn);
        shoppingCardDao = new ShoppingCardDaoImpl(conn);

        //create test user and shoppingCard, add user into db
        testUser = TestObjectTemplate.createTestUser();
        userDao.addUser(testUser);
        testShoppingCard = new ShoppingCard();
        testShoppingCard.setBalance(TestObjectTemplate.getRandomNumber() + 1.1);

        //Prints busy connections quantity
        if (conn instanceof ComboPooledDataSource) {
            LOGGER.info(((ComboPooledDataSource) conn).getNumBusyConnections());
        }
    }

    /**
     * @see ShoppingCardDao#addShoppingCard(Long, ShoppingCard)
     */
    @Test
    public void addShoppingCard_Success() {

        assertNotNull(testUser.getId());
        assertNotNull(testShoppingCard);
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);
        ShoppingCard added = shoppingCardDao.getShoppingCard(testShoppingCard.getId());
        assertEqualShoppingCards(added, testShoppingCard);
    }

    /**
     * @see ShoppingCardDao#addShoppingCard(Long, ShoppingCard)
     */
    @Test(expected = DAOException.class)
    public void addShoppingCard_Failure() {

        assertNotNull(testUser.getId());
        assertNotNull(testShoppingCard);
        shoppingCardDao.addShoppingCard(124561515415313L, testShoppingCard);
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
    @Test
    public void getShoppingCard_Failure() {
        assertNotNull(testUser.getId());
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);
        assertNotNull(testShoppingCard.getId());
        shoppingCardDao.getShoppingCard(1515131651654151351L);
    }

    /**
     * @see ShoppingCardDao#updateShoppingCard(Long, ShoppingCard)
     */
    @Test
    public void updateShoppingCard_Success() {

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
    @Test
    public void updateShoppingCard_Failure() {

        assertNotNull(testUser.getId());
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());
        testShoppingCard.setBalance(TestObjectTemplate.getRandomNumber() + 1.1);
        assertFalse(shoppingCardDao.updateShoppingCard(1516516516351635163L, testShoppingCard));
    }

    /**
     * @see ShoppingCardDao#deleteShoppingCard(Long)
     */
    @Test
    public void deleteShoppingCard_Success() {

        // check all components for null and check delete result for true
        assertNotNull(testUser.getId());
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());
        assertTrue(shoppingCardDao.deleteShoppingCard(testShoppingCard.getId()));
        testShoppingCard.setId(null);
    }

    /**
     * @see ShoppingCardDao#deleteShoppingCard(Long)
     */
    @Test
    public void deleteShoppingCard_Failure() {

        // check all components for null and check delete result for true
        assertNotNull(testUser.getId());
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());
        assertFalse(shoppingCardDao.deleteShoppingCard(4154541654564654656L));
    }

    @After
    public void tearDown() throws SQLException, ClassNotFoundException {

        //delete inserted test users,shoppingCards from db
        if (testShoppingCard.getId() != null)
            shoppingCardDao.deleteShoppingCard(testShoppingCard.getId());
        if (testUser.getId() != null)
            userDao.deleteUser(testUser.getId());

        //set temp instance refs to null
        testShoppingCard = null;
        testUser = null;

        //Prints busy connections quantity
        if (conn instanceof ComboPooledDataSource) {
            LOGGER.info(((ComboPooledDataSource) conn).getNumBusyConnections());
        }
    }
}
