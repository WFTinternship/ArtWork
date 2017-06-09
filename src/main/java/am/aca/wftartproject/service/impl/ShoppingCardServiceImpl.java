package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.dao.ShoppingCardDao;
import am.aca.wftartproject.dao.impl.ShoppingCardDaoImpl;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.exception.ServiceException;
import am.aca.wftartproject.model.ShoppingCard;
import am.aca.wftartproject.service.ShoppingCardService;
import am.aca.wftartproject.util.dbconnection.ConnectionFactory;
import am.aca.wftartproject.util.dbconnection.ConnectionModel;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.SQLException;

/**
 * Created by ASUS on 03-Jun-17
 */
public class ShoppingCardServiceImpl implements ShoppingCardService {

    private static final Logger LOGGER = Logger.getLogger(ShoppingCardServiceImpl.class);

    private ShoppingCardDao shoppingCardDao = null;

    public ShoppingCardServiceImpl() throws PropertyVetoException, SQLException, ClassNotFoundException {
        DataSource conn = new ConnectionFactory().getConnection(ConnectionModel.POOL).getProductionDBConnection();
        shoppingCardDao = new ShoppingCardDaoImpl(conn);
    }


    /**
     * @param userId
     * @param shoppingCard
     * @see ShoppingCardService#addShoppingCard(Long, ShoppingCard)
     */
    @Override
    public void addShoppingCard(Long userId, ShoppingCard shoppingCard) {
        if (userId == null || userId < 0){
            LOGGER.error(String.format("userId is invalid: %s", userId));
            throw new ServiceException("Invalid userId");
        }
        if (!shoppingCard.isValidShoppingCard()){
            LOGGER.error(String.format("Shopping card is invalid: %s", shoppingCard));
            throw new ServiceException("Invalid shoppingCard");
        }
        try {
            shoppingCardDao.addShoppingCard(userId, shoppingCard);
        }catch (DAOException e){
            String error = "Failed to add ShoppingCard";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param id
     * @return
     * @see ShoppingCardService#getShoppingCard(Long)
     */
    @Override
    public ShoppingCard getShoppingCard(Long id) {
        if (id == null || id < 0){
            LOGGER.error(String.format("Id is invalid: %s", id));
            throw new ServiceException("Invalid id");
        }
        try {
            return shoppingCardDao.getShoppingCard(id);
        }catch (DAOException e){
            String error = "Failed to get ShoppingCard";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param id
     * @param shoppingCard
     * @see ShoppingCardService#updateShoppingCard(Long, ShoppingCard)
     */
    @Override
    public void updateShoppingCard(Long id, ShoppingCard shoppingCard) throws SQLException {
        if (id == null || id < 0){
            LOGGER.error(String.format("Id is invalid: %s", id));
            throw new ServiceException("Invalid id");
        }
        if (!shoppingCard.isValidShoppingCard()){
            LOGGER.error(String.format("Shopping card is invalid: %s", shoppingCard));
            throw new ServiceException("Invalid shoppingCard");
        }
        try {
            shoppingCardDao.updateShoppingCard(id, shoppingCard);
        }catch (DAOException e){
            String error = "Failed to update ShoppingCard";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @param id
     * @see ShoppingCardService#deleteShoppingCard(Long)
     */
    @Override
    public void deleteShoppingCard(Long id) {
        if (id == null || id < 0){
            LOGGER.error(String.format("Id is invalid: %s", id));
            throw new ServiceException("Invalid id");
        }
        try {
            shoppingCardDao.deleteShoppingCard(id);
        }catch (DAOException e){
            String error = "Failed to delete ShoppingCard";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }
}
