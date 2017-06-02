package am.aca.wftartproject.service;

import am.aca.wftartproject.dao.*;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.dao.impl.UserDaoImpl;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by ASUS on 30-May-17.
 */
public class ArtistServiceImpl implements ArtistService {

    Connection conn = new DBConnection().getDBConnection(DBConnection.DBType.REAL);
    UserDao userDao = new UserDaoImpl(conn);
    ArtistDao artistDao = new ArtistDaoImpl(conn);

    public ArtistServiceImpl() throws SQLException, ClassNotFoundException {
    }


    @Override
    public void addArtist(Artist artist) {
        artistDao.addArtist(artist);
    }

    @Override
    public void updateArtist(Long id, String specialization) {
        artistDao.updateArtist(id,specialization);
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
