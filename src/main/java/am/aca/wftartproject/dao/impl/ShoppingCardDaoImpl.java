package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ShoppingCardDao;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.ShoppingCard;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by ASUS on 27-May-17
 */
public class ShoppingCardDaoImpl extends BaseDaoImpl implements ShoppingCardDao {

    private static final Logger LOGGER = Logger.getLogger(ShoppingCardDaoImpl.class);

    public ShoppingCardDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }


    /**
     * @see ShoppingCardDao#addShoppingCard(Long, ShoppingCard)
     * @param userId
     * @param shoppingCard
     */
    @Override
    public void addShoppingCard(Long userId, ShoppingCard shoppingCard) {

        try {
            jdbcTemplate = new JdbcTemplate(getDataSource());
            KeyHolder keyHolder = new GeneratedKeyHolder();
            String query = "INSERT INTO shopping_card(balance, buyer_id) VALUES (?,?)";

            PreparedStatementCreator psc = con -> {
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setDouble(1, shoppingCard.getBalance());
                ps.setLong(2, userId);
                return ps;
            };

            int rowsAffected = jdbcTemplate.update(psc, keyHolder);
            if (rowsAffected > 0) {
                shoppingCard.setId(keyHolder.getKey().longValue());
            } else {
                throw new DAOException("Failed to add ShoppingCard");
            }

        } catch (DataAccessException e) {
            String error = "Failed to add ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }

        //region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement(
//                    "INSERT INTO shopping_card(balance, buyer_id) VALUES (?,?)",
//                    Statement.RETURN_GENERATED_KEYS);
//
//            ps.setDouble(1, shoppingCard.getBalance());
//            ps.setLong(2, userId);
//            ps.executeUpdate();
//            rs = ps.getGeneratedKeys();
//            if (rs.next()) {
//                shoppingCard.setId(rs.getLong(1));
//            }
//        } catch (SQLException e) {
//            String error = "Failed to add ShoppingCard: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(rs, ps, conn);
//        }

        //endregion
    }


    /**
     * @see ShoppingCardDao#getShoppingCard(Long)
     * @param id
     * @return
     */
    @Override
    public ShoppingCard getShoppingCard(Long id) {

        ShoppingCard shoppingCard;
        try {
            jdbcTemplate = new JdbcTemplate(getDataSource());
            String query = "SELECT * FROM shopping_card WHERE id=?";

            shoppingCard = jdbcTemplate.queryForObject(query, new Object[]{id}, (rs, rowNum) -> {
                ShoppingCard tempShoppingCard = new ShoppingCard();
                tempShoppingCard.setId(rs.getLong("id"))
                        .setBalance(rs.getDouble("balance"));
                return tempShoppingCard;
            });

        } catch (DataAccessException e) {
            String error = "Failed to get ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return shoppingCard;

        //region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        ShoppingCard shoppingCard = new ShoppingCard();
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement("SELECT * FROM shopping_card WHERE id=?");
//
//            ps.setLong(1, id);
//            rs = ps.executeQuery();
//            if (rs.next()) {
//                shoppingCard.setId(rs.getLong("id"))
//                        .setBalance(rs.getDouble("balance"));
//            }
//        } catch (SQLException e) {
//            String error = "Failed to get ShoppingCard: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(rs, ps, conn);
//        }
//        return shoppingCard;

        //endregion
    }


    /**
     * @see ShoppingCardDao#updateShoppingCard(Long, ShoppingCard)
     * @param id
     * @param shoppingCard
     */
    @Override
    public Boolean updateShoppingCard(Long id, ShoppingCard shoppingCard) {

        try {
            jdbcTemplate = new JdbcTemplate(getDataSource());
            String query = "UPDATE shopping_card SET balance=? WHERE id = ?";

            int rowsAffected = jdbcTemplate.update(query, shoppingCard.getBalance(), id);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to update ShoppingCard");
            }

        } catch (DataAccessException e) {
            String error = "Failed to update ShoppingCard: %s";
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
//            ps = conn.prepareStatement("UPDATE shopping_card SET balance=? WHERE id = ?");
//            ps.setDouble(1, shoppingCard.getBalance());
//            ps.setLong(2, id);
//            if (ps.executeUpdate() > 0) {
//                success = true;
//            }
//        } catch (SQLException e) {
//            String error = "Failed to update ShoppingCard";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(ps, conn);
//        }
//        return success;

        //endregion
    }


    /**
     * @see ShoppingCardDao#deleteShoppingCard(Long)
     * @param id
     */
    @Override
    public Boolean deleteShoppingCard(Long id) {

        try {
            jdbcTemplate = new JdbcTemplate(getDataSource());
            String query = "DELETE FROM shopping_card WHERE id=?";

            int rowsAffected = jdbcTemplate.update(query, id);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to delete ShoppingCard");
            }

        } catch (DataAccessException e) {
            String error = "Failed to delete ShoppingCard: %s";
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
//            ps = conn.prepareStatement("DELETE FROM shopping_card WHERE id=?");
//            ps.setLong(1, id);
//            if (ps.executeUpdate() > 0) {
//                success = true;
//            }
//        } catch (SQLException e) {
//            String error = "Failed to delete ShoppingCard: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(ps, conn);
//        }
//        return success;

        //endregion
    }
}
