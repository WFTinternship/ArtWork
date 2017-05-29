package am.aca.wftartproject.dao;

import am.aca.wftartproject.model.ShoppingCard;

/**
 * Created by ASUS on 27-May-17.
 */
public interface ShoppingCardDao {
    void addShoppingCard(int user_id,double balance);
    void updateShoppingCard(int id, double balance);
    void deleteShoppingCard(int id);
    ShoppingCard getShoppingCard(int id);
}
