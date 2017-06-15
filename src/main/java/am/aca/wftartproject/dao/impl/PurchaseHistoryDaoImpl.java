package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.PurchaseHistoryDao;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.PurchaseHistory;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by ASUS on 27-May-17
 */
public class PurchaseHistoryDaoImpl extends BaseDaoImpl implements PurchaseHistoryDao {

    private static final Logger LOGGER = Logger.getLogger(PurchaseHistoryDaoImpl.class);

    public PurchaseHistoryDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }


    /**
     * @param purchaseHistory
     * @see PurchaseHistoryDao#addPurchase(PurchaseHistory)
     */
    @Override
    public void addPurchase(PurchaseHistory purchaseHistory) {


        try {
            jdbcTemplate = new JdbcTemplate(getDataSource());
            String query = "INSERT INTO purchase_history(user_id, item_id, purchase_date) VALUES (?,?,?)";
            Calendar cal = Calendar.getInstance();
            Timestamp timestamp = new Timestamp(cal.getTimeInMillis());

            Object[] args = new Object[]{purchaseHistory.getUserId(), purchaseHistory.getItemId(), timestamp};
            int rowsAffected = jdbcTemplate.update(query, args);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to add PurchaseHistory");
            }else{
                purchaseHistory.setPurchaseDate(timestamp);
            }

        } catch (DataAccessException e) {
            purchaseHistory.setUserId(null);
            String error = "Failed to add PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }

        //region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement(
//                    "INSERT INTO purchase_history(userId, itemId, purchase_date) VALUES (?,?,?)");
//            ps.setLong(1, purchaseHistory.getUserId());
//            ps.setLong(2, purchaseHistory.getItemId());
//            Calendar cal = Calendar.getInstance();
//            Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
//            ps.setTimestamp(3, timestamp);
//            if (ps.executeUpdate() > 0) {
//                purchaseHistory.setPurchaseDate(timestamp);
//            }
//        } catch (SQLException e) {
//            purchaseHistory.setUserId(null);
//            String error = "Failed to add PurchaseHistory: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(String.format(error, e.getMessage()));
//        } finally {
//            closeResources(ps, conn);
//        }

        //endregion
    }

    /**
     * @param userId
     * @param itemId
     * @return
     * @see PurchaseHistoryDao#getPurchase(Long, Long)
     */
    @Override
    public PurchaseHistory getPurchase(Long userId, Long itemId) {

        PurchaseHistory purchaseHistory;
        try {
            jdbcTemplate = new JdbcTemplate(getDataSource());
            String query = "SELECT * FROM purchase_history WHERE item_id = ? AND  user_id = ? ";

            purchaseHistory = jdbcTemplate.queryForObject(query, new Object[]{itemId, userId}, (rs, rowNum) -> {
                PurchaseHistory tempPurchaseHistory1 = new PurchaseHistory();
                tempPurchaseHistory1.setItemId(rs.getLong("item_id"))
                        .setUserId(rs.getLong("user_id"))
                        .setPurchaseDate(rs.getTimestamp("purchase_date"));
                return tempPurchaseHistory1;
            });

            if (purchaseHistory == null) {
                throw new DAOException("Failed to get PurchaseHistory");
            }
        } catch (DataAccessException e) {
            String error = "Failed to get PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return purchaseHistory;

        //region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        PurchaseHistory purchaseHistory = new PurchaseHistory();
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement(
//                    "SELECT * FROM purchase_history WHERE itemId = ? AND  userId = ? ");
//            ps.setLong(1, itemId);
//            ps.setLong(2, userId);
//            rs = ps.executeQuery();
//            if (rs.next()) {
//                purchaseHistory.setItemId(rs.getLong("itemId"))
//                        .setUserId(rs.getLong("userId"))
//                        .setPurchaseDate(rs.getTimestamp("purchase_date"));
//            } else {
//                return null;
//            }
//        } catch (SQLException e) {
//            String error = "Failed to get PurchaseHistory: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(rs, ps, conn);
//        }
//        return purchaseHistory;

        //endregion
    }


    /**
     * @param userId
     * @return
     * @see PurchaseHistoryDao#getPurchase(Long)
     */
    @Override
    public List<PurchaseHistory> getPurchase(Long userId) {

        List<PurchaseHistory> purchaseHistoryList = new ArrayList<>();
        try {
            jdbcTemplate = new JdbcTemplate(getDataSource());
            String query = "SELECT * FROM purchase_history WHERE user_id = ?";

            List<Map<String, Object>> phRows = jdbcTemplate.queryForList(query, userId);

            for (Map<String, Object> phRow : phRows) {
                PurchaseHistory purchaseHistory = new PurchaseHistory();
                purchaseHistory.setUserId(Long.parseLong(String.valueOf(phRow.get("user_id"))))
                        .setItemId(Long.parseLong(String.valueOf(phRow.get("item_id"))))
                        .setPurchaseDate(Timestamp.valueOf(String.valueOf(phRow.get("purchase_date"))));
                purchaseHistoryList.add(purchaseHistory);
            }
        } catch (DataAccessException e) {
            String error = "Failed to get PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return purchaseHistoryList;

        //region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        PurchaseHistory purchaseHistory = new PurchaseHistory();
//        List<PurchaseHistory> purchaseHistoryList = new ArrayList<>();
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement("SELECT * FROM purchase_history WHERE userId = ?");
//            ps.setLong(1, userId);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                purchaseHistory.setUserId(rs.getLong("user_id"))
//                        .setItemId(rs.getLong("item_id"))
//                        .setPurchaseDate(rs.getTimestamp("purchase_date"));
//
//                purchaseHistoryList.add(purchaseHistory);
//            }
//        } catch (SQLException e) {
//            String error = "Failed to get PurchaseHistory: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(rs, ps, conn);
//        }
//        return purchaseHistoryList;

        //endregion
    }


    /**
     * @param userId
     * @param itemId
     * @return
     * @see PurchaseHistoryDao#deletePurchase(Long, Long)
     */
    @Override
    public Boolean deletePurchase(Long userId, Long itemId) {

        try {
            jdbcTemplate = new JdbcTemplate(getDataSource());
            String query = "DELETE FROM purchase_history WHERE user_id=? AND item_id = ?";

            int rowsAffected = jdbcTemplate.update(query, userId, itemId);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to delete PurchaseHistory");
            }
        } catch (DataAccessException e) {
            String error = "Failed to delete PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return true;

        //region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        Boolean success = false;
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement(
//                    " DELETE FROM purchase_history WHERE userId=? and itemId = ? ");
//            ps.setLong(1, userId);
//            ps.setLong(2, itemId);
//            if (ps.executeUpdate() > 0) {
//                success = true;
//            }
//        } catch (SQLException e) {
//            String error = "Failed to delete PurchaseHistory: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(ps, conn);
//        }
//        return success;

        //endregion
    }
}
