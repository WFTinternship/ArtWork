package am.aca.wftartproject.service;

/**
 * Created by surik on 6/1/17
 */
public interface PurchaseHistoryService {

    /**
     * Adds purchased item to the database.
     *
     * @param userId
     * @param itemId
     */
    void addPurchase(Long userId, Long itemId);

}
