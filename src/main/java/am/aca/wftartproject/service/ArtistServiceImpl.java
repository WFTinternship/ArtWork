package am.aca.wftartproject.service;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by ASUS on 30-May-17
 */
public class ArtistServiceImpl implements ArtistService {

    private Connection conn = new DBConnection().getDBConnection(DBConnection.DBType.REAL);
//    private UserDao userDao = new UserDaoImpl(conn);
    private ArtistDao artistDao = new ArtistDaoImpl(conn);

    public ArtistServiceImpl() throws SQLException, ClassNotFoundException {
    }


    @Override
    public void addArtist(Artist artist) throws SQLException {
        artistDao.addArtist(artist);
    }

    @Override
    public void updateArtist(Long id, Artist artist) {
        artistDao.updateArtist(id,artist);
    }

    @Override
    public void deleteArtist(Long id) {
        artistDao.deleteArtist(id);
//        userDao.deleteUser(id);
    }

    @Override
    public Artist findArtist(Long id) {
        return artistDao.findArtist(id);
    }

    @Override
    public Artist findArtist(String email) {
        return artistDao.findArtist(email);
    }
}
