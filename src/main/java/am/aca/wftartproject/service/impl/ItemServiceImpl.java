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
import am.aca.wftartproject.util.ServiceHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isEmptyString;

/**
 * Created by surik on 6/1/17
 */
@Service
public class ItemServiceImpl extends ServiceHelper implements ItemService {

    private static final Logger LOGGER = Logger.getLogger(ItemServiceImpl.class);

    private ItemRepo itemRepo;

    private PurchaseHistoryService purchaseHistoryService;

    private ShoppingCardService shoppingCardService;

    @Autowired
    public void setItemRepo(ItemRepo itemRepo) {
        this.itemRepo = itemRepo;
    }
    @Autowired
    public void setPurchaseHistoryService(PurchaseHistoryService purchaseHistoryService) {
        this.purchaseHistoryService = purchaseHistoryService;
    }
    @Autowired
    public void setShoppingCardService(ShoppingCardService shoppingCardService) {
        this.shoppingCardService = shoppingCardService;
    }

    /**
     * @param item *
     * @see ItemService#addItem(Item)
     */

    @Override
    public void addItem(Item item) {

        //  check for item validity
        itemValidateAndProcess(item);

        //save item into db
        try {
            itemRepo.saveAndFlush(item);
        } catch (DAOException e) {
            String error = "Failed to add Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }

    /**
     * @param id *
     * @see ItemService#findItem(Long)
     */
    @Override
    public Item findItem(Long id) {

        //check items id for validity
       idValidateAndProcess(id);

        //find item from db by id
        try {
            return itemRepo.findOne(id);
        } catch (DAOException e) {
            String error = "Failed to find Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @see ItemService#getRecentlyAddedItems
     */
    @Override
    public List<Item> getRecentlyAddedItems() {

        //get recently added 20 items list from db
        try {
            return itemRepo.findTop20By();
        } catch (DAOException e) {
            String error = "Failed to get recently added Items: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param title *
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
     * @param itemType the item type
     * @see ItemService#getItemsByType(ItemType)
     */
    @Override
    public List<Item> getItemsByType(ItemType itemType) {

        if (itemType == null) {
            LOGGER.error("itemType is not valid");
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
     * @param minPrice is the min price
     * @param maxPrice is the max price
     * @see ItemService#getItemsForGivenPriceRange(Double, Double)
     */
    @Override
    public List<Item> getItemsForGivenPriceRange(Double minPrice, Double maxPrice) {

        //check minprice and maxprice for validity
        if (minPrice == null || minPrice < 0 || maxPrice == null || maxPrice < 0) {
            LOGGER.error(String.format("price is not valid: %s , %s", minPrice, maxPrice));
            throw new InvalidEntryException("Invalid price");
        }

        //get specific list by price range from db
        try {
            return itemRepo.getAllByPriceBetween(minPrice, maxPrice);
        } catch (DAOException e) {
            String error = "Failed to get items for mentioned price range: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param artistId is artis id
     * @see ItemService#getArtistItems(Long)
     */
    @Override
    public List<Item> getArtistItems(Long artistId) {

        //check artists id for validity
       idValidateAndProcess(artistId);

        // try to get all artist items by id
        try {
            return itemRepo.getAllByArtistId(artistId);
        } catch (DAOException e) {
            String error = "Failed to get items for the given artistId: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param item is item
     * @see ItemService#updateItem(Item)
     */

    @Override
    public void updateItem(Item item) {

        //check item for validity
       dbItemValidateAndProcess(item);

        //try to update item
        try {
            itemRepo.saveAndFlush(item);
        } catch (DAOException e) {
            String error = "Failed to update Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param item *
     * @see ItemService#deleteItem(Item)
     */

    @Override
    public void deleteItem(Item item) {

        //check item for validity
        dbItemValidateAndProcess(item);

        //try to delete item from db
        try {
            itemRepo.delete(item);
        } catch (DAOException e) {
            String error = "Failed to delete Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param item is item
     * @param buyer is user
     * @see ItemService#itemBuying(Item, AbstractUser)
     */
    @Override
    public void itemBuying(Item item, AbstractUser buyer) {

        //check buyer for validity
        if (buyer == null || !buyer.isValidUser()) {
            LOGGER.error(String.format("buyerId is not valid: %s", buyer));
            throw new InvalidEntryException("Invalid Id");
        }

        //check item for validity
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
