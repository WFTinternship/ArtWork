package am.aca.wftartproject.controller.unit;

import am.aca.wftartproject.controller.BaseUnitTest;
import am.aca.wftartproject.controller.LogInController;
import am.aca.wftartproject.util.controller.TestHttpSession;
import am.aca.wftartproject.exception.service.ServiceException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualArtists;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualModelAndViews;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualUsers;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestArtist;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestUser;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * @author surik
 */
public class LogInControllerUnitTest extends BaseUnitTest {

    private User testUser;
    private Artist testArtist;
    private HttpSession testSession;

    @InjectMocks
    private LogInController logInController;

    @Mock
    private UserService userServiceMock;
    @Mock
    private ArtistService artistServiceMock;
    @Mock
    private HttpServletResponse testResponseMock;
    @Mock
    private HttpServletRequest testRequestMock;
    @Mock
    private RedirectAttributes testRedirectAttributes;


    /**
     * Creates testSession, testArtist and testUser for test
     */
    @Before
    public void setUp() {
        // Create testSession
        testSession = new TestHttpSession();

        // Create testUser and testArtist
        testUser = createTestUser();
        testArtist = createTestArtist();
    }

    @After
    public void tearDown() {
    }

    // region <TEST CASE>

    /**
     * @see LogInController#loginProcess(HttpServletRequest, HttpServletResponse, RedirectAttributes)
     */
    @Test
    public void loginProcess_userLoginSuccess() {
        testArtist = null;

        // Create argument captor
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        // Setup mocks
        doReturn(testSession).when(testRequestMock).getSession(anyBoolean());
        doReturn(testUser.getEmail()).when(testRequestMock).getParameter("email");
        doReturn(testUser.getPassword()).when(testRequestMock).getParameter("password");
        doReturn(testUser).when(userServiceMock).login(argumentCaptor.capture(), argumentCaptor.capture());
        doReturn(testArtist).when(artistServiceMock).findArtist(anyLong());

        // Test method
        ModelAndView modelAndView = logInController.loginProcess(testRequestMock, testResponseMock, testRedirectAttributes);

        assertEqualModelAndViews(modelAndView, new ModelAndView("redirect:/index"));
        assertEqualUsers((User) testSession.getAttribute("user"), testUser);
        assertEquals(testUser.getEmail(), argumentCaptor.getAllValues().get(0));
        assertEquals(testUser.getPassword(), argumentCaptor.getAllValues().get(1));
    }


    /**
     * @see LogInController#loginProcess(HttpServletRequest, HttpServletResponse, RedirectAttributes)
     */
    @Test
    public void loginProcess_FindUserFromDBForLoginFailed() {
        // Setup mocks
        doReturn(testSession).when(testRequestMock).getSession(anyBoolean());
        doReturn(testUser.getEmail()).when(testRequestMock).getParameter("email");
        doReturn(testUser.getPassword()).when(testRequestMock).getParameter("password");
        doThrow(ServiceException.class).when(userServiceMock).login(anyString(), anyString());


        // Test method
        ModelAndView modelAndView = logInController.loginProcess(testRequestMock, testResponseMock, testRedirectAttributes);

        assertEqualModelAndViews(modelAndView, new ModelAndView("login"));
    }


    /**
     * @see LogInController#loginProcess(HttpServletRequest, HttpServletResponse, RedirectAttributes)
     */
    @Test
    public void loginProcess_artistLoginSuccess() {
        // Create argument captor
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Long> argumentCaptor1 = ArgumentCaptor.forClass(Long.class);

        // Setup mocks
        doReturn(testSession).when(testRequestMock).getSession(anyBoolean());
        doReturn(testArtist.getEmail()).when(testRequestMock).getParameter("email");
        doReturn(testArtist.getPassword()).when(testRequestMock).getParameter("password");
        doReturn(testUser).when(userServiceMock).login(argumentCaptor.capture(), argumentCaptor.capture());
        doReturn(testArtist).when(artistServiceMock).findArtist(argumentCaptor1.capture());

        // Test method
        ModelAndView modelAndView = logInController.loginProcess(testRequestMock, testResponseMock, testRedirectAttributes);

        assertEqualModelAndViews(modelAndView, new ModelAndView("redirect:/index"));
        assertEqualArtists((Artist) testSession.getAttribute("user"), testArtist);
        assertEquals(testArtist.getEmail(), argumentCaptor.getAllValues().get(0));
        assertEquals(testArtist.getPassword(), argumentCaptor.getAllValues().get(1));
        assertEquals(testUser.getId(), argumentCaptor1.getValue());
    }


    /**
     * @see LogInController#loginProcess(HttpServletRequest, HttpServletResponse, RedirectAttributes)
     */
    @Test
    public void loginProcess_findArtistFailed() {
        // Create argument captor
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        // Setup mocks
        doReturn(testSession).when(testRequestMock).getSession(anyBoolean());
        doReturn(testArtist.getEmail()).when(testRequestMock).getParameter("email");
        doReturn(testArtist.getPassword()).when(testRequestMock).getParameter("password");
        doReturn(testUser).when(userServiceMock).login(argumentCaptor.capture(), argumentCaptor.capture());
        doThrow(ServiceException.class).when(artistServiceMock).findArtist(anyLong());

        // Test method
        ModelAndView modelAndView = logInController.loginProcess(testRequestMock, testResponseMock, testRedirectAttributes);

        assertEqualModelAndViews(modelAndView, new ModelAndView("login"));
        assertEquals(testArtist.getEmail(), argumentCaptor.getAllValues().get(0));
        assertEquals(testArtist.getPassword(), argumentCaptor.getAllValues().get(1));
    }


    /**
     * @see LogInController#logout(HttpServletRequest, HttpServletResponse)
     */
    @Test
    public void logout_sessionNotNull() {
        // Setup mock
        doReturn(testSession).when(testRequestMock).getSession(anyBoolean());

        // Test method
        ModelAndView modelAndView = logInController.logout(testRequestMock, testResponseMock);

        assertEqualModelAndViews(modelAndView, new ModelAndView("index"));
        verify(testResponseMock).addCookie(any());
    }


    /**
     * @see LogInController#logout(HttpServletRequest, HttpServletResponse)
     */
    @Test
    public void logout_sessionNull() {
        // Setup mock
        doReturn(null).when(testRequestMock).getSession(anyBoolean());

        // Test method
        ModelAndView modelAndView = logInController.logout(testRequestMock, testResponseMock);

        assertEqualModelAndViews(modelAndView, new ModelAndView("index"));
        verify(testResponseMock, never()).addCookie(any());
    }


    // endregion
}
