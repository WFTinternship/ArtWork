package am.aca.wftartproject.dao;

/**
 * Created by ASUS on 27-May-17.
 */
public interface PurchaseHistoryDao {

    /**
     * Adds purchased item to the database.
     *
     * @param userId
     * @param itemId
     */
    void addPurchase(Long userId, Long itemId);
}
