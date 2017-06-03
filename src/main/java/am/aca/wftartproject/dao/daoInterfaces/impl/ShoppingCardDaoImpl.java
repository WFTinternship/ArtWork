package am.aca.wftartproject.dao.daoInterfaces.impl;

import am.aca.wftartproject.dao.ShoppingCardDao;
import am.aca.wftartproject.model.ShoppingCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ASUS on 27-May-17.
 */
public class ShoppingCardDaoImpl implements ShoppingCardDao {

    private Connection conn = null;

    public ShoppingCardDaoImpl(Connection conn) {
        this.conn = conn;
    }

    /**
     * @param userId
     * @param balance
     * @see ShoppingCardDao#addShoppingCard(Long, Double)
     */
    @Override
    public void addShoppingCard(Long userId, Double balance) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO shopping_card(balance, buyer_id) VALUES (?,?)");
            ps.setDouble(1, balance);
            ps.setLong(2, userId);
            int rowsAffected = ps.executeUpdate();
            if (!(rowsAffected > 0)) {
                throw new RuntimeException("There is a problem with shoppingCard insertion");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param id
     * @param balance
     * @see ShoppingCardDao#updateShoppingCard(Long, Double)
     */
    @Override
    public void updateShoppingCard(Long id, Double balance) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE shopping_card SET balance=? WHERE id = ?");
            ps.setDouble(1, balance);
            ps.setLong(2, id);
            int rowsAffected = ps.executeUpdate();
            if (!(rowsAffected > 0)) {
                throw new RuntimeException("There is a problem with shoppingCard updating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param id
     * @see ShoppingCardDao#deleteShoppingCard(Long)
     */
    @Override
    public void deleteShoppingCard(Long id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM shopping_card WHERE id=?");
            ps.setLong(1, id);
            int rowsAffected = ps.executeUpdate();
            if (!(rowsAffected > 0)) {
                throw new RuntimeException("There is a problem with shoppingCard deletion");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param id
     * @return
     * @see ShoppingCardDao#getShoppingCard(Long)
     */
    @Override
    public ShoppingCard getShoppingCard(Long id) {
        ShoppingCard shoppingCard = new ShoppingCard();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM shopping_card WHERE id=?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                shoppingCard.setId(rs.getLong(1));
                shoppingCard.setBalance(rs.getDouble(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shoppingCard;
    }
}
