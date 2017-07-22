package am.aca.wftartproject.controller.integration;

import am.aca.wftartproject.controller.BaseIntegrationTest;
import am.aca.wftartproject.controller.LogInController;
import am.aca.wftartproject.controller.util.TestHttpServletRequest;
import am.aca.wftartproject.controller.util.TestHttpServletResponse;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ShoppingCardService;
import am.aca.wftartproject.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualArtists;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualUsers;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestArtist;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestUser;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * @author surik
 */
public class LogInControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private LogInController logInController;
    @Autowired
    private UserService userService;
    @Autowired
    private ArtistService artistService;
    @Autowired
    private ShoppingCardService shoppingCardService;

    private TestHttpServletRequest testRequest;
    private TestHttpServletResponse testResponse;
    private User testUser;
    private Artist testArtist;

    /**
     * Creates testRequest, testResponse, testUser and testArtist for test
     */
    @Before
    public void setUp() {
        testRequest = new TestHttpServletRequest();
        testResponse = new TestHttpServletResponse();
        testUser = createTestUser();
        testArtist = createTestArtist();
        userService.addUser(testUser);
        artistService.addArtist(testArtist);
    }

    /**
     * Deletes user and artist created during the test
     */
    @After
    public void tearDown() {
        if (testUser.getShoppingCard() != null) {
            if (testUser.getShoppingCard().getId() != null) {
                shoppingCardService.deleteShoppingCardByBuyerId(testUser.getId());
            }
        }

        if (testUser.getId() != null) {
            userService.deleteUser(testUser.getId());
        }

        if (testArtist.getShoppingCard() != null) {
            if (testArtist.getShoppingCard().getId() != null) {
                shoppingCardService.deleteShoppingCardByBuyerId(testArtist.getId());
            }
        }

        if (testArtist.getId() != null) {
            artistService.deleteArtist(testArtist.getId());
        }

        testUser = null;
        testArtist = null;
    }

    // region <TEST CASE>

    /**
     * @see LogInController#showLogin()
     */
    @Test
    public void showLogin_Success() {
        // Test method
        ModelAndView modelAndView = logInController.showLogin();
        assertEquals(modelAndView.getView(), new ModelAndView("logIn").getView());
    }

    /**
     * @see LogInController#loginProcess(HttpServletRequest, HttpServletResponse)
     */
    @Test
    public void loginProcess_UserLoginSuccess() {
        // Put testUser email and password to testRequest
        testRequest.getParameterMap().put("email", new String[]{testUser.getEmail()});
        testRequest.getParameterMap().put("password", new String[]{testUser.getPassword()});

        User userFromDB = userService.findUser(testUser.getId());

        // Create emailCookie
        Cookie emailCookie = new Cookie("userEmail", testUser.getEmail());
        emailCookie.setMaxAge(3600);

        // Test method
        ModelAndView modelAndView = logInController.loginProcess(testRequest, testResponse);

        // Assertions
        assertEquals(modelAndView.getView(), new ModelAndView("index").getView());
        assertEqualCookies(emailCookie, testResponse.getCookieList());
        assertEqualUsers((User) testRequest.getSession().getAttribute("user"), userFromDB);
    }

    /**
     * @see LogInController#loginProcess(HttpServletRequest, HttpServletResponse)
     */
    @Test
    public void loginProcess_ArtistLoginSuccess() {
        // Put testArtist email and password to testRequest
        testRequest.getParameterMap().put("email", new String[]{testArtist.getEmail()});
        testRequest.getParameterMap().put("password", new String[]{testArtist.getPassword()});

        Artist artistFromDB = artistService.findArtist(testArtist.getId());

        // Create emailCookie
        Cookie emailCookie = new Cookie("userEmail", testArtist.getEmail());
        emailCookie.setMaxAge(3600);

        // Test method
        ModelAndView modelAndView = logInController.loginProcess(testRequest, testResponse);

        // Assertions
        assertEquals(modelAndView.getView(), new ModelAndView("index").getView());
        assertEqualCookies(emailCookie, testResponse.getCookieList());
        assertEqualArtists((Artist) testRequest.getSession().getAttribute("user"), artistFromDB);
    }

    /**
     * @see LogInController#loginProcess(HttpServletRequest, HttpServletResponse)
     */
    @Test
    public void loginProcess_Failure() {
        testUser.setPassword("fake password");

        // Put testArtist email and password to testRequest
        testRequest.getParameterMap().put("email", new String[]{testUser.getEmail()});
        testRequest.getParameterMap().put("password", new String[]{testUser.getPassword()});

        // Test method
        logInController.loginProcess(testRequest, testResponse);

        // Check errorMessage
        assertEquals(testRequest.getAttribute("message"), "The user with the entered username and password does not exists.");
    }

    /**
     * @see LogInController#logout(HttpServletRequest, HttpServletResponse)
     */
    @Test
    public void logout_Success() {
        // Test method
        ModelAndView modelAndView = logInController.logout(testRequest, testResponse);

        // Assertions
        assertTrue(testResponse.getCookieList().isEmpty());
        assertEquals(modelAndView.getView(), new ModelAndView("index").getView());
    }


    // endregion






    private void assertEqualCookies(Cookie expected, List<Cookie> real) {
        assertEquals(expected.getMaxAge(), real.get(0).getMaxAge());
        assertEquals(expected.getName(), real.get(0).getName());
        assertEquals(expected.getValue(), real.get(0).getValue());
    }
}
