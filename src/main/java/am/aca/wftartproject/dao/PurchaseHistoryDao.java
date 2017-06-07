package am.aca.wftartproject.dao;

import am.aca.wftartproject.model.PurchaseHistory;

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
     * Adds purchased item to the database.
     *
     * @param user_id,
     * @param item_id
     */
    PurchaseHistory getPurchaseHistory(Long user_id,Long  item_id );

    /**
     * Adds purchased item to the database.
     *
     * @param user_id,
     * @param item_id
     */
    Boolean deletePurchaseHistory(Long user_id,Long  item_id );
}
