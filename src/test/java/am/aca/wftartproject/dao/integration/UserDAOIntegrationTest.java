package am.aca.wftartproject.dao.integration;

import am.aca.wftartproject.dao.UserDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.util.AssertTemplates;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualUsers;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestUser;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.TestCase.*;


/**
 * Created by Armen on 5/30/2017
 */

public class UserDAOIntegrationTest extends BaseDAOIntegrationTest {

    private static Logger LOGGER = Logger.getLogger(ArtistDaoIntegrationTest.class);

    @Autowired
    private UserDao userDao;

    private User testUser;

    public UserDAOIntegrationTest() throws SQLException, ClassNotFoundException {
    }

    /**
     * Creates user for tests
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        // Create test user
        testUser = createTestUser();

        // Print busy connections quantity
        if (jdbcTemplate.getDataSource() instanceof ComboPooledDataSource) {
            LOGGER.info(String.format("Number of busy connections Start: %s",
                    ((ComboPooledDataSource) jdbcTemplate.getDataSource()).getNumBusyConnections()));
        }
    }

    /**
     * Deletes all users created during the tests
     *
     * @throws SQLException
     */
    @After
    public void tearDown() throws SQLException {
        // Delete inserted test users from db
        if (testUser.getId() != null)
            userDao.deleteUser(testUser.getId());

        // Set temp instance refs to null
        testUser = null;

        // Print busy connections quantity
        if (jdbcTemplate.getDataSource() instanceof ComboPooledDataSource) {
            LOGGER.info(String.format("Number of busy connections End: %s",
                    ((ComboPooledDataSource) jdbcTemplate.getDataSource()).getNumBusyConnections()));
        }
    }

    // region<TEST CASE>

    /**
     * @see UserDao#addUser(User)
     */
    @Test
    public void addUser_Success() {
        assertNotNull(testUser);

        // Test method
        userDao.addUser(testUser);

        assertNotNull(testUser.getId());

        // Get user by id and check for sameness with origin
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
        testUser.setLastName(null);

        // Test method
        userDao.addUser(testUser);

        assertNotNull(testUser);
        assertNull(testUser.getId());
    }

    /**
     * @see UserDao#findUser(Long)
     */
    @Test
    public void findUser_Success() {
        // Add user to DB
        userDao.addUser(testUser);

        // Test method
        User findResult = userDao.findUser(testUser.getId());

        // Check for equals
        assertEqualUsers(testUser, findResult);
    }

    /**
     * @see UserDao#findUser(Long)
     */
    @Test
    public void findUser_Failure() {
        // Add user to DB
        userDao.addUser(testUser);

        // Test method
        User findResult = userDao.findUser(-7L);

        // Check result fo null
        assertNull(findResult);
    }

    /**
     * @see UserDao#findUser(String)
     */
    @Test
    public void findUserByEmail_Success() {
        // Add user to DB
        userDao.addUser(testUser);

        // Test method
        User foundUser = userDao.findUser(testUser.getEmail());

        // Check for equals
        AssertTemplates.assertEqualUsers(testUser, foundUser);
    }

    /**
     * @see UserDao#findUser(String)
     */
    @Test
    public void findUserByEmail_Failure() {
        // Add user to DB
        userDao.addUser(testUser);

        // Test method
        User foundUser = userDao.findUser("fake email");

        // Check for null
        assertNull(foundUser);
    }

    /**
     * @see UserDao#updateUser(Long, User)
     */
    @Test
    public void updateUser_Success() {
        // Add user to DB and check for not null
        userDao.addUser(testUser);
        assertNotNull(testUser);

        // Create new user and check it for null
        User newUser = createTestUser();
        assertNotNull(newUser);

        // Test method
        userDao.updateUser(testUser.getId(), newUser);

        // Find and get updated user from DB and check sameness with testUser
        User dbUserUpdate = userDao.findUser(testUser.getId());
        assertNotNull(dbUserUpdate);
        assertNotSame(dbUserUpdate, testUser);
    }

    /**
     * @see UserDao#updateUser(Long, User)
     */
    @Test(expected = DAOException.class)
    public void updateUser_Failure() {
        // Add user to DB and check it for not null
        userDao.addUser(testUser);
        assertNotNull(testUser);

        // Create new user and set its firstName
        User newUser = createTestUser();
        newUser.setFirstName(null);

        // Check newUser for not null
        assertNotNull(newUser);

        // Test method
        userDao.updateUser(testUser.getId(), newUser);

        // Try to find and get updated user from DB and check it for not null
        User dbUserUpdate = userDao.findUser(testUser.getId());
        assertNotNull(dbUserUpdate);
    }

    /**
     * @see UserDao#deleteUser(Long)
     */
    @Test
    public void deleteUser_Success() {
        // Add user to DB and check it for not null
        userDao.addUser(testUser);
        assertNotNull(testUser);

        // Test method
        Boolean isDeleted = userDao.deleteUser(testUser.getId());

        assertTrue(isDeleted);
        testUser.setId(null);
    }

    /**
     * @see UserDao#deleteUser(Long)
     */
    @Test(expected = DAOException.class)
    public void deleteUser_Failure() {
        // Add user to DB and check it fro not null
        userDao.addUser(testUser);
        assertNotNull(testUser);

        // Test method
        assertFalse(userDao.deleteUser(4546465465465L));
    }

    // endregion
}
