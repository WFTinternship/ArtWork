package am.aca.wftartproject.dao;

import am.aca.wftartproject.model.Item;

import java.util.List;

/**
 * Created by ASUS on 27-May-17.
 */
public interface ItemDao {

    void addItem(int artistID, Item item);
    void updateItem(int id, double price);
    void deleteItem(int id);
    Item findItem(int id);


    List<Item> getRecentlyAddedItems(int limit);
    List<Item> getItemsByTitle(String title);
    List<Item> getItemsByType(String itemType);

}
