package am.aca.wftproject.service;

import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.service.impl.UserServiceImpl;
import am.aca.wftproject.BaseUnitTest;
import am.aca.wftproject.util.TestObjectTemplate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

/**
 * Created by ASUS on 22-Jun-17
 */
public class UserServiceUnitTest extends BaseUnitTest {

    User testUser = null;


    @Autowired
    UserService userService;

    UserService userDaoMock = mock(UserService.class);
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
        testUser = null;

        // Test method
        try {
            userService.addUser(null);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        testUser = TestObjectTemplate.createTestUser();
        testUser.setFirstName(null);

        // Test method
        try {
            userService.addUser(testUser);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        testUser = TestObjectTemplate.createTestUser();
        testUser.setEmail("iusfhiu");

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





    }

    /**
     * @see UserServiceImpl#addUser(am.aca.wftartproject.model.User)
     */
    @Test(expected = ServiceException.class)
    public void addUser_addFailed() {

        testUser = TestObjectTemplate.createTestUser();
        userService.addUser(testUser);

        doThrow(DAOException.class).when(userDaoMock).addUser(any(User.class));


    }

    /**
     * @see UserServiceImpl#addUser(am.aca.wftartproject.model.User)
     */
    @Test
    public void addUser_addSuccess() {


    }

    // endregion
}


