package am.aca.wftartproject.controller.unit;

import am.aca.wftartproject.controller.BaseUnitTest;
import am.aca.wftartproject.controller.HomeController;
import am.aca.wftartproject.util.controller.TestHttpSession;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualArtists;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualModelAndViews;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualUsers;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestArtist;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;

/**
 * @author surik
 */
public class HomeControllerUnitTest extends BaseUnitTest {
    private User testUser;
    private Artist testArtist;
    private HttpSession testSession;
    private Cookie[] cookies = new Cookie[1];

    @InjectMocks
    private HomeController homeController;

    @Mock
    private UserService userServiceMock;
    @Mock
    private ArtistService artistServiceMock;
    @Mock
    private HttpServletRequest testRequestMock;

    /**
     * Creates testSession, testArtist and testUser for test
     */
    @Before
    public void setUp() {
        // Create testUser, testArtist and testSession
        testUser = createTestUser();
        testArtist = createTestArtist();
        testSession = new TestHttpSession();
    }

    @After
    public void tearDown() {

    }

    // region <TEST CASE>

    /**
     * @see HomeController#welcome(HttpServletRequest)
     */
    @Test
    public void welcome_noCookies() {
        // Setup mocks
        doReturn(testSession).when(testRequestMock).getSession(anyBoolean());
        doReturn(null).when(testRequestMock).getCookies();

        // Test method
        ModelAndView modelAndView = homeController.welcome(testRequestMock);

        // Assertions
        assertEqualModelAndViews(modelAndView, new ModelAndView("home"));
        assertNull(testSession.getAttribute("user"));
    }

    /**
     * @see HomeController#welcome(HttpServletRequest)
     */
    @Test
    public void welcome_noCookieWithNameUserEmail() {
        // Create cookie with other name
        cookies[0] = new Cookie("emailCookie", testUser.getEmail());

        // Setup mocks
        doReturn(testSession).when(testRequestMock).getSession(anyBoolean());
        doReturn(cookies).when(testRequestMock).getCookies();

        // Test method
        ModelAndView modelAndView = homeController.welcome(testRequestMock);

        // Assertions
        assertEqualModelAndViews(modelAndView, new ModelAndView("home"));
        assertNull(testSession.getAttribute("user"));
    }

    /**
     * @see HomeController#welcome(HttpServletRequest)
     */
    @Test
    public void welcome_findArtistSuccess() {
        // Create argument captor
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        // Create cookie
        cookies[0] = new Cookie("userEmail", testArtist.getEmail());

        // Setup mocks
        doReturn(testSession).when(testRequestMock).getSession(anyBoolean());
        doReturn(cookies).when(testRequestMock).getCookies();
        doReturn(testArtist).when(artistServiceMock).findArtist(argumentCaptor.capture());

        // Test method
        ModelAndView modelAndView = homeController.welcome(testRequestMock);

        // Assertions
        assertEqualModelAndViews(modelAndView, new ModelAndView("home"));
        assertEqualArtists(testArtist, (Artist) testSession.getAttribute("user"));
        assertEquals(testArtist.getEmail(), argumentCaptor.getValue());
    }

    /**
     * @see HomeController#welcome(HttpServletRequest)
     */
    @Test
    public void welcome_noArtistFindUserSuccess() {
        // Create argument captor
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        // Create cookie
        cookies[0] = new Cookie("userEmail", testUser.getEmail());

        // Setup mocks
        doReturn(testSession).when(testRequestMock).getSession(anyBoolean());
        doReturn(cookies).when(testRequestMock).getCookies();
        doReturn(null).when(artistServiceMock).findArtist(anyString());
        doReturn(testUser).when(userServiceMock).findUser(argumentCaptor.capture());

        // Test method
        ModelAndView modelAndView = homeController.welcome(testRequestMock);

        // Assertions
        assertEqualModelAndViews(modelAndView, new ModelAndView("home"));
        assertEqualUsers(testUser, (User) testSession.getAttribute("user"));
        assertEquals(testUser.getEmail(), argumentCaptor.getValue());
    }

    /**
     * @see HomeController#welcome(HttpServletRequest)
     */
    @Test
    public void welcome_findArtistFindUserFailure() {
        // Create cookie
        cookies[0] = new Cookie("userEmail", testArtist.getEmail());

        // Setup mocks
        doReturn(testSession).when(testRequestMock).getSession(anyBoolean());
        doReturn(cookies).when(testRequestMock).getCookies();
        doReturn(null).when(artistServiceMock).findArtist(anyString());
        doReturn(null).when(userServiceMock).findUser(anyString());

        // Test method
        ModelAndView modelAndView = homeController.welcome(testRequestMock);

        assertEqualModelAndViews(modelAndView, new ModelAndView("home"));
        assertNull(testSession.getAttribute("user"));
    }

        // endregion
}
