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

import javax.sql.DataSource;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by ASUS on 27-May-17
 */
@Component
public class PurchaseHistoryDaoImpl extends BaseDaoImpl implements PurchaseHistoryDao {

    private static final Logger LOGGER = Logger.getLogger(PurchaseHistoryDaoImpl.class);

    @Autowired
    public PurchaseHistoryDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * @param purchaseHistory
     * @see PurchaseHistoryDao#addPurchase(PurchaseHistory)
     */
    @Override
    public void addPurchase(PurchaseHistory purchaseHistory) {

        try {
            purchaseHistory.setPurchaseDate(getCurrentDateTime());

            String query = "INSERT INTO purchase_history(user_id, item_id, purchase_date) VALUES (?,?,?)";
            DateTimeFormatter dtf =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Object[] args = new Object[]{purchaseHistory.getUserId(), purchaseHistory.getItemId(),
                    dtf.format(purchaseHistory.getPurchaseDate())};
            int rowsAffected = jdbcTemplate.update(query, args);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to add PurchaseHistory");
            }
        } catch (DataAccessException e) {
            purchaseHistory.setUserId(null);
            String error = "Failed to add PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
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
    }


    /**
     * @param userId
     * @return
     * @see PurchaseHistoryDao#getPurchase(Long)
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
    }


    /**
     * @param userId
     * @param itemId
     * @return
     * @see PurchaseHistoryDao#deletePurchase(Long, Long)
     */
    @Override
    public Boolean deletePurchase(Long userId, Long itemId) {

        Boolean status;
        try {
            String query = "DELETE FROM purchase_history WHERE user_id=? AND item_id = ?";

            int rowsAffected = jdbcTemplate.update(query, userId, itemId);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to delete PurchaseHistory");
            } else {
                status = true;
            }
        } catch (DataAccessException e) {
            String error = "Failed to delete PurchaseHistory: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return status;
    }
}
