package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.repository.AbstractUserRepo;
import am.aca.wftartproject.repository.ArtistRepo;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.entity.Artist;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.util.HashGenerator;
import am.aca.wftartproject.util.ServiceHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isEmptyString;
import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isValidEmailAddressForm;

@Service
public class ArtistServiceImpl extends ServiceHelper implements ArtistService {
    private static final Logger LOGGER = Logger.getLogger(ArtistServiceImpl.class);

    private ArtistRepo artistRepo;

    private AbstractUserRepo abstractUserRepo;

    @Autowired
    public void setAbstractUserRepo(AbstractUserRepo abstractUserRepo) {
        this.abstractUserRepo = abstractUserRepo;
    }


    @Autowired
    public void setArtistRepo(ArtistRepo artistRepo) {
        this.artistRepo = artistRepo;
    }

    /**
     * @param artist*
     * @see ArtistService#addArtist(Artist)
     */

    @Override
    public void addArtist(Artist artist) {

        // check artist validity
        artistValidateAndProcess(artist);

        //find user from db and if exist throw an exception
        findAbsUser(abstractUserRepo, artist);

        //encrypt artist password and save into db
        try {
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
     * @param id*
     * @see ArtistService#findArtist(Long)
     */
    @Override
    public Artist findArtist(Long id) {

        //check id for validity
        idValidateAndProcess(id);

        //get artist from db if exist, by id
        try {
            return artistRepo.findOne(id);
        } catch (DAOException e) {
            String error = "Failed to find Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param email the email
     * @see ArtistService#findArtist(String)
     */
    @Override
    public Artist findArtist(String email) {

        //check email for validity
        emailValidateAndProcess(email);

        //find artist from db by email
        try {
            return artistRepo.findArtistByEmail(email);
        } catch (DAOException e) {
            String error = "Failed to find Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param artist the user type
     * @see ArtistService#updateArtist(Artist)
     */

    @Override
    public void updateArtist(Artist artist) {

        //check artist validity
        dbArtistValidateAndProcess(artist);

        //encrypt artist password and update
        try {
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
     * LogIn for Artist
     *
     * @param email    the email
     * @param password the password
     * @see ArtistService#login(String, String)
     */

    public Artist login(String email, String password) {
        Artist artist;

        //check email and password for validity
        emailAdnPasswordValidateAndProcess(email, password);

        //try to find an artist by email, check passwords equality
        try {
            Artist user = artistRepo.findArtistByEmail(email);
            String hashedPassword = HashGenerator.generateHashString(password);
            if (user != null && user.getPassword().equals(hashedPassword)) {
                artist = user;
            } else throw new DAOException("Specified username or password is incorrect");
        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return artist;
    }


    /**
     * @see ArtistService#deleteArtist(Artist)
     */

    @Override
    public void deleteArtist(Artist artist) {

        //check artist for validity
        dbArtistValidateAndProcess(artist);

        //try to delete artist from db
        try {
            artistRepo.delete(artist);
        } catch (DAOException e) {
            String error = "Failed to delete Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


}
