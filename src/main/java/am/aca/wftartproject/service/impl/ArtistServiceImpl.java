package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.repository.AbstractUserRepo;
import am.aca.wftartproject.repository.ArtistRepo;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.service.DuplicateEntryException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.entity.Artist;
import am.aca.wftartproject.repository.UserRepo;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.util.HashGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isEmptyString;
import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isValidEmailAddressForm;

@Service
public class ArtistServiceImpl implements ArtistService {
    private static final Logger LOGGER = Logger.getLogger(ArtistServiceImpl.class);

    @Autowired
    private ArtistRepo artistRepo;
    @Autowired
    private AbstractUserRepo abstractUserRepo;
    @Autowired
    private UserRepo userRepo;

    /**
     * @param artist
     * @see ArtistService#addArtist(Artist)
     */

    @Override
    public void addArtist(Artist artist) {
        if (artist == null || !artist.isValidArtist() || !isValidEmailAddressForm(artist.getEmail())) {
            String error = "Incorrect data or Empty fields ";
            LOGGER.error(String.format("Artist is not valid: %s", artist));
            throw new InvalidEntryException(error);
        }
        try{
            if (abstractUserRepo.findByEmail(artist.getEmail()) != null) {
                String error = "User has already exists";
                LOGGER.error(String.format("Failed to add User: %s: %s", error, artist));
                throw new DuplicateEntryException(error);
            }
        }catch (DAOException e){
        }
        try {
            //encrypt artist password and save into db
            String encryptedPassword = HashGenerator.generateHashString(artist.getPassword());
            artist.setPassword(encryptedPassword);
            artistRepo.saveAndFlush(artist);
        } catch (RuntimeException e) {
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
            return artistRepo.findOne(id);
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
            return artistRepo.findArtistByEmail(email);
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
    public void updateArtist( Artist artist) {

        if (artist == null || !artist.isValidArtist() || artist.getId() == null || artist.getId()<0) {
            LOGGER.error(String.format("Artist is not valid"));
            throw new InvalidEntryException("Invalid artist");
        }

        try {
            //encrypt artist password and update
            String encryptedPassword = HashGenerator.generateHashString(artist.getPassword());
            artist.setPassword(encryptedPassword);
            artistRepo.saveAndFlush(artist);
        } catch (DAOException e) {
            String error = "Failed to update Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @see ArtistService#deleteArtist(Artist)
     */

    @Override
    public void deleteArtist(Artist artist) {
        if (artist == null || !artist.isValidArtist() || artist.getId() == null || artist.getId()<0) {
            LOGGER.error(String.format("Artist is not valid: %s", artist));
            throw new InvalidEntryException("Invalid id");
        }

        try {
            artistRepo.delete(artist);
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

    public Artist login(String email, String password) {
        Artist artist = null;
        if (isEmptyString(password) || isEmptyString(email)) {
            LOGGER.error(String.format("Email or password is not valid: %s , %s", email, password));
            throw new InvalidEntryException("Invalid Id");
        }

        try {
            Artist user = artistRepo.findArtistByEmail(email);
            String hashedPassword = HashGenerator.generateHashString(password);
            if (user != null && user.getPassword().equals(hashedPassword)) {
                artist = user;
            }
            else throw new DAOException("");
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
