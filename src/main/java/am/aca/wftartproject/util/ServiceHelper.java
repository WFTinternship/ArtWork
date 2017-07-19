package am.aca.wftartproject.util;

import am.aca.wftartproject.entity.AbstractUser;
import am.aca.wftartproject.entity.Artist;
import am.aca.wftartproject.entity.Item;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.service.DuplicateEntryException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.repository.AbstractUserRepo;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import org.apache.log4j.Logger;

import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isEmptyString;
import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isValidEmailAddressForm;

/**
 * Created by Armen on 7/19/2017.
 */
public class ServiceHelper {
    private static final Logger LOGGER = Logger.getLogger(ArtistServiceImpl.class);

    public void findAbsUser(AbstractUserRepo abstractUserRepo, AbstractUser abstractUser) {
        if (abstractUserRepo.findByEmail(abstractUser.getEmail()) != null) {
            String error = "User has already exists";
            LOGGER.error(String.format("Failed to add User: %s: %s", error, abstractUser));
            throw new DuplicateEntryException(error);
        }
    }

    public void artistValidateAndProcess(Artist artist){
        if (artist == null || !artist.isValidArtist() || !isValidEmailAddressForm(artist.getEmail())) {
            String error = "Incorrect data or Empty fields ";
            LOGGER.error(String.format("Artist is not valid: %s", artist));
            throw new InvalidEntryException(error);
        }
    }

    public void emailAdnPasswordValidateAndProcess(String email, String password){
        if (isEmptyString(password) || isEmptyString(email)) {
            LOGGER.error(String.format("Email or password is not valid: %s , %s", email, password));
            throw new InvalidEntryException("Invalid Id");
        }
    }

    public void itemValidateAndProcess(Item item){
        if (item == null || !item.isValidItem()) {
            LOGGER.error(String.format("Item is not valid: %s", item));
            throw new InvalidEntryException("Invalid item");
        }
    }

    public void dbItemValidateAndProcess(Item item){
        if (item == null || item.getId() == null || !item.isValidItem()) {
            LOGGER.error(String.format("Item is not valid: %s", item));
            throw new InvalidEntryException("Invalid item");
        }
    }


    public void dbArtistValidateAndProcess(Artist artist){
        if (artist == null || !artist.isValidArtist() || artist.getId() == null || artist.getId() < 0) {
            String error = "Incorrect data or Empty fields ";
            LOGGER.error(String.format("Artist is not valid: %s", artist));
            throw new InvalidEntryException(error);
        }
    }

    public void idValidateAndProcess(Long id){
        if (id == null || id < 0) {
            LOGGER.error(String.format("Id is not valid: %s", id));
            throw new InvalidEntryException("Invalid id");
        }
    }

    public void emailValidateAndProcess(String email){
        if (isEmptyString(email) || !isValidEmailAddressForm(email)) {
            LOGGER.error(String.format("Email is not valid: %s", email));
            throw new InvalidEntryException("Invalid email");
        }
    }
}
