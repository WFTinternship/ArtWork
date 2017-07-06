package am.aca.wftartproject.dao.integration;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.util.AssertTemplates;
import am.aca.wftartproject.util.TestObjectTemplate;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.security.spec.ECField;
import java.sql.SQLException;
import static junit.framework.TestCase.*;

/**
 * Created by Armen on 6/1/2017
 */

public class ArtistDaoIntegrationTest extends BaseDAOIntegrationTest {

    private static final Logger LOGGER = Logger.getLogger(ArtistDaoIntegrationTest.class);

    private Artist testArtist;

    @Autowired
    private ArtistDao artistDao;

    public ArtistDaoIntegrationTest() throws SQLException, ClassNotFoundException {
    }

    /**
     * Creates artist for tests
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        // Create artistSpecialization
        testArtist = TestObjectTemplate.createTestArtist();

        // Add artist into db
        artistDao.addArtist(testArtist);

        // Print busy connections quantity
//        if (jdbcTemplate.getDataSource() instanceof ComboPooledDataSource) {
//            LOGGER.info(String.format("Number of busy connections Start: %s",
//                    ((ComboPooledDataSource) jdbcTemplate.getDataSource()).getNumBusyConnections()));
//        }
    }


    /**
     * Deletes artists created during the tests
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @After
    public void tearDown() throws SQLException, ClassNotFoundException ,DAOException {
        // Delete inserted test users and artists from db
        if (testArtist.getId() != null) {
            artistDao.deleteArtist(testArtist);
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
     * @see ArtistDao#addArtist(Artist)
     */
    @Test
    public void addArtist_Success() throws SQLException {
        // testArtist already set into db

        // Check testArtist object and testArtist id for null
        assertNotNull(testArtist);
        assertNotNull(testArtist.getId());

        Artist addedArtist = artistDao.findArtist(testArtist.getId());

        // Check for equals
        AssertTemplates.assertEqualArtists(testArtist, addedArtist);
    }

    /**
     * @see ArtistDao#addArtist(Artist)
     */
    @Test(expected = DAOException.class)
    public void addArtist_Failure() throws SQLException {
        // Set artist into db, get generated id
        Artist testartist2 = TestObjectTemplate.createTestArtist();
        testartist2.setFirstName(null);
        artistDao.addArtist(testartist2);
    }

    /**
     * @see ArtistDao#findArtist(Long)
     */
    @Test
    public void findArtist_Success() {

        // Find and get artist from db
        Artist findArtist = artistDao.findArtist(testArtist.getId());

        // Check for sameness with testArtist
        AssertTemplates.assertEqualArtists(findArtist, testArtist);
    }

    /**
     * @see ArtistDao#findArtist(Long)
     */
    @Test
    public void findArtist_Failure() {

        // Find and get artist from db
        Artist findArtist = artistDao.findArtist(-8L);

        // Check artist for null
        assertNull(findArtist);
    }

    /**
     * @see ArtistDao#findArtist(String)
     */
    @Test
    public void findArtistByEmail_Success(){


        // Find and get artist from DB
        Artist findArtist = artistDao.findArtist(testArtist.getEmail());

        // Check for equals
        AssertTemplates.assertEqualArtists(findArtist, testArtist);
    }

    /**
     * @see ArtistDao#findArtist(String)
     */
    @Test
    public void findArtistByEmail_Failure(){

        // Find and get artist from DB
        Artist findArtist = artistDao.findArtist("jdpioahdpi8ua");

        // Check for null
        assertNull(findArtist);
    }

    /**
     * @see ArtistDao#updateArtist(Artist)
     */
    @Test
    public void updateArtist_Success() {
        // Set artist specialization  and add into db
        testArtist.setSpecialization(ArtistSpecialization.OTHER);

        // Update artists specialization field in db and check for true
        assertTrue(artistDao.updateArtist(testArtist));

    }

    /**
     * @see ArtistDao#updateArtist(Artist)
     */
    @Test(expected = DAOException.class)
    public void updateArtist_Failure() {

        //set firstname to null
        testArtist.setFirstName(null);

        // Update artists specialization field in db, get from db check for false
        try{
            assertFalse(artistDao.updateArtist(testArtist));
        }catch (DAOException e){
            // set firstname not nullable for teardown
            testArtist.setFirstName("test");
            throw new DAOException("");
        }



    }

    /**
     * @see ArtistDao#deleteArtist(Artist)
     */
    @Test
    public void deleteArtist_Success() {


        // Delete artist from db by id
        assertTrue(artistDao.deleteArtist(testArtist));

        // Explicitly setting id null
        testArtist.setId(null);
    }

    /**
     * @see ArtistDao#deleteArtist(Artist)
     */
    @Test(expected = DAOException.class)
    public void deleteArtist_Failure() {
        // Add artist into db
        // Attemp to delete artist from db
        Artist artist = TestObjectTemplate.createTestArtist();
        artist.setId(-6L);

        assertFalse(artistDao.deleteArtist(artist));

    }

    // endregion
}