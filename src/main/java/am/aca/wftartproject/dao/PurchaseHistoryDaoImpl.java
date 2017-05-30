package am.aca.wftartproject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by ASUS on 27-May-17.
 */
public class PurchaseHistoryDaoImpl implements PurchaseHistoryDao {

    private Connection conn = null;

    public PurchaseHistoryDaoImpl(Connection conn) {
        this.conn = conn;
    }

    /**
     * @param userId
     * @param itemId
     * @see PurchaseHistoryDao#addPurchase(int, int)
     */
    @Override
    public void addPurchase(int userId, int itemId) {

        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO purchase_history(user_id, item_id, purchase_date) VALUES (?,?,?)");
            ps.setInt(1, userId);
            ps.setInt(2, itemId);

            Calendar cal = Calendar.getInstance();
            Timestamp timestamp = new Timestamp(cal.getTimeInMillis());

            ps.setTimestamp(3, timestamp);
            if (ps.executeUpdate() > 0) {
                System.out.println("PurchaseHistory was successfully inserted");
            } else {
                System.out.println("There is a problem with purchaseHistory insertion");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
