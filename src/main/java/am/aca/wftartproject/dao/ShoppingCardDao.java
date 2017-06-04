package am.aca.wftartproject.dao;

import am.aca.wftartproject.model.ShoppingCard;

import java.sql.SQLException;

/**
 * Created by ASUS on 27-May-17
 */
public interface ShoppingCardDao {


    /**
     * Adds shoppingCard to the database.
     *
     * @param user_id
     * @param shoppingCard
     */
    void addShoppingCard(Long user_id, ShoppingCard shoppingCard);


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
    void updateShoppingCard(Long id, ShoppingCard shoppingCard) throws SQLException;


    /**
     * Deletes shoppingCard by id.
     *
     * @param id
     */
    void deleteShoppingCard(Long id);
}
