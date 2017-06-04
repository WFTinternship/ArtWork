package am.aca.wftartproject.service;

import am.aca.wftartproject.model.PurchaseHistory;
import am.aca.wftartproject.util.DBConnection;
import am.aca.wftartproject.dao.PurchaseHistoryDao;
import am.aca.wftartproject.dao.impl.PurchaseHistoryDaoImpl;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by surik on 6/1/17
 */
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {


    private PurchaseHistoryDao purchaseHistoryDao = null;


    public PurchaseHistoryServiceImpl() throws SQLException, ClassNotFoundException {

        purchaseHistoryDao= new PurchaseHistoryDaoImpl(new DBConnection().getDBConnection(DBConnection.DBType.REAL));

    }


    /**
     * @param purchaseHistory
     *
     * @see PurchaseHistoryService#addPurchase(PurchaseHistory)
     */
    @Override
    public void addPurchase(PurchaseHistory purchaseHistory) {

        purchaseHistoryDao.addPurchase(purchaseHistory);

    }

    /**
     * @param userId
     * @param itemId
     * @return
     *
     * @see PurchaseHistoryService#getPurchase(Long, Long)
     */
    @Override
    public PurchaseHistory getPurchase(Long userId, Long itemId) {

        return purchaseHistoryDao.getPurchase(userId, itemId);
    }

    /**
     * @param userId
     * @return
     *
     * @see PurchaseHistoryService#getPurchase(Long)
     */
    @Override
    public List<PurchaseHistory> getPurchase(Long userId) {

        return purchaseHistoryDao.getPurchase(userId);
    }

    /**
     * @param userId
     * @param itemId
     *
     * @see PurchaseHistoryService#deletePurchase(Long, Long)
     */
    @Override
    public void deletePurchase(Long userId, Long itemId) {

        purchaseHistoryDao.deletePurchase(userId, itemId);
    }

}
