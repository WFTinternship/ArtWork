package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.PurchaseHistoryDao;
import am.aca.wftartproject.dao.rowmappers.PurchaseHistoryMapper;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.entity.PurchaseHistory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;


@Component
public class PurchaseHistoryDaoImpl extends BaseDaoImpl implements PurchaseHistoryDao {

    private static final Logger LOGGER = Logger.getLogger(PurchaseHistoryDaoImpl.class);

    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public PurchaseHistoryDaoImpl( EntityManagerFactory  entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    /**
     * @param purchaseHistory
     * @see PurchaseHistoryDao#addPurchase(PurchaseHistory)
     */
    @Override
    public void addPurchase(PurchaseHistory purchaseHistory) {
        EntityTransaction tx = null;
        EntityManager entityManager = null;
        try {
            entityManager = this.entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                entityManager.getTransaction().begin();
            }
            purchaseHistory.setPurchaseDate(getCurrentDateTime());
            entityManager.persist(purchaseHistory);
            entityManager.flush();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            String error = "Failed to add Purchase: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if(entityManager.isOpen()){
                entityManager.close();
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
     * @param itemId
     * @return
     * @see PurchaseHistoryDao#getPurchase( Long)
     */
    @Override
    public PurchaseHistory getPurchase(Long itemId) {
        PurchaseHistory purchaseHistory = null;
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = this.entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                entityManager.getTransaction().begin();
            }
           purchaseHistory =  (PurchaseHistory) entityManager.createQuery("SELECT c FROM PurchaseHistory c WHERE c.item_id = :itemId").setParameter("itemId",itemId).getSingleResult();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            String error = "Failed to add Purchase: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if(entityManager.isOpen()){
                entityManager.close();
            }
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
        return purchaseHistory;
    }


    /**
     * @param userId
     * @return
     * @see PurchaseHistoryDao#getPurchase(Long)
     */
    @Override
    public List<PurchaseHistory> getPurchaseList(Long userId) {
        EntityTransaction tx = null;
        EntityManager entityManager = null;
        List<PurchaseHistory> purchaseHistoryList;
        try{
            entityManager = this.entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                entityManager.getTransaction().begin();
            }
            purchaseHistoryList = (List<PurchaseHistory>)entityManager.createQuery(
                    "SELECT c FROM PurchaseHistory c WHERE c.userId = :user_id")
                    .setParameter("user_id", userId)
                    .getResultList();
            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
            String error = "Failed to add Purchase: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if(entityManager.isOpen()){
                entityManager.close();
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
        EntityTransaction tx = null;
        EntityManager entityManager = null;
        Boolean result = false;
        try {
            entityManager = this.entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                entityManager.getTransaction().begin();
            }
            purchaseHistory = entityManager.find(PurchaseHistory.class,purchaseHistory.getId());
            entityManager.remove(purchaseHistory);
            entityManager.flush();
            tx.commit();
            result = true;
            LOGGER.info("Item deleted successfully");
        } catch (Exception e) {
            tx.rollback();
            String error = "Failed to delete Item: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if(entityManager.isOpen()){
                entityManager.close();
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
