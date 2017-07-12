package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.PurchaseHistoryDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.entity.PurchaseHistory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Component
public class PurchaseHistoryDaoImpl extends BaseDaoImpl implements PurchaseHistoryDao {

    private static final Logger LOGGER = Logger.getLogger(PurchaseHistoryDaoImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @param purchaseHistory
     * @see PurchaseHistoryDao#addPurchase(PurchaseHistory)
     */
    @Override
    public void addPurchase(PurchaseHistory purchaseHistory) {
        try {
            entityManager.persist(purchaseHistory);
            entityManager.flush();
        } catch (Exception e) {
            String error = "Failed to add Purchase: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }

    }

    /**
     * @param itemId
     * @return
     * @see PurchaseHistoryDao#getPurchase( Long)
     */
    @Override
    public PurchaseHistory getPurchase(Long itemId) {
        PurchaseHistory purchaseHistory = null;
        try {
           purchaseHistory =  (PurchaseHistory) entityManager.createQuery("SELECT c FROM PurchaseHistory c WHERE c.item_id = :itemId").setParameter("itemId",itemId).getSingleResult();
            entityManager.flush();
        } catch (Exception e) {
            String error = "Failed to add Purchase: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }

        return purchaseHistory;
    }


    /**
     * @param userId
     * @return
     * @see PurchaseHistoryDao#getPurchase(Long)
     */
    @Override
    public List<PurchaseHistory> getPurchaseList(Long userId) {
        List<PurchaseHistory> purchaseHistoryList ;
        try{
            purchaseHistoryList = (List<PurchaseHistory>)entityManager.createQuery(
                    "SELECT c FROM PurchaseHistory c WHERE c.userId = :user_id")
                    .setParameter("user_id", userId)
                    .getResultList();
            entityManager.flush();
        }
        catch (Exception e) {
            String error = "Failed to add Purchase: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        return purchaseHistoryList;

    }


    /**
     * @return
     * @see PurchaseHistoryDao#deletePurchase(PurchaseHistory)
     */
    @Override
    public Boolean deletePurchase(PurchaseHistory purchaseHistory) {
        Boolean result = false;
        try {
            entityManager.find(PurchaseHistory.class,purchaseHistory.getId());
            entityManager.remove(purchaseHistory);
            result = true;
            LOGGER.info("PurchaseHistory deleted successfully");
        } catch (Exception e) {
            String error = "Failed to delete PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        return result;

    }
}
