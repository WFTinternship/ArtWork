package am.aca.wftartproject.service.unit;

import am.aca.wftartproject.dao.ShoppingCardDao;
import am.aca.wftartproject.dao.impl.UserDaoImpl;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.service.DuplicateEntryException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.ShoppingCard;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.BaseUnitTest;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.service.impl.UserServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualUsers;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestShoppingCard;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestUser;
import static junit.framework.TestCase.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author surik
 */
public class UserServiceUnitTest extends BaseUnitTest {

    private User testUser;

    @InjectMocks
    @Autowired
    private UserService userService;

    @Mock
    private UserDaoImpl userDaoMock;

    @Mock
    private ShoppingCardDao shoppingCardDao;

    @Before
    public void beforeTest() {
    }

    @After
    public void afterTest() {
    }

    // region <TEST CASES>

    /**
     * @see UserServiceImpl#addUser(am.aca.wftartproject.model.User)
     */
    @Test
    public void addUser_userNotValidOrNull() {
        // Initialize testUser to null value
        testUser = null;

        // Test method
        try {
            userService.addUser(null);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Create testUser and make not valid
        testUser = createTestUser();
        testUser.setFirstName(null);

        // Test method
        try {
            userService.addUser(testUser);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Create testUser and make email not valid
        testUser = createTestUser();
        testUser.setEmail("invalidEmail");

        // Test method
        try {
            userService.addUser(testUser);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see UserServiceImpl#addUser(am.aca.wftartproject.model.User)
     */
    @Test
    public void addUser_exists() {
        // Create testUser
        testUser = createTestUser();
        User fakeDbUser = new User();

        // Setup mock
        doReturn(fakeDbUser).when(userDaoMock).findUser(testUser.getEmail());

        // Try to add user into db
        // Test method
        try {
            userService.addUser(testUser);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof DuplicateEntryException);
        }
    }


    /**
     * @see UserServiceImpl#addUser(am.aca.wftartproject.model.User)
     */
    @Test(expected = ServiceException.class)
    public void addUser_addFailed() {
        // Create testUser
        testUser = createTestUser();

        // Setup mocks
        doThrow(DAOException.class).when(userDaoMock).addUser(any(User.class));

        // Test method
        userService.addUser(testUser);
    }


    /**
     * @see UserServiceImpl#addUser(am.aca.wftartproject.model.User)
     */
    @Test
    public void addUser_addSuccess() {
        // Create argument capture
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);

        // Create testUser and testShoppingCard
        testUser = createTestUser();
        ShoppingCard shoppingCard = createTestShoppingCard();

        // Setup mocks
        doNothing().when(userDaoMock).addUser(argument.capture());
        doNothing().when(shoppingCardDao).addShoppingCard(testUser.getId(),shoppingCard);

        // Test method
        userService.addUser(testUser);

        // Check input argument
        assertEquals(testUser, argument.getValue());
    }


    /**
     * @see UserServiceImpl#findUser(java.lang.Long)
     */
    @Test
    public void findUser_idNullOrNegative() {
        Long id;

        // Test method
        try {
            userService.findUser((Long) null);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }

        // Make id not valid
        id = -5L;

        // Test method
        try {
            userService.findUser(id);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }


    /**
     * @see UserServiceImpl#findUser(java.lang.Long)
     */
    @Test(expected = ServiceException.class)
    public void findUser_findFailed() {
        // Create test id and initialize it with not exists number
        Long id = 516498484L;

        // Setup mocks
        doThrow(DAOException.class).when(userDaoMock).findUser(anyLong());

        // Test method
        userService.findUser(id);
    }


    /**
     * @see UserServiceImpl#findUser(java.lang.Long)
     */
    @Test
    public void findUser_findSuccess() {
        //Create argument capture
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        // Create testUser and test id
        testUser = createTestUser();
        Long id = 5L;

        // Setup mocks
        doReturn(testUser).when(userDaoMock).findUser(argumentCaptor.capture());

        // Test method
        assertEqualUsers(testUser, userService.findUser(id));

        // Check input argument
        assertEquals(id, argumentCaptor.getValue());
    }


    /**
     * @see UserServiceImpl#findUser(java.lang.String)
     */
    @Test
    public void findUserByEmail_emptyString() {
        // Create empty string
        String emptyEmail = "";

        // Try to find user with empty email
        try {
            userService.findUser(emptyEmail);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Create not valid email
        String notValidEmail = "notValidEmail";

        // Test method
        try {
            userService.findUser(notValidEmail);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }


    /**
     * @see UserServiceImpl#findUser(java.lang.String)
     */
    @Test(expected = ServiceException.class)
    public void findUserByEmail_findFailed() {
        // Create not empty email
        String email = "email@gmail.com";

        // Setup mocks
        doThrow(DAOException.class).when(userDaoMock).findUser(anyString());

        // Test methods
        userService.findUser(email);
    }


    /**
     * @see UserServiceImpl#findUser(java.lang.String)
     */
    @Test
    public void findUserByEmail_findSuccess() {
        //Create argument capture
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        // Create testUser
        testUser = createTestUser();

        String email = "anyEmail@gmail.com";

        // Setup mocks
        doReturn(testUser).when(userDaoMock).findUser(argumentCaptor.capture());

        // Test method
        assertEqualUsers(testUser, userService.findUser(email));

        // Check input argument
        assertEquals(email, argumentCaptor.getValue());
    }


    /**
     * @see UserServiceImpl#updateUser(java.lang.Long, am.aca.wftartproject.model.User)
     */
    @Test
    public void updateUser_idIsNullOrNegative() {
        // Create testUser and test id
        testUser = createTestUser();
        Long id;

        // Try to update user
        // Test method
        try {
            userService.updateUser(null, testUser);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change id to negative
        id = -5L;

        // Test method
        try {
            userService.updateUser(id, testUser);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see UserServiceImpl#updateUser(java.lang.Long, am.aca.wftartproject.model.User)
     */
    @Test
    public void updateUser_userNotValidOrNull() {
        // Create id
        Long id = 5L;

        // Test method
        try {
            userService.updateUser(id, null);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Create invalid user
        testUser = createTestUser();
        testUser.setFirstName(null);

        // Test method
        try {
            userService.updateUser(id, testUser);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see UserServiceImpl#updateUser(java.lang.Long, am.aca.wftartproject.model.User)
     */
    @Test(expected = ServiceException.class)
    public void updateUser_updateFailed() {
        // Create id and testUser
        Long id = 5L;
        testUser = createTestUser();

        // Setup mocks
        doThrow(DAOException.class).when(userDaoMock).updateUser(anyLong(), any(User.class));

        // Test method
        userService.updateUser(id, testUser);
    }


    /**
     * @see UserServiceImpl#updateUser(java.lang.Long, am.aca.wftartproject.model.User)
     */
    @Test
    public void updateUser_updateSuccess() {
        // Create argument capture
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<User> argumentCaptor1 = ArgumentCaptor.forClass(User.class);

        // Create testUser and id
        testUser = createTestUser();
        Long id = 5L;

        // Setup mocks
        doReturn(true).when(userDaoMock).updateUser(argumentCaptor.capture(), argumentCaptor1.capture());

        // Test method
        userService.updateUser(id, testUser);

        // Check input arguments
        assertEquals(id, argumentCaptor.getValue());
        assertEquals(testUser, argumentCaptor1.getValue());
    }


    /**
     * @see UserServiceImpl#deleteUser(java.lang.Long)
     */
    @Test
    public void deleteUser_idNullOrNegative() {
        // Create test id
        Long id;

        // Try to delete user
        try {
            userService.deleteUser(null);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change id to negative
        id = -5L;

        // Try to delete user
        try {
            userService.deleteUser(id);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see UserServiceImpl#deleteUser(java.lang.Long)
     */
    @Test(expected = ServiceException.class)
    public void deleteUser_deleteFailed() {
        // Setup mocks
        doThrow(DAOException.class).when(userDaoMock).deleteUser(anyLong());

        // Test method
        userService.deleteUser(5L);
    }


    /**
     * @see UserServiceImpl#deleteUser(java.lang.Long)
     */
    @Test
    public void deleteUser_Success() {
        //Create argument capture
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);

        // Create test id
        Long id = 5L;

        // Setup mocks
        doReturn(true).when(userDaoMock).deleteUser(argumentCaptor.capture());

        // Test method
        userService.deleteUser(id);

        // Check input argument
        assertEquals(id, argumentCaptor.getValue());
    }


    /**
     * @see UserServiceImpl#login(java.lang.String, java.lang.String)
     */
    @Test
    public void login_emptyStringEmailOrPassword() {
        // Create empty email
        String email = "";
        String password = "password";

        // Try to login
        try {
            userService.login(email, password);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }

        // Create empty password
        email = "email";
        password = "";

        // Try to login
        try {
            userService.login(email, password);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }


    /**
     * @see UserServiceImpl#login(java.lang.String, java.lang.String)
     */
    @Test(expected = ServiceException.class)
    public void login_findUserFailed() {
        // Create email and password
        String email = "email";
        String password = "password";

        // Setup mocks
        doThrow(DAOException.class).when(userDaoMock).findUser(anyString());

        // Test methods
        userService.login(email, password);
    }


    /**
     * @see UserServiceImpl#login(java.lang.String, java.lang.String)
     */

    @Test(expected = ServiceException.class)
    public void login_loginPasswordNotMatch() {
        // Create email and password
        String email = "email";
        String password = "wrongPassword";

        // Create user with same email
        User testUser = createTestUser();
        testUser.setEmail(email);

        // Setup mock
        doReturn(testUser).when(userDaoMock).findUser(anyString());

        // Test method
        userService.login(email, password);
    }

    /**
     * @see UserServiceImpl#login(java.lang.String, java.lang.String)
     */
    @Test
    public void login_Success() {
        //Create argument capture
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        // Create testUser, email and password
        User testUser = createTestUser();
        String email = testUser.getEmail();
        String password = testUser.getPassword();

        // Setup mock
        doReturn(testUser).when(userDaoMock).findUser(argumentCaptor.capture());

        // Test method
        assertEqualUsers(testUser, userService.login(email, password));

        // Check input argument
        assertEquals(email, argumentCaptor.getValue());
    }

    // endregion
}
