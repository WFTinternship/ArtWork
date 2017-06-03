package am.aca.wftartproject.dao;

import am.aca.wftartproject.model.Item;

import java.util.List;

/**
 * Created by ASUS on 27-May-17.
 */
public interface ItemDao {

    /**
     * Adds Item info for appropriate artist
     *
     * @param artistID
     * @param item
     */
    void addItem(Long artistID, Item item);

    /**
     * Finds Item by id
     *
     * @param id
     * @return
     */
    Item findItem(Long id);

    /**
     * Gets recently added items
     *
     * @param limit
     * @return
     */
    List<Item> getRecentlyAddedItems(int limit);


    /**
     * Gets all items with the following title.
     *
     * @param title
     * @return
     */
    List<Item> getItemsByTitle(String title);

    /**
     * Gets all items with the following type.
     *
     * @param itemType
     * @return
     */
    List<Item> getItemsByType(String itemType);


    /**
     * Updates Item price by id
     *
     * @param id
     * @param price
     */
    void updateItem(Long id, double price);

    /**
     * Deletes Item by id
     *
     * @param id
     */
    void deleteItem(Long id);


}
