package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.PurchaseHistoryDao;
import am.aca.wftartproject.exception.DAOFailException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by ASUS on 27-May-17
 */
public class PurchaseHistoryDaoImpl implements PurchaseHistoryDao {

    private static final Logger LOGGER = Logger.getLogger(PurchaseHistoryDaoImpl.class);
    private Connection conn = null;

    public PurchaseHistoryDaoImpl(Connection conn) {
        this.conn = conn;
    }

    /**
     * @param userId
     * @param itemId
     * @see PurchaseHistoryDao#addPurchase(Long, Long)
     */
    @Override
    public void addPurchase(Long userId, Long itemId) {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO purchase_history(user_id, item_id, purchase_date) VALUES (?,?,?)")) {
            ps.setLong(1, userId);
            ps.setLong(2, itemId);
            Calendar cal = Calendar.getInstance();
            Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
            ps.setTimestamp(3, timestamp);
            ps.executeUpdate();
        } catch (SQLException e) {
            String error = "Failed to add PurchaseHistory: %s";
            LOGGER.error(String.format(error,e.getMessage()));
            throw new DAOFailException(error, e);
        }
    }
}
