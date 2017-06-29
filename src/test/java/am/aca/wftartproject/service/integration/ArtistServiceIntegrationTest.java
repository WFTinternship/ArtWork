package am.aca.wftartproject.service.integration;

import am.aca.wftartproject.BaseIntegrationTest;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualArtists;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestArtist;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

/**
 * @author surik
 */
@Transactional
public class ArtistServiceIntegrationTest extends BaseIntegrationTest {

    private Artist testArtist;
    @Autowired
    private ArtistServiceImpl artistService;

    /**
     * Creates artist for tests
     */
    @Before
    public void setUp() {
        testArtist = createTestArtist();
    }

    /**
     * Deletes all artists created during the tests
     */
    @After
    public void tearDown() {
        if (testArtist.getId() != null){
            artistService.deleteArtist(testArtist.getId());
        }
        testArtist = null;
    }

    // region<TEST CASE>

    /**
     * @see ArtistServiceImpl#addArtist(am.aca.wftartproject.model.Artist)
     */
    @Test
    public void addArtist_Success() {
        // Test method
        artistService.addArtist(testArtist);

        // Check testArtist and its id for null
        assertNotNull(testArtist);
        assertNotNull(testArtist.getId());

        // Get artist from DB and make sure it equals to testArtist
        Artist addedArtist = artistService.findArtist(testArtist.getId());
        assertEqualArtists(addedArtist, testArtist);
    }

    /**
     * @see ArtistServiceImpl#addArtist(am.aca.wftartproject.model.Artist)
     */
    @Test(expected = InvalidEntryException.class)
    public void addArtist_Failure() {
        // Test method
        artistService.addArtist(null);
    }

    /**
     * @see ArtistServiceImpl#findArtist(java.lang.Long)
     */
    @Test
    public void findArtist_Success() {
        // Add artist into DB
        artistService.addArtist(testArtist);

        // Try to find artist by id
        Artist foundedArtist = artistService.findArtist(testArtist.getId());

        // check for equals with testArtist
        assertEqualArtists(foundedArtist, testArtist);
    }

    /**
     * @see ArtistServiceImpl#findArtist(java.lang.Long)
     */
    @Test(expected = InvalidEntryException.class)
    public void findArtist_Failure() {
        // Test method
        artistService.findArtist(-5L);
    }

    /**
     * @see ArtistServiceImpl#findArtist(java.lang.String)
     */
    @Test
    public void findArtistByEmail_Success() {
        // Add artist into DB
        artistService.addArtist(testArtist);

        // Try to find artist by email
        Artist foundedArtist = artistService.findArtist(testArtist.getEmail());

        // Check for sameness with testArtist
        assertEqualArtists(foundedArtist, testArtist);
    }

    /**
     * @see ArtistServiceImpl#findArtist(java.lang.String)
     */
    @Test(expected = InvalidEntryException.class)
    public void findArtistByEmail_Failure() {
        // Test method
        artistService.findArtist("this is an invalid email");
    }

    /**
     * @see ArtistServiceImpl#updateArtist(java.lang.Long, am.aca.wftartproject.model.Artist)
     */
    @Test
    public void updateArtist_Success() {
        // Add artist into DB
        artistService.addArtist(testArtist);
        testArtist.setSpecialization(ArtistSpecialization.OTHER);

        Long id = testArtist.getId();
        // Try to update artist
        artistService.updateArtist(id, testArtist);

        // Check is
        Artist updatedArtist = artistService.findArtist(id);
        assertEqualArtists(updatedArtist, testArtist);
    }

    /**
     * @see ArtistServiceImpl#updateArtist(java.lang.Long, am.aca.wftartproject.model.Artist)
     */
    @Test(expected = InvalidEntryException.class)
    public void updateArtist_Failure() {
        // Test method
        artistService.updateArtist(-5L, testArtist);
    }

    /**
     * @see ArtistServiceImpl#deleteArtist(java.lang.Long)
     */
    @Test
    public void deleteArtist_Success() {
        // Add artist into DB
        artistService.addArtist(testArtist);

        assertNotNull(testArtist);
        assertNotNull(testArtist.getId());

        // Delete artist by id
        artistService.deleteArtist(testArtist.getId());

        // Make sure that it's deleted
        assertNull(artistService.findArtist(testArtist.getId()));

        testArtist.setId(null);
    }

    /**
     * @see ArtistServiceImpl#deleteArtist(java.lang.Long)
     */
    @Test(expected = InvalidEntryException.class)
    public void deleteArtist_Failure() {
        // Test method
        artistService.deleteArtist(null);
    }

    // endregion
}
