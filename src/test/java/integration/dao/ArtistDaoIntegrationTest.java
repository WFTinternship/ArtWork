package integration.dao;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.dao.impl.UserDaoImpl;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.model.User;
import am.aca.wftartproject.util.DBConnection;
import integration.service.TestObjectTemplate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static integration.service.AssertTemplates.assertEqualArtists;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;


import java.sql.SQLException;

/**
 * Created by Armen on 6/1/2017
 */
public class ArtistDaoIntegrationTest {

    private DBConnection connection = new DBConnection();
    private ArtistDaoImpl artistDao = new ArtistDaoImpl(connection.getDBConnection(DBConnection.DBType.TEST));
    private UserDaoImpl userDao = new UserDaoImpl(connection.getDBConnection(DBConnection.DBType.TEST));
    private Artist testArtist;
    private User testUser;

    public ArtistDaoIntegrationTest() throws SQLException, ClassNotFoundException {
    }

    @Before
    public void setUp() {
        //create test user and test artist
        testUser = TestObjectTemplate.createTestUser();
        testArtist = new Artist();
        testArtist.setSpecialization(ArtistSpecialization.PAINTER);
        //insert test user into db, get generated id
        userDao.addUser(testUser);
        //set user field with generated user_id into artist obj
        testArtist.setId(null);
    }

    /**
     * @see ArtistDao#addArtist(Artist)
     */
    @Test
    public void addArtist_Success() {

        //check testArtist object for null
        assertNotNull(testArtist);

        //set artist into db, get generated id
        artistDao.addArtist(testArtist);

        //check testArtist id for null
        assertNotNull(testArtist.getId());

        //get artist by id from db
        Long id = testArtist.getId();
        Artist addedArtist = artistDao.findArtist(id);

        //check for equals
        assertEqualArtists(testArtist, addedArtist);
    }

    /**
     * @see ArtistDao#findArtist(Long)
     */
    @Test
    public void findArtist_success() {

        //find and get artist from db

        Artist findArtist = artistDao.findArtist(testArtist.getId());

        //check for sameness with testArtist

        assertEqualArtists(findArtist, testArtist);
    }

    /**
     * @see ArtistDao#updateArtist(Long, Artist) )
     */
    @Test
    public void updateArtist_success() {
        //set artist specialization  and add into db

        testArtist.setSpecialization(ArtistSpecialization.OTHER);
        artistDao.addArtist(testArtist);

        //update artists specialization field in db, get from db and check for sameness

        artistDao.updateArtist(testArtist.getId(), testArtist);
        Artist updated = artistDao.findArtist(testArtist.getId());
        assertNotSame(updated.getSpecialization(), testArtist.getSpecialization());

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

        //check is deleted
        Artist deleted = artistDao.findArtist(testArtist.getId());
        assertNull(deleted);
        testArtist.setId(null);

    }

    @After
    public void tearDown() {
        //delete inserted test users and artists from db

        if (testArtist.getId() != null)
            artistDao.deleteArtist(testArtist.getId());
        if (testUser.getId() != null)
            userDao.deleteUser(testUser.getId());

        //set null test artist  object
        testArtist = null;


        //set null test user object
        testUser = null;
    }
}