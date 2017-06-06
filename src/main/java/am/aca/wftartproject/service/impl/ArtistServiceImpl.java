package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.exception.ServiceException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.util.DBConnection;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by surik on 6/3/17
 */
public class ArtistServiceImpl implements ArtistService {

    private static final Logger LOGGER = Logger.getLogger(ArtistService.class);

    private Connection conn = new DBConnection().getDBConnection(DBConnection.DBType.REAL);
    private ArtistDao artistDao = new ArtistDaoImpl(conn);

    public ArtistServiceImpl() throws SQLException, ClassNotFoundException {
    }


    /**
     * @param artist
     *
     * @see ArtistService#addArtist(Artist)
     */
    @Override
    public void addArtist(Artist artist) {

        if (artist.getSpecialization() != null){

            try{
                artistDao.addArtist(artist);
            }catch (DAOException e){
                String error = "Failed to add Artist: %s";
                LOGGER.error(String.format(error, e.getMessage()));
                throw new ServiceException(String.format(error, e.getMessage()));
            }
        }

    }

    /**
     * @param id
     * @return
     *
     * @see ArtistService#findArtist(Long)
     */
    @Override
    public Artist findArtist(Long id) {
        try{
            return artistDao.findArtist(id);
        }catch (DAOException e){
            String error = "Failed to find Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }

    /**
     * @param email
     * @return
     *
     * @see ArtistService#findArtist(String)
     */
    @Override
    public Artist findArtist(String email) {
        try{
            return artistDao.findArtist(email);
        }catch (DAOException e){
            String error = "Failed to find Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }

    /**
     * @param id
     * @param artist
     *
     * @see ArtistService#updateArtist(Long, Artist)
     */
    @Override
    public void updateArtist(Long id, Artist artist) {
        try {
            artistDao.updateArtist(id, artist);
        }catch (DAOException e){
            String error = "Failed to update Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }

    /**
     * @param id
     *
     * @see ArtistService#deleteArtist(Long)
     */
    @Override
    public void deleteArtist(Long id) {
        try {
            artistDao.deleteArtist(id);
//        userDao.deleteUser(id);
        }catch (DAOException e){
            String error = "Failed to delete Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }
}
