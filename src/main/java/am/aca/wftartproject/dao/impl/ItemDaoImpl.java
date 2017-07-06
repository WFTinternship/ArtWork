package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ItemDao;
import am.aca.wftartproject.dao.rowmappers.ItemMapper;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.Item;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SuppressWarnings("ALL")
@Component
public class ItemDaoImpl extends BaseDaoImpl implements ItemDao {

    private static final Logger LOGGER = Logger.getLogger(ItemDaoImpl.class);

    private SessionFactory sessionFactory;

    @Autowired
    public ItemDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @param item
     * @see ItemDao#addItem(Item)
     */
    @Override
    public void addItem(Item item) {
        Transaction tx = null;
        try {
            item.setAdditionDate(getCurrentDateTime());
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.getTransaction();
            if(!tx.isActive()){
                tx = session.beginTransaction();
            }
            session.save(item);
            tx.commit();
            LOGGER.info("Person saved successfully, Person Details=" + item);
        } catch (Exception e) {
            String error = "Failed to add Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
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
        Transaction tx =null;
        Item item = null;
        try {
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.getTransaction();
            if(!tx.isActive()){
                tx = session.beginTransaction();
            }
            item = (Item) session.get(Item.class, id);
            tx.commit();
        } catch (Exception e) {
            String error = "Failed to get Item by id: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
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
        Transaction tx = null;
        List<Item> itemList = null;
        try {
            Session session = sessionFactory.getCurrentSession();
            tx = session.getTransaction();
            if(!tx.isActive()){
                tx = session.beginTransaction();
            }
            itemList = (List<Item>) session.createQuery(
                    "SELECT c FROM Item c").setMaxResults(limit)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            String error = "Failed to get recently added items: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
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
////                        .setStatus(rs.getBoolean("result"))
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
        //OLD VERSION WITH SPRING JDBC
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
////                        .setStatus(rs.getBoolean("result"))
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
        Transaction tx = null;
        List<Item> itemList = null;
        try {
            Session session = sessionFactory.getCurrentSession();
            tx = session.getTransaction();
            if(!tx.isActive()){
                tx = session.beginTransaction();
            }
            itemList = (List<Item>) session.createQuery(
                    "SELECT c FROM Item c where c.itemType = :itemType").setParameter("itemType", itemType).setMaxResults(100)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            String error = "Failed to get Items by type: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
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
////                        .setStatus(rs.getBoolean("result"))
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
        //OLD VERSION WITH SPRING JDBC 
        List<Item> itemList;
        try {
            String query = "SELECT * FROM item WHERE result=0 AND price BETWEEN ? AND ?";
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

//        region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        List<Item> itemList = new ArrayList<>();
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement("SELECT * FROM item WHERE result=0 AND price between ? AND ? ");
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
////                        .setStatus(rs.getBoolean("result"))
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
     * @return
     * @see ItemDao#getArtistItems(Long)
     */
    @Override
    public List<Item> getArtistItems(Long artistId) {
        List<Item> list = null;
        Transaction tx = null;
        Session session = sessionFactory.getCurrentSession();
        tx = session.getTransaction();
        if(!tx.isActive()){
            tx = session.beginTransaction();
        }
        try{
            list =  (List<Item>) sessionFactory.getCurrentSession().createQuery(
                    "SELECT c FROM Item c WHERE c.artist_id = :artist_id")
                    .setParameter("artist_id", artistId)
                    .getResultList();
            tx.commit();
        }
        catch (Exception e) {
            String error = "Failed to get Items by type: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }


        return list;
    }


    /**
     * @param item
     * @see ItemDao#updateItem(Item)
     */
    @Override
    public Boolean updateItem(Item item) {
        Transaction tx = null;
        Boolean result = false;
        try {
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.getTransaction();
            if(!tx.isActive()){
                tx = session.beginTransaction();
            }
            session.saveOrUpdate(item);
            tx.commit();
            result = true;
            LOGGER.info("Item updated successfully, Item Details=" + item);
        } catch (Exception e) {
            String error = "Failed to update Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }

        return result;

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
        Transaction tx = null;
        Boolean result = false;
        try {

            Session session = this.sessionFactory.getCurrentSession();
            tx = session.getTransaction();
            if(!tx.isActive()){
                tx = session.beginTransaction();
            }
            Item item = (Item) session.get(Item.class, id);
            session.delete(item);
            tx.commit();
            result = true;
            LOGGER.info("Item deleted successfully");
        } catch (Exception e) {
            String error = "Failed to delete Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
        return result;
    }
}