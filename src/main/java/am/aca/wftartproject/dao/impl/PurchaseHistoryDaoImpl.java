package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.PurchaseHistoryDao;
import am.aca.wftartproject.dao.rowmappers.PurchaseHistoryMapper;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.PurchaseHistory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by ASUS on 27-May-17
 */
@Component
public class PurchaseHistoryDaoImpl extends BaseDaoImpl implements PurchaseHistoryDao {

    private static final Logger LOGGER = Logger.getLogger(PurchaseHistoryDaoImpl.class);

    @Autowired
    public PurchaseHistoryDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    /**
     * @see PurchaseHistoryDao#addPurchase(PurchaseHistory)
     * @param purchaseHistory
     */
    @Override
    public void addPurchase(PurchaseHistory purchaseHistory) {

        try {
            purchaseHistory.setPurchaseDate(getCurrentDateTime());

            String query = "INSERT INTO purchase_history(user_id, item_id, purchase_date) VALUES (?,?,?)";
//            Calendar cal = Calendar.getInstance();
//            Timestamp timestamp = new Timestamp(cal.getTimeInMillis());

            Object[] args = new Object[]{purchaseHistory.getUserId(), purchaseHistory.getItemId(), purchaseHistory.getPurchaseDate()};
            int rowsAffected = jdbcTemplate.update(query, args);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to add PurchaseHistory");
            }

//            else {
//                purchaseHistory.setPurchaseDate(getCurrentDateTime());
//            }

        } catch (DataAccessException e) {
            purchaseHistory.setUserId(null);
            String error = "Failed to add PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }

//        region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement(
//                    "INSERT INTO purchase_history(userId, itemId, purchase_date) VALUES (?,?,?)");
//            ps.setLong(1, purchaseHistory.getUserId());
//            ps.setLong(2, purchaseHistory.getItemId());
////            Calendar cal = Calendar.getInstance();
////            Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
//            ps.setTimestamp(3, getCurrentDateTime());
//            if (ps.executeUpdate() > 0) {
//                purchaseHistory.setPurchaseDate(getCurrentDateTime());
//            }
//        } catch (SQLException e) {
//            purchaseHistory.setUserId(null);
//            String error = "Failed to add PurchaseHistory: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(String.format(error, e.getMessage()));
//        } finally {
//            closeResources(ps, conn);
//        }

//        endregion
    }

    /**
     * @see PurchaseHistoryDao#getPurchase(Long, Long)
     * @param userId
     * @param itemId
     * @return
     */
    @Override
    public PurchaseHistory getPurchase(Long userId, Long itemId) {

        try {
            String query = "SELECT * FROM purchase_history WHERE item_id = ? AND  user_id = ? ";
            return jdbcTemplate.queryForObject(query, new Object[]{itemId, userId},
                    (rs, rowNum) -> new PurchaseHistoryMapper().mapRow(rs, rowNum));

        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(String.format("Failed to get purchase item by userId and itemId: %s %s", userId, itemId));
            return null;
        } catch (DataAccessException e) {
            String error = "Failed to get PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }


//        region <Version with Simple JDBC>

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

//        endregion
    }


    /**
     * @see PurchaseHistoryDao#getPurchase(Long)
     * @param userId
     * @return
     */
    @Override
    public List<PurchaseHistory> getPurchase(Long userId) {

        List<PurchaseHistory> purchaseHistoryList;
        try {
            String query = "SELECT * FROM purchase_history WHERE user_id = ?";
            purchaseHistoryList = this.jdbcTemplate.query(query, new Object[]{userId}, new PurchaseHistoryMapper());

        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(String.format("Failed to get purchase history by userId: %s", userId));
            return null;
        } catch (DataAccessException e) {
            String error = "Failed to get PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return purchaseHistoryList;

//        region <Version with Simple JDBC>

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

//        endregion
    }


    /**
     * @see PurchaseHistoryDao#deletePurchase(Long, Long)
     * @param userId
     * @param itemId
     * @return
     */
    @Override
    public Boolean deletePurchase(Long userId, Long itemId) {

        Boolean status = false;
        try {
            String query = "DELETE FROM purchase_history WHERE user_id=? AND item_id = ?";

            int rowsAffected = jdbcTemplate.update(query, userId, itemId);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to delete PurchaseHistory");
            }else{
                status = true;
            }
        } catch (DataAccessException e) {
            String error = "Failed to delete PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return status;

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

//        endregion
    }
}
