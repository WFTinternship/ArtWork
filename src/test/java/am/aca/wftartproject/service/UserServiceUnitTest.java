package am.aca.wftartproject.service;

import am.aca.wftartproject.BaseUnitTest;
import am.aca.wftartproject.dao.impl.UserDaoImpl;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.service.DuplicateEntryException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.impl.UserServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualUsers;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestUser;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
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

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
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
        testUser = null;

        // Test method
        try {
            userService.addUser(null);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        testUser = createTestUser();
        testUser.setFirstName(null);

        // Test method
        try {
            userService.addUser(testUser);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

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
        testUser = createTestUser();

        User fakeDbUser = new User();

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

        testUser = createTestUser();

        // Setup mocks
        doThrow(DAOException.class).when(userDaoMock).addUser(testUser);

        // Test method
        userService.addUser(testUser);
    }

    /**
     * @see UserServiceImpl#addUser(am.aca.wftartproject.model.User)
     */
    @Test
    public void addUser_addSuccess() {

        testUser = createTestUser();

        // Setup mocks
        doNothing().when(userDaoMock).addUser(testUser);

        // Test method
        userService.addUser(testUser);
    }

    /**
     * @see UserServiceImpl#findUser(java.lang.Long)
     */
    @Test
    public void findUser_idNullOrNegative() {
        Long id = null;

        // Test method
        try {
            userService.findUser(id);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }

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
        // Create testUser
        testUser = createTestUser();

        //TODO ask about anyLong
        Long id = 5L;

        // Setup mocks
        doReturn(testUser).when(userDaoMock).findUser(anyLong());

        // Test method
        assertEqualUsers(testUser, userService.findUser(id));
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
        // try to find user by this email
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
        // Create testUser
        testUser = createTestUser();

        String email = "anyEmail@gmail.com";

        // Setup mocks
        doReturn(testUser).when(userDaoMock).findUser(email);

        // Test method
        assertEqualUsers(testUser, userService.findUser(email));
    }

    /**
     * @see UserServiceImpl#updateUser(java.lang.Long, am.aca.wftartproject.model.User)
     */
    @Test
    public void updateUser_idIsNullOrNegative() {
        // Create testUser and null id
        testUser = createTestUser();
        Long id = null;
        // Try to update user
        // Test method
        try {
            userService.updateUser(id, testUser);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Create negative id
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
        // Create id and null testUser
        Long id = 5L;
        testUser = null;

        // Test method
        try {
            userService.updateUser(id, testUser);
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
        // Create testUser and id
        testUser = createTestUser();
        Long id = 5L;

        // Test method
        userService.updateUser(id, testUser);

        verify(userDaoMock).updateUser(id, testUser);
    }

    /**
     * @see UserServiceImpl#deleteUser(java.lang.Long)
     */
    @Test
    public void deleteUser_idNullOrNegative() {
        // Create null id
        Long id = null;

        // Try to delete user
        try {
            userService.deleteUser(id);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Create negative id
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
        Long id = 5L;

        // Test method
        userService.deleteUser(id);

        // check invocation
        verify(userDaoMock).deleteUser(id);
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
    @Test
    public void login_userNullOrIncorrectPassword() {


    }
    /**
     * @see UserServiceImpl#login(java.lang.String, java.lang.String)
     */
    @Test
    public void login_loginFailed() {

    }
    /**
     * @see UserServiceImpl#login(java.lang.String, java.lang.String)
     */
    @Test
    public void login_Success() {

    }

    // endregion
}
