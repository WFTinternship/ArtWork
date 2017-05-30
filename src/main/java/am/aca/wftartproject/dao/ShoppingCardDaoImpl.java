package am.aca.wftartproject.dao;

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
     * @see ShoppingCardDao#addShoppingCard(int, double)
     */
    @Override
    public void addShoppingCard(int userId, double balance) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO shopping_card(balance, buyer_id) VALUES (?,?)");
            ps.setDouble(1, balance);
            ps.setInt(2, userId);
            if (ps.executeUpdate() > 0) {
                System.out.println("ShoppingCard info was successfully inserted");
            } else {
                System.out.println("There is a problem with shoppingCard insertion");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param id
     * @param balance
     * @see ShoppingCardDao#updateShoppingCard(int, double)
     */
    @Override
    public void updateShoppingCard(int id, double balance) {

        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE shopping_card SET balance=? WHERE id = ?");
            ps.setDouble(1, balance);
            ps.setInt(2, id);
            if (ps.executeUpdate() > 0) {
                System.out.println("ShoppingCard info was successfully updated");
            } else {
                System.out.println("There is a problem with shoppingCard updating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param id
     * @see ShoppingCardDao#deleteShoppingCard(int)
     */
    @Override
    public void deleteShoppingCard(int id) {

        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM shopping_card WHERE id=?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                System.out.println("ShoppingCard info was successfully deleted");
            } else {
                System.out.println("There is a problem with shoppingCard deletion");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param id
     * @return
     * @see ShoppingCardDao#getShoppingCard(int)
     */
    @Override
    public ShoppingCard getShoppingCard(int id) {
        ShoppingCard shoppingCard = new ShoppingCard();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM shopping_card WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                shoppingCard.setId(rs.getInt(1));
                shoppingCard.setBalance(rs.getDouble(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shoppingCard;
    }
}
