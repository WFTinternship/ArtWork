package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ShoppingCardDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.dao.NotEnoughMoneyException;
import am.aca.wftartproject.entity.ShoppingCard;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;


@Component
public class ShoppingCardDaoImpl extends BaseDaoImpl implements ShoppingCardDao {

    private static final Logger LOGGER = Logger.getLogger(ShoppingCardDaoImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @param shoppingCard
     * @see ShoppingCardDao#addShoppingCard(ShoppingCard)
     */
    @Override
    public void addShoppingCard(ShoppingCard shoppingCard) {
        try {
            entityManager.persist(shoppingCard);
            entityManager.flush();
            LOGGER.info("ShoppingCard saved successfully, ShoppingCard Details=" + shoppingCard);
        } catch (Exception e) {
            String error = "Failed to add ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }

    }


    /**
     * @param id
     * @return
     * @see ShoppingCardDao#getShoppingCard(Long)
     */
    @Override
    public ShoppingCard getShoppingCard(Long id) {
        ShoppingCard shoppingCard = null;

        try {
            shoppingCard = (ShoppingCard) entityManager.createQuery(
                    "SELECT c FROM ShoppingCard c WHERE c.buyer_id= :buyer_id")
                    .setParameter("buyer_id", id)
                    .getSingleResult();
            entityManager.flush();
        } catch (Exception e) {
            String error = "Failed to get ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }

        return shoppingCard;

    }


    /**
     * @param shoppingCard
     * @see ShoppingCardDao#updateShoppingCard(ShoppingCard)
     */
    @Override
    public Boolean updateShoppingCard(ShoppingCard shoppingCard) {
        Boolean result = false;
        try {
            entityManager.merge(shoppingCard);
            entityManager.flush();
            result = true;
        } catch (Exception e) {
            String error = "Failed to update ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        return result;

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
        try {
            if (shoppingCard.getBalance() >= itemPrice) {
                shoppingCard.setBalance(shoppingCard.getBalance() - itemPrice);
                updateShoppingCard(shoppingCard);
                isEnoughBalance = true;
            } else {
                throw new NotEnoughMoneyException("Not enough money on the account.");
            }
        } catch (NotEnoughMoneyException e) {
            String error = "Not enough money on the account: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new NotEnoughMoneyException(String.format(error, e.getMessage()));
        } catch (Exception e) {
            String error = "Not enough money on the account: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }


        return isEnoughBalance;
    }


    /**
     * @param shoppingCard
     * @see ShoppingCardDao#deleteShoppingCard(ShoppingCard)
     */
    @Override
    public Boolean deleteShoppingCard(ShoppingCard shoppingCard) {
        Boolean result = false;
        try {
            shoppingCard = entityManager.find(ShoppingCard.class, shoppingCard.getId());
            entityManager.remove(shoppingCard);
            entityManager.flush();
            result = true;
        } catch (Exception e) {
            String error = "Failed to delete shoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        return result;

    }

}
