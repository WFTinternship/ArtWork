package integration.dao;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.util.dbconnection.ConnectionFactory;
import am.aca.wftartproject.util.dbconnection.ConnectionModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.AssertTemplates;
import util.TestObjectTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

import static junit.framework.TestCase.*;

/**
 * Created by Armen on 6/1/2017.
 */
public class ArtistDaoIntegrationTest {
    //testArtist and artistDaoImplementation

    private Artist testArtist;

    private ArtistDao artistDao;


    public ArtistDaoIntegrationTest() throws SQLException, ClassNotFoundException {
    }

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        //create db connection,artistDaoImplementation and artist for testing

        DataSource conn = new ConnectionFactory()
                .getConnection(ConnectionModel.POOL)
                .getTestDBConnection();

        artistDao = new ArtistDaoImpl(conn);

        testArtist = TestObjectTemplate.createTestArtist();

    }

    /**
     * @see ArtistDao#addArtist(Artist)
     */
    @Test
    public void addArtist_Success() throws SQLException {
        //set artist into db, get generated id

        artistDao.addArtist(testArtist);
        System.out.println(testArtist);

        //check testartist object and testartist id for null

        assertNotNull(testArtist);
        assertNotNull(testArtist.getId());

        //get artist by id from db

        Long id = testArtist.getId();
        System.out.println(id);
        Artist addedArtist = artistDao.findArtist(id);

        //check for equals

        AssertTemplates.assertEqualArtists(testArtist, addedArtist);

    }

    /**
     * @see ArtistDao#addArtist(Artist)
     */
    @Test(expected = DAOException.class)
    public void addArtist_Failure() throws SQLException {

        //set artist into db, get generated id

        testArtist.setFirstName(null);
        artistDao.addArtist(testArtist);
        System.out.println(testArtist);

        //check testartist object and testartist id for null

        assertNotNull(testArtist);
        assertNotNull(testArtist.getId());

        //get artist by id from db

        Long id = testArtist.getId();
        System.out.println(id);
        Artist addedArtist = artistDao.findArtist(id);

        //check for equals

        assertEquals(testArtist, addedArtist);

    }

    /**
     * @see ArtistDao#updateArtist(Long, Artist)
     */
    @Test
    public void updateArtist_success() {
        //set artist specialization  and add into db

        testArtist.setSpecialization(ArtistSpecialization.PAINTER);
        artistDao.addArtist(testArtist);
        testArtist.setSpecialization(ArtistSpecialization.OTHER);
        System.out.println(testArtist.getSpecialization().getId());

        //update artists specialization field in db, get from db and check for sameness

        artistDao.updateArtist(testArtist.getId(), testArtist);
        Artist updated = artistDao.findArtist(testArtist.getId());
        System.out.println(updated.getSpecialization().getId());
        System.out.println(testArtist.getSpecialization().getId());
        assertEquals(updated.getSpecialization().getId(), testArtist.getSpecialization().getId());

    }

    /**
     * @see ArtistDao#updateArtist(Long, Artist)
     */
    @Test(expected = DAOException.class)
    public void updateArtist_failure() {
        //set artist specialization  and add into db

        artistDao.addArtist(testArtist);
        testArtist.setSpecialization(ArtistSpecialization.OTHER);


        //update artists specialization field in db, get from db

        testArtist.setFirstName(null);
        artistDao.updateArtist(testArtist.getId(), testArtist);
        Artist updated = artistDao.findArtist(testArtist.getId());

        //check for sameness

        assertEquals(updated.getSpecialization().getId(), ArtistSpecialization.OTHER.getId());

    }

    /**
     * @see ArtistDao#deleteArtist(Long)
     */
    @Test
    public void deleteArtist_success() {
        //add artist into db

        artistDao.addArtist(testArtist);

        //delete artist from db by id


        assertTrue(artistDao.deleteArtist(testArtist.getId()));

        //check isdeleted

        Artist deleted = artistDao.findArtist(testArtist.getId());
        assertNull(deleted);
        testArtist.setId(null);

    }

    /**
     * @see ArtistDao#deleteArtist(Long)
     */
    @Test
    public void deleteArtist_Failure() {
        //add artist into db

        artistDao.addArtist(testArtist);

        //delete artist from db by id

        assertFalse(artistDao.deleteArtist(-36L));

        //check isdeleted


    }

    /**
     * @see ArtistDao#findArtist(Long)
     */
    @Test
    public void findArtist_success() {
        artistDao.addArtist(testArtist);

        //find and get artist from db

        Artist findArtist = artistDao.findArtist(testArtist.getId());
        System.out.println(findArtist);

        AssertTemplates.assertEqualArtists(findArtist, testArtist);

        //check for sameness with testartist

    }

    /**
     * @see ArtistDao#findArtist(Long)
     */
    @Test
    public void findArtist_failure() {
        artistDao.addArtist(testArtist);


        //find and get artist from db

        Artist findArtist = artistDao.findArtist(-8L);


        assertNull(findArtist);

        //check for sameness with testartist

    }

    @After
    public void tearDown() {
        //delete inserted test users and artists from db

        if (testArtist.getId() != null) {
            artistDao.deleteArtist(testArtist.getId());
        }


        //set null test artist  object

        testArtist = null;

    }
}