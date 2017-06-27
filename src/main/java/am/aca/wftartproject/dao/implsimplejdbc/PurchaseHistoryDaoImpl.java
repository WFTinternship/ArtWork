package am.aca.wftartproject.dao.implsimplejdbc;

import am.aca.wftartproject.dao.PurchaseHistoryDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.PurchaseHistory;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 27-May-17
 */
public class PurchaseHistoryDaoImpl extends BaseDaoImpl implements PurchaseHistoryDao {

    private static final Logger LOGGER = Logger.getLogger(PurchaseHistoryDaoImpl.class);

    public PurchaseHistoryDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * @param purchaseHistory
     * @see PurchaseHistoryDao#addPurchase(PurchaseHistory)
     */
    @Override
    public void addPurchase(PurchaseHistory purchaseHistory) {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(
                    "INSERT INTO purchase_history(userId, itemId, purchase_date) VALUES (?,?,?)");
            ps.setLong(1, purchaseHistory.getUserId());
            ps.setLong(2, purchaseHistory.getItemId());
            ps.setTimestamp(3, getCurrentDateTime());
            if (ps.executeUpdate() > 0) {
                purchaseHistory.setPurchaseDate(getCurrentDateTime());
            }
        } catch (SQLException e) {
            purchaseHistory.setUserId(null);
            String error = "Failed to add PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        } finally {
            closeResources(ps, conn);
        }
    }


    /**
     * @param userId
     * @param itemId
     * @return
     * @see PurchaseHistoryDao#getPurchase(Long, Long)
     */
    @Override
    public PurchaseHistory getPurchase(Long userId, Long itemId) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(
                    "SELECT * FROM purchase_history WHERE itemId = ? AND  userId = ? ");
            ps.setLong(1, itemId);
            ps.setLong(2, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                purchaseHistory.setItemId(rs.getLong("itemId"))
                        .setUserId(rs.getLong("userId"))
                        .setPurchaseDate(rs.getTimestamp("purchase_date"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            String error = "Failed to get PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            closeResources(rs, ps, conn);
        }
        return purchaseHistory;
    }


    /**
     * @param userId
     * @return
     * @see PurchaseHistoryDao#getPurchase(Long)
     */
    @Override
    public List<PurchaseHistory> getPurchase(Long userId) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        List<PurchaseHistory> purchaseHistoryList = new ArrayList<>();
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("SELECT * FROM purchase_history WHERE userId = ?");
            ps.setLong(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                purchaseHistory.setUserId(rs.getLong("user_id"))
                        .setItemId(rs.getLong("item_id"))
                        .setPurchaseDate(rs.getTimestamp("purchase_date"));

                purchaseHistoryList.add(purchaseHistory);
            }
        } catch (SQLException e) {
            String error = "Failed to get PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            closeResources(rs, ps, conn);
        }
        return purchaseHistoryList;
    }


    /**
     * @param userId
     * @param itemId
     * @return
     * @see PurchaseHistoryDao#deletePurchase(Long, Long)
     */
    @Override
    public Boolean deletePurchase(Long userId, Long itemId) {

        Connection conn = null;
        PreparedStatement ps = null;
        Boolean success = false;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(
                    " DELETE FROM purchase_history WHERE userId=? and itemId = ? ");
            ps.setLong(1, userId);
            ps.setLong(2, itemId);
            if (ps.executeUpdate() > 0) {
                success = true;
            }
        } catch (SQLException e) {
            String error = "Failed to delete PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            closeResources(ps, conn);
        }
        return success;
    }
}
