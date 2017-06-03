package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ItemDao;
import am.aca.wftartproject.exception.DAOFailException;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.ItemType;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 27-May-17
 */
public class ItemDaoImpl implements ItemDao {

    private Connection conn = null;
    private static final Logger LOGGER = Logger.getLogger(ItemDaoImpl.class);

    public ItemDaoImpl(Connection conn) {
        this.conn = conn;
    }

    /**
     * @param artistID
     * @param item
     * @see ItemDao#addItem(Long, Item)
     */
    @Override
    public void addItem(Long artistID, Item item) {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO item(title, description, price, artist_id, photo_url, status, type) VALUE (?,?,?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, item.getTitle());
            ps.setString(2, item.getDescription());
            ps.setString(3, item.getPhotoURL());
            ps.setDouble(4, item.getPrice());
            ps.setLong(5, artistID);
            ps.setBoolean(6, item.isStatus());
            ps.setString(7, item.getItemType().toString());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getLong(1));
            }
            rs.close();
        } catch (SQLException e) {
            String error = "Failed to add Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOFailException(error, e);
        }
    }


    /**
     * @param id
     * @return
     * @see ItemDao#findItem(Long)
     */
    @Override
    public Item findItem(Long id) {
        Item item = new Item();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM item WHERE id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                item.setId(rs.getLong("id"));
                item.setTitle(rs.getString("title"));
                item.setDescription(rs.getString("description"));
                item.setPhotoURL(rs.getString("photo_url"));
                item.setPrice(rs.getDouble("price"));
                item.setStatus(rs.getBoolean("status"));
                item.setItemType(ItemType.valueOf(rs.getString("type")));
            }
            rs.close();
        } catch (SQLException e) {
            String error = "Failed to get Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOFailException(error, e);
        }
        return item;
    }

    /**
     * @param id
     * @param item
     * @see ItemDao#updateItem(Long, Item)
     */
    @Override
    public void updateItem(Long id, Item item) {
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE item SET title=? AND description=? AND price=? AND type_id=? WHERE id=?")) {

            ps.setString(1, item.getTitle());
            ps.setString(2, item.getDescription());
            ps.setDouble(3, item.getPrice());
            ps.setInt(4, item.getItemType().getTypeId());
            ps.setLong(5, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            String error = "Failed to update Item:  %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOFailException(error, e);
        }
    }

    /**
     * @param id
     * @see ItemDao#deleteItem(Long)
     */
    @Override
    public void deleteItem(Long id) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM item WHERE id=?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            String error = "Failed to delete Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOFailException(error, e);
        }
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
                tempItem.setId(rs.getLong(1));
                itemList.add(tempItem);
            }
            rs.close();
        } catch (SQLException e) {
            String error = "Failed to get RecentlyAddedItems: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOFailException(error, e);
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
                tempItem.setId(rs.getLong(1));
                itemList.add(tempItem);
            }
            rs.close();
        } catch (SQLException e) {
            String error = "Failed to get ItemsByTitle: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOFailException(error, e);
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
                tempItem.setId(rs.getLong(1));
                itemList.add(tempItem);
            }
            rs.close();
        } catch (SQLException e) {
            String error = "Failed to get ItemsByType: %s";
            LOGGER.error(String.format(error,e.getMessage()));
            throw new DAOFailException(error, e);
        }
        return itemList;
    }
}
