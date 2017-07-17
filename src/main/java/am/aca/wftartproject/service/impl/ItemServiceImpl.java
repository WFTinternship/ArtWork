package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.entity.*;
import am.aca.wftartproject.repository.ItemRepo;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.dao.NotEnoughMoneyException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.service.ItemService;
import am.aca.wftartproject.service.PurchaseHistoryService;
import am.aca.wftartproject.service.ShoppingCardService;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isEmptyString;

/**
 * Created by surik on 6/1/17
 */
@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger LOGGER = Logger.getLogger(ItemServiceImpl.class);
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private PurchaseHistoryService purchaseHistoryService;
    @Autowired
    private ShoppingCardService shoppingCardService; //= CtxListener.getBeanFromSpring(SpringBeanType.SHOPPINGCARDSERVICE, ShoppingCardDaoImpl.class);

/*
    public void setItemDao(ItemRepo itemRepo) {
        this.itemRepo = itemRepo;
    }*/


    /**
     * @param item
     * @see ItemService#addItem(Item)
     */

    @Override
    public void addItem(Item item) {

        if (item == null || !item.isValidItem()) {
            LOGGER.error(String.format("Item is not valid: %s", item));
            throw new InvalidEntryException("Invalid item");
        }

        try {
            itemRepo.saveAndFlush(item);
        } catch (DAOException e) {
            String error = "Failed to add Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param id
     * @return
     * @see ItemService#findItem(Long)
     */
    @Override
    public Item findItem(Long id) {
        if (id == null || id < 0) {
            LOGGER.error(String.format("Id is not valid: %s", id));
            throw new InvalidEntryException("Invalid Id");
        }

        try {
            return itemRepo.findOne(id);
        } catch (DAOException e) {
            String error = "Failed to find Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param limit
     * @return
     * @see ItemService#getRecentlyAddedItems(int)
     */
    @Override
    public List<Item> getRecentlyAddedItems(int limit) {

        if (limit <= 0) {
            LOGGER.error(String.format("limit is not valid: %s", limit));
            throw new InvalidEntryException("Invalid limit");
        }

        try {
            return itemRepo.findTop20By();
        } catch (DAOException e) {
            String error = "Failed to get recently added Items: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param title
     * @return
     * @see ItemService#getItemsByTitle(String)
     */
    @Override
    public List<Item> getItemsByTitle(String title) {

        if (isEmptyString(title)) {
            LOGGER.error(String.format("title is not valid: %s", title));
            throw new InvalidEntryException("Invalid title");
        }

        try {
            return itemRepo.getAllByTitle(title);
        } catch (DAOException e) {
            String error = "Failed to get items by title: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param itemType
     * @return
     * @see ItemService#getItemsByType(ItemType)
     */
    @Override
    public List<Item> getItemsByType(ItemType itemType) {

        if (itemType == null) {
            LOGGER.error(String.format("itemType is not valid: %s", itemType));
            throw new InvalidEntryException("Invalid itemType");
        }

        try {
            return itemRepo.getAllByItemType(itemType);
        } catch (DAOException e) {
            String error = "Failed to get items by type: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param minPrice
     * @param maxPrice
     * @return
     * @see ItemService#getItemsForGivenPriceRange(Double, Double)
     */
    @Override
    public List<Item> getItemsForGivenPriceRange(Double minPrice, Double maxPrice) {

        if (minPrice == null || minPrice < 0 || maxPrice == null || maxPrice < 0) {
            LOGGER.error(String.format("price is not valid: %s , %s", minPrice, maxPrice));
            throw new InvalidEntryException("Invalid price");
        }

        try {
            return itemRepo.getAllByPriceBetween(minPrice, maxPrice);
        } catch (DAOException e) {
            String error = "Failed to get items for mentioned price range: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param artistId
     * @return
     * @see ItemService#getArtistItems(Long)
     */
    @Override
    public List<Item> getArtistItems(Long artistId) {
        if (artistId == null || artistId < 0 ) {
            LOGGER.error(String.format("artistId or itemId or limit is not valid"));
            throw new InvalidEntryException("Invalid artistId or limit");
        }

        try {
            return itemRepo.getAllByArtistId(artistId);
        } catch (DAOException e) {
            String error = "Failed to get items for the given artistId: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param item
     * @see ItemService#updateItem(Item)
     */

    @Override
    public void updateItem(Item item) {
        if (item == null || item.getId() == null || !item.isValidItem()) {
            LOGGER.error(String.format("Item is not valid: %s", item));
            throw new InvalidEntryException("Invalid item");
        }

        try {
            itemRepo.saveAndFlush(item);
        } catch (DAOException e) {
            String error = "Failed to update Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param item
     * @see ItemService#deleteItem(Item)
     */

    @Override
    public void deleteItem(Item item) {
        if (item == null || !item.isValidItem()) {
            LOGGER.error(String.format("Item is not valid: %s", item));
            throw new InvalidEntryException("Invalid Item");
        }
        try {
            itemRepo.delete(item);
        } catch (DAOException e) {
            String error = "Failed to delete Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param item
     * @param buyer
     * @see ItemService#itemBuying(Item, AbstractUser)
     */
    @Override
    public void itemBuying(Item item, AbstractUser buyer) {
        if (buyer == null || !buyer.isValidUser()) {
            LOGGER.error(String.format("buyerId is not valid: %s", buyer));
            throw new InvalidEntryException("Invalid Id");
        }

        if (item == null || !item.isValidItem()) {
            LOGGER.error(String.format("Item is not valid: %s", item));
            throw new InvalidEntryException("Invalid item");
        }

        // Withdraw money from payment method
        try {
            ShoppingCard shoppingCard = shoppingCardService.getShoppingCard(buyer.getId());
            if (shoppingCard.getBalance() >= item.getPrice()) {
                shoppingCard.setBalance(shoppingCard.getBalance() - item.getPrice());
                shoppingCardService.updateShoppingCard(shoppingCard);
            } else {
                throw new NotEnoughMoneyException("Not enough money on the account.");
            }
        } catch (DAOException e) {
            String error = "Failed to debit money: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }

        // Add item to the buyer's purchase history
        try {
            PurchaseHistory purchaseHistory = new PurchaseHistory();
            purchaseHistory.setItem(item);
            purchaseHistory.setAbsUser(buyer);
            purchaseHistory.setPurchaseDate(Calendar.getInstance().getTime());
            purchaseHistoryService.addPurchase(purchaseHistory);
        } catch (DAOException e) {
            String error = "Failed to add item in purchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }

        // Change item status to sold
        try {
            item.setStatus(false);
            itemRepo.saveAndFlush(item);

        } catch (DAOException e) {
            String error = "Failed to change item status to sold: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }
}
