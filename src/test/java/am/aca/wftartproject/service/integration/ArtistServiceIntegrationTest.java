package am.aca.wftartproject.service.integration;

import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.model.ShoppingCard;
import am.aca.wftartproject.service.BaseIntegrationTest;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import am.aca.wftartproject.service.impl.ShoppingCardServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualArtists;
import static am.aca.wftartproject.util.AssertTemplates.assertEqualShoppingCards;
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
            if (testArtist.getShoppingCard().getId() != null)
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

        ShoppingCard shoppingCard = shoppingCardService.getShoppingCardByBuyerId(testArtist.getId());
        assertEqualShoppingCards(shoppingCard, testArtist.getShoppingCard());
    }

    /**
     * @see ArtistServiceImpl#addArtist(am.aca.wftartproject.model.Artist)
     */
    @Test(expected = InvalidEntryException.class)
    public void addArtist_Failure() {
        // Set artist null shopping card
        testArtist.setShoppingCard(null);

        // Test method
        artistService.addArtist(testArtist);
    }

    /**
     * @see ArtistServiceImpl#findArtist(java.lang.Long)
     */
    @Test
    public void findArtist_Success() {
        // Add artist to DB
        artistService.addArtist(testArtist);

        // Test method
        Artist foundedArtist = artistService.findArtist(testArtist.getId());

        // check for equals with testArtist
        assertEqualArtists(foundedArtist, testArtist);
    }

    /**
     * @see ArtistServiceImpl#findArtist(java.lang.Long)
     */
    @Test
    public void findArtist_Failure() {
        testArtist.setId(2000L);

        // Test method
        Artist artist = artistService.findArtist(testArtist.getId());
        assertNull(artist);
        testArtist.setId(null);
    }

    /**
     * @see ArtistServiceImpl#findArtist(java.lang.String)
     */
    @Test
    public void findArtistByEmail_Success() {
        // Add artist to DB
        artistService.addArtist(testArtist);

        // Test method
        Artist foundedArtist = artistService.findArtist(testArtist.getEmail());

        // Check for sameness with testArtist
        assertEqualArtists(foundedArtist, testArtist);
    }

    /**
     * @see ArtistServiceImpl#findArtist(java.lang.String)
     */
    @Test
    public void findArtistByEmail_Failure() {
        // Create invalid artist
        testArtist.setEmail("testemail@email.com");

        // Test method
        Artist artist = artistService.findArtist(testArtist.getEmail());
        assertNull(artist);
    }

    /**
     * @see ArtistServiceImpl#updateArtist(java.lang.Long, am.aca.wftartproject.model.Artist)
     */
    @Test
    public void updateArtist_Success() {
        // Add artist to DB
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
        testArtist.setSpecialization(null);

        // Test method
        artistService.updateArtist(testArtist.getId(), testArtist);
    }

    /**
     * @see ArtistServiceImpl#deleteArtist(java.lang.Long)
     */
    @Test
    public void deleteArtist_Success() {
        // Add artist to DB
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
        // Test method
        artistService.deleteArtist(testArtist.getId());
    }

    // endregion
}