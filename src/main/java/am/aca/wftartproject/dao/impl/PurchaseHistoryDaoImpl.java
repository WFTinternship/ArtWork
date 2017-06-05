package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.PurchaseHistoryDao;
import am.aca.wftartproject.exception.DAOFailException;
import am.aca.wftartproject.model.PurchaseHistory;
import org.apache.log4j.Logger;

import java.sql.*;
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
     * @param purchaseHistory
     * @see PurchaseHistoryDao#addPurchase(PurchaseHistory)
     */
    @Override
    public void addPurchase(PurchaseHistory purchaseHistory) {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO purchase_history(user_id, item_id, purchase_date) VALUES (?,?,?)")) {
            ps.setLong(1, purchaseHistory.getUserId());
            ps.setLong(2, purchaseHistory.getItemId());
            Calendar cal = Calendar.getInstance();
            Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
            ps.setTimestamp(3, timestamp);
            if(ps.executeUpdate()>0){
                purchaseHistory.setPurchaseDate(timestamp);
            };

        } catch (SQLException e) {
            String error = "Failed to add PurchaseHistory: %s";
            LOGGER.error(String.format(error,e.getMessage()));
            throw new DAOFailException(error, e);
        }
        catch (Exception e){
            String error = "Failed to add PurchaseHistory: %s";
            LOGGER.error(String.format(error,e.getMessage()));
            throw new DAOFailException(error, e);
        }
    }

    @Override
    public PurchaseHistory getPurchaseHistory(Long user_id, Long item_id) {
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
            }
            ps.close();
        } catch (SQLException e) {
            String error = "Failed to get PurchaseHistory: %s";
            LOGGER.error(String.format(error,e.getMessage()));
            throw new DAOFailException(error, e);
        }
        catch (Exception e){
            String error = "Failed to get PurchaseHistory: %s";
            LOGGER.error(String.format(error,e.getMessage()));
            throw new DAOFailException(error, e);
        }
        return purchaseHistory;
    }

    @Override
    public void deletePurchaseHistory(Long user_id, Long item_id) {
        try (PreparedStatement ps = conn.prepareStatement(
                " DELETE FROM purchase_history WHERE user_id=? and item_id = ? ")) {
            ps.setLong(1,user_id);
            ps.setLong(2,item_id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            String error = "Failed to get PurchaseHistory: %s";
            LOGGER.error(String.format(error,e.getMessage()));
            throw new DAOFailException(error, e);
        }
        catch (Exception e){
            String error = "Failed to get PurchaseHistory: %s";
            LOGGER.error(String.format(error,e.getMessage()));
            throw new DAOFailException(error, e);
        }
    }
}
