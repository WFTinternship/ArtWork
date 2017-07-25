package am.aca.wftartproject.dao.rowmappers;

import am.aca.wftartproject.model.Item;
import am.aca.wftartproject.model.ItemType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * Created by Armen on 6/16/2017
 */
public class ItemMapper implements RowMapper<Item> {
    private DateTimeFormatter dtf =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

    @Override
    public Item mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Item item = new Item();
        item.setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setDescription(resultSet.getString("description"))
                .setPhotoURL(Arrays.asList(resultSet.getString("photo_url").split(",[ ]*")))
                .setPrice(resultSet.getDouble("price"))
                .setArtistId(resultSet.getLong("artist_id"))
                .setStatus(resultSet.getBoolean("status"))
                .setItemType(ItemType.valueOf(resultSet.getString("type")))
                .setAdditionDate(LocalDateTime.parse(resultSet.getString("addition_date"), dtf));
        return item;
    }
}
