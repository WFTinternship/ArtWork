package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.dao.PurchaseHistoryDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.entity.PurchaseHistory;
import am.aca.wftartproject.service.PurchaseHistoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by surik on 6/1/17
 */
@Service
@Transactional(readOnly = true)
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {

    private static final Logger LOGGER = Logger.getLogger(PurchaseHistoryServiceImpl.class);

    private final PurchaseHistoryDao purchaseHistoryDao;

    @Autowired
    public PurchaseHistoryServiceImpl(PurchaseHistoryDao purchaseHistoryDao) {
        this.purchaseHistoryDao = purchaseHistoryDao;
    }


    /**
     * @see PurchaseHistoryService#addPurchase(PurchaseHistory)
     * @param purchaseHistory
     */
    @Transactional(readOnly = false)
    @Override
    public void addPurchase(PurchaseHistory purchaseHistory) {
        if (purchaseHistory == null || !purchaseHistory.isValidPurchaseHistory()) {
            LOGGER.error(String.format("PurchaseHistory is not valid: %s", purchaseHistory));
            throw new InvalidEntryException("Invalid purchaseHistory");
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
     * @see PurchaseHistoryService#getPurchase(Long)
     * @param itemId
     * @return
     */
    @Override
    public PurchaseHistory getPurchase(Long itemId) {
        if (itemId == null || itemId < 0) {
            LOGGER.error(String.format("ItemId is not valid: %s", itemId));
            throw new InvalidEntryException("Invalid itemId");
        }

        try {
            return purchaseHistoryDao.getPurchase(itemId);
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
    public List<PurchaseHistory> getPurchaseList(Long userId) {

        if (userId == null || userId < 0){
            LOGGER.error(String.format("UserId is not valid: %s", userId));
            throw new InvalidEntryException("Invalid userId");
        }

        try {
            return purchaseHistoryDao.getPurchaseList(userId);
        }catch (DAOException e) {
            String error = "Failed to get purchase history: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @see PurchaseHistoryService#deletePurchase(PurchaseHistory)
     * @param purchaseHistory
     */
    @Transactional(readOnly = false)
    @Override
    public void deletePurchase(PurchaseHistory purchaseHistory) {
        if (purchaseHistory == null || !purchaseHistory.isValidPurchaseHistory()){
            LOGGER.error(String.format("UserId is not valid: %s", purchaseHistory));
            throw new InvalidEntryException("Invalid userId");
        }

        try {
            if (!purchaseHistoryDao.deletePurchase(purchaseHistory)){
                throw new DAOException("Failed to delete purchase history");
            }
        }catch (DAOException e) {
            String error = "Failed to delete purchase history: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }
}
