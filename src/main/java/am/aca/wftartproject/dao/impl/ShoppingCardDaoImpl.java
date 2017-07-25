package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ShoppingCardDao;
import am.aca.wftartproject.dao.rowmappers.ShoppingCardMapper;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.dao.NotEnoughMoneyException;
import am.aca.wftartproject.model.ShoppingCard;
import org.apache.log4j.Logger;
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

    @Autowired
    public ShoppingCardDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    /**
     * @param userId
     * @param shoppingCard
     * @see ShoppingCardDao#addShoppingCard(Long, ShoppingCard)
     */
    @Override
    public void addShoppingCard(Long userId, ShoppingCard shoppingCard) {
        try {
            shoppingCard.setBalance(getRandomBalance());

            KeyHolder keyHolder = new GeneratedKeyHolder();
            String query = "INSERT INTO shopping_card(balance, buyer_id, type) VALUES (?,?,?)";

            PreparedStatementCreator psc = con -> {
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setDouble(1, shoppingCard.getBalance());
                ps.setLong(2, userId);
                ps.setString(3, shoppingCard.getShoppingCardType().getType());
                return ps;
            };

            int rowsAffected = jdbcTemplate.update(psc, keyHolder);
            if (rowsAffected > 0) {
                shoppingCard.setId(keyHolder.getKey().longValue());
                shoppingCard.setBuyerId(userId);
            } else {
                throw new DAOException("Failed to add ShoppingCard");
            }

        } catch (DataAccessException e) {
            String error = "Failed to add ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
    }

    /**
     * @param id
     * @return
     * @see ShoppingCardDao#getShoppingCard(Long)
     */
    @Override
    public ShoppingCard getShoppingCard(Long id) {
        try {
            String query = "SELECT * FROM shopping_card WHERE id=?";
            return jdbcTemplate.queryForObject(query, new Object[]{id}, new ShoppingCardMapper());

        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(String.format("Failed to get shopping card by id: %s Empty result", id));
            return null;
        } catch (DataAccessException e) {
            String error = "Failed to get ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
    }

    /**
     * @param buyerId
     * @return
     * @see ShoppingCardDao#getShoppingCardByBuyerId(Long)
     */
    @Override
    public ShoppingCard getShoppingCardByBuyerId(Long buyerId) {
        try {
            String query = "SELECT * FROM shopping_card WHERE buyer_id = ?";
            return jdbcTemplate.queryForObject(query, new Object[]{buyerId}, new ShoppingCardMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(String.format("Failed to get shopping card by buyerId: %s Empty result", buyerId));
            return null;
        } catch (DataAccessException e) {
            String error = "Failed to get ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
    }

    /**
     * @param id
     * @param shoppingCard
     * @see ShoppingCardDao#updateShoppingCard(Long, ShoppingCard)
     */
    @Override
    public Boolean updateShoppingCard(Long id, ShoppingCard shoppingCard) {
        Boolean status;
        try {
            String query = "UPDATE shopping_card SET balance=?, type=? WHERE id = ?";

            int rowsAffected = jdbcTemplate.update(query, shoppingCard.getBalance(), shoppingCard.getShoppingCardType().getType(), id);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to update ShoppingCard");
            } else {
                status = true;
            }
        } catch (DataAccessException e) {
            String error = "Failed to update ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return status;
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
        ShoppingCard shoppingCard = getShoppingCardByBuyerId(buyerId);

        if (shoppingCard.getBalance() >= itemPrice) {
            shoppingCard.setBalance(shoppingCard.getBalance() - itemPrice);
            updateShoppingCard(shoppingCard.getId(), shoppingCard);
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
            } else {
                status = true;
            }
        } catch (DataAccessException e) {
            String error = "Failed to delete ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return status;
    }

    /**
     * @see ShoppingCardDao#deleteShoppingCardByBuyerId(Long)
     * @param buyerId
     * @return
     */
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
