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
    @Autowired
    private ShoppingCardRepo shoppingCardRepo;
    /**
     * @see ShoppingCardService#addShoppingCard(ShoppingCard)
     * @param shoppingCard
     */
    @Override
    public void addShoppingCard(ShoppingCard shoppingCard) {
        if (shoppingCard == null || !shoppingCard.isValidShoppingCard()){
            LOGGER.error(String.format("Shopping card is not valid: %s", shoppingCard));
            throw new InvalidEntryException("Invalid shoppingCard");
        }
        try {
            shoppingCardRepo.saveAndFlush(shoppingCard);
        }catch (DAOException e){
            String error = "Failed to add ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }

    /**
     * @see ShoppingCardService#getShoppingCard(Long)
     * @param id
     * @return
     */
    @Override
    public ShoppingCard getShoppingCard(Long id) {
        if (id == null || id < 0){
            LOGGER.error(String.format("Id is not valid: %s", id));
            throw new InvalidEntryException("Invalid id");
        }
        try {
            return shoppingCardRepo.findByAbstractUser_Id(id);
        }catch (DAOException e){
            String error = "Failed to get ShoppingCard";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @see ShoppingCardService#updateShoppingCard( ShoppingCard)
     * @param shoppingCard
     */
    @Override
    public void updateShoppingCard(ShoppingCard shoppingCard) {
        if (shoppingCard == null || !shoppingCard.isValidShoppingCard()){
            LOGGER.error(String.format("Shopping card is not valid: %s", shoppingCard));
            throw new InvalidEntryException("Invalid shoppingCard");
        }
        try {
            if (shoppingCardRepo.saveAndFlush(shoppingCard) == null) {
                throw new DAOException("Failed update shopping card");
            }
        }catch (DAOException e){
            String error = "Failed to update ShoppingCard";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @see ShoppingCardService#debitBalanceForItemBuying(Long, Double)
     * @param itemPrice
     * @param buyerId
     */
    @Override
    public void debitBalanceForItemBuying(Long buyerId,Double itemPrice){
        if(itemPrice== null || itemPrice<0 || buyerId==null||buyerId<0){
            LOGGER.error(String.format("buyerId or itemPrice is not valid: %s, %s", buyerId,itemPrice));
            throw new InvalidEntryException("Invalid id");
        }

        try{
            debitBalance(buyerId,itemPrice);
        }catch (NotEnoughMoneyException e){
            String error = "Failed to update ShoppingCard";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @see ShoppingCardService#deleteShoppingCard(ShoppingCard)
     * @param shoppingCard
     */
    @Override
    public void deleteShoppingCard(ShoppingCard shoppingCard) {
        if (shoppingCard == null || !shoppingCard.isValidShoppingCard()){
            LOGGER.error(String.format("Id is not valid: %s", shoppingCard));
            throw new InvalidEntryException("Invalid id");
        }
        try {

            shoppingCardRepo.delete(shoppingCard);

        }catch (DAOException e){
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

    public Boolean debitBalance(Long buyerId, Double itemPrice) {

        Boolean isEnoughBalance;
        ShoppingCard shoppingCard = getShoppingCard(buyerId);
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
            String error = "Not enough money on the account: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }


        return isEnoughBalance;
    }
}
