package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ItemDao;
import am.aca.wftartproject.dao.rowmappers.ItemMapper;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.Item;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collections;
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

//        Calendar cal = Calendar.getInstance();
//        Timestamp timestamp = new Timestamp(cal.getTimeInMillis());

        try {
            item.setAdditionDate(getCurrentDateTime());
            KeyHolder keyHolder = new GeneratedKeyHolder();
            String query = "INSERT INTO item(title, description, price, artist_id, photo_url, status, type, addition_date) VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatementCreator psc = con -> {
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, item.getTitle());
                ps.setString(2, item.getDescription());
                ps.setDouble(3, item.getPrice());
                ps.setLong(4, artistID);
                ps.setString(5, item.getPhotoURL());
                ps.setBoolean(6, true);
                ps.setString(7, item.getItemType().getType());
                ps.setDate(8, item.getAdditionDate());
                return ps;
            };

            int rowsAffected = jdbcTemplate.update(psc, keyHolder);
            if (rowsAffected > 0) {
                item.setId(keyHolder.getKey().longValue());
                item.setArtistId(artistID);
            } else {
                throw new DAOException("Failed to add Item");
            }
        } catch (DataAccessException e) {
            String error = "Failed to add Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }

        //region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        Calendar cal = Calendar.getInstance();
//        Timestamp timestamp = new Timestamp(cal.getTimeInMillis());

//        try {
//            item.setAdditionDate(getCurrentDateTime());
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement(
//                    "INSERT INTO item(title, description, price, artist_id, photo_url, status, type, addition_date) VALUES (?,?,?,?,?,?,?,?)",
//                    Statement.RETURN_GENERATED_KEYS);
//            ps.setString(1, item.getTitle());
//            ps.setString(2, item.getDescription());
//            ps.setDouble(3, item.getPrice());
//            ps.setLong(4, artistID);
//            ps.setString(5, item.getPhotoURL());
//            ps.setBoolean(6, item.getStatus());
//            ps.setString(7, item.getItemType().getType());
//            ps.setString(8, item.getAdditionDate());
//            ps.executeUpdate();
//            rs = ps.getGeneratedKeys();
//            if (rs.next()) {
//                item.setId(rs.getLong(1));
//            }
//        } catch (SQLException e) {
//            String error = "Failed to add Item: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(rs, ps, conn);
//        }

        //endregion
    }


    /**
     * @param id
     * @return
     * @see ItemDao#findItem(Long)
     */
    @Override
    public Item findItem(Long id) {

        try {
            String query = "SELECT * FROM item WHERE id = ?";
            return jdbcTemplate.queryForObject(query, new Object[]{id}, (rs, rowNum) -> new ItemMapper().mapRow(rs, rowNum));

        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(String.format("Failed to find item by id: %s", id));
            return null;
        } catch (DataAccessException e) {
            String error = "Failed to get Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }

//        region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        Item item = new Item();
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement("SELECT * FROM item WHERE id = ?");
//            ps.setLong(1, id);
//            rs = ps.executeQuery();
//            if (rs.next()) {
//                getItemFromResultSet(item, rs);
////                item.setId(rs.getLong("id"))
////                        .setTitle(rs.getString("title"))
////                        .setDescription(rs.getString("description"))
////                        .setPhotoURL(rs.getString("photo_url"))
////                        .setPrice(rs.getDouble("price"))
////                        .setStatus(rs.getBoolean("status"))
////                        .setItemType(ItemType.valueOf(rs.getString("type"))
////                        .setAdditionDate(rs.getTimestamp("addition_date"));
//            }
//        } catch (SQLException e) {
//            String error = "Failed to get Item: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(rs, ps, conn);
//        }
//        return item;

//        endregion
    }


    /**
     * @param limit
     * @return
     * @see ItemDao#getRecentlyAddedItems(int)
     */
    @Override
    public List<Item> getRecentlyAddedItems(int limit) {

        List<Item> itemList;
        try {
            String query = "SELECT it.* FROM item it ORDER BY 1 DESC LIMIT ?";
            itemList = this.jdbcTemplate.query(query, new Object[]{limit}, new ItemMapper());

        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("Failed to find recently added items: %s");
            return null;
        } catch (DataAccessException e) {
            String error = "Failed to get ItemsByTitle: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return itemList;


//            region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        List<Item> itemList = new ArrayList<>();
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement("SELECT it.* FROM item it ORDER BY 1 DESC LIMIT ?");
//            ps.setInt(1, limit);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                Item item = new Item();
//                getItemFromResultSet(item, rs);
////                item.setId(rs.getLong("id"))
////                        .setTitle(rs.getString("title"))
////                        .setDescription(rs.getString("description"))
////                        .setPrice(rs.getDouble("price"))
////                        .setPhotoURL(rs.getString("photo_url"))
////                        .setStatus(rs.getBoolean("status"))
////                        .setItemType(ItemType.valueOf(rs.getString("type"))
////                        .setAdditionDate(rs.getTimestamp("addition_date"));
//                itemList.add(item);
//            }
//        } catch (SQLException e) {
//            String error = "Failed to get RecentlyAddedItems: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(rs, ps, conn);
//        }
//        return itemList;

//            endregion

    }


    /**
     * @param title
     * @return
     * @see ItemDao#getItemsByTitle(String)
     */
    @Override
    public List<Item> getItemsByTitle(String title) {

        List<Item> itemList;
        try {
            String query = "SELECT * FROM item WHERE title=?";
            itemList = this.jdbcTemplate.query(query, new Object[]{title}, new ItemMapper());

        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(String.format("Failed to get items by title: %s", title));
            return null;
        } catch (DataAccessException e) {
            String error = "Failed to get ItemsByTitle: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return itemList;


//        region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        List<Item> itemList = new ArrayList<>();
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement("SELECT * FROM item WHERE title=?");
//            ps.setString(1, title);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                Item item = new Item();
//                getItemFromResultSet(item, rs);
////                item.setId(rs.getLong("id"))
////                        .setTitle(rs.getString("title"))
////                        .setDescription(rs.getString("description"))
////                        .setPrice(rs.getDouble("price"))
////                        .setPhotoURL(rs.getString("photo_url"))
////                        .setStatus(rs.getBoolean("status"))
////                        .setItemType(ItemType.valueOf(rs.getString("type"))
////                        .setAdditionDate(rs.getTimestamp("addition_date"));
//                itemList.add(item);
//            }
//        } catch (SQLException e) {
//            String error = "Failed to get ItemsByTitle: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(rs, ps, conn);
//        }
//        return itemList;

//        endregion
    }


    /**
     * @param itemType
     * @return
     * @see ItemDao#getItemsByType(String)
     */
    @Override
    public List<Item> getItemsByType(String itemType) {

        List<Item> itemList;
        try {
            String query = "SELECT * FROM item WHERE type =?";
            itemList = this.jdbcTemplate.query(query, new Object[]{itemType}, new ItemMapper());

        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(String.format("Failed to get items by type: %s", itemType));
            return null;
        } catch (DataAccessException e) {
            String error = "Failed to get ItemsByType: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return itemList;

//        region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        List<Item> itemList = new ArrayList<>();
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement("SELECT * FROM item WHERE item_type =?");
//            ps.setString(1, itemType);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                Item item = new Item();
//                getItemFromResultSet(item, rs);
////                item.setId(rs.getLong("id"))
////                        .setTitle(rs.getString("title"))
////                        .setDescription(rs.getString("description"))
////                        .setPrice(rs.getDouble("price"))
////                        .setPhotoURL(rs.getString("photo_url"))
////                        .setStatus(rs.getBoolean("status"))
////                        .setItemType(ItemType.valueOf(rs.getString("type"))
////                        .setAdditionDate(rs.getTimestamp("addition_date"));
//                itemList.add(item);
//            }
//        } catch (SQLException e) {
//            String error = "Failed to get ItemsByType: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(rs, ps, conn);
//        }
//        return itemList;

//        endregion
    }


    /**
     * @param minPrice
     * @param maxPrice
     * @return
     * @see ItemDao#getItemsForGivenPriceRange(Double, Double)
     */
    @Override
    public List<Item> getItemsForGivenPriceRange(Double minPrice, Double maxPrice) {

        List<Item> itemList;
        try {
            String query = "SELECT * FROM item WHERE status=0 AND price BETWEEN ? AND ?";
            itemList = this.jdbcTemplate.query(query, new Object[]{minPrice, maxPrice}, new ItemMapper());

        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(String.format("Failed to get items for given price range: %s %s", minPrice, maxPrice));
            return Collections.emptyList();
        } catch (DataAccessException e) {
            String error = "Failed to get items by the given price range: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return itemList;

//        region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        List<Item> itemList = new ArrayList<>();
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement("SELECT * FROM item WHERE status=0 AND price between ? AND ? ");
//            ps.setDouble(1, minPrice);
//            ps.setDouble(2, maxPrice);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                Item item = new Item();
//                getItemFromResultSet(item, rs);
////                item.setId(rs.getLong("id"))
////                        .setTitle(rs.getString("title"))
////                        .setDescription(rs.getString("description"))
////                        .setPrice(rs.getDouble("price"))
////                        .setPhotoURL(rs.getString("photo_url"))
////                        .setStatus(rs.getBoolean("status"))
////                        .setItemType(ItemType.valueOf(rs.getString("type"))
////                        .setAdditionDate(rs.getTimestamp("addition_date"));
//                itemList.add(item);
//            }
//        } catch (SQLException e) {
//            String error = "Failed to get items by the given price range: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(rs, ps, conn);
//        }
//        return itemList;

//        endregion
    }

    /**
     * @param artistId
     * @param limit
     * @return
     * @see ItemDao#getArtistItems(Long, Long, Long)
     */
    @Override
    public List<Item> getArtistItems(Long artistId, Long itemId, Long limit) {

        List<Item> itemList;
        try {
            String query = "SELECT * FROM item WHERE artist_id=? AND id!=? LIMIT ?";
            itemList = this.jdbcTemplate.query(query, new Object[]{artistId, itemId, limit}, new ItemMapper());

        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(String.format("Failed to get artist items: %s", artistId));
            return null;
        } catch (DataAccessException e) {
            String error = "Failed to get items for the given artistId: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return itemList;

//        region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        List<Item> itemList = new ArrayList<>();
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement("SELECT * FROM item WHERE artist_id=? and id!=? LIMIT ?");
//            ps.setLong(1, artistId);
//            ps.setLong(2, itemId);
//            ps.setLong(3, limit);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                Item item = new Item();
//                getItemFromResultSet(item, rs);
////                item.setId(rs.getLong("id"))
////                        .setTitle(rs.getString("title"))
////                        .setDescription(rs.getString("description"))
////                        .setPrice(rs.getDouble("price"))
////                        .setPhotoURL(rs.getString("photo_url"))
////                        .setStatus(rs.getBoolean("status"))
////                        .setItemType(ItemType.valueOf(rs.getString("type"))
////                        .setAdditionDate(rs.getTimestamp("addition_date"));
//                itemList.add(item);
//            }
//        } catch (SQLException e) {
//            String error = "Failed to get items for the given artistId: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(rs, ps, conn);
//        }
//        return itemList;

//        endregion
    }


    /**
     * @param id
     * @param item
     * @see ItemDao#updateItem(Long, Item)
     */
    @Override
    public void updateItem(Long id, Item item) {

        try {
            String query = "UPDATE item SET title=?, description=?, price=?, type=? , status = ? WHERE id=?";
            Object[] args = new Object[]{item.getTitle(), item.getDescription(), item.getPrice(), item.getItemType().getType(),item.getStatus(), item.getId()};

            int rowsAffected = jdbcTemplate.update(query, args);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to update Item");
            }
        } catch (DataAccessException e) {
            String error = "Failed to update Item:  %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }

//        region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement(
//                    "UPDATE item SET title=?, description=?, price=?, type=? WHERE id=?");
//            ps.setString(1, item.getTitle());
//            ps.setString(2, item.getDescription());
//            ps.setDouble(3, item.getPrice());
//            ps.setString(4, item.getItemType().getType());
//            ps.setLong(5, id);
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            String error = "Failed to update Item:  %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(ps, conn);
//        }

//        endregion
    }


    /**
     * @param id
     * @see ItemDao#deleteItem(Long)
     */
    @Override
    public Boolean deleteItem(Long id) {

        Boolean status;
        try {
            String query = "DELETE FROM item WHERE id=?";

            int rowsAffected = jdbcTemplate.update(query, id);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to delete Item");
            } else {
                status = true;
            }
        } catch (DataAccessException e) {
            String error = "Failed to delete Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return status;

        //region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement("DELETE FROM item WHERE id=?");
//            ps.setLong(1, id);
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            String error = "Failed to delete Item: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(ps, conn);
//        }

        //endregion
    }


//    private void getItemFromResultSet(Item item, ResultSet rs) throws SQLException {
//        item.setId(rs.getLong("id"))
//                .setTitle(rs.getString("title"))
//                .setDescription(rs.getString("description"))
//                .setPhotoURL(rs.getString("photo_url"))
//                .setPrice(rs.getDouble("price"))
//                .setArtistId(rs.getLong("artist_id"))
//                .setStatus(rs.getBoolean("status"))
//                .setItemType(ItemType.valueOf(rs.getString("type"))
//                .setAdditionDate(rs.getTimestamp("addition_date")));
//    }
//
//    private void getItemFromResultSet(Item item, Map<String, Object> itemRow) {
//        item.setId(Long.parseLong(String.valueOf(itemRow.get("id"))))
//                .setTitle(String.valueOf(itemRow.get("title")))
//                .setDescription(String.valueOf(itemRow.get("description")))
//                .setPhotoURL(String.valueOf(itemRow.get("photo_url")))
//                .setPrice(Double.parseDouble(String.valueOf(itemRow.get("price"))))
//                .setArtistId(Long.parseLong(String.valueOf(itemRow.get("artist_id"))))
//                .setStatus(Boolean.parseBoolean(String.valueOf(itemRow.get("status"))))
//                .setItemType(ItemType.valueOf(String.valueOf(itemRow.get("type")))
//                .setAdditionDate(rs.getTimestamp("addition_date")));
//    }
}