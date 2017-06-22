package am.aca.wftartproject.dao.integration;

import am.aca.wftartproject.dao.UserDao;
import am.aca.wftartproject.dao.impl.UserDaoImpl;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.User;
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
import util.AssertTemplates;
import util.TestObjectTemplate;

import java.sql.SQLException;

import static junit.framework.Assert.assertNotSame;
import static junit.framework.TestCase.*;
import static util.AssertTemplates.assertEqualUsers;

/**
 * Created by Armen on 5/30/2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:springconfig/daointegration/spring-dao-integration.xml",
        "classpath:springconfig/database/spring-database.xml"})
public class UserDAOIntegrationTest extends BaseDAOIntegrationTest{

    private static Logger LOGGER = Logger.getLogger(ArtistDaoIntegrationTest.class);

    @Autowired
    private UserDaoImpl userDao;
    private User testUser;

    public UserDAOIntegrationTest() throws SQLException, ClassNotFoundException {
    }

    /**
     * Creates connection and user for tests
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {

        // create db connection
        dataSource = new ConnectionFactory()
                .getConnection(ConnectionModel.POOL)
                .getTestDBConnection();

        // create test user
        testUser = TestObjectTemplate.createTestUser();

        // print busy connections quantity
        if (dataSource instanceof ComboPooledDataSource) {
            LOGGER.info(String.format("Number of busy connections Start: %s", ((ComboPooledDataSource) dataSource).getNumBusyConnections()));
        }
    }

    /**
     * Deletes all users created during the tests
     * @throws SQLException
     */
    @After
    public void tearDown() throws SQLException {

        // delete inserted test users from db
        if (testUser.getId() != null)
            userDao.deleteUser(testUser.getId());

        // set temp instance refs to null
        testUser = null;

        // print busy connections quantity
        if (dataSource instanceof ComboPooledDataSource) {
            LOGGER.info(String.format("Number of busy connections End: %s", ((ComboPooledDataSource) dataSource).getNumBusyConnections()));        }
    }

    //region(TEST_CASE)

    /**
     * @see UserDao#addUser(User)
     */
    @Test
    public void addUser_Success() {

        // test user already inserted in setup, get record by userId
        testUser.setId(null);

        // add user into db and check id for null
        assertNotNull(testUser);

        userDao.addUser(testUser);

        assertNotNull(testUser.getId());

        // get user by id and check for sameness with origin
        Long id = testUser.getId();

        User dbUser = userDao.findUser(id);

        assertNotNull(dbUser);
        assertEqualUsers(dbUser, testUser);
    }

    /**
     * @see UserDao#addUser(User)
     */
    @Test(expected = DAOException.class)
    public void addUser_Failure() {

        // test user already inserted in setup, get record by user
        testUser.setLastName(null);
        userDao.addUser(testUser);
        assertNotNull(testUser);
        assertNull(testUser.getId());
    }

    /**
     * @see UserDao#findUser(Long)
     */
    @Test
    public void findUser_Success() {
        // add user into DB
        userDao.addUser(testUser);

        // try to find user by email
        User findResult = userDao.findUser(testUser.getId());

        // check for equals
        assertEqualUsers(testUser, findResult);
    }


    /**
     * @see UserDao#findUser(Long)
     */
    @Test
    public void findUser_Failure() {
        userDao.addUser(testUser);
        User findResult = userDao.findUser(-7L);
        assertNull(findResult);
    }

    /**
     * @see UserDao#findUser(String)
     */
    @Test
    public void findUserByEmail_Success(){
        // add user into DB
        userDao.addUser(testUser);

        // try to find user by email
        User foundUser = userDao.findUser(testUser.getEmail());

        //check for equals
        AssertTemplates.assertEqualUsers(testUser, foundUser);
    }

    /**
     * @see UserDao#findUser(String)
     */
    @Test
    public void findUserByEmail_Failure(){
        // add user into DB
        userDao.addUser(testUser);

        // try to find user
        User foundUser = userDao.findUser("flasyrsgiu");

        // check for null
        assertNull(foundUser);
    }

    /**
     * @see UserDao#updateUser(Long, User)
     */
    @Test
    public void updateUser_Success() {

        // add user into DB and check for not null
        userDao.addUser(testUser);
        assertNotNull(testUser);

        // create new user and check it for null
        User newUser = TestObjectTemplate.createTestUser();
        assertNotNull(newUser);

        // try to update user
        userDao.updateUser(testUser.getId(), newUser);

        // find and get updated user from DB and check sameness with testUser
        User dbUserUpdate = userDao.findUser(testUser.getId());
        assertNotNull(dbUserUpdate);
        assertNotSame(dbUserUpdate, testUser);
    }

    /**
     * @see UserDao#updateUser(Long, User)
     */
    @Test(expected = DAOException.class)
    public void updateUser_Failure() {

        // add user into DB and check it for not null
        userDao.addUser(testUser);
        assertNotNull(testUser);

        // create new user and set its firstName
        User newUser = TestObjectTemplate.createTestUser();
        newUser.setFirstName(null);

        // check newUser for not null
        assertNotNull(newUser);

        // try to update testUser
        userDao.updateUser(testUser.getId(), newUser);

        // try to find and get updated user from DB and check it for not null
        User dbUserUpdate = userDao.findUser(testUser.getId());
        assertNotNull(dbUserUpdate);
    }


    /**
     * @see UserDao#deleteUser(Long)
     */
    @Test
    public void deleteUser_Success() {

        // add user into DB and check it for not null
        userDao.addUser(testUser);
        assertNotNull(testUser);

        // try to delete user adn then set its id null
        assertTrue(userDao.deleteUser(testUser.getId()));
        testUser.setId(null);
    }

    /**
     * @see UserDao#deleteUser(Long)
     */
    @Test(expected = DAOException.class)
    public void deleteUser_Failure() {

        // add user into DB and check it fro not null
        userDao.addUser(testUser);
        assertNotNull(testUser);

        // try to delete user with non-existent id
        assertFalse(userDao.deleteUser(4546465465465L));
    }
    //endregion
}
