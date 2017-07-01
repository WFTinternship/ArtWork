package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ItemDao;
import am.aca.wftartproject.dao.rowmappers.ItemMapper;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.Item;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

/**
 * Created by ASUS on 27-May-17
 */
@Component
public class ItemDaoImpl extends BaseDaoImpl implements ItemDao {

    private static final Logger LOGGER = Logger.getLogger(ItemDaoImpl.class);

    @Autowired
    public ItemDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
                ps.setBoolean(6, item.getStatus());
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
            return null;
        } catch (DataAccessException e) {
            String error = "Failed to get items by the given price range: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
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
    }


    /**
     * @param artistId
     * @return
     * @see ItemDao#getArtistItems(Long, Long, Long)
     */
    @Override
    public List<Item> getAvailableItemsForGivenArtist(Long artistId) {

        List<Item> itemList;
        try {
            String query = "SELECT * FROM item WHERE artist_id=? AND status=FALSE";
            itemList = this.jdbcTemplate.query(query, new Object[]{artistId}, new ItemMapper());

        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(String.format("Failed to get artist items: %s", artistId));
            return null;
        } catch (DataAccessException e) {
            String error = "Failed to get items for the given artistId: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return itemList;
    }


    /**
     * @param id
     * @param item
     * @see ItemDao#updateItem(Long, Item)
     */
    @Override
    public void updateItem(Long id, Item item) {

        try {
            String query = "UPDATE item SET title=?, description=?, price=?, type=?, status=? WHERE id=?";
            Object[] args = new Object[]{item.getTitle(), item.getDescription(), item.getPrice(), item.getItemType().getType(), item.getStatus(), item.getId()};

            int rowsAffected = jdbcTemplate.update(query, args);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to update Item");
            }
        } catch (DataAccessException e) {
            String error = "Failed to update Item:  %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
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
    }
}