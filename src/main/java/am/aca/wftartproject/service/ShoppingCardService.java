package am.aca.wftartproject.service;

import am.aca.wftartproject.model.ShoppingCard;

/**
 * Created by ASUS on 03-Jun-17
 */
public interface ShoppingCardService {

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
     * Updates shoppingCard in springconfig.database.
     *
     * @param id
     * @param shoppingCard
     */
    void updateShoppingCard(Long id, ShoppingCard shoppingCard);

    /**
     * Debits balance for item buying
     *
     * @param buyerId
     * @param itemPrice
     * @return
     */
    void debitBalanceForItemBuying(Long buyerId, Double itemPrice);

    /**
     * Deletes shoppingCard by id.
     *
     * @param id
     */
    void deleteShoppingCard(Long id);
}
