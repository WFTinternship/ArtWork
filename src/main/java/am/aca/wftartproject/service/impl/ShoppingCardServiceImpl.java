package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.dao.ShoppingCardDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.dao.NotEnoughMoneyException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.ShoppingCard;
import am.aca.wftartproject.service.ShoppingCardService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ASUS on 03-Jun-17
 */
@Service
@Transactional(readOnly = true)
public class ShoppingCardServiceImpl implements ShoppingCardService {

    private static final Logger LOGGER = Logger.getLogger(ShoppingCardServiceImpl.class);

    private ShoppingCardDao shoppingCardDao;

    @Autowired
    public ShoppingCardServiceImpl(ShoppingCardDao shoppingCardDao) {
        this.shoppingCardDao = shoppingCardDao;
    }

    public void setShoppingCardDao(ShoppingCardDao shoppingCardDao) {
        this.shoppingCardDao = shoppingCardDao;
    }

    /**
     * @see ShoppingCardService#addShoppingCard(Long, ShoppingCard)
     * @param userId
     * @param shoppingCard
     */
    @Override
    @Transactional
    public void addShoppingCard(Long userId, ShoppingCard shoppingCard) {
        if (userId == null || userId < 0){
            LOGGER.error(String.format("UserId is not valid: %s", userId));
            throw new InvalidEntryException("Invalid userId");
        }
        if (shoppingCard == null || !shoppingCard.isValidShoppingCard()){
            LOGGER.error(String.format("Shopping card is not valid: %s", shoppingCard));
            throw new InvalidEntryException("Invalid shoppingCard");
        }
        try {
            shoppingCardDao.addShoppingCard(userId, shoppingCard);
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
            return shoppingCardDao.getShoppingCard(id);
        }catch (DAOException e){
            String error = "Failed to get ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }

    /**
     * @see ShoppingCardService#getShoppingCardByBuyerId(Long)
     * @param buyerId
     * @return
     */
    @Override
    public ShoppingCard getShoppingCardByBuyerId(Long buyerId) {
        if (buyerId == null || buyerId < 0){
            LOGGER.error(String.format("BuyerId is not valid: %s", buyerId));
            throw new InvalidEntryException("Invalid buyerId");
        }
        try {
            return shoppingCardDao.getShoppingCardByBuyerId(buyerId);
        }catch (DAOException e){
            String error = "Failed to get ShoppingCard by buyerID: %s";
            LOGGER.error(String.format(error, buyerId, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @see ShoppingCardService#updateShoppingCard(Long, ShoppingCard)
     * @param id
     * @param shoppingCard
     */
    @Override
    @Transactional
    public void updateShoppingCard(Long id, ShoppingCard shoppingCard) {
        if (id == null || id < 0){
            LOGGER.error(String.format("Id is not valid: %s", id));
            throw new InvalidEntryException("Invalid id");
        }
        if (shoppingCard == null || !shoppingCard.isValidShoppingCard()){
            LOGGER.error(String.format("Shopping card is not valid: %s", shoppingCard));
            throw new InvalidEntryException("Invalid shoppingCard");
        }
        try {
            if (!shoppingCardDao.updateShoppingCard(id, shoppingCard)) {
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
    @Transactional
    public void debitBalanceForItemBuying(Long buyerId,Double itemPrice){
        if(itemPrice== null || itemPrice<0 || buyerId==null||buyerId<0){
            LOGGER.error(String.format("buyerId or itemPrice is not valid: %s, %s", buyerId,itemPrice));
            throw new InvalidEntryException("Invalid id");
        }

        try{
            shoppingCardDao.debitBalanceForItemBuying(buyerId,itemPrice);
        }catch (NotEnoughMoneyException e){
            String error = "Failed to update ShoppingCard";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @see ShoppingCardService#deleteShoppingCard(Long)
     * @param id
     */
    @Override
    @Transactional
    public void deleteShoppingCard(Long id) {
        if (id == null || id < 0){
            LOGGER.error(String.format("Id is not valid: %s", id));
            throw new InvalidEntryException("Invalid id");
        }
        try {
            if (!shoppingCardDao.deleteShoppingCard(id)){
                throw new DAOException("Failed to delete shopping card");
            }
        }catch (DAOException e){
            String error = "Failed to delete ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }

    @Override
    @Transactional
    public void deleteShoppingCardByBuyerId(Long buyerId) {
        if (buyerId == null || buyerId < 0){
            LOGGER.error(String.format("BuyerId is not valid: %s", buyerId));
            throw new InvalidEntryException("Invalid buyerId");
        }
        try {
            if (!shoppingCardDao.deleteShoppingCardByBuyerId(buyerId)){
                throw new DAOException("Failed to delete shopping card by buyerId");
            }
        }catch (DAOException e){
            String error = "Failed to delete ShoppingCard";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }
}
