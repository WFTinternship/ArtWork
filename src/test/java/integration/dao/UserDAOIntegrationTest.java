package integration.dao;

import am.aca.wftartproject.dao.UserDao;
import am.aca.wftartproject.dao.impl.UserDaoImpl;
import am.aca.wftartproject.exception.DAOException;
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

import static junit.framework.Assert.assertNotSame;
import static junit.framework.TestCase.*;
import static util.AssertTemplates.assertEqualUsers;

/**
 * Created by Armen on 5/30/2017
 */
public class UserDAOIntegrationTest {

    private Logger LOGGER = Logger.getLogger(ArtistDaoIntegrationTest.class);
    private DataSource conn;

    private UserDaoImpl userDao;
    private User testUser;

    public UserDAOIntegrationTest() throws SQLException, ClassNotFoundException {
    }

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {

        //create db connection
        conn = new ConnectionFactory()
                .getConnection(ConnectionModel.POOL)
                .getTestDBConnection();
        userDao = new UserDaoImpl(conn);

        //create test user
        testUser = TestObjectTemplate.createTestUser();

        if (conn instanceof ComboPooledDataSource) {
            LOGGER.info(((ComboPooledDataSource) conn).getNumBusyConnections());
        }

    }

    /**
     * @see UserDao#addUser(User)
     */
    @Test
    public void addUser_Success() {

        //test user already inserted in setup, get record by userId
        testUser.setId(null);

        // add user into db and check id for null
        assertNotNull(testUser);

        userDao.addUser(testUser);

        assertNotNull(testUser.getId());

        // get user by id and check for sameness with origin
        Long id = testUser.getId();

        User dbUser = userDao.findUser(id);
        System.out.println(dbUser.getId());

        assertNotNull(dbUser);
        assertEqualUsers(dbUser, testUser);
    }

    /**
     * @see UserDao#addUser(User)
     */
    @Test(expected = DAOException.class)
    public void addUser_Failure() {

        //test user already inserted in setup, get record by user
        testUser.setLastName(null);
        userDao.addUser(testUser);
        assertNotNull(testUser);
        assertNull(testUser.getId());

    }

    /**
     * @see UserDao#updateUser(Long, User)
     */
    @Test
    public void updateUser_Success() {
        userDao.addUser(testUser);
        assertNotNull(testUser);
        User newUser = TestObjectTemplate.createTestUser();
        assertNotNull(newUser);
        userDao.updateUser(testUser.getId(), newUser);
        User dbUserUpdate = userDao.findUser(testUser.getId());
        assertNotNull(dbUserUpdate);
        assertNotSame(dbUserUpdate, testUser);
    }

    /**
     * @see UserDao#updateUser(Long, User)
     */
    @Test(expected = DAOException.class)
    public void updateUser_Failure() {
        userDao.addUser(testUser);
        assertNotNull(testUser);
        User newUser = TestObjectTemplate.createTestUser();
        newUser.setFirstName(null);
        assertNotNull(newUser);
        userDao.updateUser(testUser.getId(), newUser);
        User dbUserUpdate = userDao.findUser(testUser.getId());
        assertNotNull(dbUserUpdate);
    }

    /**
     * @see UserDao#deleteUser(Long)
     */
    @Test
    public void deleteUser_Success() {
        userDao.addUser(testUser);
        assertNotNull(testUser);
        assertTrue(userDao.deleteUser(testUser.getId()));

        testUser.setId(null);
    }

    /**
     * @see UserDao#deleteUser(Long)
     */
    @Test
    public void deleteUser_Failure() {

        userDao.addUser(testUser);
        assertNotNull(testUser);
        assertFalse(userDao.deleteUser(4546465465465L));
    }


    /**
     * @see UserDao#findUser(Long)
     */
    @Test
    public void findUser_Success() {
        userDao.addUser(testUser);
        User findResult = userDao.findUser(testUser.getId());
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


    @After
    public void tearDown() throws SQLException {

        //delete inserted test users from db
        if (testUser.getId() != null)
            userDao.deleteUser(testUser.getId());

        //set temp instance refs to null
        testUser = null;
        if (conn instanceof ComboPooledDataSource) {
            LOGGER.info(((ComboPooledDataSource) conn).getNumBusyConnections());
        }
    }
}
