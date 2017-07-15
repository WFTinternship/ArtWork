package am.aca.wftartproject.service.integration;

import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.entity.Artist;
import am.aca.wftartproject.entity.ArtistSpecialization;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.BaseIntegrationTest;
import am.aca.wftartproject.service.ShoppingCardService;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualArtists;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestArtist;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

/**
 * Created by ASUS on 30-Jun-17
 */
public class ArtistServiceIntegrationTest extends BaseIntegrationTest {
    private Artist testArtist;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ShoppingCardService shoppingCardService;

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

        if (testArtist.getId() != null) {
            artistService.deleteArtist(testArtist);
        }
        testArtist = null;
    }

    // region<TEST CASE>

    /**
     * @see ArtistServiceImpl#addArtist(am.aca.wftartproject.entity.Artist)
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
     * @see ArtistServiceImpl#addArtist(am.aca.wftartproject.entity.Artist)
     */
    @Test(expected = InvalidEntryException.class)
    public void addArtist_Failure() {
        // Create invalid artist
        testArtist = new Artist();

        // Test method
        artistService.addArtist(testArtist);
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
        testArtist = new Artist();

        // Test method
        artistService.findArtist(testArtist.getId());
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
        // Create invalid artist
        testArtist = new Artist();

        // Test method
        artistService.findArtist(testArtist.getEmail());
    }

    /**
     * @see ArtistServiceImpl#updateArtist(Artist)
     */
    @Test
    public void updateArtist_Success() {
        // Add artist into DB
        artistService.addArtist(testArtist);
        testArtist.setSpecialization(ArtistSpecialization.OTHER);

        Long id = testArtist.getId();
        // Try to update artist
        artistService.updateArtist(testArtist);

        // Check is updated
        Artist updatedArtist = artistService.findArtist(id);
        assertEqualArtists(updatedArtist, testArtist);
    }

    /**
     * @see ArtistServiceImpl#updateArtist(Artist)
     */
    @Test(expected = InvalidEntryException.class)
    public void updateArtist_Failure() {
        // Create invalid artist
        testArtist = new Artist();

        // Test method
        artistService.updateArtist(testArtist);
    }

    /**
     * @see ArtistServiceImpl#deleteArtist(Artist)
     */
    @Test
    public void deleteArtist_Success() {
        // Add artist into DB
        artistService.addArtist(testArtist);

        assertNotNull(testArtist);
        assertNotNull(testArtist.getId());

        // Delete artist
        artistService.deleteArtist(testArtist);

        // Make sure that it's deleted
        assertNull(artistService.findArtist(testArtist.getId()));
        testArtist.setId(null);
    }

    /**
     * @see ArtistServiceImpl#deleteArtist(Artist)
     */
    @Test(expected = InvalidEntryException.class)
    public void deleteArtist_Failure() {
        // Create invalid artist
        testArtist = new Artist();

        // Test method
        artistService.deleteArtist(testArtist);
    }

    // endregion
}

