package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ItemDao;
import am.aca.wftartproject.dao.rowmappers.ItemMapper;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.ItemType;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ASUS on 27-May-17
 */
public class ItemDaoImpl extends BaseDaoImpl implements ItemDao {

    private static final Logger LOGGER = Logger.getLogger(ItemDaoImpl.class);

    public ItemDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * @see ItemDao#addItem(Long, Item)
     * @param artistID
     * @param item
     */
    @Override
    public void addItem(Long artistID, Item item) {

        try {
            
            KeyHolder keyHolder = new GeneratedKeyHolder();

            String query = "INSERT INTO item(title, description, price, artist_id, photo_url, status, type) VALUES (?,?,?,?,?,?,?)";

            PreparedStatementCreator psc = con -> {
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, item.getTitle());
                ps.setString(2, item.getDescription());
                ps.setDouble(3, item.getPrice());
                ps.setLong(4, artistID);
                ps.setString(5, item.getPhotoURL());
                ps.setBoolean(6, item.isStatus());
                ps.setString(7, item.getItemType().getType());
                return ps;
            };

            int rowsAffected = jdbcTemplate.update(psc, keyHolder);
            if (rowsAffected > 0) {
                item.setId(keyHolder.getKey().longValue());
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
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement(
//                    "INSERT INTO item(title, description, price, artist_id, photo_url, status, type) VALUES (?,?,?,?,?,?,?)",
//                    Statement.RETURN_GENERATED_KEYS);
//            ps.setString(1, item.getTitle());
//            ps.setString(2, item.getDescription());
//            ps.setDouble(3, item.getPrice());
//            ps.setLong(4, artistID);
//            ps.setString(5, item.getPhotoURL());
//            ps.setBoolean(6, item.isStatus());
//            ps.setString(7, item.getItemType().getType());
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
     * @see ItemDao#findItem(Long)
     * @param id
     * @return
     */
    @Override
    public Item findItem(Long id) {

        Item item;
        try {
            
            String query = "SELECT * FROM item WHERE id = ?";

            item = jdbcTemplate.queryForObject(query, new Object[]{id}, (rs, rowNum) -> {
               return new ItemMapper().mapRow(rs,rowNum);
            });
        } catch (DataAccessException e) {
            String error = "Failed to get Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return item;

        //region <Version with Simple JDBC>

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
////                        .setItemType(ItemType.valueOf(rs.getString("type")));
//            }
//        } catch (SQLException e) {
//            String error = "Failed to get Item: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(rs, ps, conn);
//        }
//        return item;

        //endregion
    }


    /**
     * @see ItemDao#getRecentlyAddedItems(int)
     * @param limit
     * @return
     */
    @Override
    public List<Item> getRecentlyAddedItems(int limit) {

        List<Item> itemList = new ArrayList<>();
        try {

            String query = "SELECT it.* FROM item it ORDER BY 1 DESC LIMIT ?";
            itemList = this.jdbcTemplate.query(query, new Object[]{limit}, new ItemMapper());

            //region <Version with Simple JDBC>

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
////                        .setItemType(ItemType.valueOf(rs.getString("type")));
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

            //endregion
        }
        catch (DataAccessException e) {
            String error = "Failed to get ItemsByTitle: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
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

        List<Item> itemList = new ArrayList<>();
        try {
            
            String query = "SELECT * FROM item WHERE title=?";

            itemList = this.jdbcTemplate.query(query, new Object[]{title}, new ItemMapper());

        } catch (DataAccessException e) {
            String error = "Failed to get ItemsByTitle: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return itemList;


        //region <Version with Simple JDBC>

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
////                        .setItemType(ItemType.valueOf(rs.getString("type")));
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

        //endregion
    }


    /**
     * @see ItemDao#getItemsByType(String)
     * @param itemType
     * @return
     */
    @Override
    public List<Item> getItemsByType(String itemType) {

        List<Item> itemList = new ArrayList<>();
        try {
            
            String query = "SELECT * FROM item WHERE type =?";

            itemList = this.jdbcTemplate.query(query, new Object[]{itemType}, new ItemMapper());

        } catch (DataAccessException e) {
            String error = "Failed to get ItemsByType: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return itemList;

        //region <Version with Simple JDBC>

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
////                        .setItemType(ItemType.valueOf(rs.getString("type")));
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

        //endregion
    }


    /**
     * @see ItemDao#getItemsForGivenPriceRange(Double, Double)
     * @param minPrice
     * @param maxPrice
     * @return
     */
    @Override
    public List<Item> getItemsForGivenPriceRange(Double minPrice, Double maxPrice) {

        List<Item> itemList = new ArrayList<>();
        try {
            
            String query = "SELECT * FROM item WHERE status=0 AND price BETWEEN ? AND ?";

            itemList = this.jdbcTemplate.query(query, new Object[]{minPrice,maxPrice}, new ItemMapper());

        } catch (DataAccessException e) {
            String error = "Failed to get items by the given price range: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return itemList;

        //region <Version with Simple JDBC>

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
////                        .setItemType(ItemType.valueOf(rs.getString("type")));
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

        //endregion
    }

    /**
     * @see ItemDao#getArtistItems(Long, Long, Long)
     * @param artistId
     * @param limit
     * @return
     */
    @Override
    public List<Item> getArtistItems(Long artistId, Long itemId, Long limit) {

        List<Item> itemList = new ArrayList<>();
        try {
            
            String query = "SELECT * FROM item WHERE artist_id=? AND id!=? LIMIT ?";

            itemList = this.jdbcTemplate.query(query, new Object[]{artistId,itemId,limit}, new ItemMapper());

        } catch (DataAccessException e) {
            String error = "Failed to get items for the given artistId: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return itemList;

        //region <Version with Simple JDBC>

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
////                        .setItemType(ItemType.valueOf(rs.getString("type")));
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

        //endregion
    }


    /**
     * @see ItemDao#updateItem(Long, Item)
     * @param id
     * @param item
     */
    @Override
    public void updateItem(Long id, Item item) {

        try {
            
            String query = "UPDATE item SET title=?, description=?, price=?, type=? WHERE id=?";
            Object[] args = new Object[]{item.getTitle(), item.getDescription(), item.getPrice(), item.getItemType().getType(), id};

            int rowsAffected = jdbcTemplate.update(query, args);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to update Item");
            }

        } catch (DataAccessException e) {
            String error = "Failed to update Item:  %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }

        //region <Version with Simple JDBC>

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

        //endregion
    }


    /**
     * @see ItemDao#deleteItem(Long)
     * @param id
     */
    @Override
    public Boolean deleteItem(Long id) {

        Boolean status = false;
        try {
            
            String query = "DELETE FROM item WHERE id=?";
            int rowsAffected = jdbcTemplate.update(query, id);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to delete Item");
            }else{
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

}