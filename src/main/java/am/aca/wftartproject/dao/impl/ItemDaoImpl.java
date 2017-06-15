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
     * @see ItemDao#addItem(Long, Item)
     * @param artistID
     * @param item
     */
    @Override
    public void addItem(Long artistID, Item item) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(
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
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            String error = "Failed to add Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            closeResources(rs, ps, conn);
        }
    }


    /**
     * @see ItemDao#findItem(Long)
     * @param id
     * @return
     */
    @Override
    public Item findItem(Long id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Item item = new Item();
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("SELECT * FROM item WHERE id = ?");
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                getItemFromResultSet(item, rs);
//                item.setId(rs.getLong("id"))
//                        .setTitle(rs.getString("title"))
//                        .setDescription(rs.getString("description"))
//                        .setPhotoURL(rs.getString("photo_url"))
//                        .setPrice(rs.getDouble("price"))
//                        .setStatus(rs.getBoolean("status"))
//                        .setItemType(ItemType.valueOf(rs.getString("type")));
            }
            else {
                LOGGER.error(String.format("Failed to get item by this id: %s", id));
                throw new DAOException("Failed to get item by this id!");
            }
        } catch (SQLException e) {
            String error = "Failed to get Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            closeResources(rs, ps, conn);
        }
        return item;
    }


    /**
     * @see ItemDao#getRecentlyAddedItems(int)
     * @param limit
     * @return
     */
    @Override
    public List<Item> getRecentlyAddedItems(int limit) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Item> itemList = new ArrayList<>();
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("SELECT it.* FROM item it ORDER BY 1 DESC LIMIT ?");
            ps.setInt(1, limit);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                getItemFromResultSet(item, rs);
//                item.setId(rs.getLong("id"))
//                        .setTitle(rs.getString("title"))
//                        .setDescription(rs.getString("description"))
//                        .setPrice(rs.getDouble("price"))
//                        .setPhotoURL(rs.getString("photo_url"))
//                        .setStatus(rs.getBoolean("status"))
//                        .setItemType(ItemType.valueOf(rs.getString("type")));
                itemList.add(item);
            }
        } catch (SQLException e) {
            String error = "Failed to get RecentlyAddedItems: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            closeResources(rs, ps, conn);
        }
        return itemList;
    }


    /**
     * @see ItemDao#getItemsByTitle(String)
     * @param title
     * @return
     */
    @Override
    public List<Item> getItemsByTitle(String title) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Item> itemList = new ArrayList<>();
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("SELECT * FROM item WHERE title=?");
            ps.setString(1, title);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                getItemFromResultSet(item, rs);
//                item.setId(rs.getLong("id"))
//                        .setTitle(rs.getString("title"))
//                        .setDescription(rs.getString("description"))
//                        .setPrice(rs.getDouble("price"))
//                        .setPhotoURL(rs.getString("photo_url"))
//                        .setStatus(rs.getBoolean("status"))
//                        .setItemType(ItemType.valueOf(rs.getString("type")));
                itemList.add(item);
            }
        } catch (SQLException e) {
            String error = "Failed to get ItemsByTitle: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            closeResources(rs, ps, conn);
        }
        return itemList;
    }


    /**
     * @see ItemDao#getItemsByType(String)
     * @param itemType
     * @return
     */
    @Override
    public List<Item> getItemsByType(String itemType) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Item> itemList = new ArrayList<>();
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("SELECT * FROM item WHERE type =?");
            ps.setString(1, itemType);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                getItemFromResultSet(item, rs);
//                item.setId(rs.getLong("id"))
//                        .setTitle(rs.getString("title"))
//                        .setDescription(rs.getString("description"))
//                        .setPrice(rs.getDouble("price"))
//                        .setPhotoURL(rs.getString("photo_url"))
//                        .setStatus(rs.getBoolean("status"))
//                        .setItemType(ItemType.valueOf(rs.getString("type")));
                itemList.add(item);
            }
        } catch (SQLException e) {
            String error = "Failed to get ItemsByType: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            closeResources(rs, ps, conn);
        }
        return itemList;
    }


    /**
     * @see ItemDao#getItemsForGivenPriceRange(Double, Double)
     * @param minPrice
     * @param maxPrice
     * @return
     */
    @Override
    public List<Item> getItemsForGivenPriceRange(Double minPrice, Double maxPrice) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Item> itemList = new ArrayList<>();
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("SELECT * FROM item WHERE status=0 AND price between ? AND ? ");
            ps.setDouble(1, minPrice);
            ps.setDouble(2, maxPrice);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                getItemFromResultSet(item, rs);
//                item.setId(rs.getLong("id"))
//                        .setTitle(rs.getString("title"))
//                        .setDescription(rs.getString("description"))
//                        .setPrice(rs.getDouble("price"))
//                        .setPhotoURL(rs.getString("photo_url"))
//                        .setStatus(rs.getBoolean("status"))
//                        .setItemType(ItemType.valueOf(rs.getString("type")));
                itemList.add(item);
            }
        } catch (SQLException e) {
            String error = "Failed to get items by the given price range: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            closeResources(rs, ps, conn);
        }
        return itemList;
    }

    /**
     * @see ItemDao#getArtistItems(Long, Long, Long)
     * @param artistId
     * @param limit
     * @return
     */
    @Override
    public List<Item> getArtistItems(Long artistId, Long itemId, Long limit) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Item> itemList = new ArrayList<>();
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("SELECT * FROM item WHERE artist_id=? and id!=? LIMIT ?");
            ps.setLong(1, artistId);
            ps.setLong(2,itemId);
            ps.setLong(3, limit);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                getItemFromResultSet(item, rs);
//                item.setId(rs.getLong("id"))
//                        .setTitle(rs.getString("title"))
//                        .setDescription(rs.getString("description"))
//                        .setPrice(rs.getDouble("price"))
//                        .setPhotoURL(rs.getString("photo_url"))
//                        .setStatus(rs.getBoolean("status"))
//                        .setItemType(ItemType.valueOf(rs.getString("type")));
                itemList.add(item);
            }
        } catch (SQLException e) {
            String error = "Failed to get items for the given artistId: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            closeResources(rs, ps, conn);
        }
        return itemList;
    }


    /**
     * @see ItemDao#updateItem(Long, Item)
     * @param id
     * @param item
     */
    @Override
    public void updateItem(Long id, Item item) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(
                    "UPDATE item SET title=?, description=?, price=?, type=? WHERE id=?");
            ps.setString(1, item.getTitle());
            ps.setString(2, item.getDescription());
            ps.setDouble(3, item.getPrice());
            ps.setString(4, item.getItemType().getType());
            ps.setLong(5, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            String error = "Failed to update Item:  %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            closeResources(ps, conn);
        }
    }


    /**
     * @see ItemDao#deleteItem(Long)
     * @param id
     */
    @Override
    public void deleteItem(Long id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("DELETE FROM item WHERE id=?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            String error = "Failed to delete Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            closeResources(ps, conn);
        }
    }


    private void getItemFromResultSet(Item item, ResultSet rs) throws SQLException {
        item.setId(rs.getLong("id"))
                .setTitle(rs.getString("title"))
                .setDescription(rs.getString("description"))
                .setPhotoURL(rs.getString("photo_url"))
                .setPrice(rs.getDouble("price"))
                .setArtistId(rs.getLong("artist_id"))
                .setStatus(rs.getBoolean("status"))
                .setItemType(ItemType.valueOf(rs.getString("type")));
    }
}