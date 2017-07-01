package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.dao.ItemDao;
import am.aca.wftartproject.dao.PurchaseHistoryDao;
import am.aca.wftartproject.dao.ShoppingCardDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.dao.NotEnoughMoneyException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.PurchaseHistory;
import am.aca.wftartproject.model.ShoppingCard;
import am.aca.wftartproject.service.ItemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isEmptyString;

/**
 * Created by surik on 6/1/17
 */
@Service
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private static final Logger LOGGER = Logger.getLogger(ItemServiceImpl.class);
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private PurchaseHistoryDao purchaseHistoryDao;
    @Autowired
    private ShoppingCardDao shoppingCardDao;

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

//        public ItemServiceImpl() throws SQLException, ClassNotFoundException {
//        DataSource conn = new ConnectionFactory().getConnection(ConnectionModel.POOL).getProductionDBConnection();
//        itemDao = new ItemDaoImpl(conn);
//    }


    /**
     * @param artistID
     * @param item
     * @see ItemService#addItem(Long, Item)
     */
    @Override
    public void addItem(Long artistID, Item item) {

        if (artistID == null || artistID < 0) {
            LOGGER.error(String.format("ArtistId is not valid: %s", artistID));
            throw new InvalidEntryException("Invalid artistId");
        }
        if (item == null || !item.isValidItem()) {
            LOGGER.error(String.format("Item is not valid: %s", item));
            throw new InvalidEntryException("Invalid item");
        }

        try {
            itemDao.addItem(artistID, item);
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
            return itemDao.findItem(id);
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
            return itemDao.getRecentlyAddedItems(limit);
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
            return itemDao.getItemsByTitle(title);
        } catch (DAOException e) {
            String error = "Failed to get items by title: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param itemType
     * @return
     * @see ItemService#getItemsByType(String)
     */
    @Override
    public List<Item> getItemsByType(String itemType) {

        if (isEmptyString(itemType)) {
            LOGGER.error(String.format("itemType is not valid: %s", itemType));
            throw new InvalidEntryException("Invalid itemType");
        }

        try {
            return itemDao.getItemsByType(itemType);
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
            return itemDao.getItemsForGivenPriceRange(minPrice, maxPrice);
        } catch (DAOException e) {
            String error = "Failed to get items for mentioned price range: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param artistId
     * @param limit
     * @return
     * @see ItemService#getArtistItems(Long, Long, Long)
     */
    @Override
    public List<Item> getArtistItems(Long artistId, Long itemId, Long limit) {
        if (artistId == null || artistId < 0 || itemId == null || itemId < 0 || limit == null || limit < 0) {
            LOGGER.error(String.format("artistId or itemId or limit is not valid: %s , %s, %s", artistId, itemId, limit));
            throw new InvalidEntryException("Invalid artistId or limit");
        }

        try {
            return itemDao.getArtistItems(artistId, itemId, limit);
        } catch (DAOException e) {
            String error = "Failed to get items for the given artistId: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param id
     * @param item
     * @see ItemService#updateItem(Long, Item)
     */
    @Override
    public void updateItem(Long id, Item item) {
        if (id == null || id < 0) {
            LOGGER.error(String.format("Id is not valid: %s", id));
            throw new InvalidEntryException("Invalid Id");
        }
        if (item == null || !item.isValidItem()) {
            LOGGER.error(String.format("Item is not valid: %s", item));
            throw new InvalidEntryException("Invalid item");
        }

        try {
            itemDao.updateItem(id, item);
        } catch (DAOException e) {
            String error = "Failed to update Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param id
     * @see ItemService#deleteItem(Long)
     */
    @Override
    public void deleteItem(Long id) {
        if (id == null || id < 0) {
            LOGGER.error(String.format("Id is not valid: %s", id));
            throw new InvalidEntryException("Invalid Id");
        }

        try {
            itemDao.deleteItem(id);
        } catch (DAOException e) {
            String error = "Failed to delete Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param item
     * @param buyerId
     * @see ItemService#itemBuying(Item, Long)
     */
    @Transactional
    public void itemBuying(Item item, Long buyerId) {
        if (buyerId == null || buyerId < 0) {
            LOGGER.error(String.format("buyerId is not valid: %s", buyerId));
            throw new InvalidEntryException("Invalid Id");
        }

        if (item == null || !item.isValidItem()) {
            LOGGER.error(String.format("Item is not valid: %s", item));
            throw new InvalidEntryException("Invalid item");
        }

        // Withdraw money from payment method
        try {
            ShoppingCard shoppingCard = shoppingCardDao.getShoppingCard(buyerId);
            if (shoppingCard.getBalance() >= item.getPrice()) {
                shoppingCard.setBalance(shoppingCard.getBalance() - item.getPrice());
                shoppingCardDao.updateShoppingCard(buyerId, shoppingCard);
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
            purchaseHistoryDao.addPurchase(new PurchaseHistory(buyerId, item.getId()));
        } catch (DAOException e) {
            String error = "Failed to add item in purchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }

        // Change item status to sold
        try {
            item.setStatus(false);
            updateItem(item.getArtistId(), item);

        } catch (DAOException e) {
            String error = "Failed to change item status to sold: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }
}
