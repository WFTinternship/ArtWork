package am.aca.wftartproject.service.integration;

import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.service.BaseIntegrationTest;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import am.aca.wftartproject.service.impl.ShoppingCardServiceImpl;
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

    @Autowired
    private ShoppingCardServiceImpl shoppingCardService;

    /**
     * Creates artist for test
     */
    @Before
    public void setUp() {
        testArtist = createTestArtist();
    }

    /**
     * Deletes all artists created during the test
     */
    @After
    public void tearDown() {
        if (testArtist.getShoppingCard() != null) {
            shoppingCardService.deleteShoppingCardByBuyerId(testArtist.getId());
        }

        if (testArtist.getId() != null) {
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

        // Test method
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

        // Test method
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
     * @see ArtistServiceImpl#updateArtist(java.lang.Long, am.aca.wftartproject.model.Artist)
     */
    @Test
    public void updateArtist_Success() {
        // Add artist into DB
        artistService.addArtist(testArtist);
        testArtist.setSpecialization(ArtistSpecialization.OTHER);

        Long id = testArtist.getId();

        // Test method
        artistService.updateArtist(id, testArtist);

        // Check is updated
        Artist updatedArtist = artistService.findArtist(id);
        assertEqualArtists(updatedArtist, testArtist);
    }

    /**
     * @see ArtistServiceImpl#updateArtist(java.lang.Long, am.aca.wftartproject.model.Artist)
     */
    @Test(expected = InvalidEntryException.class)
    public void updateArtist_Failure() {
        // Create invalid artist
        testArtist = new Artist();

        // Test method
        artistService.updateArtist(testArtist.getId(), testArtist);
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

        // Test method
        shoppingCardService.deleteShoppingCardByBuyerId(testArtist.getId());
        testArtist.setShoppingCard(null);
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
        // Create invalid artist
        testArtist = new Artist();

        // Test method
        artistService.deleteArtist(testArtist.getId());
    }

    // endregion
}