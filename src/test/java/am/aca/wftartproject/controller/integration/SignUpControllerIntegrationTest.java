package am.aca.wftartproject.controller.integration;

import am.aca.wftartproject.controller.BaseIntegrationTest;
import am.aca.wftartproject.controller.SignUpController;
import am.aca.wftartproject.util.controller.TestHttpServletRequest;
import am.aca.wftartproject.util.controller.TestHttpServletResponse;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.model.ShoppingCard;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ShoppingCardService;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.util.controller.TestRedirectAttributes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualArtists;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualCookies;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualModelAndViews;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualUsers;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestArtist;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestUser;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNull;

/**
 * @author surik
 */
public class SignUpControllerIntegrationTest extends BaseIntegrationTest {
    private TestHttpServletResponse testResponse;
    private TestHttpServletRequest testRequest;
    private TestRedirectAttributes testRedirectAttributes;
    private User testUser;
    private Artist testArtist;
    private MultipartFile image;

    @Autowired
    private SignUpController signUpController;
    @Autowired
    private UserService userService;
    @Autowired
    private ArtistService artistService;
    @Autowired
    private ShoppingCardService shoppingCardService;

    @Before
    public void setUp() {
        // Create testRequest and testResponse
        testRequest = new TestHttpServletRequest();
        testResponse = new TestHttpServletResponse();
        testRedirectAttributes = new TestRedirectAttributes();

        // Create testUser
        testUser = createTestUser();

        // Create testArtist
        testArtist = createTestArtist();

        image = new MockMultipartFile("userPic", new byte[10]);
    }

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
        testResponse = null;
        testRequest = null;
    }

    // region<TEST CASE>

    /**
     * @see SignUpController#showRegistrationPage()
     */
    @Test
    public void showRegistrationPage() {
        // Test method
        ModelAndView modelAndView = signUpController.showRegistrationPage();

        // Check objects in modelAndView
        assertEqualUsers((User) modelAndView.getModel().get("user"), new User());
        assertEqualArtists((Artist) modelAndView.getModel().get("artist"), new Artist());
        assertTrue(Arrays.equals((Object[]) modelAndView.getModel().get("artistSpecTypes"), ArtistSpecialization.values()));
    }

    /**
     * @see SignUpController#addUser(HttpServletRequest, HttpServletResponse, RedirectAttributes, User, String, String)
     */
    @Test
    public void addUser_Success() {
        // Create userEmailCookie
        Cookie userEmailCookie = new Cookie("userEmail", testUser.getEmail());
        userEmailCookie.setMaxAge(3600);

        // Test method
        ModelAndView modelAndView = signUpController.addUser(testRequest, testResponse, testRedirectAttributes,
                testUser, testUser.getShoppingCard().getShoppingCardType().getType(), testUser.getUserPasswordRepeat());

        // Check testUser and its id for not null
        assertNotNull(testUser);
        assertNotNull(testUser.getId());

        // Check user added to database
        User userFromDB = userService.findUser(testUser.getId());
        assertEqualUsers(testUser, userFromDB);

        // Check session and cookie
        assertEqualUsers((User) testRequest.getSession().getAttribute("user"), testUser);
        assertEqualCookies(userEmailCookie, testResponse.getCookieList());
        assertEqualModelAndViews(modelAndView, new ModelAndView("home"));
    }

    /**
     * @see SignUpController#addUser(HttpServletRequest, HttpServletResponse, RedirectAttributes, User, String, String)
     */
    @Test
    public void addUser_AddInvalidUserFailure() {
        // Error message
        String errorMessage = "There are invalid fields, please fill them all correctly and try again.";
        // Set testUser invalid firstName
        testUser.setFirstName(null);

        // Test method
        ModelAndView modelAndView = signUpController.addUser(testRequest, testResponse, testRedirectAttributes,
                testUser, testUser.getShoppingCard().getShoppingCardType().getType(), testUser.getUserPasswordRepeat());

        // Assertions
        assertNull(userService.findUser(testUser.getEmail()));
        assertEquals(testRedirectAttributes.getFlashAttributes().get("message"), errorMessage);
        assertEqualModelAndViews(modelAndView, new ModelAndView("redirect:/signup"));
    }

    /**
     * @see SignUpController#addUser(HttpServletRequest, HttpServletResponse, RedirectAttributes, User, String, String)
     */
    @Test
    public void addUser_UserExistsFailure() {
        // Add testUser to DB
        userService.addUser(testUser);
        ShoppingCard shoppingCard = testUser.getShoppingCard();

        // Test method
        ModelAndView modelAndView = signUpController.addUser(testRequest, testResponse, testRedirectAttributes,
                testUser, testUser.getShoppingCard().getShoppingCardType().getType(), testUser.getUserPasswordRepeat());

        // Set shoppingCard because in addUser method it changed
        testUser.setShoppingCard(shoppingCard);

        // Assertions
        assertNotNull(testRedirectAttributes.getFlashAttributes().get("message"));
        assertEqualModelAndViews(modelAndView, new ModelAndView("redirect:/signup"));
    }

    /**
     * @see SignUpController#addArtist(HttpServletRequest, HttpServletResponse, RedirectAttributes, MultipartFile)
     */
    @Test
    public void addArtist_Success() throws IOException {
        // Create userEmailCookie
        Cookie userEmailCookie = new Cookie("userEmail", testArtist.getEmail());
        userEmailCookie.setMaxAge(3600);

        // Set to testRequest all parameters
        testRequest.getParameterMap().put("firstName", new String[]{testArtist.getFirstName()});
        testRequest.getParameterMap().put("lastName", new String[]{testArtist.getLastName()});
        testRequest.getParameterMap().put("age", new String[]{String.valueOf(testArtist.getAge())});
        testRequest.getParameterMap().put("email", new String[]{testArtist.getEmail()});
        testRequest.getParameterMap().put("password", new String[]{testArtist.getPassword()});
        testRequest.getParameterMap().put("passwordRepeat", new String[]{testArtist.getUserPasswordRepeat()});
        testRequest.getParameterMap().put("artistSpec", new String[]{testArtist.getSpecialization().getType()});
        testRequest.getParameterMap().put("paymentType", new String[]{testArtist.getShoppingCard().getShoppingCardType().toString()});

        // Test method
        ModelAndView modelAndView = signUpController.addArtist(testRequest, testResponse, testRedirectAttributes, image);

        Artist addedArtist = artistService.findArtist(((Artist) testRequest.getSession().getAttribute("user")).getId());

        assertEqualModelAndViews(modelAndView, new ModelAndView("home"));
        assertEqualArtists((Artist) testRequest.getSession().getAttribute("user"), testArtist);
        assertEqualCookies(userEmailCookie, testResponse.getCookieList());
        assertEqualArtists(testArtist, addedArtist);
    }

    /**
     * @see SignUpController#addArtist(HttpServletRequest, HttpServletResponse, RedirectAttributes, MultipartFile)
     */
    @Test
    public void addArtist_Failure() {

    }

    // endregion
}
