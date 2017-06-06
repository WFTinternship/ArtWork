package integration.dao;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.util.DBConnection;
import integration.service.AssertTemplates;
import integration.service.TestObjectTemplate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotSame;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;


import java.sql.SQLException;

/**
 * Created by Armen on 6/1/2017
 */
public class ArtistDaoIntegrationTest {
    //create connection,testArtist and artistDaoImplementation

    private DBConnection connection;

    private Artist testArtist;

    private ArtistDao artistDao;


    public ArtistDaoIntegrationTest() throws SQLException, ClassNotFoundException {
        connection = null;
    }

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        //create db connection,artistDaoImplementation and artist for testing
        connection = new DBConnection();

        artistDao = new ArtistDaoImpl(connection.getDBConnection(DBConnection.DBType.TEST));

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

        //check testArtist object and testArtist id for null

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
        System.out.println(testArtist.getSpecialization().getSpecId());

        //update artists specialization field in db, get from db and check for sameness

        artistDao.updateArtist(testArtist.getId(), testArtist);
        Artist updated = artistDao.findArtist(testArtist.getId());
        System.out.println(updated.getSpecialization().getSpecId());
        System.out.println(testArtist.getSpecialization().getSpecId());
        assertEquals(updated.getSpecialization().getSpecId(), testArtist.getSpecialization().getSpecId());

    }

    /**
     * @see ArtistDao#updateArtist(Long, Artist)
     */
    @Test(expected = NullPointerException.class)
    public void updateArtist_failure() {
        //set artist specialization  and add into db

        testArtist.setSpecialization(null);
        artistDao.addArtist(testArtist);
        testArtist.setSpecialization(ArtistSpecialization.OTHER);
        System.out.println(testArtist.getSpecialization().getSpecId());

        //update artists specialization field in db, get from db

        artistDao.updateArtist(testArtist.getId(), testArtist);
        Artist updated = artistDao.findArtist(testArtist.getId());
        System.out.println(updated.getSpecialization().getSpecId());
        System.out.println(testArtist.getSpecialization().getSpecId());

        //check for sameness

        assertNotSame(updated.getSpecialization().getSpecId(), testArtist.getSpecialization().getSpecId());

    }

    /**
     * @see ArtistDao#deleteArtist(Long)
     */
    @Test
    public void deleteArtist_success() {
        //add artist into db

        artistDao.addArtist(testArtist);

        //delete artist from db by id

        artistDao.deleteArtist(testArtist.getId());

        //check isdeleted

        Artist deleted = artistDao.findArtist(testArtist.getId());
        assertNull(deleted.getId());
        testArtist.setId(null);

    }

    /**
     * @see ArtistDao#findArtist(Long)
     */
    @Test
    public void findArtist_success() {
        artistDao.addArtist(testArtist);

        //find and get artist from db

        Artist findArtist = artistDao.findArtist(testArtist.getId());

        AssertTemplates.assertEqualArtists(findArtist, testArtist);

        //check for sameness with testartist

    }

    /**
     * @see ArtistDao#findArtist(Long)
     */
    @Test
    public void findArtist_failure() {
        artistDao.addArtist(testArtist);

        testArtist.setFirstName("lll");

        //find and get artist from db

        Artist findArtist = artistDao.findArtist(testArtist.getId());

        assertNotSame(findArtist.getFirstName(), testArtist.getFirstName());

        //check for sameness with testartist

    }

    @After
    public void tearDown() {
        //delete inserted test users and artists from db

        if (testArtist.getId() != null)

            artistDao.deleteArtist(testArtist.getId());

        //set null test artist  object

        testArtist = null;

    }
}