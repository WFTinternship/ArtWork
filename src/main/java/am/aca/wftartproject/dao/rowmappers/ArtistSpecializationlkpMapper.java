package am.aca.wftartproject.dao.rowmappers;

import am.aca.wftartproject.model.ArtistSpecialization;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Armen on 6/16/2017.
 */
public class ArtistSpecializationlkpMapper implements RowMapper<ArtistSpecialization> {
    @Override
    public ArtistSpecialization mapRow(ResultSet resultSet, int i) throws SQLException {
       ArtistSpecialization artSpec = ArtistSpecialization.valueOf(resultSet.getString("spec_type"));
        return artSpec;
    }
}
