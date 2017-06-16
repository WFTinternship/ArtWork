package am.aca.wftartproject.dao.rowmappers;

import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.ItemType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Armen on 6/16/2017.
 */
public class ItemMapper implements RowMapper<Item> {

    @Override
    public Item mapRow(ResultSet resultSet, int rownumber) throws SQLException {
        Item item = new Item();
        item.setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setDescription(resultSet.getString("description"))
                .setPhotoURL(resultSet.getString("photo_url"))
                .setPrice(resultSet.getDouble("price"))
                .setArtistId(resultSet.getLong("artist_id"))
                .setStatus(resultSet.getBoolean("status"))
                .setItemType(ItemType.valueOf(resultSet.getString("type")));
        return item;
    }
}
