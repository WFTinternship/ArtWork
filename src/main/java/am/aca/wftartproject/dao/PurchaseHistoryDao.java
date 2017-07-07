package am.aca.wftartproject.dao;

import am.aca.wftartproject.entity.PurchaseHistory;

import java.util.List;

/**
 * Created by ASUS on 27-May-17
 */
public interface PurchaseHistoryDao {


    /**
     * Adds purchased item to the springconfig.database.
     *
     * @param purchaseHistory
     */
    void addPurchase(PurchaseHistory purchaseHistory);


    /**
     * Gets purchase item by userId and itemId
     *
     * @param itemId
     * @return
     */
    PurchaseHistory getPurchase(Long itemId);


    /**
     * Gets all purchase items by userId
     *
     * @param userId
     * @return
     */
    List<PurchaseHistory> getPurchaseList(Long userId);


    /**
     * Deletes purchase item by userId and itemId
     * @param purchaseHistory
     */
    Boolean deletePurchase(PurchaseHistory purchaseHistory);


}