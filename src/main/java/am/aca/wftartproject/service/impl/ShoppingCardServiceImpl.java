package am.aca.wftartproject.service.impl;

import am.aca.wftartproject.dao.ShoppingCardDao;
import am.aca.wftartproject.model.ShoppingCard;
import am.aca.wftartproject.service.ShoppingCardService;

import java.sql.SQLException;

/**
 * Created by ASUS on 03-Jun-17
 */
public class ShoppingCardServiceImpl implements ShoppingCardService {

    private ShoppingCardDao shoppingCardDao = null;

    public void setShoppingCardDao(ShoppingCardDao shoppingCardDao) {
        this.shoppingCardDao = shoppingCardDao;
    }

//    public ShoppingCardServiceImpl() throws PropertyVetoException, SQLException, ClassNotFoundException {
//        DataSource conn = new ConnectionFactory().getConnection(ConnectionModel.POOL).getProductionDBConnection();
//        shoppingCardDao = new ShoppingCardDaoImpl(conn);
//    }


    /**
     * @param userId
     * @param shoppingCard
     * @see ShoppingCardService#addShoppingCard(Long, ShoppingCard)
     */
    @Override
    public void addShoppingCard(Long userId, ShoppingCard shoppingCard) {
        shoppingCardDao.addShoppingCard(userId, shoppingCard);
    }


    /**
     * @param id
     * @return
     * @see ShoppingCardService#getShoppingCard(Long)
     */
    @Override
    public ShoppingCard getShoppingCard(Long id) {
        return shoppingCardDao.getShoppingCard(id);
    }


    /**
     * @param id
     * @param shoppingCard
     * @see ShoppingCardService#updateShoppingCard(Long, ShoppingCard)
     */
    @Override
    public void updateShoppingCard(Long id, ShoppingCard shoppingCard) throws SQLException {
        shoppingCardDao.updateShoppingCard(id, shoppingCard);
    }


    /**
     * @param id
     * @see ShoppingCardService#deleteShoppingCard(Long)
     */
    @Override
    public void deleteShoppingCard(Long id) {
        shoppingCardDao.deleteShoppingCard(id);
    }
}
