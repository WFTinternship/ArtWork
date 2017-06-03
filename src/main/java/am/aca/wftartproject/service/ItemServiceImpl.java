package am.aca.wftartproject.service;

import am.aca.wftartproject.util.DBConnection;
import am.aca.wftartproject.dao.ItemDao;
import am.aca.wftartproject.dao.daoInterfaces.impl.ItemDaoImpl;
import am.aca.wftartproject.model.Item;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by surik on 6/1/17
 */

public class ItemServiceImpl implements ItemService {

    ItemDao itemDao = null;


    public ItemServiceImpl() throws SQLException, ClassNotFoundException {

         itemDao = new ItemDaoImpl(new DBConnection().getDBConnection(DBConnection.DBType.REAL));

    }


    /**
     * @param artistID
     * @param item
     *
     * @see ItemService#addItem(int, Item)
     */
    @Override
    public void addItem(int artistID, Item item) {

        itemDao.addItem(artistID, item);

    }


    /**
     * @param id
     * @param price
     *
     * @see ItemService#updateItem(int, double)
     */
    @Override
    public void updateItem(int id, double price) {

        itemDao.updateItem(id, price);

    }


    /**
     * @param id
     *
     * @see ItemService#deleteItem(int)
     */
    @Override
    public void deleteItem(int id) {

        itemDao.deleteItem(id);

    }


    /**
     * @param id
     * @return
     *
     * @see ItemService#findItem(int)
     */
    @Override
    public Item findItem(int id) {

        return itemDao.findItem(id);

    }


    /**
     * @param limit
     * @return
     *
     * @see ItemService#getRecentlyAddedItems(int)
     */
    @Override
    public List<Item> getRecentlyAddedItems(int limit) {

        return itemDao.getRecentlyAddedItems(limit);

    }


    /**
     * @param title
     * @return
     *
     * @see ItemService#getItemsByTitle(String)
     */
    @Override
    public List<Item> getItemsByTitle(String title) {

        return itemDao.getItemsByTitle(title);

    }


    /**
     * @param itemType
     * @return
     *
     * @see ItemService#getItemsByType(String)
     */
    @Override
    public List<Item> getItemsByType(String itemType) {

        return itemDao.getItemsByType(itemType);

    }

}
