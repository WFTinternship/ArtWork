package am.aca.wftartproject.service.integration;

import am.aca.wftartproject.entity.ShoppingCard;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.entity.User;
import am.aca.wftartproject.service.BaseIntegrationTest;
import am.aca.wftartproject.service.impl.ShoppingCardServiceImpl;
import am.aca.wftartproject.service.impl.UserServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualUsers;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestShoppingCard;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestUser;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

/**
 * Created by ASUS on 30-Jun-17
 */
public class UserServiceIntegrationTest extends BaseIntegrationTest {

    private User testUser;
    private ShoppingCard testshoppingCard;
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ShoppingCardServiceImpl shoppingCardService;



    /**
     * Creates user for tests
     */
    @Before
    public void setUp() {
        testUser = createTestUser();
//        testshoppingCard = createTestShoppingCard();
//        testUser.setShoppingCard(testshoppingCard);
    }

    /**
     * Deletes all objects created during the tests
     */
    @After
    public void tearDown() {
//        if (testUser.getShoppingCard() != null)
//            shoppingCardService.deleteShoppingCard(testshoppingCard);

        if (testUser.getId() != null)
            userService.deleteUser(testUser);
    }

    // region<TEST CASE>

    /**
     * @see UserServiceImpl#addUser(am.aca.wftartproject.entity.User)
     */
    @Test
    public void addUser_Success() {
        assertNotNull(testUser);

        // Test method
        userService.addUser(testUser);
//        testshoppingCard.setBuyer_id(testUser.getId());
//        shoppingCardService.addShoppingCard(testshoppingCard);

        // Check sameness for added user and testUser
        User foundedUser = userService.findUser(testUser.getId());
        assertEqualUsers(foundedUser, testUser);
    }

    /**
     * @see UserServiceImpl#addUser(am.aca.wftartproject.entity.User)
     */
    @Test(expected = InvalidEntryException.class)
    public void addUser_Failure() {
        // Create invalid user
        testUser = new User();

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

        // Test user
        User foundedUser = userService.findUser(testUser.getId());
        assertEqualUsers(foundedUser, testUser);
    }

    /**
     * @see UserServiceImpl#findUser(java.lang.Long)
     */
    @Test(expected = InvalidEntryException.class)
    public void findUser_Failure() {
        // Create invalid user
        testUser = new User();

        // Test method
        userService.findUser(testUser.getId());
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
    @Test(expected = ServiceException.class)
    public void findUserByEmail_Failure() {
        // Create invalid user
        testUser = new User();

        testUser.setEmail("email@email.com");

        // Test method
        assertNull(userService.findUser(testUser.getEmail()));
    }

    /**
     * @see UserServiceImpl#updateUser(User)
     */
    @Test
    public void updateUser_Success() {
        // Add testUser into DB
        userService.addUser(testUser);

        // change testUser for updating
        testUser.setFirstName("another first name");

        // Test method
        userService.updateUser(testUser);

        assertEqualUsers(testUser, userService.findUser(testUser.getId()));
    }

    /**
     * @see UserServiceImpl#updateUser(User)
     */
    @Test(expected = InvalidEntryException.class)
    public void updateUser_Failure() {
        // Create invalid user
        testUser = new User();

        // Test method
        userService.updateUser(testUser);
    }

    /**
     * @see UserServiceImpl#deleteUser(User)
     */
    @Test(expected = ServiceException.class)
    public void deleteUser_Success() {
        // Add testUser into DB
        userService.addUser(testUser);

      // TestMethod
        userService.deleteUser(testUser);
        testUser.setId(null);
        // Make sure it deleted
        User founded = userService.findUser(testUser.getEmail());
        assertNull(founded);
    }

    /**
     * @see UserServiceImpl#deleteUser(User)
     */
    @Test(expected = InvalidEntryException.class)
    public void deleteUser_Failure() {
        // Create invalid testUser
        testUser = new User();

        // Test method
        userService.deleteUser(testUser);
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
    @Test
    public void login_Failure() {
        // Add testUser into DB
        userService.addUser(testUser);

        // Test method
        assertNull(userService.login(testUser.getEmail(), "fake password"));
    }

    // endregion
}

