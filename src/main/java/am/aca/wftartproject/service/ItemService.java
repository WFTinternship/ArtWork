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
    void addItem(Long artistID, Item item);

    /**
     * Find Item by id
     *
     * @param id
     * @return
     */
    Item findItem(Long id);

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
     * @param artistId
     * @param itemId
     * @param limit
     * @return
     */
    List<Item> getArtistItems(Long artistId, Long itemId, Long limit);

    /**
     * Update Item price by id
     *
     * @param id
     * @param item
     */
    void updateItem(Long id, Item item);

    /**
     * Delete Item by id
     *
     * @param id
     */
    void deleteItem(Long id);


}
