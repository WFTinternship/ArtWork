package am.aca.wftartproject.dao;

import am.aca.wftartproject.entity.ShoppingCard;

/**
 * Created by ASUS on 27-May-17
 */
public interface ShoppingCardDao {

    /**
     * Adds shoppingCard to the database.
     *
     * @param shoppingCard
     */
    void addShoppingCard(ShoppingCard shoppingCard);

    /**
     * Gets shoppingCard from database.
     *
     * @param id
     * @return
     */
    ShoppingCard getShoppingCard(Long id);

    /**
     * Updates shoppingCard in database.
     * @param shoppingCard
     */
    Boolean updateShoppingCard(ShoppingCard shoppingCard);

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
     * @param shoppingCard
     */
    Boolean deleteShoppingCard(ShoppingCard shoppingCard);

    /**
     * Deletes shoppingCard by buyerId
     *
     * @param buyerId
     * @return
     */

}
