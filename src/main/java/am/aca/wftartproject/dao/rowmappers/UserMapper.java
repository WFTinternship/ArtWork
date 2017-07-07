package am.aca.wftartproject.dao.rowmappers;

import am.aca.wftartproject.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Armen on 6/16/2017
 */
public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
//        user.setId(rs.getLong("id"))
//                .setFirstName(rs.getString("firstname"))
//                .setLastName(rs.getString("lastname"))
//                .setAge(rs.getInt("age"))
//                .setEmail(rs.getString("email"))
//                .setPassword(rs.getString("password"));
        return user;
    }
}
