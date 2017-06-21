package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.dao.ItemDao;
import am.aca.wftartproject.dao.impl.ItemDaoImpl;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.exception.ServiceException;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.service.ItemService;
import org.apache.log4j.Logger;

import java.util.List;

import static am.aca.wftartproject.service.impl.validator.ValidatorUtil.isEmptyString;

/**
 * Created by surik on 6/1/17
 */

public class ItemServiceImpl implements ItemService {

    private static final Logger LOGGER = Logger.getLogger(ItemServiceImpl.class);

    private ItemDao itemDao ;

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

//        public ItemServiceImpl() throws SQLException, ClassNotFoundException {
//        DataSource conn = new ConnectionFactory().getConnection(ConnectionModel.POOL).getProductionDBConnection();
//        itemDao = new ItemDaoImpl(conn);
//    }


    /**
     * @see ItemService#addItem(Long, Item)
     * @param artistID
     * @param item
     */
    @Override
    public void addItem(Long artistID, Item item) {

        if (artistID == null || artistID < 0) {
            LOGGER.error(String.format("ArtistId is invalid: %s", artistID));
            throw new ServiceException("Invalid artistId");
        }
//        if (item == null || !item.isValidItem()) {
//            LOGGER.error(String.format("Item is invalid: %s", item));
//            throw new ServiceException("Invalid item");
//        }

        try {
            itemDao.addItem(artistID, item);
        } catch (DAOException e) {
            String error = "Failed to add Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @see ItemService#findItem(Long)
     * @param id
     * @return
     */
    @Override
    public Item findItem(Long id) {
        if (id == null || id < 0) {
            LOGGER.error(String.format("Id is invalid: %s", id));
            throw new ServiceException("Invalid Id");
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
     * @see ItemService#getRecentlyAddedItems(int)
     * @param limit
     * @return
     */
    @Override
    public List<Item> getRecentlyAddedItems(int limit) {

        if (limit <= 0) {
            LOGGER.error(String.format("limit is invalid: %s", limit));
            throw new ServiceException("Invalid limit");
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
     * @see ItemService#getItemsByTitle(String)
     * @param title
     * @return
     */
    @Override
    public List<Item> getItemsByTitle(String title) {

        if (!isEmptyString(title)) {
            LOGGER.error(String.format("title is invalid: %s", title));
            throw new ServiceException("Invalid title");
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
     * @see ItemService#getItemsByType(String)
     * @param itemType
     * @return
     */
    @Override
    public List<Item> getItemsByType(String itemType) {

        if (!isEmptyString(itemType)) {
            LOGGER.error(String.format("itemType is invalid: %s", itemType));
            throw new ServiceException("Invalid itemType");
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
     * @see ItemService#getItemsForGivenPriceRange(Double, Double)
     * @param minPrice
     * @param maxPrice
     * @return
     */
    @Override
    public List<Item> getItemsForGivenPriceRange(Double minPrice, Double maxPrice) {

        if (minPrice == null || minPrice < 0 || maxPrice == null || maxPrice < 0) {
            LOGGER.error(String.format("price is invalid: %s , %s", minPrice, maxPrice));
            throw new ServiceException("Invalid price");
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
     * @see ItemService#getArtistItems(Long, Long, Long)
     * @param artistId
     * @param limit
     * @return
     */
    @Override
    public List<Item> getArtistItems(Long artistId, Long itemId, Long limit) {
        if (artistId == null || artistId < 0 || itemId == null || itemId < 0 || limit == null || limit < 0) {
            LOGGER.error(String.format("artistId or limit is invalid: %s , %s", artistId, limit));
            throw new ServiceException("Invalid artistId or limit");
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
     * @see ItemService#updateItem(Long, Item)
     * @param id
     * @param item
     */
    @Override
    public void updateItem(Long id, Item item) {
        if (id == null || id < 0) {
            LOGGER.error(String.format("Id is invalid: %s", id));
            throw new ServiceException("Invalid Id");
        }
        if (item == null || !item.isValidItem()) {
            LOGGER.error(String.format("Item is invalid: %s", item));
            throw new ServiceException("Invalid item");
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
     * @see ItemService#deleteItem(Long)
     * @param id
     */
    @Override
    public void deleteItem(Long id) {
        if (id == null || id < 0) {
            LOGGER.error(String.format("Id is invalid: %s", id));
            throw new ServiceException("Invalid Id");
        }

        try {
            itemDao.deleteItem(id);
        } catch (DAOException e) {
            String error = "Failed to delete Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }
}
