package am.aca.wftartproject.service;

import am.aca.wftartproject.dao.DBConnection;
import am.aca.wftartproject.dao.PurchaseHistoryDao;
import am.aca.wftartproject.dao.PurchaseHistoryDaoImpl;
import java.sql.SQLException;

/**
 * Created by surik on 6/1/17
 */
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {

    PurchaseHistoryDao purchaseHistory = null;

    public PurchaseHistoryServiceImpl() throws SQLException, ClassNotFoundException {

        purchaseHistory= new PurchaseHistoryDaoImpl(new DBConnection().getDBConnection(DBConnection.DBType.REAL));

    }


    /**
     * @param userId
     * @param itemId
     *
     * @see PurchaseHistoryService#addPurchase(int, int)
     */
    @Override
    public void addPurchase(int userId, int itemId) {

        purchaseHistory.addPurchase(userId, itemId);

    }

}
