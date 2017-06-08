package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.exception.ServiceException;
import am.aca.wftartproject.model.PurchaseHistory;
import am.aca.wftartproject.service.PurchaseHistoryService;
import am.aca.wftartproject.util.DBConnection;
import am.aca.wftartproject.dao.PurchaseHistoryDao;
import am.aca.wftartproject.dao.impl.PurchaseHistoryDaoImpl;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Created by surik on 6/1/17
 */
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {

    private static final Logger LOGGER = Logger.getLogger(PurchaseHistoryServiceImpl.class);
    private PurchaseHistoryDao purchaseHistoryDao = null;


    public PurchaseHistoryServiceImpl() throws SQLException, ClassNotFoundException {

        purchaseHistoryDao= new PurchaseHistoryDaoImpl(new DBConnection().getDBConnection(DBConnection.DBType.REAL));

    }


    /**
     * @param purchaseHistory
     *
     * @see PurchaseHistoryService#addPurchase(PurchaseHistory)
     */
    @Override
    public void addPurchase(PurchaseHistory purchaseHistory) {

        if (purchaseHistory == null || !purchaseHistory.isValidPurchaseHistroy()) {
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
     * @param userId
     * @param itemId
     * @return
     *
     * @see PurchaseHistoryService#getPurchase(Long, Long)
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
     * @param userId
     * @return
     *
     * @see PurchaseHistoryService#getPurchase(Long)
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
     * @param userId
     * @param itemId
     *
     * @see PurchaseHistoryService#deletePurchase(Long, Long)
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
