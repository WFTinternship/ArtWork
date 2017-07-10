package am.aca.wftartproject.service;

import am.aca.wftartproject.entity.Item;
import am.aca.wftartproject.entity.ItemType;

import java.util.List;

/**
 * Created by surik on 6/1/17
 */
public interface ItemService {

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
    List<Item> getItemsByType(ItemType itemType);

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
     */
    List<Item> getArtistItems(Long artistId);


    /**
     * Updates Item price by id
     *
     * @param item
     */
    void updateItem(Item item);


    /**
     * Deletes Item by id
     *
     * @param item
     */
    void deleteItem(Item item);

    /**
     * Makes buying processes.
     * @param item
     * @param buyerId
     */
    void itemBuying(Item item, Long buyerId);

}
