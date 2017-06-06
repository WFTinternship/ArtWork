package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.service.ArtistService;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by ASUS on 30-May-17
 */
public class ArtistServiceImpl implements ArtistService {

    private static final Logger LOGGER = Logger.getLogger(ArtistDaoImpl.class);
    private Connection conn = null;
    private ArtistDao artistDao = null;

    public ArtistServiceImpl() throws SQLException, ClassNotFoundException {
//        Connection conn = new dbconnection().getConnection(ConnectionModel.BASIC).getProductionDBConnection();
        artistDao = new ArtistDaoImpl(conn);
    }



    /**
     * @param artist
     * @see ArtistService#addArtist(Artist)
     */
    @Override
    public void addArtist(Artist artist) {
        artistDao.addArtist(artist);
    }


    /**
     * @param id
     * @return
     * @see ArtistService#findArtist(Long)
     */
    @Override
    public Artist findArtist(Long id) {
        return artistDao.findArtist(id);
    }


    /**
     * @param email
     * @return
     * @see ArtistService#findArtist(String)
     */
    @Override
    public Artist findArtist(String email) {
        return artistDao.findArtist(email);
    }



    /**
     * @param id
     * @param artist
     * @see ArtistService#updateArtist(Long, Artist)
     */
    @Override
    public void updateArtist(Long id, Artist artist) {
        artistDao.updateArtist(id, artist);
    }



    /**
     * @param id
     * @see ArtistService#deleteArtist(Long)
     */
    @Override
    public void deleteArtist(Long id) {
        artistDao.deleteArtist(id);
    }


}
