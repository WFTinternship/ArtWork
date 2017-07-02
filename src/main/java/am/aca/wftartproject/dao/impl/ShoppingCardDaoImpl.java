package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ShoppingCardDao;
import am.aca.wftartproject.dao.rowmappers.ShoppingCardMapper;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.dao.NotEnoughMoneyException;
import am.aca.wftartproject.model.ShoppingCard;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Created by ASUS on 27-May-17
 */
@Component
public class ShoppingCardDaoImpl extends BaseDaoImpl implements ShoppingCardDao {

    private static final Logger LOGGER = Logger.getLogger(ShoppingCardDaoImpl.class);

    private SessionFactory sessionFactory;
    @Autowired
    public ShoppingCardDaoImpl(SessionFactory sf) {
        this.sessionFactory = sf;
    }
    /**
     * @param userId
     * @param shoppingCard
     * @see ShoppingCardDao#addShoppingCard(Long, ShoppingCard)
     */
    @Override
    public void addShoppingCard(Long userId, ShoppingCard shoppingCard) {

        try {
//            shoppingCard.setBalance(getRandomBalance());

            KeyHolder keyHolder = new GeneratedKeyHolder();
            String query = "INSERT INTO shopping_card(balance, buyer_id, type) VALUES (?,?,?)";

            PreparedStatementCreator psc = con -> {
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setDouble(1, shoppingCard.getBalance());
                ps.setLong(2, userId);
                ps.setString(3,shoppingCard.getShoppingCardType().getType());
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
//            shoppingCard.setBalance(getRandomBalance());

//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement(
//                    "INSERT INTO shopping_card(balance, buyer_id, type) VALUES (?,?,?)",
//                    Statement.RETURN_GENERATED_KEYS);
//
//            ps.setDouble(1, shoppingCard.getBalance());
//            ps.setLong(2, userId);
//            ps.setString(3,shoppingCard.getShoppingCardType().getType());
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
     * @param id
     * @return
     * @see ShoppingCardDao#getShoppingCard(Long)
     */
    @Override
    public ShoppingCard getShoppingCard(Long id) {

        try {
            String query = "SELECT * FROM shopping_card WHERE buyer_id=?";
            return jdbcTemplate.queryForObject(query, new Object[]{id}, (rs, rowNum) -> new ShoppingCardMapper().mapRow(rs,rowNum));

        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(String.format("Failed to get shopping card by id: %s", id));
            return null;
        } catch (DataAccessException e) {
            String error = "Failed to get ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }

//        region <Version with Simple JDBC>

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
//                        .setBalance(rs.getDouble("balance"))
//                        .setShoppingCardType(ShoppingCardType.valueOf(resultSet.getString("type")));;
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

        Boolean status;
        try {
            String query = "UPDATE shopping_card SET balance=?, type=? WHERE buyer_id = ?";

            int rowsAffected = jdbcTemplate.update(query, shoppingCard.getBalance(), shoppingCard.getShoppingCardType().getType(), id);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to update ShoppingCard");
            }else{
                status = true;
            }
        } catch (DataAccessException e) {
            String error = "Failed to update ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return status;

//        region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        Boolean success = false;
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement("UPDATE shopping_card SET balance=?, type=? WHERE id = ?");
//            ps.setDouble(1, shoppingCard.getBalance());
//            ps.setLong(2, id);
//            ps.setString(3, shoppingCard.getShoppingCardType().getType());
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
     * @param itemPrice
     * @param buyerId
     * @return
     * @see ShoppingCardDao#debitBalanceForItemBuying(Long, Double)
     */
    @Override
    public Boolean debitBalanceForItemBuying(Long buyerId, Double itemPrice) {

        Boolean isEnoughBalance;
        ShoppingCard shoppingCard = getShoppingCard(buyerId);

        if (shoppingCard.getBalance() >= itemPrice) {
            shoppingCard.setBalance(shoppingCard.getBalance() - itemPrice);
            updateShoppingCard(buyerId, shoppingCard);
            isEnoughBalance = true;
        } else {
            throw new NotEnoughMoneyException("Not enough money on the account.");
        }

        return isEnoughBalance;
    }


    /**
     * @param id
     * @see ShoppingCardDao#deleteShoppingCard(Long)
     */
    @Override
    public Boolean deleteShoppingCard(Long id) {

        Boolean status;
        try {
            String query = "DELETE FROM shopping_card WHERE id=?";

            int rowsAffected = jdbcTemplate.update(query, id);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to delete ShoppingCard");
            }else{
                status = true;
            }
        } catch (DataAccessException e) {
            String error = "Failed to delete ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return status;

//        region <Version with Simple JDBC>

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

    @Override
    public Boolean deleteShoppingCardByBuyerId(Long buyerId) {
        Boolean status;
        try {
            String query = "DELETE FROM shopping_card WHERE buyer_id=?";

            int rowsAffected = jdbcTemplate.update(query, buyerId);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to delete ShoppingCard by buyerId");
            } else {
                status = true;
            }
        } catch (DataAccessException e) {
            String error = "Failed to delete ShoppingCard: %s buyerId: %s";
            LOGGER.error(String.format(error, e.getMessage(), buyerId));
            throw new DAOException(error, e);
        }
        return status;
    }
}
