package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.exception.ServiceException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.service.ArtistService;
import org.apache.log4j.Logger;

import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isEmptyString;

/**
 * Created by surik on 6/3/17
 */
public class ArtistServiceImpl implements ArtistService {

    private static final Logger LOGGER = Logger.getLogger(ArtistServiceImpl.class);

    private ArtistDao artistDao;

    public void setArtistDao(ArtistDao artistDao) {
        this.artistDao = artistDao;
    }

//        public ArtistServiceImpl() throws SQLException, ClassNotFoundException {
//        DataSource conn = new ConnectionFactory().getConnection(ConnectionModel.POOL).getProductionDBConnection();
//        artistDao = new ArtistDaoImpl(conn);
//    }


    /**
     * @see ArtistService#addArtist(Artist)
     * @param artist
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
     * @see ArtistService#findArtist(Long)
     * @param id
     * @return
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
     * @see ArtistService#findArtist(String)
     * @param email
     * @return
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
     * @see ArtistService#updateArtist(Long, Artist)
     * @param id
     * @param artist
     */
    @Override
    public void updateArtist(Long id, Artist artist) {
        if (id == null || id < 0) {
            LOGGER.error(String.format("Id is invalid: %s", id));
            throw new ServiceException("Invalid id");
        }
        if (artist == null || !artist.isValidArtist()) {
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
     * @see ArtistService#deleteArtist(Long)
     * @param id
     */
    @Override
    public void deleteArtist(Long id) {
        if (id == null || id < 0) {
            LOGGER.error(String.format("Id is invalid: %s", id));
            throw new ServiceException("Invalid id");
        }

        try {
            artistDao.deleteArtist(id);
        } catch (DAOException e) {
            String error = "Failed to delete Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }
}
