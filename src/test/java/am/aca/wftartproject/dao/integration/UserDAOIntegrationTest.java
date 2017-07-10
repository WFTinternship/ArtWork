package am.aca.wftartproject.dao.integration;

import am.aca.wftartproject.dao.UserDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.entity.User;
import am.aca.wftartproject.util.AssertTemplates;
import am.aca.wftartproject.util.TestObjectTemplate;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

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

    private User testUser;

    @Autowired
    private UserDao userDao;

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
//        if (jdbcTemplate.getDataSource() instanceof ComboPooledDataSource) {
//            LOGGER.info(String.format("Number of busy connections Start: %s",
//                    ((ComboPooledDataSource) jdbcTemplate.getDataSource()).getNumBusyConnections()));
//        }
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
            userDao.deleteUser(testUser);

        // Set temp instance refs to null
        testUser = null;

        // Print busy connections quantity
//        if (jdbcTemplate.getDataSource() instanceof ComboPooledDataSource) {
//            LOGGER.info(String.format("Number of busy connections End: %s",
//                    ((ComboPooledDataSource) jdbcTemplate.getDataSource()).getNumBusyConnections()));
//        }
    }

    // region<TEST CASE>

    /**
     * @see UserDao#addUser(User)
     */
    @Test
    public void addUser_Success() {
        // Test user already inserted in setup, get record by userId
        testUser.setId(null);

        // Add user into db and check id for null
        assertNotNull(testUser);

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
        // Test user already inserted in setup, get record by user
        testUser.setLastName(null);
        try{
            userDao.addUser(testUser);
        }
        catch (DAOException e){
            testUser.setLastName("test");
            throw new DAOException("");
        }

        assertNotNull(testUser);
        assertNull(testUser.getId());
    }

    /**
     * @see UserDao#findUser(Long)
     */
    @Test
    public void findUser_Success() {
        // Add user into DB
        userDao.addUser(testUser);

        // Try to find user by email
        User findResult = userDao.findUser(testUser.getId());

        // Check for equals
        assertEqualUsers(testUser, findResult);
    }

    /**
     * @see UserDao#findUser(Long)
     */
    @Test
    public void findUser_Failure() {
        // Add user into DB
        userDao.addUser(testUser);

        // Try to find user with negative id
        User findResult = userDao.findUser(-7L);

        // Check result fo null
        assertNull(findResult);
    }

    /**
     * @see UserDao#findUser(String)
     */
    @Test
    public void findUserByEmail_Success() {
        // Add user into DB
        userDao.addUser(testUser);

        // Try to find user by email
        User foundUser = userDao.findUser(testUser.getEmail());

        // Check for equals
        AssertTemplates.assertEqualUsers(testUser, foundUser);
    }

    /**
     * @see UserDao#findUser(String)
     */
    @Test(expected = DAOException.class)
    public void findUserByEmail_Failure() {
        // Add user into DB
        userDao.addUser(testUser);

        // Try to find user
        User foundUser = userDao.findUser("flasyrsgiu");

        // Check for null
        assertNull(foundUser);
    }

    /**
     * @see UserDao#updateUser(User)
     */
    @Test
    public void updateUser_Success() {
        // Add user into DB and check for not null
        userDao.addUser(testUser);
        assertNotNull(testUser);
        Integer temp = testUser.getAge();
        testUser.setAge(77);
        // Try to update user
        assertFalse(temp.equals(testUser.getAge()));
        userDao.updateUser(testUser);
        // Find and get updated user from DB and check sameness with testUser
        User dbUserUpdate = userDao.findUser(testUser.getId());
        assertNotNull(dbUserUpdate);
        assertFalse(temp.equals(dbUserUpdate.getAge()));
    }

    /**
     * @see UserDao#updateUser(User)
     */
    @Test(expected = DAOException.class)
    public void updateUser_Failure() {
        // Add user into DB and check it for not null
        userDao.addUser(testUser);
        assertNotNull(testUser);

        // Create new user and set its firstName
        User newUser = createTestUser();
        newUser.setFirstName(null);

        // Check newUser for not null
        assertNotNull(newUser);

        // Try to update testUser
        userDao.updateUser(newUser);

        // Try to find and get updated user from DB and check it for not null
        User dbUserUpdate = userDao.findUser(testUser.getId());
        assertNotNull(dbUserUpdate);
    }


    /**
     * @see UserDao#deleteUser(User)
     */
    @Test
    public void deleteUser_Success() {
        // Add user into DB and check it for not null
        userDao.addUser(testUser);
        assertNotNull(testUser);

        // Try to delete user adn then set its id null
        assertTrue(userDao.deleteUser(testUser));
        testUser.setId(null);
    }

    /**
     * @see UserDao#deleteUser(User)
     */
    @Test(expected = DAOException.class)
    public void deleteUser_Failure() {
        // Add user into DB and check it fro not null
        userDao.addUser(testUser);
        assertNotNull(testUser);
        testUser.setId(null);

        // Try to delete user with non-existent id
        assertFalse(userDao.deleteUser(testUser));
    }

    // endregion
}
