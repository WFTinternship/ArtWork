package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.repository.ShoppingCardRepo;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.dao.NotEnoughMoneyException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.entity.ShoppingCard;
import am.aca.wftartproject.service.ShoppingCardService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ASUS on 03-Jun-17
 */
@Service
public class ShoppingCardServiceImpl implements ShoppingCardService {

    private static final Logger LOGGER = Logger.getLogger(ShoppingCardServiceImpl.class);

    private ShoppingCardRepo shoppingCardRepo;

    @Autowired
    public void setShoppingCardRepo(ShoppingCardRepo shoppingCardRepo) {
        this.shoppingCardRepo = shoppingCardRepo;
    }

    /**
     * @param shoppingCard *
     * @see ShoppingCardService#addShoppingCard(ShoppingCard)
     */
    @Override
    public void addShoppingCard(ShoppingCard shoppingCard) {

        //check shoppingCard for validity
        if (shoppingCard == null || !shoppingCard.isValidShoppingCard()) {
            LOGGER.error(String.format("Shopping card is not valid: %s", shoppingCard));
            throw new InvalidEntryException("Invalid shoppingCard");
        }

        //try to save shoppingcCard into db
        try {
            shoppingCardRepo.saveAndFlush(shoppingCard);
        } catch (DAOException e) {
            String error = "Failed to add ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }

    /**
     * @param id*
     * @see ShoppingCardService#getShoppingCard(Long)
     */
    @Override
    public ShoppingCard getShoppingCard(Long id) {

        //check user id for validity
        if (id == null || id < 0) {
            LOGGER.error(String.format("Id is not valid: %s", id));
            throw new InvalidEntryException("Invalid id");
        }

        //try to find shoppingCard frpom db by user id
        try {
            return shoppingCardRepo.findByAbstractUser_Id(id);
        } catch (DAOException e) {
            String error = "Failed to get ShoppingCard";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param shoppingCard*
     * @see ShoppingCardService#updateShoppingCard(ShoppingCard)
     */
    @Override
    public void updateShoppingCard(ShoppingCard shoppingCard) {

        //check shoppingCard for validity
        if (shoppingCard == null || !shoppingCard.isValidShoppingCard()) {
            LOGGER.error(String.format("Shopping card is not valid: %s", shoppingCard));
            throw new InvalidEntryException("Invalid shoppingCard");
        }

        //try to update shoppingCard in db
        try {
            if (shoppingCardRepo.saveAndFlush(shoppingCard) == null) {
                throw new DAOException("Failed update shopping card");
            }
        } catch (DAOException e) {
            String error = "Failed to update ShoppingCard";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param itemPrice*
     * @param buyerId*
     * @see ShoppingCardService#debitBalanceForItemBuying(Long, Double)
     */
    @Override
    public void debitBalanceForItemBuying(Long buyerId, Double itemPrice) {

        //check item price and buyer id for validity
        if (itemPrice == null || itemPrice < 0 || buyerId == null || buyerId < 0) {
            LOGGER.error(String.format("buyerId or itemPrice is not valid: %s, %s", buyerId, itemPrice));
            throw new InvalidEntryException("Invalid id");
        }

        //debit balance for item buying
        try {
            debitBalance(buyerId, itemPrice);
        } catch (NotEnoughMoneyException e) {
            String error = "Failed to update ShoppingCard";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        } catch (ServiceException e) {
            String error = "Incorrect data";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param shoppingCard*
     * @see ShoppingCardService#deleteShoppingCard(ShoppingCard)
     */
    @Override
    public void deleteShoppingCard(ShoppingCard shoppingCard) {

        //check shoppingCard for validity
        if (shoppingCard == null || !shoppingCard.isValidShoppingCard()) {
            LOGGER.error(String.format("Id is not valid: %s", shoppingCard));
            throw new InvalidEntryException("Invalid id");
        }

        //try to delete shoppingCard
        try {
            shoppingCardRepo.delete(shoppingCard);
        } catch (DAOException e) {
            String error = "Failed to delete ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }

//    @Override
//    @Transactional
//    public void deleteShoppingCardByBuyerId(Long buyerId) {
//        if (buyerId == null || buyerId < 0){
//            LOGGER.error(String.format("BuyerId is not valid: %s", buyerId));
//            throw new InvalidEntryException("Invalid buyerId");
//        }
//        try {
//            if (!shoppingCardRepo.deleteShoppingCardByBuyerId(buyerId)){
//                throw new DAOException("Failed to delete shopping card by buyerId");
//            }
//        }catch (DAOException e){
//            String error = "Failed to delete ShoppingCard";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new ServiceException(String.format(error, e.getMessage()));
//        }
//    }

    private Boolean debitBalance(Long buyerId, Double itemPrice) {
        Boolean isEnoughBalance;

        //get shoppingCard from db by user id
        ShoppingCard shoppingCard = getShoppingCard(buyerId);

        //check if user has enough money , process item buying and update shoppingCard for balance
        try {
            if (shoppingCard.getBalance() >= itemPrice) {
                shoppingCard.setBalance(shoppingCard.getBalance() - itemPrice);
                updateShoppingCard(shoppingCard);
                isEnoughBalance = true;
            } else {
                throw new NotEnoughMoneyException("Not enough money on the account.");
            }
        } catch (NotEnoughMoneyException e) {
            String error = "Not enough money on the account: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new NotEnoughMoneyException(String.format(error, e.getMessage()));
        } catch (Exception e) {
            String error = "Incorrect Data: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }

        return isEnoughBalance;
    }
}
