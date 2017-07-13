package am.aca.wftartproject.repository.rowmappers;

import am.aca.wftartproject.entity.Artist;
import am.aca.wftartproject.entity.ArtistSpecialization;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Armen on 6/16/2017
 */

public class ArtistMapper implements RowMapper<Artist> {

    @Override
    public Artist mapRow(ResultSet rs, int rowNum) throws SQLException {
        Artist artist = new Artist();
//        artist.setId(rs.getLong("id"))
//                .setFirstName(rs.getString("firstname"))
//                .setLastName(rs.getString("lastname"))
//                .setAge(rs.getInt("age"))
//                .setEmail(rs.getString("email"))
//                .setPassword(rs.getString("password"));
        return artist;
    }


    public Artist mapRowSecond(ResultSet rs, int rowNum) throws SQLException {
        Artist artist = new Artist();
        artist.setArtistPhoto(rs.getBytes("photo"))
                .setSpecialization(ArtistSpecialization.valueOf(rs.getString("spec_type")));
        return artist;
    }
}