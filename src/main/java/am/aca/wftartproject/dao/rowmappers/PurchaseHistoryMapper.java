package am.aca.wftartproject.dao.rowmappers;

import am.aca.wftartproject.model.PurchaseHistory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Armen on 6/16/2017.
 */
public class PurchaseHistoryMapper implements RowMapper<PurchaseHistory>{
    @Override
    public PurchaseHistory mapRow(ResultSet resultSet, int i) throws SQLException {
        PurchaseHistory purchasehistory = new PurchaseHistory();
        purchasehistory.setItemId(resultSet.getLong("item_id"))
                .setUserId(resultSet.getLong("user_id"))
                .setPurchaseDate(resultSet.getTimestamp("purchase_date"));
        return purchasehistory;
    }
}
