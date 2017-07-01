package am.aca.wftartproject.dao.implsimplejdbc;

import am.aca.wftartproject.dao.ShoppingCardDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.dao.NotEnoughMoneyException;
import am.aca.wftartproject.model.ShoppingCard;
import am.aca.wftartproject.model.ShoppingCardType;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by ASUS on 27-May-17
 */
public class ShoppingCardDaoImpl extends BaseDaoImpl implements ShoppingCardDao {

    private static final Logger LOGGER = Logger.getLogger(ShoppingCardDaoImpl.class);

    public ShoppingCardDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * @param userId
     * @param shoppingCard
     * @see ShoppingCardDao#addShoppingCard(Long, ShoppingCard)
     */
    @Override
    public void addShoppingCard(Long userId, ShoppingCard shoppingCard) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            shoppingCard.setBalance(getRandomBalance());

            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(
                    "INSERT INTO shopping_card(balance, buyer_id, type) VALUES (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setDouble(1, shoppingCard.getBalance());
            ps.setLong(2, userId);
            ps.setString(3, shoppingCard.getShoppingCardType().getType());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                shoppingCard.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            String error = "Failed to add ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            closeResources(rs, ps, conn);
        }
    }


    /**
     * @param id
     * @return
     * @see ShoppingCardDao#getShoppingCard(Long)
     */
    @Override
    public ShoppingCard getShoppingCard(Long id) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ShoppingCard shoppingCard = new ShoppingCard();
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("SELECT * FROM shopping_card WHERE id=?");

            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                shoppingCard.setId(rs.getLong("id"))
                        .setBalance(rs.getDouble("balance"))
                        .setBuyerId(rs.getLong("buyer_id"))
                        .setShoppingCardType(ShoppingCardType.valueOf(rs.getString("type")));
            }
        } catch (SQLException e) {
            String error = "Failed to get ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            closeResources(rs, ps, conn);
        }
        return shoppingCard;
    }

    @Override
    public ShoppingCard getShoppingCardByBuyerId(Long buyerId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ShoppingCard shoppingCard = new ShoppingCard();
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("SELECT * FROM shopping_card WHERE buyer_id = ?");

            ps.setLong(1, buyerId);
            rs = ps.executeQuery();
            if (rs.next()) {
                shoppingCard.setId(rs.getLong("id"))
                        .setBalance(rs.getDouble("balance"))
                        .setBuyerId(rs.getLong("buyer_id"))
                        .setShoppingCardType(ShoppingCardType.valueOf(rs.getString("type")));
            }
        } catch (SQLException e) {
            String error = "Failed to get shopping card by buyer id: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            closeResources(rs, ps, conn);
        }
        return shoppingCard;
    }


    /**
     * @param id
     * @param shoppingCard
     * @see ShoppingCardDao#updateShoppingCard(Long, ShoppingCard)
     */
    @Override
    public Boolean updateShoppingCard(Long id, ShoppingCard shoppingCard) {

        Connection conn = null;
        PreparedStatement ps = null;
        Boolean success = false;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("UPDATE shopping_card SET balance=?, type=? WHERE id = ?");
            ps.setDouble(1, shoppingCard.getBalance());
            ps.setLong(2, id);
            ps.setString(3, shoppingCard.getShoppingCardType().getType());
            if (ps.executeUpdate() > 0) {
                success = true;
            }
        } catch (SQLException e) {
            String error = "Failed to update ShoppingCard";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            closeResources(ps, conn);
        }
        return success;
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

        Connection conn = null;
        PreparedStatement ps = null;
        Boolean success = false;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("DELETE FROM shopping_card WHERE id=?");
            ps.setLong(1, id);
            if (ps.executeUpdate() > 0) {
                success = true;
            }
        } catch (SQLException e) {
            String error = "Failed to delete ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            closeResources(ps, conn);
        }
        return success;
    }
}
