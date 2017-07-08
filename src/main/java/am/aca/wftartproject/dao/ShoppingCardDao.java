package am.aca.wftartproject.dao;

import am.aca.wftartproject.model.ShoppingCard;

/**
 * Created by ASUS on 27-May-17
 */
public interface ShoppingCardDao {

    /**
     * Adds shoppingCard to the springconfig.database.
     *
     * @param userId
     * @param shoppingCard
     */
    void addShoppingCard(Long userId, ShoppingCard shoppingCard);

    /**
     * Gets shoppingCard from springconfig.database.
     *
     * @param id
     * @return
     */
    ShoppingCard getShoppingCard(Long id);

    /**
     * Gets shoppingCard from database by buyerId
     *
     * @param buyerId
     * @return
     */
    ShoppingCard getShoppingCardByBuyerId(Long buyerId);

    /**
     * Updates shoppingCard in springconfig.database.
     *
     * @param id
     * @param shoppingCard
     */
    Boolean updateShoppingCard(Long id, ShoppingCard shoppingCard);

    /**
     * Debits balance for item buying
     *
     * @param buyerId
     * @param itemPrice
     * @return
     */
    Boolean debitBalanceForItemBuying(Long buyerId, Double itemPrice);

    /**
     * Deletes shoppingCard by id.
     *
     * @param id
     */
    Boolean deleteShoppingCard(Long id);

    /**
     * Deletes shoppingCard by buyerId
     *
     * @param buyerId
     * @return
     */
    Boolean deleteShoppingCardByBuyerId(Long buyerId);
}
