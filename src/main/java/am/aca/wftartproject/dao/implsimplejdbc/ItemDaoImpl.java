package am.aca.wftartproject.dao.implsimplejdbc;

import am.aca.wftartproject.dao.ItemDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.ItemType;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 27-May-17
 */
public class ItemDaoImpl extends BaseDaoImpl implements ItemDao {

    private static final Logger LOGGER = Logger.getLogger(ItemDaoImpl.class);

    public ItemDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * @param artistID
     * @param item
     * @see ItemDao#addItem(Long, Item)
     */
    @Override
    public void addItem(Long artistID, Item item) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            item.setAdditionDate(getCurrentDateTime());
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(
                    "INSERT INTO item(title, description, price, artist_id, photo_url, status, type, addition_date) VALUES (?,?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, item.getTitle());
            ps.setString(2, item.getDescription());
            ps.setDouble(3, item.getPrice());
            ps.setLong(4, artistID);
            ps.setString(5, item.getPhotoURL());
            ps.setBoolean(6, item.getStatus());
            ps.setString(7, item.getItemType().getType());
            ps.setString(8, sdf.format(item.getAdditionDate()));
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
     * @param id
     * @return
     * @see ItemDao#findItem(Long)
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
     * @param limit
     * @return
     * @see ItemDao#getRecentlyAddedItems(int)
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
     * @param title
     * @return
     * @see ItemDao#getItemsByTitle(String)
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
     * @param itemType
     * @return
     * @see ItemDao#getItemsByType(String)
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
     * @param minPrice
     * @param maxPrice
     * @return
     * @see ItemDao#getItemsForGivenPriceRange(Double, Double)
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
     * @param artistId
     * @param limit
     * @return
     * @see ItemDao#getArtistItems(Long, Long, Long)
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
            ps.setLong(2, itemId);
            ps.setLong(3, limit);
            rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                getItemFromResultSet(item, rs);
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



    @Override
    public List<Item> getAvailableItemsForGivenArtist(Long artistId) {
        return null;
    }


    /**
     * @param id
     * @param item
     * @see ItemDao#updateItem(Long, Item)
     */
    @Override
    public void updateItem(Long id, Item item) {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(
                    "UPDATE item SET title=?, description=?, price=?, type=?, status=? WHERE id=?");
            ps.setString(1, item.getTitle());
            ps.setString(2, item.getDescription());
            ps.setDouble(3, item.getPrice());
            ps.setString(4, item.getItemType().getType());
            ps.setBoolean(5, item.getStatus());
            ps.setLong(6, item.getId());
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
     * @param id
     * @see ItemDao#deleteItem(Long)
     */
    @Override
    public Boolean deleteItem(Long id) {

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
        return true;
    }


    private void getItemFromResultSet(Item item, ResultSet rs) throws SQLException {
        DateTimeFormatter dtf =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        item.setId(rs.getLong("id"))
                .setTitle(rs.getString("title"))
                .setDescription(rs.getString("description"))
                .setPhotoURL(rs.getString("photo_url"))
                .setPrice(rs.getDouble("price"))
                .setArtistId(rs.getLong("artist_id"))
                .setStatus(rs.getBoolean("status"))
                .setAdditionDate(LocalDateTime.parse(rs.getString("addition_date"), dtf))
                .setItemType(ItemType.valueOf(rs.getString("type")));
    }
}