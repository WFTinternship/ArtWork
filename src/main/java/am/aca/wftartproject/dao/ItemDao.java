package am.aca.wftartproject.dao;

import am.aca.wftartproject.model.Item;

import java.util.List;

/**
 * Created by ASUS on 27-May-17
 */
public interface ItemDao {

    /**
     * Adds Item info for appropriate artist
     * @param item
     */
    void addItem(Item item);


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
     * Gets all items for the given price range.
     *
     * @param minPrice
     * @param maxPrice
     * @return
     */
    List<Item> getItemsForGivenPriceRange(Double minPrice, Double maxPrice);


    /**
     * Gets artist items for the given limit.
     *
     * @param artistId
     * @return
     */
    List<Item> getArtistItems(Long artistId);


    /**
     * Updates Item price by id
     * @param item
     */
    Boolean updateItem(Item item);


    /**
     * Deletes Item by id
     *
     * @param id
     */
    Boolean deleteItem(Long id);
}
