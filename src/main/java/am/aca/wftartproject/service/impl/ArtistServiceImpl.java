package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.service.DuplicateEntryException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ShoppingCardService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

    private ArtistDao artistDao;
    private ShoppingCardService shoppingCardService;

    @Autowired
    public ArtistServiceImpl(ArtistDao artistDao) {
        this.artistDao = artistDao;
    }

    @Autowired
    public void setShoppingCardService(ShoppingCardService shoppingCardService) {
        this.shoppingCardService = shoppingCardService;
    }

    public void setArtistDao(ArtistDao artistDao) {
        this.artistDao = artistDao;
    }


    /**
     * @param artist
     * @see ArtistService#addArtist(Artist)
     */
    @Override
    @Transactional
    public void addArtist(Artist artist) {
        if (artist == null || !artist.isValidArtist() || !isValidEmailAddressForm(artist.getEmail())) {
            LOGGER.error(String.format("Artist is not valid: %s", artist));
            throw new InvalidEntryException("Invalid artist");
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
            shoppingCardService.addShoppingCard(artist.getId(),artist.getShoppingCard());
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
     * @param id
     * @param artist
     * @see ArtistService#updateArtist(Long, Artist)
     */
    @Override
    @Transactional
    public void updateArtist(Long id, Artist artist) {
        if (id == null || id < 0) {
            LOGGER.error(String.format("Id is not valid: %s", id));
            throw new InvalidEntryException("Invalid id");
        }
        if (artist == null || !artist.isValidArtist()) {
            LOGGER.error(String.format("Artist is not valid: %s", id));
            throw new InvalidEntryException("Invalid artist");
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
    @Transactional
    public void deleteArtist(Long id) {
        if (id == null || id < 0) {
            LOGGER.error(String.format("Id is not valid: %s", id));
            throw new InvalidEntryException("Invalid id");
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
