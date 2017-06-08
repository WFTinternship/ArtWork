package am.aca.wftartproject.dao;

import am.aca.wftartproject.model.ShoppingCard;

/**
 * Created by ASUS on 27-May-17
 */
public interface ShoppingCardDao {

    /**
     * Adds shoppingCard to the database.
     *
     * @param userId
     * @param shoppingCard
     */
    void addShoppingCard(Long userId, ShoppingCard shoppingCard);

    /**
     * Gets shoppingCard from database.
     *
     * @param id
     * @return
     */
    ShoppingCard getShoppingCard(Long id);

    /**
     * Updates shoppingCard in database.
     *
     * @param id
     * @param shoppingCard
     */
    Boolean updateShoppingCard(Long id, ShoppingCard shoppingCard);

    /**
     * Deletes shoppingCard by id.
     *
     * @param id
     */
    Boolean deleteShoppingCard(Long id);
}
