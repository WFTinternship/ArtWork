package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.dao.ShoppingCardDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.service.DuplicateEntryException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.service.ArtistService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isEmptyString;
import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isValidEmailAddressForm;

/**
 * Created by surik on 6/3/17
 */
@Service
@Transactional(readOnly = true)
public class ArtistServiceImpl implements ArtistService {
    private static final Logger LOGGER = Logger.getLogger(ArtistServiceImpl.class);

    @Autowired
    private final ArtistDao artistDao;

    @Autowired
    public ArtistServiceImpl(ArtistDao artistDao) {
        this.artistDao = artistDao;
    }
    @Autowired
    private ShoppingCardDao shoppingCardDao;

    @Autowired
    public void setShoppingCardDao(ShoppingCardDao shoppingCardDao) {
        this.shoppingCardDao = shoppingCardDao;
    }

    /**
     * @param artist
     * @see ArtistService#addArtist(Artist)
     */

    @Override
    @Transactional(readOnly = false)
    public void addArtist(Artist artist) {
        if (artist == null || !artist.isValidArtist() || !isValidEmailAddressForm(artist.getEmail())) {
            String error = "Incorrect data or Empty fields ";
            LOGGER.error(String.format("Artist is not valid: %s", artist));
            throw new InvalidEntryException(error);
        }
        if (artistDao.findArtist(artist.getEmail()) != null) {
            String error = "User has already exists";
            LOGGER.error(String.format("Failed to add User: %s: %s", error, artist));
            throw new DuplicateEntryException(error);
        }

        try {
            artistDao.addArtist(artist);
        } catch (DAOException e) {
            String error = "Failed to add Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }

        try {
            artist.getShoppingCard().setBuyer_id(artist.getId());
            shoppingCardDao.addShoppingCard(artist.getId(),artist.getShoppingCard());
        } catch (DAOException e) {
            String error = "Failed to add ShoppingCard: %s";
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
            LOGGER.error(String.format("Id is not valid: %s", id));
            throw new InvalidEntryException("Invalid id");
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
        if (isEmptyString(email) || !isValidEmailAddressForm(email)) {
            LOGGER.error(String.format("Email is not valid: %s", email));
            throw new InvalidEntryException("Invalid email");
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
     * @param artist
     * @see ArtistService#updateArtist(Artist)
     */

    @Override
    @Transactional(readOnly = false)
    public void updateArtist( Artist artist) {

        if (artist == null || !artist.isValidArtist()) {
            LOGGER.error(String.format("Artist is not valid: %s", artist.getId()));
            throw new InvalidEntryException("Invalid artist");
        }

        try {
            artistDao.updateArtist(artist);
        } catch (DAOException e) {
            String error = "Failed to update Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @see ArtistService#deleteArtist(Artist)
     */
    @Transactional(readOnly = false)
    @Override
    public void deleteArtist(Artist artist) {
        if (!artist.isValidArtist()) {
            LOGGER.error(String.format("Artist is not valid: %s", artist));
            throw new InvalidEntryException("Invalid id");
        }

        try {
            artistDao.deleteArtist(artist);
        } catch (DAOException e) {
            String error = "Failed to delete Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }
    /**
     * LogIn for Artist
     *
     * @param email
     * @param password
     * @see ArtistService#login(String, String)
     */
    @Transactional(readOnly = false)
    public Artist login(String email, String password) {
        Artist artist = null;
        if (isEmptyString(password) || isEmptyString(email)) {
            LOGGER.error(String.format("Email or password is not valid: %s , %s", email, password));
            throw new InvalidEntryException("Invalid Id");
        }

        try {
            Artist user = artistDao.findArtist(email);
            if (user != null && user.getPassword().equals(password)) {
                artist = user;
            }
        } catch (DAOException e) {
            String error = "Failed to find User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        } catch (RuntimeException e) {
            String error = "Failed to login: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
        return artist;
    }
}
