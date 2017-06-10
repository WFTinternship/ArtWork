package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.dao.PurchaseHistoryDao;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.exception.ServiceException;
import am.aca.wftartproject.model.PurchaseHistory;
import am.aca.wftartproject.service.PurchaseHistoryService;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by surik on 6/1/17
 */
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {

    private static final Logger LOGGER = Logger.getLogger(PurchaseHistoryServiceImpl.class);

    private PurchaseHistoryDao purchaseHistoryDao = null;

    public void setPurchaseHistoryDao(PurchaseHistoryDao purchaseHistoryDao) {
        this.purchaseHistoryDao = purchaseHistoryDao;
    }

//        public PurchaseHistoryServiceImpl() throws SQLException, ClassNotFoundException {
//        DataSource conn = new ConnectionFactory().getConnection(ConnectionModel.POOL).getProductionDBConnection();
//        purchaseHistoryDao= new PurchaseHistoryDaoImpl(conn);
//    }


    /**
     * @see PurchaseHistoryService#addPurchase(PurchaseHistory)
     * @param purchaseHistory
     */
    @Override
    public void addPurchase(PurchaseHistory purchaseHistory) {
        if (purchaseHistory == null || !purchaseHistory.isValidPurchaseHistory()) {
            LOGGER.error(String.format("purchaseHistory is invalid: %s", purchaseHistory));
            throw new ServiceException("Invalid purchaseHistory");
        }

        try {
            purchaseHistoryDao.addPurchase(purchaseHistory);
        } catch (DAOException e) {
            String error = "Failed to add purchase history: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @see PurchaseHistoryService#getPurchase(Long, Long)
     * @param userId
     * @param itemId
     * @return
     */
    @Override
    public PurchaseHistory getPurchase(Long userId, Long itemId) {
        if (userId == null || userId < 0){
            LOGGER.error(String.format("userId is invalid: %s", userId));
            throw new ServiceException("Invalid userId");
        }
        if (itemId == null || itemId < 0) {
            LOGGER.error(String.format("itemId is invalid: %s", itemId));
            throw new ServiceException("Invalid itemId");
        }

        try {
            return purchaseHistoryDao.getPurchase(userId, itemId);
        }catch (DAOException e) {
            String error = "Failed to get purchase history: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @see PurchaseHistoryService#getPurchase(Long)
     * @param userId
     * @return
     */
    @Override
    public List<PurchaseHistory> getPurchase(Long userId) {
        if (userId == null || userId < 0){
            LOGGER.error(String.format("userId is invalid: %s", userId));
            throw new ServiceException("Invalid userId");
        }

        try {
            return purchaseHistoryDao.getPurchase(userId);
        }catch (DAOException e) {
            String error = "Failed to get purchase history: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @see PurchaseHistoryService#deletePurchase(Long, Long)
     * @param userId
     * @param itemId
     */
    @Override
    public void deletePurchase(Long userId, Long itemId) {
        if (userId == null || userId < 0){
            LOGGER.error(String.format("userId is invalid: %s", userId));
            throw new ServiceException("Invalid userId");
        }
        if (itemId == null || itemId < 0) {
            LOGGER.error(String.format("itemId is invalid: %s", itemId));
            throw new ServiceException("Invalid itemId");
        }

        try {
            purchaseHistoryDao.deletePurchase(userId, itemId);
        }catch (DAOException e) {
            String error = "Failed to delete purchase history: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }
}
