package am.aca.wftartproject.dao.integration;

import am.aca.wftartproject.dao.ShoppingCardDao;
import am.aca.wftartproject.dao.UserDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.ShoppingCard;
import am.aca.wftartproject.model.ShoppingCardType;
import am.aca.wftartproject.model.User;
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

import static am.aca.wftartproject.util.AssertTemplates.assertEqualShoppingCards;
import static junit.framework.TestCase.*;


/**
 * Created by Armen on 6/2/2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:springconfig/daointegration/spring-dao-integration.xml",
        "classpath:springconfig/database/spring-database.xml"})
public class ShoppingCardDaoIntegrationTest extends BaseDAOIntegrationTest{

    private static Logger LOGGER = Logger.getLogger(ArtistDaoIntegrationTest.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private ShoppingCardDao shoppingCardDao;
    private User testUser;
    private ShoppingCard testShoppingCard;

    public ShoppingCardDaoIntegrationTest() throws SQLException, ClassNotFoundException {
    }

    /**
     * Creates connection, user and shoppingCard for tests
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {

        // create db connection
        dataSource = new ConnectionFactory()
                .getConnection(ConnectionModel.POOL)
                .getTestDBConnection();

        // create test user and shoppingCard, add user into db
        testUser = TestObjectTemplate.createTestUser();
        userDao.addUser(testUser);
        testShoppingCard = new ShoppingCard();
//        testShoppingCard.setBalance(TestObjectTemplate.getRandomNumber() + 1.1);
        testShoppingCard.setShoppingCardType(ShoppingCardType.PAYPAL);

        // print busy connections quantity
        if (dataSource instanceof ComboPooledDataSource) {
            LOGGER.info(String.format("Number of busy connections Start: %s", ((ComboPooledDataSource) dataSource).getNumBusyConnections()));        }
    }

    /**
     * Deletes all users and shoppingCards created during the tests
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @After
    public void tearDown() throws SQLException, ClassNotFoundException {

        // delete inserted test users,shoppingCards from db
        if (testShoppingCard.getId() != null)
            shoppingCardDao.deleteShoppingCard(testShoppingCard.getId());
        if (testUser.getId() != null)
            userDao.deleteUser(testUser.getId());

        // set temp instance refs to null
        testShoppingCard = null;
        testUser = null;

        // print busy connections quantity
        if (dataSource instanceof ComboPooledDataSource) {
            LOGGER.info(String.format("Number of busy connections End: %s", ((ComboPooledDataSource) dataSource).getNumBusyConnections()));        }
    }

    //region(TEST_CASE)

    /**
     * @see ShoppingCardDao#addShoppingCard(Long, ShoppingCard)
     */
    @Test
    public void addShoppingCard_Success() {

        // check testUser id and testShoppingCard for null
        assertNotNull(testUser.getId());
        assertNotNull(testShoppingCard);

        // set testShoppingCard object into DB
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);

        // get shoppingCard from DB
        ShoppingCard added = shoppingCardDao.getShoppingCard(testShoppingCard.getId());

        // check for equals
        assertEqualShoppingCards(added, testShoppingCard);
    }

    /**
     * @see ShoppingCardDao#addShoppingCard(Long, ShoppingCard)
     */
    @Test(expected = DAOException.class)
    public void addShoppingCard_Failure() {

        // check testUser id and testShoppingCard for null
        assertNotNull(testUser.getId());
        assertNotNull(testShoppingCard);

        // try to add shoppingCard with out of range id
        shoppingCardDao.addShoppingCard(124561515415313L, testShoppingCard);
    }

    /**
     * @see ShoppingCardDao#getShoppingCard(Long)
     */
    @Test
    public void getShoppingCard_Success() {

        // check testUser id for null
        assertNotNull(testUser.getId());

        // set testShoppingCard object into DB
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);

        // check testShoppingCard object and testShoppingCard id for null
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());

        // get shoppingCard from DB
        ShoppingCard shoppingCard = shoppingCardDao.getShoppingCard(testShoppingCard.getId());

        // check for equals
        assertEqualShoppingCards(shoppingCard, testShoppingCard);
    }

    /**
     * @see ShoppingCardDao#getShoppingCard(Long)
     */
    @Test
    public void getShoppingCard_Failure() {

        // check testUser id for null
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

        // check testUser for not null and add shoppingCard into DB
        assertNotNull(testUser.getId());
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);

        // check shoppingCard for not null
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());

        // try to update shoppingCard
        testShoppingCard.setBalance(TestObjectTemplate.getRandomNumber() + 1.1);
        shoppingCardDao.updateShoppingCard(testShoppingCard.getId(), testShoppingCard);

        // find and get updated shoppingCard from DB and check its sameness with testShoppingCard
        ShoppingCard updatedShoppingCard = shoppingCardDao.getShoppingCard(testShoppingCard.getId());
        assertEquals(updatedShoppingCard.getBalance(), testShoppingCard.getBalance());
    }

    /**
     * @see ShoppingCardDao#updateShoppingCard(Long, ShoppingCard)
     */
    @Test(expected = DAOException.class)
    public void updateShoppingCard_Failure() {

        // check testUser for not null and add shoppingCard into DB
        assertNotNull(testUser.getId());
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);

        // check shoppingCard for not null
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());

        // try to update shoppingCard
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
    @Test(expected = DAOException.class)
    public void deleteShoppingCard_Failure() {

        // check all components for null and check delete result for true
        assertNotNull(testUser.getId());
        shoppingCardDao.addShoppingCard(testUser.getId(), testShoppingCard);
        assertNotNull(testShoppingCard);
        assertNotNull(testShoppingCard.getId());
        assertFalse(shoppingCardDao.deleteShoppingCard(4154541654564654656L));
    }

    //endregion
}
