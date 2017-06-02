package am.aca.wftartproject.dao;

import am.aca.wftartproject.model.ShoppingCard;

import java.sql.SQLException;

/**
 * Created by ASUS on 27-May-17.
 */
public interface ShoppingCardDao {

    /**
     * Adds shoppingCard to the database.
     *
     * @param user_id
     * @param balance
     */
    void addShoppingCard(Long user_id, Double balance);

    /**
     * Updates shoppingCard in database.
     *
     * @param id
     * @param balance
     */
    void updateShoppingCard(Long id, Double balance) throws SQLException;

    /**
     * Deletes shoppingCard by id.
     *
     * @param id
     */
    void deleteShoppingCard(Long id);

    /**
     * Gets shoppingCard from database.
     *
     * @param id
     * @return
     */
    ShoppingCard getShoppingCard(Long id);
}
