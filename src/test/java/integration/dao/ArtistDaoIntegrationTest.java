package integration.dao;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.dao.ArtistSpecializationLkpDao;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.dao.impl.ArtistSpecializationLkpDaoImpl;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.util.dbconnection.ConnectionFactory;
import am.aca.wftartproject.util.dbconnection.ConnectionModel;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.AssertTemplates;
import util.TestObjectTemplate;

import java.sql.SQLException;

import static junit.framework.TestCase.*;

/**
 * Created by Armen on 6/1/2017
 */
public class ArtistDaoIntegrationTest extends BaseDAOIntegrationTest {

    private static final Logger LOGGER = Logger.getLogger(ArtistDaoIntegrationTest.class);
    private Artist testArtist;
    private ArtistDao artistDao;

    public ArtistDaoIntegrationTest() throws SQLException, ClassNotFoundException {
    }

    /**
     * Creates connection and artist
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {

        // create db connection,artistDaoImplementation and artist for testing
        dataSource = new ConnectionFactory()
                .getConnection(ConnectionModel.POOL)
                .getTestDBConnection();

        ArtistSpecializationLkpDao artistSpecialization = new ArtistSpecializationLkpDaoImpl(dataSource);

        if (artistSpecialization.getArtistSpecialization(1) == null) {
            artistSpecialization.addArtistSpecialization();
        }
        artistDao = new ArtistDaoImpl(dataSource);
        testArtist = TestObjectTemplate.createTestArtist();

        // print busy connections quantity
        if (dataSource instanceof ComboPooledDataSource) {
            LOGGER.info(String.format("Number of busy connections Start: %s", ((ComboPooledDataSource) dataSource).getNumBusyConnections()));
        }
    }


    /**
     * Deletes artists created during the tests
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @After
    public void tearDown() throws SQLException, ClassNotFoundException {


        // delete inserted test users and artists from db
        if (testArtist.getId() != null) {
            artistDao.deleteArtist(testArtist.getId());
        }

        // set null test artist object
        testArtist = null;

        // print busy connections quantity
        if (dataSource instanceof ComboPooledDataSource) {
            LOGGER.info(String.format("Number of busy connections End: %s", ((ComboPooledDataSource) dataSource).getNumBusyConnections()));
        }
    }

    //region(TEST_CASE)

    /**
     * @see ArtistDao#addArtist(Artist)
     */
    @Test
    public void addArtist_Success() throws SQLException {

        // set artist into db, get generated id
        artistDao.addArtist(testArtist);
        System.out.println(testArtist);

        // check testArtist object and testArtist id for null
        assertNotNull(testArtist);
        assertNotNull(testArtist.getId());

        // get artist by id from db
        Long id = testArtist.getId();
        System.out.println(id);
        Artist addedArtist = artistDao.findArtist(id);

        // check for equals
        AssertTemplates.assertEqualArtists(testArtist, addedArtist);
    }

    /**
     * @see ArtistDao#addArtist(Artist)
     */
    @Test(expected = DAOException.class)
    public void addArtist_Failure() throws SQLException {

        // set artist into db, get generated id
        testArtist.setFirstName(null);
        artistDao.addArtist(testArtist);
        System.out.println(testArtist);

        // check testArtist object and testArtist id for null
        assertNotNull(testArtist);
        assertNotNull(testArtist.getId());

        // get artist by id from db
        Long id = testArtist.getId();
        System.out.println(id);
        Artist addedArtist = artistDao.findArtist(id);

        // check for equals
        assertEquals(testArtist, addedArtist);
    }

    /**
     * @see ArtistDao#findArtist(Long)
     */
    @Test
    public void findArtist_Success() {
        artistDao.addArtist(testArtist);

        // find and get artist from db
        Artist findArtist = artistDao.findArtist(testArtist.getId());

        // check for sameness with testArtist
        AssertTemplates.assertEqualArtists(findArtist, testArtist);

    }

    /**
     * @see ArtistDao#findArtist(Long)
     */
    @Test(expected = DAOException.class)
    public void findArtist_Failure() {
        // add artist into DB
        artistDao.addArtist(testArtist);

        // find and get artist from db
        Artist findArtist = artistDao.findArtist(-8L);

        // check artist for null
        assertNull(findArtist.getId());
    }

    /**
     * @see ArtistDao#findArtist(String)
     */
    @Test
    public void findArtistByEmail_Success(){
        // add artist into Db
        artistDao.addArtist(testArtist);

        // find and get artist from DB
        Artist findArtist = artistDao.findArtist(testArtist.getEmail());

        // check for equals
        AssertTemplates.assertEqualArtists(findArtist, testArtist);
    }

    /**
     * @see ArtistDao#findArtist(String)
     */
    @Test(expected = DAOException.class)
    public void findArtistByEmail_Failure(){
        // add artist into DB
        artistDao.addArtist(testArtist);

        // find and get artist from DB
        Artist findArtist = artistDao.findArtist("jdpioahdpi8ua");

        // check for null
        assertNull(findArtist.getId());
    }

    /**
     * @see ArtistDao#updateArtist(Long, Artist)
     */
    @Test
    public void updateArtist_Success() {

        // set artist specialization  and add into db
        testArtist.setSpecialization(ArtistSpecialization.PAINTER);
        artistDao.addArtist(testArtist);
        testArtist.setSpecialization(ArtistSpecialization.OTHER);

        // update artists specialization field in db, get from db and check for sameness
        artistDao.updateArtist(testArtist.getId(), testArtist);
        Artist updated = artistDao.findArtist(testArtist.getId());
        assertEquals(updated.getSpecialization().getId(), testArtist.getSpecialization().getId());
    }

    /**
     * @see ArtistDao#updateArtist(Long, Artist)
     */
    @Test(expected = DAOException.class)
    public void updateArtist_Failure() {

        // set artist specialization  and add into db
        artistDao.addArtist(testArtist);
        testArtist.setSpecialization(ArtistSpecialization.OTHER);

        // update artists specialization field in db, get from db
        testArtist.setFirstName(null);
        artistDao.updateArtist(testArtist.getId(), testArtist);
        Artist updated = artistDao.findArtist(testArtist.getId());

        // check for sameness
        assertEquals(updated.getSpecialization().getId(), ArtistSpecialization.OTHER.getId());
    }

    /**
     * @see ArtistDao#deleteArtist(Long)
     */
    @Test
    public void deleteArtist_Success() {

        // add artist into db
        artistDao.addArtist(testArtist);

        // delete artist from db by id
        assertTrue(artistDao.deleteArtist(testArtist.getId()));

        // explicitly setting id null
        testArtist.setId(null);
    }

    /**
     * @see ArtistDao#deleteArtist(Long)
     */
    @Test(expected = DAOException.class)
    public void deleteArtist_Failure() {

        // add artist into db
        artistDao.addArtist(testArtist);

        // delete artist from db by id and check isDeleted
        assertFalse(artistDao.deleteArtist(-36L));
    }

    //endregion
}