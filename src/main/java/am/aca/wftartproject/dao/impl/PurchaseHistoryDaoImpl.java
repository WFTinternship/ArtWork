package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.PurchaseHistoryDao;
import am.aca.wftartproject.dao.rowmappers.PurchaseHistoryMapper;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.PurchaseHistory;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PurchaseHistoryDaoImpl extends BaseDaoImpl implements PurchaseHistoryDao {

    private static final Logger LOGGER = Logger.getLogger(PurchaseHistoryDaoImpl.class);

    private SessionFactory sessionFactory;

    @Autowired
    public PurchaseHistoryDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @param purchaseHistory
     * @see PurchaseHistoryDao#addPurchase(PurchaseHistory)
     */
    @Override
    public void addPurchase(PurchaseHistory purchaseHistory) {
        Transaction tx = null;
        try {
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.getTransaction();
            if(!tx.isActive()){
                tx = session.beginTransaction();
            }
            purchaseHistory.setPurchaseDate(getCurrentDateTime());
            session.save(purchaseHistory);
            tx.commit();
        } catch (Exception e) {
            String error = "Failed to add Purchase: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }

//        region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement(
//                    "INSERT INTO purchase_history(userId, itemId, purchase_date) VALUES (?,?,?)");
//            ps.setLong(1, purchaseHistory.getUserId());
//            ps.setLong(2, purchaseHistory.getItemId());
////            Calendar cal = Calendar.getInstance();
////            Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
//            ps.setTimestamp(3, getCurrentDateTime());
//            if (ps.executeUpdate() > 0) {
//                purchaseHistory.setPurchaseDate(getCurrentDateTime());
//            }
//        } catch (SQLException e) {
//            purchaseHistory.setUserId(null);
//            String error = "Failed to add PurchaseHistory: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(String.format(error, e.getMessage()));
//        } finally {
//            closeResources(ps, conn);
//        }

//        endregion
    }

    /**
     * @param userId
     * @param itemId
     * @return
     * @see PurchaseHistoryDao#getPurchase(Long, Long)
     */
    @Override
    public PurchaseHistory getPurchase(Long userId, Long itemId) {
        //OLD VERSION WITH SPRING JDBC
        try {
            String query = "SELECT * FROM purchase_history WHERE item_id = ? AND  user_id = ? ";
            return jdbcTemplate.queryForObject(query, new Object[]{itemId, userId},
                    (rs, rowNum) -> new PurchaseHistoryMapper().mapRow(rs, rowNum));

        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(String.format("Failed to get purchase item by userId and itemId: %s %s", userId, itemId));
            return null;
        } catch (DataAccessException e) {
            String error = "Failed to get PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }


//        region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        PurchaseHistory purchaseHistory = new PurchaseHistory();
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement(
//                    "SELECT * FROM purchase_history WHERE itemId = ? AND  userId = ? ");
//            ps.setLong(1, itemId);
//            ps.setLong(2, userId);
//            rs = ps.executeQuery();
//            if (rs.next()) {
//                purchaseHistory.setItemId(rs.getLong("itemId"))
//                        .setUserId(rs.getLong("userId"))
//                        .setPurchaseDate(rs.getTimestamp("purchase_date"));
//            } else {
//                return null;
//            }
//        } catch (SQLException e) {
//            String error = "Failed to get PurchaseHistory: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(rs, ps, conn);
//        }
//        return purchaseHistory;

//        endregion
    }


    /**
     * @param userId
     * @return
     * @see PurchaseHistoryDao#getPurchase(Long)
     */
    @Override
    public List<PurchaseHistory> getPurchase(Long userId) {
        Transaction tx = null;
        List<PurchaseHistory> purchaseHistoryList;
        try{
            Session session = sessionFactory.getCurrentSession();
            tx = session.getTransaction();
            if(!tx.isActive()){
                tx = session.beginTransaction();
            }
            purchaseHistoryList = (List<PurchaseHistory>) sessionFactory.getCurrentSession().createQuery(
                    "SELECT c FROM PurchaseHistory c WHERE c.userId = :user_id")
                    .setParameter("user_id", userId)
                    .getResultList();
            tx.commit();
        }
        catch (Exception e) {
            String error = "Failed to add Purchase: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
        return purchaseHistoryList;

//        region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        PurchaseHistory purchaseHistory = new PurchaseHistory();
//        List<PurchaseHistory> purchaseHistoryList = new ArrayList<>();
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement("SELECT * FROM purchase_history WHERE userId = ?");
//            ps.setLong(1, userId);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                purchaseHistory.setUserId(rs.getLong("user_id"))
//                        .setItemId(rs.getLong("item_id"))
//                        .setPurchaseDate(rs.getTimestamp("purchase_date"));
//
//                purchaseHistoryList.add(purchaseHistory);
//            }
//        } catch (SQLException e) {
//            String error = "Failed to get PurchaseHistory: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(rs, ps, conn);
//        }
//        return purchaseHistoryList;

//        endregion
    }


    /**
     * @return
     * @see PurchaseHistoryDao#deletePurchase(PurchaseHistory)
     */
    @Override
    public Boolean deletePurchase(PurchaseHistory purchaseHistory) {
        Transaction tx = null;
        Boolean result = false;
        try {

            Session session = this.sessionFactory.getCurrentSession();
            tx = session.getTransaction();
            if(!tx.isActive()){
                tx = session.beginTransaction();
            }
            session.delete(purchaseHistory);
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

        //region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        Boolean success = false;
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement(
//                    " DELETE FROM purchase_history WHERE userId=? and itemId = ? ");
//            ps.setLong(1, userId);
//            ps.setLong(2, itemId);
//            if (ps.executeUpdate() > 0) {
//                success = true;
//            }
//        } catch (SQLException e) {
//            String error = "Failed to delete PurchaseHistory: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(ps, conn);
//        }
//        return success;

//        endregion
    }
}
