package am.aca.wftartproject.controller.unit;

import am.aca.wftartproject.controller.BaseUnitTest;
import am.aca.wftartproject.controller.SignUpController;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.UserService;
import am.aca.wftartproject.util.controller.TestHttpSession;
import am.aca.wftartproject.util.controller.TestRedirectAttributes;
import com.sun.org.apache.xpath.internal.Arg;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialException;

import java.io.IOException;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualArtists;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualModelAndViews;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualUsers;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestArtist;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestUser;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.*;

/**
 * @author surik
 */
public class SignUpControllerUnitTest extends BaseUnitTest {
    private User testUser;
    private Artist testArtist;
    private HttpSession testSession;
    private RedirectAttributes testRedirectAttributes;
    private MultipartFile image;


    @InjectMocks
    private SignUpController signUpController;
    @Mock
    private UserService userServiceMock;
    @Mock
    private ArtistService artistServiceMock;
    @Mock
    private HttpServletRequest testRequestMock;
    @Mock
    private HttpServletResponse testResponseMock;

    /**
     * Creates testUser, testArtist, testSession and testRedirectAttributes for test
     */
    @Before
    public void setUp() {
        testUser = createTestUser();
        testArtist = createTestArtist();
        testSession = new TestHttpSession();
        testRedirectAttributes = new TestRedirectAttributes();
        image = new MockMultipartFile("userPic", new byte[10]);
    }

    @After
    public void tearDown() {

    }

    // region<TEST CASE>

    /**
     * @see SignUpController#addUser(HttpServletRequest, HttpServletResponse, RedirectAttributes, User, String, String)
     */
    @Test
    public void addUser_Success() {
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);

        // Setup mocks
        doReturn(testSession).when(testRequestMock).getSession(anyBoolean());
        doNothing().when(userServiceMock).addUser(argumentCaptor.capture());
        doNothing().when(testResponseMock).addCookie(any(Cookie.class));

        // Test method
        ModelAndView actualModelAndView = signUpController.addUser(testRequestMock, testResponseMock, testRedirectAttributes,
                testUser, testUser.getShoppingCard().getShoppingCardType().getType(), testUser.getUserPasswordRepeat());

        // Assertions
        assertEqualModelAndViews(actualModelAndView, new ModelAndView("home"));
        assertEqualUsers((User) testSession.getAttribute("user"), testUser);
        assertEqualUsers(testUser, argumentCaptor.getValue());
        assertNull(testRedirectAttributes.getFlashAttributes().get("message"));
    }

    /**
     * @see SignUpController#addUser(HttpServletRequest, HttpServletResponse, RedirectAttributes, User, String, String)
     */
    @Test
    public void addUser_InvalidUserAddFailure() {
        // Error message
        String errorMessage = "There are invalid fields, please fill them all correctly and try again.";
        testUser.setFirstName(null);

        // Setup mocks
        doReturn(testSession).when(testRequestMock).getSession(anyBoolean());
        doThrow(InvalidEntryException.class).when(userServiceMock).addUser(any(User.class));

        // Test method
        ModelAndView actualModelAndView = signUpController.addUser(testRequestMock, testResponseMock, testRedirectAttributes,
                testUser, testUser.getShoppingCard().getShoppingCardType().getType(), testUser.getUserPasswordRepeat());

        // Assertions
        assertEqualModelAndViews(actualModelAndView, new ModelAndView("redirect:/signup"));
        assertEquals(testRedirectAttributes.getFlashAttributes().get("message"), errorMessage);
        assertNull(testSession.getAttribute("user"));
    }

    /**
     * @see SignUpController#addUser(HttpServletRequest, HttpServletResponse, RedirectAttributes, User, String, String)
     */
    @Test
    public void addUser_addUserFailure() {
        // Setup mocks
        doReturn(testSession).when(testRequestMock).getSession(anyBoolean());
        doThrow(ServiceException.class).when(userServiceMock).addUser(any(User.class));

        // Test method
        ModelAndView actualModelAndView = signUpController.addUser(testRequestMock, testResponseMock, testRedirectAttributes,
                testUser, testUser.getShoppingCard().getShoppingCardType().getType(), testUser.getUserPasswordRepeat());

        // Assertions
        assertEqualModelAndViews(actualModelAndView, new ModelAndView("redirect:/signup"));
        verify(testResponseMock, never()).addCookie(any(Cookie.class));
        assertNull(testSession.getAttribute("user"));
    }

    /**
     * @see SignUpController#addArtist(HttpServletRequest, HttpServletResponse, RedirectAttributes, MultipartFile)
     */
    @Test
    public void addArtist_addSuccess() throws IOException {
        ArgumentCaptor<Artist> artistArgumentCaptor = ArgumentCaptor.forClass(Artist.class);

        // Setup mocks
        doReturn(testSession).when(testRequestMock).getSession();
        doReturn(testArtist.getSpecialization().getType()).when(testRequestMock).getParameter("artistSpec");
        doReturn(testArtist.getFirstName()).when(testRequestMock).getParameter("firstName");
        doReturn(testArtist.getLastName()).when(testRequestMock).getParameter("lastName");
        doReturn(String.valueOf(testArtist.getAge())).when(testRequestMock).getParameter("age");
        doReturn(testArtist.getEmail()).when(testRequestMock).getParameter("email");
        doReturn(testArtist.getPassword()).when(testRequestMock).getParameter("password");
        doReturn(testArtist.getUserPasswordRepeat()).when(testRequestMock).getParameter("passwordRepeat");
        doReturn(testArtist.getShoppingCard().getShoppingCardType().getType()).when(testRequestMock).getParameter("paymentType");
        doNothing().when(artistServiceMock).addArtist(artistArgumentCaptor.capture());
        doNothing().when(testResponseMock).addCookie(any(Cookie.class));

        // Test method
        ModelAndView actualModelAndView = signUpController.addArtist(testRequestMock, testResponseMock, testRedirectAttributes, image);

        // Assertions
        assertEqualModelAndViews(actualModelAndView, new ModelAndView("home"));
        assertEqualArtists((Artist) testSession.getAttribute("user"), testArtist);
        assertEqualArtists(testArtist, artistArgumentCaptor.getValue());
        assertNull(testRedirectAttributes.getFlashAttributes().get("message"));
    }

    /**
     * @see SignUpController#addArtist(HttpServletRequest, HttpServletResponse, RedirectAttributes, MultipartFile)
     */
    @Test
    public void addArtist_invalidArtistAddFailure() throws IOException {
        // Error message
        String errorMessage = "There are invalid fields, please fill them all correctly and try again.";
        testArtist.setFirstName(null);

        // Setup mocks
        doReturn(testSession).when(testRequestMock).getSession();
        doReturn(testArtist.getSpecialization().getType()).when(testRequestMock).getParameter("artistSpec");
        doReturn(testArtist.getFirstName()).when(testRequestMock).getParameter("firstName");
        doReturn(testArtist.getLastName()).when(testRequestMock).getParameter("lastName");
        doReturn(String.valueOf(testArtist.getAge())).when(testRequestMock).getParameter("age");
        doReturn(testArtist.getEmail()).when(testRequestMock).getParameter("email");
        doReturn(testArtist.getPassword()).when(testRequestMock).getParameter("password");
        doReturn(testArtist.getUserPasswordRepeat()).when(testRequestMock).getParameter("passwordRepeat");
        doReturn(testArtist.getShoppingCard().getShoppingCardType().getType()).when(testRequestMock).getParameter("paymentType");
        doThrow(InvalidEntryException.class).when(artistServiceMock).addArtist(any(Artist.class));

        // Test method
        ModelAndView actualModelAndView = signUpController.addArtist(testRequestMock, testResponseMock, testRedirectAttributes, image);

        // Assertions
        assertEqualModelAndViews(actualModelAndView, new ModelAndView("redirect:/signup"));
        assertEquals(testRedirectAttributes.getFlashAttributes().get("message"), errorMessage);
        assertNull(testSession.getAttribute("user"));
    }

    /**
     * @see SignUpController#addArtist(HttpServletRequest, HttpServletResponse, RedirectAttributes, MultipartFile)
     */
    @Test
    public void addArtist_addFailure() throws IOException {
        // Setup mocks
        doReturn(testSession).when(testRequestMock).getSession();
        doReturn(testArtist.getSpecialization().getType()).when(testRequestMock).getParameter("artistSpec");
        doReturn(testArtist.getFirstName()).when(testRequestMock).getParameter("firstName");
        doReturn(testArtist.getLastName()).when(testRequestMock).getParameter("lastName");
        doReturn(String.valueOf(testArtist.getAge())).when(testRequestMock).getParameter("age");
        doReturn(testArtist.getEmail()).when(testRequestMock).getParameter("email");
        doReturn(testArtist.getPassword()).when(testRequestMock).getParameter("password");
        doReturn(testArtist.getUserPasswordRepeat()).when(testRequestMock).getParameter("passwordRepeat");
        doReturn(testArtist.getShoppingCard().getShoppingCardType().getType()).when(testRequestMock).getParameter("paymentType");
        doThrow(ServiceException.class).when(artistServiceMock).addArtist(any(Artist.class));

        // Test method
        ModelAndView actualModelAndView = signUpController.addArtist(testRequestMock, testResponseMock, testRedirectAttributes, image);

        // Assertions
        assertEqualModelAndViews(actualModelAndView, new ModelAndView("redirect:/signup"));
        verify(testResponseMock, never()).addCookie(any(Cookie.class));
        assertNull(testSession.getAttribute("user"));
    }

    // endregion
}
