package am.aca.wftartproject.dao.rowmappers;


import am.aca.wftartproject.model.ShoppingCard;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Armen on 6/16/2017.
 */
public class ShoppingCardMapper implements RowMapper<ShoppingCard> {

    @Override
    public ShoppingCard mapRow(ResultSet resultSet, int i) throws SQLException {
        ShoppingCard tempShoppingCard = new ShoppingCard();
        tempShoppingCard.setId(resultSet.getLong("id"))
                .setBalance(resultSet.getDouble("balance"));
        return tempShoppingCard;
    }
}
