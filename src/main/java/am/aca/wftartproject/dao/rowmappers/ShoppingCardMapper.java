package am.aca.wftartproject.dao.rowmappers;


import am.aca.wftartproject.model.ShoppingCard;
import am.aca.wftartproject.model.ShoppingCardType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Armen on 6/16/2017
 */
public class ShoppingCardMapper implements RowMapper<ShoppingCard> {
    @Override
    public ShoppingCard mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        ShoppingCard tempShoppingCard = new ShoppingCard();
        tempShoppingCard.setId(resultSet.getLong("id"))
                .setBalance(resultSet.getDouble("balance"))
                .setBuyerId(resultSet.getLong("buyer_id"))
                .setShoppingCardType(ShoppingCardType.valueOf(resultSet.getString("type")));
        return tempShoppingCard;
    }
}
