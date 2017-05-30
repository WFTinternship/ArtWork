package am.aca.wftartproject.dao;

import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.ItemType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 27-May-17.
 */
public class ItemDaoImpl implements ItemDao {

    private Connection conn = null;

    public ItemDaoImpl(Connection conn) {
        this.conn = conn;
    }

    /**
     * @param artistID
     * @param item
     * @see ItemDao#addItem(int, Item)
     */
    @Override
    public void addItem(int artistID, Item item) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO item(title, description, photo_url, price, artist_id, status, item_type) VALUE (?,?,?,?,?,?,?)");

            ps.setString(1, item.getTitle());
            ps.setString(2, item.getDescription());
            ps.setString(3, item.getPhotoURL());
            ps.setDouble(4, item.getPrice());
            ps.setInt(5, artistID);
            ps.setBoolean(6, item.isStatus());
            ps.setString(7, item.getItemType().toString());

            if (ps.executeUpdate() > 0) {
                System.out.println("The item was successfully inserted");
            } else {
                System.out.println("There is problem with item inserting");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param id
     * @param price
     * @see ItemDao#updateItem(int, double)
     */
    @Override
    public void updateItem(int id, double price) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE item SET price=? WHERE id=?");
            ps.setDouble(1, price);
            ps.setInt(2, id);
            if (ps.executeUpdate() > 0) {
                System.out.println("The item info was successfully updated");
            } else {
                System.out.println("There is a problem with item info updating");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param id
     * @see ItemDao#deleteItem(int)
     */
    @Override
    public void deleteItem(int id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM item WHERE id=?");
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                System.out.println("The item was successfully deleted");
            } else {
                System.out.println("There is a problem with item deleting");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param id
     * @return
     * @see ItemDao#findItem(int)
     */
    @Override
    public Item findItem(int id) {
        Item item = new Item();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM item WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                item.setId(rs.getInt("id"));
                item.setTitle(rs.getString("title"));
                item.setDescription(rs.getString("description"));
                item.setPhotoURL(rs.getString("photo_url"));
                item.setPrice(rs.getDouble("price"));
                item.setStatus(rs.getBoolean("status"));
                item.setItemType(ItemType.valueOf(rs.getString("item_type")));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }


    /**
     * @param limit
     * @return
     * @see ItemDao#getRecentlyAddedItems(int)
     */
    @Override
    public List<Item> getRecentlyAddedItems(int limit) {
        List<Item> itemList = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT it.* FROM item it ORDER BY 1 DESC LIMIT ?")) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item tempItem = new Item(rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getBoolean(7),
                        ItemType.valueOf(rs.getString(8)));
                tempItem.setId(rs.getInt(1));
                itemList.add(tempItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemList;
    }


    /**
     * @param title
     * @return
     * @see ItemDao#getItemsByTitle(String)
     */
    @Override
    public List<Item> getItemsByTitle(String title) {
        List<Item> itemList = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM item WHERE title=?")) {
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item tempItem = new Item(rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getBoolean(7),
                        ItemType.valueOf(rs.getString(8)));
                tempItem.setId(rs.getInt(1));
                itemList.add(tempItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemList;
    }


    /**
     * @param itemType
     * @return
     * @see ItemDao#getItemsByType(String)
     */
    @Override
    public List<Item> getItemsByType(String itemType) {
        List<Item> itemList = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM item WHERE item_type=?")) {
            ps.setString(1, itemType);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item tempItem = new Item(rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getBoolean(7),
                        ItemType.valueOf(rs.getString(8)));
                tempItem.setId(rs.getInt(1));
                itemList.add(tempItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemList;
    }

}
