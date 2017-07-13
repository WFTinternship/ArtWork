package am.aca.wftartproject.dao.integration;

import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.entity.User;
import am.aca.wftartproject.repository.UserRepo;
import am.aca.wftartproject.util.AssertTemplates;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.sql.SQLException;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualUsers;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestUser;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.TestCase.*;


/**
 * Created by Armen on 5/30/2017
 */
public class UserRepoIntegrationTest extends BaseDAOIntegrationTest {

    private static Logger LOGGER = Logger.getLogger(UserRepoIntegrationTest.class);

    private User testUser;

    @Autowired
    private UserRepo userRepo;

    public UserRepoIntegrationTest() throws SQLException, ClassNotFoundException {
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
        if (testUser!=null && testUser.getId() != null)
            userRepo.delete(testUser);

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
     * @see UserRepo#saveAndFlush(Object) (User)
     */
    @Test
    public void addUser_Success() {
        // Test user already inserted in setup, get record by userId
        testUser.setId(null);

        // Add user into db and check id for null
        assertNotNull(testUser);

        userRepo.saveAndFlush(testUser);

        assertNotNull(testUser.getId());

        // Get user by id and check for sameness with origin
        Long id = testUser.getId();

        User dbUser = userRepo.findOne(id);

        assertNotNull(dbUser);
        assertEqualUsers(dbUser, testUser);
    }

    /**
     * @see UserRepo#saveAndFlush(Object) (User)
     */
    @Test(expected = DAOException.class)
    public void addUser_Failure() {
        // Test user already inserted in setup, get record by user
        testUser.setLastName(null);
        try{
            userRepo.saveAndFlush(testUser);
        }
        catch (Exception e){
            testUser.setLastName("test");
            throw new DAOException("");
        }

    }

    /**
     * @see UserRepo#findOne(Serializable)
     */
    @Test
    public void findUser_Success() {
        // Add user into DB
        userRepo.saveAndFlush(testUser);

        // Try to find user by email
        User findResult = userRepo.findOne(testUser.getId());

        // Check for equals
        assertEqualUsers(testUser, findResult);
    }

    /**
     * @see UserRepo#findOne(Serializable)
     */
    @Test
    public void findUser_Failure() {
        // Add user into DB
        userRepo.saveAndFlush(testUser);

        // Try to find user with negative id
        User findResult = userRepo.findOne(-7L);

        // Check result fo null
        assertNull(findResult);
    }

    /**
     * @see UserRepo#findOne(Serializable)
     */
    @Test
    public void findUserByEmail_Success() {
        // Add user into DB
        userRepo.saveAndFlush(testUser);

        // Try to find user by email
        User foundUser = userRepo.findUserByEmail(testUser.getEmail());

        // Check for equals
        AssertTemplates.assertEqualUsers(testUser, foundUser);
    }

    /**
     * @see UserRepo#findUserByEmail(String)
     */
    @Test
    public void findUserByEmail_Failure() {
        // Add user into DB
        userRepo.saveAndFlush(testUser);

        // Try to find user
        User foundUser = userRepo.findUserByEmail("flasyrsgiu");

        // Check for null
        assertNull(foundUser);
    }

    /**
     * @see UserRepo#saveAndFlush(Object) (User)
     */
    @Test
    public void updateUser_Success() {
        // Add user into DB and check for not null
        userRepo.saveAndFlush(testUser);
        assertNotNull(testUser);
        Integer temp = testUser.getAge();
        testUser.setAge(77);
        // Try to update user
        assertFalse(temp.equals(testUser.getAge()));
        userRepo.saveAndFlush(testUser);
        // Find and get updated user from DB and check sameness with testUser
        User dbUserUpdate = userRepo.findOne(testUser.getId());
        assertNotNull(dbUserUpdate);
        assertFalse(temp.equals(dbUserUpdate.getAge()));
    }

    /**
     * @see UserRepo#saveAndFlush(Object)
     */
    @Test(expected = DAOException.class)
    public void updateUser_Failure() {
        // Add user into DB and check it for not null
        userRepo.saveAndFlush(testUser);
        assertNotNull(testUser);

        // Create new user and set its firstName
        User newUser = createTestUser();
        newUser.setFirstName(null);

        // Check newUser for not null
        assertNotNull(newUser);

        // Try to update testUser
        try{
            userRepo.saveAndFlush(newUser);
        }catch (Exception e){
            throw new DAOException(e.getMessage());
        }

    }


    /**
     * @see UserRepo#delete(Object)
     */
    @Test
    public void deleteUser_Success() {
        // Add user into DB and check it for not null
        userRepo.saveAndFlush(testUser);
        assertNotNull(testUser);

        // Try to delete user adn then set its id null
        userRepo.delete(testUser);
        testUser.setId(null);
    }

    /**
     * @see UserRepo#delete(Object)
     */
    @Test(expected = DAOException.class)
    public void deleteUser_Failure() {
        // Add user into DB and check it fro not null
        userRepo.saveAndFlush(testUser);
        assertNotNull(testUser);
        testUser = null;

        // Try to delete user with non-existent id
        try{
            userRepo.delete(testUser);
        }
        catch (Exception e){
            throw new DAOException(e.getMessage());
        }
    }

    // endregion
}
