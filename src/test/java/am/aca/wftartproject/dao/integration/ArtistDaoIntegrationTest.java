package am.aca.wftartproject.dao.integration;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.dao.ArtistSpecializationLkpDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

import static am.aca.wftartproject.util.AssertTemplates.assertEqualArtists;
import static am.aca.wftartproject.util.TestObjectTemplate.createTestArtist;
import static junit.framework.TestCase.*;

/**
 * Created by Armen on 6/1/2017
 */
public class ArtistDaoIntegrationTest extends BaseDAOIntegrationTest {

    private static final Logger LOGGER = Logger.getLogger(ArtistDaoIntegrationTest.class);

    private Artist testArtist;

    @Autowired
    private ArtistDao artistDao;

    @Autowired
    private ArtistSpecializationLkpDao artistSpecializationLkpDao;

    public ArtistDaoIntegrationTest() {
    }

    /**
     * Creates artist for test
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        // Add specialization values, if appropriate table is empty
        if (artistSpecializationLkpDao.getArtistSpecialization(1) == null) {
            artistSpecializationLkpDao.addArtistSpecialization();
        }
        testArtist = createTestArtist();

        // Print busy connections quantity
        if (jdbcTemplate.getDataSource() instanceof ComboPooledDataSource) {
            LOGGER.info(String.format("Number of busy connections Start: %s",
                    ((ComboPooledDataSource) jdbcTemplate.getDataSource()).getNumBusyConnections()));
        }
    }

    /**
     * Deletes artists created during the test
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @After
    public void tearDown() throws SQLException, ClassNotFoundException {
        // Delete inserted test users and artists from db
        if (testArtist.getId() != null) {
            artistDao.deleteArtist(testArtist.getId());
        }

        // Set null test artist object
        testArtist = null;

        // Print busy connections quantity
        if (jdbcTemplate.getDataSource() instanceof ComboPooledDataSource) {
            LOGGER.info(String.format("Number of busy connections End: %s",
                    ((ComboPooledDataSource) jdbcTemplate.getDataSource()).getNumBusyConnections()));
        }
    }

    // region<TEST_CASE>

    /**
     * @see ArtistDao#addArtist(Artist)
     */
    @Test
    public void addArtist_Success() throws SQLException {
        // Test method
        artistDao.addArtist(testArtist);

        // Check testArtist object and testArtist id for null
        assertNotNull(testArtist);
        assertNotNull(testArtist.getId());

        // Get artist by id from db
        Long id = testArtist.getId();
        Artist addedArtist = artistDao.findArtist(id);

        // Check for equals
        assertEqualArtists(testArtist, addedArtist);
    }

    /**
     * @see ArtistDao#addArtist(Artist)
     */
    @Test(expected = DAOException.class)
    public void addArtist_Failure() throws SQLException {
        testArtist.setFirstName(null);

        // Test method
        artistDao.addArtist(testArtist);
    }

    /**
     * @see ArtistDao#findArtist(Long)
     */
    @Test
    public void findArtist_Success() {
        // Add artist to db
        artistDao.addArtist(testArtist);

        // Test method
        Artist findArtist = artistDao.findArtist(testArtist.getId());

        // Check for sameness with testArtist
        assertEqualArtists(findArtist, testArtist);
    }

    /**
     * @see ArtistDao#findArtist(Long)
     */
    @Test
    public void findArtist_Failure() {
        // Add artist into DB
        artistDao.addArtist(testArtist);

        // Test method
        Artist findArtist = artistDao.findArtist(-8L);

        // Check artist for null
        assertNull(findArtist);
    }

    /**
     * @see ArtistDao#findArtist(String)
     */
    @Test
    public void findArtistByEmail_Success() {
        // Add artist into Db
        artistDao.addArtist(testArtist);

        // Test method
        Artist findArtist = artistDao.findArtist(testArtist.getEmail());

        // Check for equals
        assertEqualArtists(findArtist, testArtist);
    }

    /**
     * @see ArtistDao#findArtist(String)
     */
    @Test
    public void findArtistByEmail_Failure() {
        // Add artist into DB
        artistDao.addArtist(testArtist);

        // Test method
        Artist findArtist = artistDao.findArtist("fake email");

        // Check for null
        assertNull(findArtist);
    }

    /**
     * @see ArtistDao#updateArtist(Long, Artist)
     */
    @Test
    public void updateArtist_Success() {
        // Set artist specialization  and add into db
        testArtist.setSpecialization(ArtistSpecialization.PAINTER);
        artistDao.addArtist(testArtist);
        testArtist.setSpecialization(ArtistSpecialization.OTHER);

        // Test method
        artistDao.updateArtist(testArtist.getId(), testArtist);

        // Get from db and check for sameness
        Artist updated = artistDao.findArtist(testArtist.getId());
        assertEquals(updated.getSpecialization().getId(), testArtist.getSpecialization().getId());
    }

    /**
     * @see ArtistDao#updateArtist(Long, Artist)
     */
    @Test(expected = DAOException.class)
    public void updateArtist_Failure() {
        // Set artist specialization  and add into db
        artistDao.addArtist(testArtist);
        testArtist.setSpecialization(ArtistSpecialization.OTHER);

        testArtist.setFirstName(null);

        // Test method
        artistDao.updateArtist(testArtist.getId(), testArtist);

        Artist updated = artistDao.findArtist(testArtist.getId());

        // Check for sameness
        assertEquals(updated.getSpecialization().getId(), ArtistSpecialization.OTHER.getId());
    }

    /**
     * @see ArtistDao#deleteArtist(Long)
     */
    @Test
    public void deleteArtist_Success() {
        // Add artist into db
        artistDao.addArtist(testArtist);

        // Test method
        Boolean isDeleted = artistDao.deleteArtist(testArtist.getId());

        assertTrue(isDeleted);

        // Explicitly setting id null
        testArtist.setId(null);
    }

    /**
     * @see ArtistDao#deleteArtist(Long)
     */
    @Test(expected = DAOException.class)
    public void deleteArtist_Failure() {
        // Add artist into db
        artistDao.addArtist(testArtist);

        // Test method
        Boolean isDeleted = artistDao.deleteArtist(-36L);

        // Check isDeleted
        assertFalse(isDeleted);
    }

    // endregion
}