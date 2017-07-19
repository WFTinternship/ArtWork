package am.aca.wftartproject.dao.integration;

import am.aca.wftartproject.entity.ShoppingCard;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.entity.Artist;
import am.aca.wftartproject.entity.ArtistSpecialization;
import am.aca.wftartproject.repository.ArtistRepo;
import am.aca.wftartproject.repository.ShoppingCardRepo;
import am.aca.wftartproject.util.AssertTemplates;
import am.aca.wftartproject.util.TestObjectTemplate;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.sql.SQLException;

import static junit.framework.TestCase.*;

/**
 * Created by Armen on 6/1/2017
 */

public class ArtistRepoIntegrationTest extends BaseDAOIntegrationTest {

    private static final Logger LOGGER = Logger.getLogger(ArtistRepoIntegrationTest.class);

    private Artist testArtist;
    private ShoppingCard testShoppingCard;

    @Autowired
    private ArtistRepo artistRepo;

    public ArtistRepoIntegrationTest() throws SQLException, ClassNotFoundException {
    }

    /**
     * Creates artist for tests
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Before
    @Transactional
    public void setUp() throws SQLException, ClassNotFoundException {
        // Create artistSpecialization
        testArtist = TestObjectTemplate.createTestArtist();

        // Add artist into db
        artistRepo.saveAndFlush(testArtist);

    }


    /**
     * Deletes artists created during the tests
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @After
    public void tearDown() throws SQLException, ClassNotFoundException, DAOException {
        // Delete inserted test users and artists from db
        if (testArtist != null && testArtist.getId() != null && testArtist.isValidArtist()) {
            artistRepo.delete(testArtist);
        }

        // Set null test artist object
        testArtist = null;

        // Print busy connections quantity
//        if (jdbcTemplate.getDataSource() instanceof ComboPooledDataSource) {
//            LOGGER.info(String.format("Number of busy connections End: %s",
//                    ((ComboPooledDataSource) jdbcTemplate.getDataSource()).getNumBusyConnections()));
//        }
    }

    // region<TEST_CASE>

    /**
     * @see am.aca.wftartproject.repository.ArtistRepo(Artist)
     */
    @Test
    public void addArtist_Success() throws SQLException {
        // testArtist already set into db

        // Check testArtist object and testArtist id for null
        assertNotNull(testArtist);
        assertNotNull(testArtist.getId());

        Artist addedArtist = artistRepo.findOne(testArtist.getId());

        // Check for equals
        AssertTemplates.assertEqualArtists(testArtist, addedArtist);
    }

    /**
     * @see ArtistRepo#saveAndFlush(Object)
     */
    @Test(expected = DAOException.class)
    public void addArtist_Failure() throws SQLException {
        // Set artist into db, get generated id
        Artist testartist2 = TestObjectTemplate.createTestArtist();
        testartist2.setFirstName(null);
        try {
            artistRepo.saveAndFlush(testartist2);
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }

    }

    /**
     * @see ArtistRepo#findOne(Serializable)
     */
    @Test
    public void findOne_Success() {

        // Find and get artist from db
        Artist findOne = artistRepo.findOne(testArtist.getId());

        // Check for sameness with testArtist
        AssertTemplates.assertEqualArtists(findOne, testArtist);
    }

    /**
     * @see ArtistRepo#findOne(Serializable)
     */
    @Test
    public void findOne_Failure() {

        // Find and get artist from db
        Artist findOne = artistRepo.findOne(-8L);

        // Check artist for null
        assertNull(findOne);
    }

    /**
     * @see ArtistRepo#findArtistByEmail(String)
     */
    @Test
    public void findArtistByEmail_Success() {


        // Find and get artist from DB
        Artist findOne = artistRepo.findArtistByEmail(testArtist.getEmail());

        // Check for equals
        AssertTemplates.assertEqualArtists(findOne, testArtist);
    }

    /**
     * @see ArtistRepo#findArtistByEmail(String)
     */
    @Test
    public void findArtistByEmail_Failure() {

        // Find and get artist from DB

        Artist findOne = artistRepo.findArtistByEmail("jdpioahdpi8ua");
        assertNull(findOne);
    }

    /**
     * @see ArtistRepo#saveAndFlush(Object)
     */
    @Test
    public void updateArtist_Success() {
        // Set artist specialization  and add into db
        testArtist.setSpecialization(ArtistSpecialization.OTHER);

        // Update artists specialization field in db and check for true
        assertEquals(artistRepo.saveAndFlush(testArtist), testArtist);

    }

    /**
     * @see ArtistRepo#saveAndFlush(Object)
     */
    @Test(expected = DAOException.class)
    public void updateArtist_Failure() {

        //set firstname to null
        testArtist.setFirstName(null);

        // Update artists specialization field in db, get from db check for false
        try {
            artistRepo.saveAndFlush(testArtist).equals(testArtist);
        } catch (Exception e) {
            // set firstname not nullable for teardown
            testArtist.setFirstName("test");
            throw new DAOException("");
        }


    }

    /**
     * @see ArtistRepo#delete(Object)
     */
    @Test
    public void deleteArtist_Success() {


        // Delete artist from db by id
        artistRepo.delete(testArtist);

        // Explicitly setting id null
        testArtist.setId(null);
    }

    /**
     * @see ArtistRepo#delete(Artist)
     */
    @Test(expected = DAOException.class)
    public void deleteArtist_Failure() {
        // Add artist into db
        // Attemp to delete artist from db
        testArtist = null;
        try {
            artistRepo.delete(testArtist);
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

    // endregion
}