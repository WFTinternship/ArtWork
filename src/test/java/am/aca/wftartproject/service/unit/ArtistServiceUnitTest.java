package am.aca.wftartproject.service.unit;

import am.aca.wftartproject.BaseUnitTest;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.service.DuplicateEntryException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import am.aca.wftartproject.service.impl.UserServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualArtists;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestArtist;
import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

/**
 * Created by ASUS on 24-Jun-17
 */
public class ArtistServiceUnitTest extends BaseUnitTest {

    private Artist testArtist;


    @InjectMocks
    @Autowired
    private ArtistService artistService;

    @Mock
    private ArtistDaoImpl artistDaoMock;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void afterTest() {

    }


    /**
     * @see ArtistServiceImpl#addArtist(Artist)
     */
    @Test
    public void addArtist_artistNotValidOrNull() {
        testArtist = null;

        // Test method
        try {
            artistService.addArtist(null);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        testArtist = createTestArtist();
        testArtist.setFirstName(null);

        // Test method
        try {
            artistService.addArtist(testArtist);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        testArtist = createTestArtist();
        testArtist.setEmail("invalidEmail");

        // Test method
        try {
            artistService.addArtist(testArtist);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ArtistServiceImpl#addArtist(Artist)
     */
    @Test
    public void addArtist_exists() {
        testArtist = createTestArtist();

        Artist fakeDbArtist = new Artist();

        doReturn(fakeDbArtist).when(artistDaoMock).findArtist(testArtist.getEmail());
        // Try to add user into db
        // Test method
        try {
            artistService.addArtist(testArtist);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof DuplicateEntryException);
        }
    }


    /**
     * @see ArtistServiceImpl#addArtist(Artist)
     */
    @Test(expected = ServiceException.class)
    public void addArtist_addFailed() {

        testArtist = createTestArtist();

        // Setup mocks
        doThrow(DAOException.class).when(artistDaoMock).addArtist(testArtist);

        // Test method
        artistService.addArtist(testArtist);
    }


    /**
     * @see ArtistServiceImpl#addArtist(Artist)
     */
    @Test
    public void addArtist_addSuccess() {

        testArtist = createTestArtist();

        // Setup mocks
        doNothing().when(artistDaoMock).addArtist(testArtist);

        // Test method
        artistService.addArtist(testArtist);
    }

    /**
     * @see ArtistServiceImpl#findArtist(java.lang.Long)
     */
    @Test
    public void findArtist_idNullOrNegative() {
        Long id = null;

        // Test method
        try {
            artistService.findArtist(id);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }

        id = -5L;

        // Test method
        try {
            artistService.findArtist(id);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }


    /**
     * @see ArtistServiceImpl#findArtist(java.lang.Long)
     */
    @Test(expected = ServiceException.class)
    public void findArtist_findFailed() {
        Long id = 516498484L;

        // Setup mocks
        doThrow(DAOException.class).when(artistDaoMock).findArtist(anyLong());

        // Test method
        artistService.findArtist(id);
    }


    /**
     * @see UserServiceImpl#findUser(java.lang.Long)
     */
    @Test
    public void findArtist_findSuccess() {
        // Create testUser
        testArtist = createTestArtist();

        //TODO ask about anyLong
        Long id = 5L;

        // Setup mocks
        doReturn(testArtist).when(artistDaoMock).findArtist(anyLong());

        // Test method
        assertEqualArtists(testArtist, artistService.findArtist(id));
    }


    /**
     * @see ArtistServiceImpl#findArtist(java.lang.String)
     */
    @Test
    public void findUserByEmail_emptyString() {

        // Create empty string
        String emptyEmail = "";

        // Try to find user with empty email
        try {
            artistService.findArtist(emptyEmail);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Create not valid email
        String notValidEmail = "notValidEmail";

        // Test method
        try {
            artistService.findArtist(notValidEmail);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidEntryException);
        }
    }


    /**
     * @see ArtistServiceImpl#findArtist(java.lang.String)
     */
    @Test(expected = ServiceException.class)
    public void findArtistByEmail_findFailed() {

        // Create not empty email
        String email = "email@gmail.com";

        // try to find artist by this email
        // Setup mocks
        doThrow(DAOException.class).when(artistDaoMock).findArtist(anyString());

        // Test methods
        artistService.findArtist(email);
    }

    /**
     * @see ArtistServiceImpl#findArtist(java.lang.String)
     */
    @Test
    public void findArtistByEmail_findSuccess() {
        // create testArtist
        testArtist = createTestArtist();

        // save testArtist email for further check
        String email = "test@gmail.com";

        // Setup mock
        doReturn(testArtist).when(artistDaoMock).findArtist(email);

        // Test method
        assertEquals(testArtist, artistService.findArtist(email));
    }


    /**
     * @see ArtistServiceImpl#updateArtist(Long, Artist)
     */
    @Test
    public void updateArtist_idIsNullOrNegative() {

        // Create test id and testArtist
        Long id = null;
        testArtist = createTestArtist();

        // Test method
        try {
            artistService.updateArtist(id, testArtist);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change id value to negative
        id = -5L;

        // Test method
        try {
            artistService.updateArtist(id, testArtist);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ArtistServiceImpl#updateArtist(Long, Artist)
     */
    @Test
    public void updateArtist_artistIsNullOrNotValid() {

        // Create test id and testArtist
        Long id = 5L;
        testArtist = null;

        // Test method
        try {
            artistService.updateArtist(id, testArtist);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Makes testArtist not valid
        testArtist = createTestArtist();
        testArtist.setLastName(null);

        // Test method
        try {
            artistService.updateArtist(id, testArtist);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

    }


    /**
     * @see ArtistService#updateArtist(Long, Artist)
     */
    @Test(expected = ServiceException.class)
    public void updateArtist_updateFail() {

        // Create test id and test Artist
        Long id = 5L;
        testArtist = createTestArtist();

        // Setup mock
        doThrow(DAOException.class).when(artistDaoMock).updateArtist(anyLong(), any(Artist.class));

        // Test method
        artistService.updateArtist(id, testArtist);

    }

    /**
     * @see ArtistServiceImpl#updateArtist(Long, Artist)
     */
    @Test
    public void updateArtist_updateSuccess() {

        // Create test id and test artist
        Long id = 5L;
        testArtist = createTestArtist();

        // Test method
        artistService.updateArtist(id, testArtist);

        verify(artistDaoMock).updateArtist(id, testArtist);

    }

    /**
     * @see ArtistServiceImpl#deleteArtist(Long)
     */
    @Test
    public void deleteArtist_idIsNullOrNegative() {

        // Create test id with null value
        Long id = null;

        // Test method
        try {
            artistService.deleteArtist(id);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidEntryException);
        }

        // Change id value to negative
        id = -5L;

        // Test method
        try{
            artistService.deleteArtist(id);
            fail();
        }catch (Exception ex){
            assertTrue(ex instanceof InvalidEntryException);
        }
    }


    /**
     * @see ArtistServiceImpl#deleteArtist(Long)
     */
    @Test(expected = ServiceException.class)
    public void deleteArtist_deleteFail(){

        // Create test id;
        Long id = 5L;

        // Setup mock
        doThrow(DAOException.class).when(artistDaoMock).deleteArtist(anyLong());

        artistService.deleteArtist(id);
    }


    /**
     * @see ArtistServiceImpl#deleteArtist(Long)
     */
    @Test
    public void deleteArtist_deleteSuccess(){

        // Create test id
        Long id = 5L;

        // Test method
        artistService.deleteArtist(id);

        // check invocation
        verify(artistDaoMock).deleteArtist(id);

    }

}
