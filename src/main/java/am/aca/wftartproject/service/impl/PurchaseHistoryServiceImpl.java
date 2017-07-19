package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.repository.PurchaseHistoryRepo;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.entity.PurchaseHistory;
import am.aca.wftartproject.service.PurchaseHistoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by surik on 6/1/17
 */
@Service
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {

    private static final Logger LOGGER = Logger.getLogger(PurchaseHistoryServiceImpl.class);
    @Autowired
    private PurchaseHistoryRepo purchaseHistoryRepo;



    /**
     * @see PurchaseHistoryService#addPurchase(PurchaseHistory)
     * @param purchaseHistory
     */

    @Override
    public void addPurchase(PurchaseHistory purchaseHistory) {
        if (purchaseHistory == null || !purchaseHistory.isValidPurchaseHistory()) {
            LOGGER.error(String.format("PurchaseHistory is not valid: %s", purchaseHistory));
            throw new InvalidEntryException("Invalid purchaseHistory");
        }

        try {
            purchaseHistoryRepo.saveAndFlush(purchaseHistory);
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
            return purchaseHistoryRepo.findByPurchaseItem_Id(itemId);
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
            return purchaseHistoryRepo.getAllByAbsUser_Id(userId);
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

    @Override
    public void deletePurchase(PurchaseHistory purchaseHistory) {
        if (purchaseHistory == null || !purchaseHistory.isValidPurchaseHistory()){
            LOGGER.error(String.format("UserId is not valid: %s", purchaseHistory));
            throw new InvalidEntryException("Invalid userId");
        }

        try {
            purchaseHistoryRepo.delete(purchaseHistory);
        }catch (DAOException e) {
            String error = "Failed to delete purchase history: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }
}
