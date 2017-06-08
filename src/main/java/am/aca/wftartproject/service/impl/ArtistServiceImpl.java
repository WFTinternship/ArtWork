package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.exception.ServiceException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.util.dbconnection.ConnectionFactory;
import am.aca.wftartproject.util.dbconnection.ConnectionModel;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.SQLException;

import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isEmptyString;

/**
 * Created by surik on 6/3/17
 */
public class ArtistServiceImpl implements ArtistService {

    private static final Logger LOGGER = Logger.getLogger(ArtistServiceImpl.class);

    private DataSource conn;
    private ArtistDao artistDao;

    public ArtistServiceImpl() throws SQLException, ClassNotFoundException {
        conn = new ConnectionFactory().getConnection(ConnectionModel.POOL).getProductionDBConnection();
        artistDao = new ArtistDaoImpl(conn);
    }


    /**
     * @param artist
     * @see ArtistService#addArtist(Artist)
     */
    @Override
    public void addArtist(Artist artist) {

        if (artist == null || !artist.isValidArtist()) {
            LOGGER.error(String.format("Artist is invalid: %s", artist));
            throw new ServiceException("Invalid artist");
        }

        try {
            artistDao.addArtist(artist);
        } catch (DAOException e) {
            String error = "Failed to add Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }


    }

    /**
     * @param id
     * @return
     * @see ArtistService#findArtist(Long)
     */
    @Override
    public Artist findArtist(Long id) {
        if (id == null || id < 0) {
            LOGGER.error(String.format("Id is invalid: %s", id));
            throw new ServiceException("Invalid id");
        }

        try {
            return artistDao.findArtist(id);
        } catch (DAOException e) {
            String error = "Failed to find Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }

    /**
     * @param email
     * @return
     * @see ArtistService#findArtist(String)
     */
    @Override
    public Artist findArtist(String email) {
        if (isEmptyString(email)) {
            LOGGER.error(String.format("Email is invalid: %s", email));
            throw new ServiceException("Invalid email");
        }

        try {
            return artistDao.findArtist(email);
        } catch (DAOException e) {
            String error = "Failed to find Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }

    /**
     * @param id
     * @param artist
     * @see ArtistService#updateArtist(Long, Artist)
     */
    @Override
    public void updateArtist(Long id, Artist artist) {
        if (id == null || id < 0){
            LOGGER.error(String.format("Id is invalid: %s", id));
            throw new ServiceException("Invalid id");
        }
        if (artist == null || !artist.isValidArtist()){
            LOGGER.error(String.format("Artist is invalid: %s", id));
            throw new ServiceException("Invalid artist");
        }

        try {
            artistDao.updateArtist(id, artist);
        } catch (DAOException e) {
            String error = "Failed to update Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }

    /**
     * @param id
     * @see ArtistService#deleteArtist(Long)
     */
    @Override
    public void deleteArtist(Long id) {
        if (id == null || id < 0){
            LOGGER.error(String.format("Id is invalid: %s", id));
            throw new ServiceException("Invalid id");
        }

        try {
            artistDao.deleteArtist(id);
//        userDao.deleteUser(id);
        } catch (DAOException e) {
            String error = "Failed to delete Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }
}
