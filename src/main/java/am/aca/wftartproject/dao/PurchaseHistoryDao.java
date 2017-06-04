package am.aca.wftartproject.dao;

import am.aca.wftartproject.model.PurchaseHistory;

import java.util.List;

/**
 * Created by ASUS on 27-May-17
 */
public interface PurchaseHistoryDao {

    /**
     * Adds purchased item to the database.
     *
     * @param purchaseHistory
     */
    void addPurchase(PurchaseHistory purchaseHistory);

    /**
     * Gets purchase item by userId and itemId
     *
     * @param userId
     * @param itemId
     * @return
     */
    PurchaseHistory getPurchase(Long userId, Long itemId);

    /**
     * Gets all purchase items by userId
     * @param userId
     * @return
     */
    List<PurchaseHistory> getPurchase(Long userId);

    /**
     * Deletes purchase item by userId and itemId
     * @param userId
     * @param itemId
     */
    void deletePurchase(Long userId, Long itemId);



}
