package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.repository.PurchaseHistoryRepo;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.entity.PurchaseHistory;
import am.aca.wftartproject.service.PurchaseHistoryService;
import am.aca.wftartproject.util.ServiceHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseHistoryServiceImpl extends ServiceHelper implements PurchaseHistoryService {

    private static final Logger LOGGER = Logger.getLogger(PurchaseHistoryServiceImpl.class);


    private PurchaseHistoryRepo purchaseHistoryRepo;

    @Autowired
    public void setPurchaseHistoryRepo(PurchaseHistoryRepo purchaseHistoryRepo) {
        this.purchaseHistoryRepo = purchaseHistoryRepo;
    }

    /**
     * @param purchaseHistory is purchase history
     * @see PurchaseHistoryService#addPurchase(PurchaseHistory)
     */

    @Override
    public void addPurchase(PurchaseHistory purchaseHistory) {

        //check purchase history for validity
       checkPurchaseHistoryForValidity(purchaseHistory);

        //try to save purchase history into db
        try {
            purchaseHistoryRepo.saveAndFlush(purchaseHistory);
        } catch (DAOException e) {
            String error = "Failed to add purchase history: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param itemId is items id
     * @see PurchaseHistoryService#getPurchase(Long)
     */
    @Override
    public PurchaseHistory getPurchase(Long itemId) {

        //check item id for validity
       checkIdForValidity(itemId);

        //find purchase from db by item id
        try {
            return purchaseHistoryRepo.findByPurchaseItem_Id(itemId);
        } catch (DAOException e) {
            String error = "Failed to get purchase history: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param userId*
     * @see PurchaseHistoryService#getPurchase(Long)
     */
    @Override
    public List<PurchaseHistory> getPurchaseList(Long userId) {

        //check user id for validity
            checkIdForValidity(userId);

        //get purchase list from db by user id
        try {
            return purchaseHistoryRepo.getAllByAbsUser_Id(userId);
        } catch (DAOException e) {
            String error = "Failed to get purchase history: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param purchaseHistory is purchase history
     * @see PurchaseHistoryService#deletePurchase(PurchaseHistory)
     */

    @Override
    public void deletePurchase(PurchaseHistory purchaseHistory) {

        //check purchase history for validity
        checkPurchaseHistoryForValidity(purchaseHistory);

        //try to delete purchase history from db
        try {
            purchaseHistoryRepo.delete(purchaseHistory);
        } catch (DAOException e) {
            String error = "Failed to delete purchase history: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }
}
