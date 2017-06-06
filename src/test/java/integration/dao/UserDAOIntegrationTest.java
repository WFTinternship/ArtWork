package integration.dao;

import am.aca.wftartproject.dao.UserDao;

import am.aca.wftartproject.dao.impl.UserDaoImpl;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.util.DBConnection;
import integration.service.TestObjectTemplate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static integration.service.AssertTemplates.assertEqualUsers;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

/**
 * Created by Armen on 5/30/2017
 */
public class UserDAOIntegrationTest {
    private DBConnection connection = new DBConnection();
    private UserDaoImpl userDao = new UserDaoImpl(connection.getDBConnection(DBConnection.DBType.TEST));
    private User testUser;

    public UserDAOIntegrationTest() throws SQLException, ClassNotFoundException {
    }

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {

        connection = new DBConnection();
        userDao = new UserDaoImpl(connection.getDBConnection(DBConnection.DBType.TEST));

        //create test user

        testUser = TestObjectTemplate.createTestUser();

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

        // get user by id and check for sameness with original

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
        //test user already inserted in setup, get record by userId

        testUser.setId(null);

        // Test method

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
    public void deleteUser_success() {
        userDao.addUser(testUser);
        assertNotNull(testUser);
        userDao.deleteUser(testUser.getId());

        User deleteTest = userDao.findUser(testUser.getId());

        assertNull(deleteTest.getId());
    }

    /**
     * @see UserDao#deleteUser(Long)
     */
    @Test(expected = DAOException.class)
    public void deleteUser_failure() {

        userDao.addUser(testUser);
        assertNotNull(testUser);
        testUser.setLastName(null);
        userDao.updateUser(testUser.getId(), testUser);
        userDao.deleteUser(testUser.getId());

        User deleteTest = userDao.findUser(testUser.getId());

        assertNull(deleteTest.getId());

    }


    /**
     * @see UserDao#findUser(Long)
     */
    @Test
    public void findUser_success() {
        userDao.addUser(testUser);
        User findResult = userDao.findUser(testUser.getId());
        assertEqualUsers(testUser, findResult);
    }

    /**
     * @see UserDao#findUser(Long)
     */
    @Test
    public void findUser_failure() {
        userDao.addUser(testUser);
        testUser.setId(-7L);
        User findResult = userDao.findUser(testUser.getId());
        assertNotSame(testUser, findResult);
    }


    @After
    public void tearDown() {
        //delete inserted test users from db
        if (testUser.getId() != null)
            userDao.deleteUser(testUser.getId());

        //delete test user object
        testUser = null;
    }


}
