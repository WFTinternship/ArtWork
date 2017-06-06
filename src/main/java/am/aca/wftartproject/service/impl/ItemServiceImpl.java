package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.dao.ItemDao;
import am.aca.wftartproject.dao.impl.ItemDaoImpl;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.service.ItemService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ASUS on 03-Jun-17
 */
public class ItemServiceImpl implements ItemService {

    private Connection conn = null;
    private ItemDao itemDao = null;

    public ItemServiceImpl() throws SQLException, ClassNotFoundException {
//        Connection conn = new dbconnection().getConnection(ConnectionModel.SINGLETON).getProductionDBConnection();
        itemDao = new ItemDaoImpl(conn);
    }


    /**
     * @param artistID
     * @param item
     * @see ItemService#addItem(Long, Item)
     */
    @Override
    public void addItem(Long artistID, Item item) {

        itemDao.addItem(artistID, item);
    }


    /**
     * @param id
     * @return
     * @see ItemService#findItem(Long)
     */
    @Override
    public Item findItem(Long id) {
        return itemDao.findItem(id);
    }


    /**
     * @param id
     * @param item
     * @see ItemService#updateItem(Long, Item)
     */
    @Override
    public void updateItem(Long id, Item item) {
        itemDao.updateItem(id, item);
    }


    /**
     * @param id
     * @see ItemService#deleteItem(Long)
     */
    @Override
    public void deleteItem(Long id) {
        itemDao.deleteItem(id);
    }


    /**
     * @param limit
     * @return
     * @see ItemService#getRecentlyAddedItems(int)
     */
    @Override
    public List<Item> getRecentlyAddedItems(int limit) {
        return itemDao.getRecentlyAddedItems(limit);
    }

    /**
     * @param title
     * @return
     * @see ItemService#getItemsByTitle(String)
     */
    @Override
    public List<Item> getItemsByTitle(String title) {
        return itemDao.getItemsByTitle(title);
    }

    /**
     * @param itemType
     * @return
     * @see ItemService#getItemsByType(String)
     */
    @Override
    public List<Item> getItemsByType(String itemType) {
        return itemDao.getItemsByType(itemType);
    }

}
