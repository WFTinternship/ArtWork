package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ShoppingCardDao;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.ShoppingCard;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Created by ASUS on 27-May-17
 */
public class ShoppingCardDaoImpl implements ShoppingCardDao {

    private static final Logger LOGGER = Logger.getLogger(ShoppingCardDaoImpl.class);
    private Connection conn = null;

    public ShoppingCardDaoImpl(Connection conn) {
        this.conn = conn;
    }

    /**
     * @param userId
     * @param shoppingCard
     * @see ShoppingCardDao#addShoppingCard(Long, ShoppingCard)
     */
    @Override
    public void addShoppingCard(Long userId, ShoppingCard shoppingCard) {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO shopping_card(balance, buyer_id) VALUES (?,?)",
                Statement.RETURN_GENERATED_KEYS)) {

            ps.setDouble(1, shoppingCard.getBalance());
            ps.setLong(2, userId);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                shoppingCard.setId(rs.getLong(1));
            }
            rs.close();
        } catch (SQLException e) {
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
        ShoppingCard shoppingCard = new ShoppingCard();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM shopping_card WHERE id=?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                shoppingCard.setId(rs.getLong("id"))
                        .setBalance(rs.getDouble("balance"));
            }
            else throw new DAOFailException("No such element in db");
        } catch (SQLException e) {
            String error = "Failed to get ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        catch (DAOFailException e ){
            String error = "Failed to get ShoppingCard: %s";
            LOGGER.error(String.format(error,e.getMessage()));
            throw new DAOFailException(error, e);
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
        Boolean success = false;
        try (PreparedStatement ps = conn.prepareStatement("UPDATE shopping_card SET balance=? WHERE id = ?")) {
            ps.setDouble(1, shoppingCard.getBalance());
            ps.setLong(2, id);
           if(ps.executeUpdate()>0){
               success = true;
           }
           else throw new DAOFailException("Failed to update shoppingCard");
        }
        catch (DAOFailException e) {
            String error = "Failed to update ShoppingCard";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        catch (SQLException e) {
            String error = "Failed to update ShoppingCard";
            LOGGER.error(String.format(error,e.getMessage()));
            throw new DAOFailException(error, e);
        }
        return success;

    }


    /**
     * @param id
     * @see ShoppingCardDao#deleteShoppingCard(Long)
     */
    @Override
    public Boolean deleteShoppingCard(Long id) {
        Boolean success = false;
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM shopping_card WHERE id=?")) {
            ps.setLong(1, id);
            if(ps.executeUpdate()>0){
                success = true;
            }
            else throw  new DAOFailException("Failed to delete ShoppingCard");
        } catch (SQLException e) {
            String error = "Failed to delete ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        catch (DAOFailException e) {
            String error = "Failed to delete ShoppingCard";
            LOGGER.error(String.format(error,e.getMessage()));
            throw new DAOFailException(error, e);
        }
        return success;
    }
}
