package am.aca.wftartproject.service;

import am.aca.wftartproject.util.DBConnection;
import am.aca.wftartproject.dao.PurchaseHistoryDao;
import am.aca.wftartproject.dao.daoInterfaces.impl.PurchaseHistoryDaoImpl;
import java.sql.SQLException;

/**
 * Created by surik on 6/1/17
 */
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {

    private PurchaseHistoryDao purchaseHistory = null;

    public PurchaseHistoryServiceImpl() throws SQLException, ClassNotFoundException {

        purchaseHistory= new PurchaseHistoryDaoImpl(new DBConnection().getDBConnection(DBConnection.DBType.REAL));

    }


    /**
     * @param userId
     * @param itemId
     *
     * @see PurchaseHistoryService#addPurchase(Long, Long)
     */
    @Override
    public void addPurchase(Long userId, Long itemId) {

        purchaseHistory.addPurchase(userId, itemId);

    }

}
