package am.aca.wftartproject.controller.integration;

import am.aca.wftartproject.controller.BaseIntegrationTest;
import am.aca.wftartproject.controller.HomeController;
import am.aca.wftartproject.util.controller.TestHttpServletRequest;
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

import static am.aca.wftartproject.util.AssertTemplates.assertEqualArtists;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualModelAndViews;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualUsers;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestArtist;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestUser;
import static junit.framework.TestCase.assertTrue;

/**
 * @author surik
 */
public class HomeControllerIntegrationTest extends BaseIntegrationTest {

    private TestHttpServletRequest testRequest;
    private User testUser;
    private Artist testArtist;

    @Autowired
    private HomeController homeController;
    @Autowired
    private UserService userService;
    @Autowired
    private ArtistService artistService;
    @Autowired
    private ShoppingCardService shoppingCardService;

    /**
     * Creates and adds to database user and artist
     */
    @Before
    public void setUp() {
        testRequest = new TestHttpServletRequest();

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
    }

    // region<TEST CASE>

    /**
     * @see HomeController#welcome(HttpServletRequest)
     */
    @Test
    public void welcome_findArtistSuccess() {
        // set emailCookie for artist to testRequest
        Cookie emailCookie = new Cookie("userEmail", testArtist.getEmail());
        testRequest.setCookie(emailCookie);

        // Test method
        ModelAndView modelAndView = homeController.welcome(testRequest);

        // Check modelAndView and session
        assertEqualModelAndViews(modelAndView, new ModelAndView("index"));
        assertEqualArtists((Artist) testRequest.getSession().getAttribute("user"), testArtist);
    }

    /**
     * @see HomeController#welcome(HttpServletRequest)
     */
    @Test
    public void welcome_findUserSuccess() {
        // set emailCookie for user to testRequest
        Cookie emailCookie = new Cookie("userEmail", testUser.getEmail());
        testRequest.setCookie(emailCookie);

        // Test method
        ModelAndView modelAndView = homeController.welcome(testRequest);

        // Check modelAndView and session
        assertEqualModelAndViews(modelAndView, new ModelAndView("index"));
        assertEqualUsers((User) testRequest.getSession().getAttribute("user"), testUser);
    }

    /**
     * @see HomeController#welcome(HttpServletRequest)
     */
    @Test
    public void welcome_findArtistOrUserFailure() {
        // set emailCookie to testRequest
        Cookie emailCookie = new Cookie("userEmail", "fakeEmail@email.com");
        testRequest.setCookie(emailCookie);

        // Test method
        ModelAndView modelAndView = homeController.welcome(testRequest);

        // Check modelAndView and session
        assertEqualModelAndViews(modelAndView, new ModelAndView("index"));
        assertTrue(testRequest.getSession().getAttribute("user") == null);
    }

    // endregion
}
