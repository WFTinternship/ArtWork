package am.aca.wftartproject.service;

import am.aca.wftartproject.model.ShoppingCard;

import java.sql.SQLException;

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
    void updateShoppingCard(Long id, ShoppingCard shoppingCard) throws SQLException;

    /**
     * Deletes shoppingCard by id.
     *
     * @param id
     */
    void deleteShoppingCard(Long id);
}
