package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ShoppingCardDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.dao.NotEnoughMoneyException;
import am.aca.wftartproject.model.ShoppingCard;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ShoppingCardDaoImpl extends BaseDaoImpl implements ShoppingCardDao {

    private static final Logger LOGGER = Logger.getLogger(ShoppingCardDaoImpl.class);

    private SessionFactory sessionFactory;

    @Autowired
    public ShoppingCardDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @param userId
     * @param shoppingCard
     * @see ShoppingCardDao#addShoppingCard(Long, ShoppingCard)
     */
    @Override
    public void addShoppingCard(Long userId, ShoppingCard shoppingCard) {
        Transaction tx = null;
        try {
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.getTransaction();
            if(!tx.isActive()){
                tx = session.beginTransaction();
            }
            shoppingCard.setBuyer_id(userId);
            session.save(shoppingCard);
            tx.commit();
            LOGGER.info("ShoppingCard saved successfully, ShoppingCard Details=" + shoppingCard);
        } catch (Exception e) {
            String error = "Failed to add ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }finally {
            if(tx.isActive()){
                tx.commit();
            }
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
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.getCurrentSession();
            tx = session.getTransaction();
            if(!tx.isActive()){
                tx = session.beginTransaction();
            }
            shoppingCard = (ShoppingCard) session.createQuery(
                    "SELECT c FROM ShoppingCard c WHERE c.buyer_id= :buyer_id")
                    .setParameter("buyer_id", id)
                    .getSingleResult();
            tx.commit();
        } catch (Exception e) {
            String error = "Failed to get ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if(tx.isActive()){
                tx.commit();
            }
        }
        return shoppingCard;

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
     * @param shoppingCard
     * @see ShoppingCardDao#updateShoppingCard(ShoppingCard)
     */
    @Override
    public Boolean updateShoppingCard(ShoppingCard shoppingCard) {

        Boolean result = false;
        Transaction tx = null;
        try {
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.getTransaction();
            if(!tx.isActive()){
                tx = session.beginTransaction();
            }
            session.saveOrUpdate(shoppingCard);
            tx.commit();
            result = true;
        } catch (Exception e) {
            String error = "Failed to update ShoppingCard: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }finally {
            if(tx.isActive()){
                tx.commit();
            }
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
        } catch (Exception e) {
            String error = "Not enough money on the account: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }


        return isEnoughBalance;
    }


    /**
     * @param id
     * @see ShoppingCardDao#deleteShoppingCard(Long)
     */
    @Override
    public Boolean deleteShoppingCard(Long id) {

        Boolean result;
        try {
            String query = "DELETE FROM shopping_card WHERE id=?";
            int rowsAffected = jdbcTemplate.update(query, id);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to delete ShoppingCard");
            } else {
                result = true;
            }
        } catch (Exception e) {
            String error = "Failed to delete ShoppingCard";
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
        Boolean result;
        try {
            String query = "DELETE FROM shopping_card WHERE buyer_id=?";
            int rowsAffected = jdbcTemplate.update(query, buyerId);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to delete ShoppingCard by buyerId");
            } else {
                result = true;
            }
        } catch (Exception e) {
            String error = "Failed to delete ShoppingCard";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        return result;
    }
}
