package am.aca.wftartproject.service;

import am.aca.wftartproject.model.Item;

import java.util.List;

/**
 * Created by surik on 6/1/17
 */
public interface ItemService {

    /**
     * Add Item info for appropriate artist
     *
     * @param artistID
     * @param item
     */
    void addItem(int artistID, Item item);

    /**
     * Update Item price by id
     *
     * @param id
     * @param price
     */
    void updateItem(int id, double price);

    /**
     * Delete Item by id
     *
     * @param id
     */
    void deleteItem(int id);

    /**
     * Find Item by id
     *
     * @param id
     * @return
     */
    Item findItem(int id);


    /**
     * Get recently added items
     *
     * @param limit
     * @return
     */
    List<Item> getRecentlyAddedItems(int limit);

    /**
     * Get all items with the following title.
     *
     * @param title
     * @return
     */
    List<Item> getItemsByTitle(String title);


    /**
     * Get all items with the following type.
     *
     * @param itemType
     * @return
     */
    List<Item> getItemsByType(String itemType);



}
