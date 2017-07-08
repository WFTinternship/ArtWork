package am.aca.wftartproject.dao.rowmappers;

import am.aca.wftartproject.model.PurchaseHistory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Armen on 6/16/2017
 */

public class PurchaseHistoryMapper implements RowMapper<PurchaseHistory>{
    private DateTimeFormatter dtf =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

    @Override
    public PurchaseHistory mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        PurchaseHistory purchasehistory = new PurchaseHistory();
        purchasehistory.setItemId(resultSet.getLong("item_id"))
                .setUserId(resultSet.getLong("user_id"))
                .setPurchaseDate(LocalDateTime.parse(resultSet.getString("purchase_date"), dtf));
        return purchasehistory;
    }
}
