package am.aca.wftartproject.service;

import am.aca.wftartproject.BaseUnitTest;
import am.aca.wftartproject.dao.impl.UserDaoImpl;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.impl.UserServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import util.TestObjectTemplate;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;

/**
 * @author surik
 */
public class UserServiceUnitTest extends BaseUnitTest {

    User testUser = null;


    @Autowired
    UserService userService;

    @Mock
    UserDaoImpl userDaoMock;
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

        // Setup mocks
        doThrow(DAOException.class).when(userDaoMock).addUser(any(User.class));

        // Test method
        userDaoMock.addUser(testUser);
    }

    /**
     * @see UserServiceImpl#addUser(am.aca.wftartproject.model.User)
     */
    @Test
    public void addUser_addSuccess() {

    }

    // endregion
}
