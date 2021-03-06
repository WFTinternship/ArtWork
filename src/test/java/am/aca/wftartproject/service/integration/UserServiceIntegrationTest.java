package am.aca.wftartproject.service.integration;

import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.BaseIntegrationTest;
import am.aca.wftartproject.service.impl.ShoppingCardServiceImpl;
import am.aca.wftartproject.service.impl.UserServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualUsers;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestUser;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

/**
 * Created by ASUS on 30-Jun-17
 */
@Transactional
public class UserServiceIntegrationTest extends BaseIntegrationTest {

    private User testUser;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ShoppingCardServiceImpl shoppingCardService;

    /**
     * Creates user for test
     */
    @Before
    public void setUp() {
        testUser = createTestUser();
    }

    /**
     * Deletes all objects created during the test
     */
    @After
    public void tearDown() {
        if (testUser.getShoppingCard() != null) {
            if (testUser.getShoppingCard().getId() != null)
                shoppingCardService.deleteShoppingCardByBuyerId(testUser.getId());
        }

        if (testUser.getId() != null)
            userService.deleteUser(testUser.getId());

        testUser = null;
    }

    // region<TEST CASE>

    /**
     * @see UserServiceImpl#addUser(am.aca.wftartproject.model.User)
     */
    @Test
    public void addUser_Success() {
        assertNotNull(testUser);

        // Test method
        userService.addUser(testUser);

        // Check sameness for added user and testUser
        User foundedUser = userService.findUser(testUser.getId());
        assertEqualUsers(foundedUser, testUser);
    }

    /**
     * @see UserServiceImpl#addUser(am.aca.wftartproject.model.User)
     */
    @Test(expected = InvalidEntryException.class)
    public void addUser_Failure() {
        // Create invalid user
        testUser.setShoppingCard(null);

        // Test method
        userService.addUser(testUser);
    }

    /**
     * @see UserServiceImpl#findUser(java.lang.Long)
     */
    @Test
    public void findUser_Success() {
        // Add testUser into DB
        userService.addUser(testUser);

        // Test method
        User foundedUser = userService.findUser(testUser.getId());

        assertEqualUsers(foundedUser, testUser);
    }

    /**
     * @see UserServiceImpl#findUser(java.lang.Long)
     */
    @Test
    public void findUser_Failure() {
        testUser.setId(2000L);

        // Test method
        User user = userService.findUser(testUser.getId());

        assertNull(user);
        testUser.setId(null);
    }

    /**
     * @see UserServiceImpl#findUser(java.lang.String)
     */
    @Test
    public void findUserByEmail_Success() {
        // Add testUser into DB
        userService.addUser(testUser);

        // Test method
        User foundedUser = userService.findUser(testUser.getEmail());

        assertEqualUsers(foundedUser, testUser);
    }

    /**
     * @see UserServiceImpl#findUser(java.lang.String)
     */
    @Test
    public void findUserByEmail_Failure() {
        // Create invalid user
        testUser.setEmail("email@email.com");

        // Test method
        User user = userService.findUser(testUser.getEmail());
        assertNull(user);
    }

    /**
     * @see UserServiceImpl#updateUser(java.lang.Long, am.aca.wftartproject.model.User)
     */
    @Test
    public void updateUser_Success() {
        // Add testUser into DB
        userService.addUser(testUser);

        // change testUser for updating
        testUser.setFirstName("another first name");

        // Test method
        userService.updateUser(testUser.getId(), testUser);

        assertEqualUsers(testUser, userService.findUser(testUser.getId()));
    }

    /**
     * @see UserServiceImpl#updateUser(java.lang.Long, am.aca.wftartproject.model.User)
     */
    @Test(expected = ServiceException.class)
    public void updateUser_Failure() {
        // Create fake id
        Long id = 2000L;

        // Test method
        userService.updateUser(id, testUser);
    }

    /**
     * @see UserServiceImpl#deleteUser(java.lang.Long)
     */
    @Test
    public void deleteUser_Success() {
        // Add testUser into DB
        userService.addUser(testUser);

        // Test method
        shoppingCardService.deleteShoppingCardByBuyerId(testUser.getId());
        testUser.setShoppingCard(null);
        userService.deleteUser(testUser.getId());

        // Make sure it deleted
        User founded = userService.findUser(testUser.getEmail());
        assertNull(founded);

        testUser.setId(null);
    }

    /**
     * @see UserServiceImpl#deleteUser(java.lang.Long)
     */
    @Test(expected = InvalidEntryException.class)
    public void deleteUser_Failure() {
        // Test method
        userService.deleteUser(testUser.getId());
    }

    /**
     * @see UserServiceImpl#login(java.lang.String, java.lang.String)
     */
    @Test
    public void login_Success() {
        // Add testUser into DB
        userService.addUser(testUser);

        // Test method
        User loginUser = userService.login(testUser.getEmail(), testUser.getPassword());

        assertEqualUsers(loginUser, testUser);
    }

    /**
     * @see UserServiceImpl#login(java.lang.String, java.lang.String)
     */
    @Test(expected = ServiceException.class)
    public void login_Failure() {
        // Add testUser into DB
        userService.addUser(testUser);

        // Test method
        userService.login(testUser.getEmail(), "fake password");
    }

    // endregion
}