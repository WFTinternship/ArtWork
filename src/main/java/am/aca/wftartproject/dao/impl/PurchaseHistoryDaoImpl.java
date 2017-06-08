package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.PurchaseHistoryDao;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.PurchaseHistory;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ASUS on 27-May-17
 */
public class PurchaseHistoryDaoImpl extends BaseDaoImpl implements PurchaseHistoryDao {

    private static final Logger LOGGER = Logger.getLogger(PurchaseHistoryDaoImpl.class);

    public PurchaseHistoryDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @param purchaseHistory
     * @see PurchaseHistoryDao#addPurchase(PurchaseHistory)
     */
    @Override
    public void addPurchase(PurchaseHistory purchaseHistory) {
        Connection conn = null;
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(
                "INSERT INTO purchase_history(user_id, item_id, purchase_date) VALUES (?,?,?)")) {
            ps.setLong(1, purchaseHistory.getUserId());
            ps.setLong(2, purchaseHistory.getItemId());
            Calendar cal = Calendar.getInstance();
            Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
            ps.setTimestamp(3, timestamp);
            if (ps.executeUpdate() > 0) {
                purchaseHistory.setPurchaseDate(timestamp);
            }

        } catch (SQLException e) {
            String error = "Failed to add PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public PurchaseHistory getPurchase(Long user_id, Long item_id) {
        Connection conn = null;
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM purchase_history WHERE item_id = ? AND  user_id = ? ")) {
            ps.setLong(1, item_id);
            ps.setLong(2, user_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                purchaseHistory.setItemId(rs.getLong("item_id"));
                purchaseHistory.setUserId(rs.getLong("user_id"));
                purchaseHistory.setPurchaseDate(rs.getTimestamp("purchase_date"));
            } else return purchaseHistory = null;

            ps.close();
        } catch (SQLException e) {
            String error = "Failed to get PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return purchaseHistory;
    }


    @Override
    public List<PurchaseHistory> getPurchase(Long userId) {

        Connection conn = null;
        List<PurchaseHistory> purchaseHistoryList = new ArrayList<>();
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM purchase_history WHERE user_id = ?")) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                purchaseHistory.setUserId(rs.getLong("user_id"));
                purchaseHistory.setItemId(rs.getLong("item_id"));
                purchaseHistory.setPurchaseDate(rs.getTimestamp("purchase_date"));

                purchaseHistoryList.add(purchaseHistory);
            }
            rs.close();
        } catch (SQLException e) {
            String error = "Failed to get PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return purchaseHistoryList;
    }

    @Override
    public Boolean deletePurchase(Long user_id, Long item_id) {
        Boolean success = false;
        Connection conn = null;
        try (PreparedStatement ps = conn.prepareStatement(
                " DELETE FROM purchase_history WHERE user_id=? and item_id = ? ")) {
            ps.setLong(1, user_id);
            ps.setLong(2, item_id);
            if (ps.executeUpdate() > 0) {
                success = true;
            }
        } catch (SQLException e) {
            String error = "Failed to get PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }
}
