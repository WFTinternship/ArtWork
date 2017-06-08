package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ItemDao;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.ItemType;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 27-May-17
 */
public class ItemDaoImpl extends BaseDaoImpl implements ItemDao {

    private static final Logger LOGGER = Logger.getLogger(ItemDaoImpl.class);

    public ItemDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }


    /**
     * @param artistID
     * @param item
     * @see ItemDao#addItem(Long, Item)
     */
    @Override
    public void addItem(Long artistID, Item item) {
        Connection conn = null;
        try {
            conn = getDataSource().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item(title, description, price, artist_id, photo_url, status, type) VALUES (?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, item.getTitle());
            ps.setString(2, item.getDescription());
            ps.setDouble(3, item.getPrice());
            ps.setLong(4, artistID);
            ps.setString(5, item.getPhotoURL());
            ps.setBoolean(6, item.isStatus());
            ps.setString(7, item.getItemType().getType());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getLong(1));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            String error = "Failed to add Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @param id
     * @return
     * @see ItemDao#findItem(Long)
     */
    @Override
    public Item findItem(Long id) {
        Connection conn = null;
        Item item = new Item();
        try {
            conn = getDataSource().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM item WHERE id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                item.setId(rs.getLong("id"))
                        .setTitle(rs.getString("title"))
                        .setDescription(rs.getString("description"))
                        .setPhotoURL(rs.getString("photo_url"))
                        .setPrice(rs.getDouble("price"))
                        .setStatus(rs.getBoolean("status"))
                        .setItemType(ItemType.valueOf(rs.getString("type")));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            String error = "Failed to get Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        Connection conn = null;
        try {
            conn = getDataSource().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE item SET title=?, description=?, price=?, type=? WHERE id=?");
            ps.setString(1, item.getTitle());
            ps.setString(2, item.getDescription());
            ps.setDouble(3, item.getPrice());
            ps.setString(4, item.getItemType().getType());
            ps.setLong(5, id);
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            String error = "Failed to update Item:  %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @param id
     * @see ItemDao#deleteItem(Long)
     */
    @Override
    public void deleteItem(Long id) {
        Connection conn = null;
        try {
            conn = getDataSource().getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM item WHERE id=?");
            ps.setLong(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            String error = "Failed to delete Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @param limit
     * @return
     * @see ItemDao#getRecentlyAddedItems(int)
     */
    @Override
    public List<Item> getRecentlyAddedItems(int limit) {
        Connection conn = null;
        Item item = new Item();
        List<Item> itemList = new ArrayList<>();
        try {
            conn = getDataSource().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT it.* FROM item it ORDER BY 1 DESC LIMIT ?");
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                item.setId(rs.getLong("id"))
                        .setTitle(rs.getString("title"))
                        .setDescription(rs.getString("description"))
                        .setPrice(rs.getDouble("price"))
                        .setPhotoURL(rs.getString("photo_url"))
                        .setStatus(rs.getBoolean("status"))
                        .setItemType(ItemType.valueOf(rs.getString("type")));
                itemList.add(item);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            String error = "Failed to get RecentlyAddedItems: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        Connection conn = null;
        Item item = new Item();
        List<Item> itemList = new ArrayList<>();
        try {
            conn = getDataSource().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM item WHERE title=?");
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                item.setId(rs.getLong("id"))
                        .setTitle(rs.getString("title"))
                        .setDescription(rs.getString("description"))
                        .setPrice(rs.getDouble("price"))
                        .setPhotoURL(rs.getString("photo_url"))
                        .setStatus(rs.getBoolean("status"))
                        .setItemType(ItemType.valueOf(rs.getString("type")));
                itemList.add(item);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            String error = "Failed to get ItemsByTitle: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        Connection conn = null;
        Item item = new Item();
        List<Item> itemList = new ArrayList<>();
        try {
            conn = getDataSource().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM item WHERE item_type=?");
            ps.setString(1, itemType);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                item.setId(rs.getLong("id"))
                        .setTitle(rs.getString("title"))
                        .setDescription(rs.getString("description"))
                        .setPrice(rs.getDouble("price"))
                        .setPhotoURL(rs.getString("photo_url"))
                        .setStatus(rs.getBoolean("status"))
                        .setItemType(ItemType.valueOf(rs.getString("type")));
                itemList.add(item);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            String error = "Failed to get ItemsByType: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return itemList;
    }
}