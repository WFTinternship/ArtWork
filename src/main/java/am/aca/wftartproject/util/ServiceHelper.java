package am.aca.wftartproject.util;

import am.aca.wftartproject.entity.*;
import am.aca.wftartproject.exception.service.DuplicateEntryException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.repository.AbstractUserRepo;
import am.aca.wftartproject.service.PurchaseHistoryService;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import org.apache.log4j.Logger;

import java.util.Calendar;

import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isEmptyString;
import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isValidEmailAddressForm;

/**
 * Created by Armen on 7/19/2017
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

    public void checkArtistForValidity(Artist artist){
        if (artist == null || !artist.isValidArtist() || !isValidEmailAddressForm(artist.getEmail())) {
            String error = "Incorrect data or Empty fields ";
            LOGGER.error(String.format("Artist is not valid: %s", artist));
            throw new InvalidEntryException(error);
        }
    }

    public void checkEmailAndPasswordForValidity(String email, String password){
        if (isEmptyString(password) || isEmptyString(email)) {
            LOGGER.error(String.format("Email or password is not valid: %s , %s", email, password));
            throw new InvalidEntryException("Invalid Id");
        }
    }

    public void checkItemForValidity(Item item){
        if (item == null || !item.isValidItem()) {
            LOGGER.error(String.format("Item is not valid: %s", item));
            throw new InvalidEntryException("Invalid item");
        }
    }

    public void checkDbItemForValidity(Item item){
        if (item == null || item.getId() == null || !item.isValidItem()) {
            LOGGER.error(String.format("Item is not valid: %s", item));
            throw new InvalidEntryException("Invalid item");
        }
    }

    public void purchaseHistoryCreateAndSave(Item item, AbstractUser buyer, PurchaseHistoryService purchaseHistoryService){
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        purchaseHistory.setItem(item);
        purchaseHistory.setAbsUser(buyer);
        purchaseHistory.setPurchaseDate(Calendar.getInstance().getTime());
        purchaseHistoryService.addPurchase(purchaseHistory);
    }


    public void checkDbArtistForValidity(Artist artist){
        if (artist == null || !artist.isValidArtist() || artist.getId() == null || artist.getId() < 0) {
            String error = "Incorrect data or Empty fields ";
            LOGGER.error(String.format("Artist is not valid: %s", artist));
            throw new InvalidEntryException(error);
        }
    }

    public void checkIdForValidity(Long id){
        if (id == null || id < 0) {
            LOGGER.error(String.format("Id is not valid: %s", id));
            throw new InvalidEntryException("Invalid id");
        }
    }

    public void checkEmailForValidity(String email){
        if (isEmptyString(email) || !isValidEmailAddressForm(email)) {
            LOGGER.error(String.format("Email is not valid: %s", email));
            throw new InvalidEntryException("Invalid email");
        }
    }

    public void checkMinPriceAndMAxPriceForValidity(Double minPrice, Double maxPrice){
        if (minPrice == null || minPrice < 0 || maxPrice == null || maxPrice < 0) {
            LOGGER.error(String.format("price is not valid: %s , %s", minPrice, maxPrice));
            throw new InvalidEntryException("Invalid price");
        }
    }

    public void checkPurchaseHistoryForValidity(PurchaseHistory purchaseHistory){
        if (purchaseHistory == null || !purchaseHistory.isValidPurchaseHistory()) {
            LOGGER.error(String.format("PurchaseHistory is not valid: %s", purchaseHistory));
            throw new InvalidEntryException("Invalid purchaseHistory");
        }
    }

    public void checkShoppingCardForValidity(ShoppingCard shoppingCard){
        if (shoppingCard == null || !shoppingCard.isValidShoppingCard()) {
            LOGGER.error(String.format("Id is not valid: %s", shoppingCard));
            throw new InvalidEntryException("Invalid id");
        }
    }

    public void checkItemPriceAndBuyerIdForValidity(Double itemPrice, Long buyerId){
        if (itemPrice == null || itemPrice < 0 || buyerId == null || buyerId < 0) {
            LOGGER.error(String.format("buyerId or itemPrice is not valid: %s, %s", buyerId, itemPrice));
            throw new InvalidEntryException("Invalid id");
        }
    }
    public void checkUserForValidity(User user){
        if (user == null || !user.isValidUser() || !isValidEmailAddressForm(user.getEmail())) {
            String error = "Incorrect data or Empty fields ";
            LOGGER.error(String.format("Failed to add User: %s: %s", error, user));
            throw new InvalidEntryException(error);
        }
    }

    public void checkDbUserForValidity(User user){
        if (user == null || !user.isValidUser() || user.getId() == null || user.getId() < 0) {
            LOGGER.error(String.format("User is not valid: %s", user));
            throw new InvalidEntryException("Invalid user");
        }
    }
}
