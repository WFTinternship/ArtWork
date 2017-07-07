package am.aca.wftartproject.service;

import am.aca.wftartproject.entity.PurchaseHistory;

import java.util.List;

/**
 * Created by surik on 6/1/17
 */
public interface PurchaseHistoryService {

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
     *
     * @param purchaseHistory
     */
    void deletePurchase(PurchaseHistory purchaseHistory);


}
