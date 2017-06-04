package am.aca.wftartproject.service;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.dao.UserDao;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.dao.impl.UserDaoImpl;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by surik on 6/3/17
 */
public class ArtistServiceImpl implements ArtistService {

    Connection conn = new DBConnection().getDBConnection(DBConnection.DBType.REAL);
    UserDao userDao = new UserDaoImpl(conn);
    ArtistDao artistDao = new ArtistDaoImpl(conn);

    public ArtistServiceImpl() throws SQLException, ClassNotFoundException {
    }

    /**
     * @param artist
     *
     * @see ArtistService#addArtist(Artist)
     */
    @Override
    public void addArtist(Artist artist) {
        artistDao.addArtist(artist);
    }

    /**
     * @param id
     * @return
     *
     * @see ArtistService#findArtist(Long)
     */
    @Override
    public Artist findArtist(Long id) {
        return artistDao.findArtist(id);
    }

    /**
     * @param email
     * @return
     *
     * @see ArtistService#findArtist(String)
     */
    @Override
    public Artist findArtist(String email) {
        return artistDao.findArtist(email);
    }

    /**
     * @param id
     * @param artist
     *
     * @see ArtistService#updateArtist(Long, Artist)
     */
    @Override
    public void updateArtist(Long id, Artist artist) {
        artistDao.updateArtist(id, artist);
    }

    /**
     * @param id
     *
     * @see ArtistService#deleteArtist(Long)
     */
    @Override
    public void deleteArtist(Long id) {
        artistDao.deleteArtist(id);
//        userDao.deleteUser(id);
    }
}
