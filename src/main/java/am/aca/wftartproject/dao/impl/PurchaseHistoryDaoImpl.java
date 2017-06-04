package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.PurchaseHistoryDao;
import am.aca.wftartproject.exception.DAOFailException;
import am.aca.wftartproject.model.PurchaseHistory;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
            ps.setDate(3, (Date) purchaseHistory.getPurchaseDate());
            /*Calendar cal = Calendar.getInstance();
            Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
            ps.setTimestamp(3, timestamp);*/
            ps.executeUpdate();
        } catch (SQLException e) {
            String error = "Failed to add PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOFailException(error, e);
        }
    }

    @Override
    public PurchaseHistory getPurchase(Long userId, Long itemId) {

        PurchaseHistory purchaseHistory = null;
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM purchase_history WHERE user_id = ? AND item_Id = ?")) {
            ps.setLong(1, userId);
            ps.setLong(2, itemId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                purchaseHistory = new PurchaseHistory(
                        rs.getDate(3));
                purchaseHistory.setItemId(itemId);
                purchaseHistory.setUserId(userId);
            }
            rs.close();
        } catch (SQLException e) {
            String error = "Failed to get PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOFailException(error, e);
        }
        return purchaseHistory;
    }

    @Override
    public List<PurchaseHistory> getPurchase(Long userId){

        List<PurchaseHistory> purchaseHistoryList = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM purchase_history WHERE user_id = ?")){
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                PurchaseHistory purchaseHistory = new PurchaseHistory(rs.getDate("purchase_date"));
                purchaseHistory.setUserId(userId);
                purchaseHistory.setItemId(rs.getLong("item_id"));
                purchaseHistoryList.add(purchaseHistory);
            }
            rs.close();
        } catch (SQLException e) {
            String error = "Failed to get PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOFailException(error, e);
        }
        return purchaseHistoryList;
    }

    @Override
    public void deletePurchase(Long userId, Long itemId) {

        try{
            PreparedStatement ps = conn.prepareStatement("DELETE FROM purchase_history WHERE user_id = ? AND item_id = ?");
            ps.setLong(1, userId);
            ps.setLong(2, itemId);
            ps.executeUpdate();

        } catch (SQLException e) {
            String error = "Failed to delete PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOFailException(error, e);
        }
    }
}