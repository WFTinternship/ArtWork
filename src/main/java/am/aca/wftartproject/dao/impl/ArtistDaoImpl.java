package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.dao.impl.rowmappers.ArtistMapper;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.Artist;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Created by ASUS on 27-May-17
 */
public class ArtistDaoImpl extends BaseDaoImpl implements ArtistDao {

    private static final Logger LOGGER = Logger.getLogger(ArtistDaoImpl.class);

    @Autowired
    public ArtistDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * @see ArtistDao#addArtist(Artist)
     * @param artist
     */
    @Override
    public void addArtist(Artist artist) {

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            String query1 = "INSERT INTO user(firstname, lastname, age, email, password) VALUE (?,?,?,?,?)";

            PreparedStatementCreator psc = con -> {
                PreparedStatement ps = con.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, artist.getFirstName());
                ps.setString(2, artist.getLastName());
                ps.setInt(3, artist.getAge());
                ps.setString(4, artist.getEmail());
                ps.setString(5, artist.getPassword());
                return ps;
            };
            int rowsAffected = jdbcTemplate.update(psc, keyHolder);
            if (rowsAffected > 0) {
                artist.setId(keyHolder.getKey().longValue());
            } else {
                throw new DAOException("Failed to add Artist");
            }

            String query2 = "INSERT INTO artist(spec_id, photo, user_id) VALUE (?,?,?)";
            Object[] args = new Object[]{artist.getSpecialization().getSpecId(), artist.getArtistPhoto(), artist.getId()};

            rowsAffected = jdbcTemplate.update(query2, args);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to add Artist");
            }
        } catch (DataAccessException e) {
            String error = "Failed to add Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
    }

    /**
     * @see ArtistDao#findArtist(Long)
     * @param id
     * @return
     */
    @Override
    public Artist findArtist(Long id) {

        Artist artist;
        try {
            String query1 = "SELECT * FROM user WHERE id=?";
            artist = jdbcTemplate.queryForObject(query1, new Object[]{id},
                    (rs, rowNum) -> new ArtistMapper().mapRow(rs, rowNum));

            String query2 = "SELECT ar.photo,art.spec_type FROM artist ar " +
                    "INNER JOIN artist_specialization_lkp art ON ar.spec_id=art.id WHERE ar.user_id=?";
            Artist tempArtist = jdbcTemplate.queryForObject(query2, new Object[]{id},
                    (rs, rowNum) -> new ArtistMapper().mapRowSecond(rs, rowNum));

            artist.setArtistPhoto(tempArtist.getArtistPhoto())
                    .setSpecialization(tempArtist.getSpecialization());

        }catch (EmptyResultDataAccessException e){
            LOGGER.warn(String.format("No artist found by id: %s", id));
            return null;
        } catch (DataAccessException e) {
            String error = "Failed to get Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        return artist;
    }


    /**
     * @see ArtistDao#findArtist(String)
     * @param email
     * @return
     */
    @Override
    public Artist findArtist(String email) {

        Artist artist;
        try {
            String query1 = "SELECT * FROM user WHERE email=?";
            artist = jdbcTemplate.queryForObject(query1, new Object[]{email}, (rs, rowNum) -> new ArtistMapper().mapRow(rs,rowNum));


            String query2 = "SELECT ar.photo,art.spec_type FROM artist ar " +
                    "INNER JOIN artist_specialization_lkp art ON ar.spec_id=art.id WHERE user_id=?";
            Artist tempArtist = jdbcTemplate.queryForObject(query2, new Object[]{artist.getId()}, (rs, rowNum) -> new ArtistMapper().mapRowSecond(rs,rowNum));
            artist.setArtistPhoto(tempArtist.getArtistPhoto())
                    .setSpecialization(tempArtist.getSpecialization());

        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn(String.format("No artist found by email: %s", email));
            return null;
        } catch (DataAccessException e) {
            String error = "Failed to get Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        return artist;
    }


    /**
     * @see ArtistDao#updateArtist(Long, Artist)
     * @param id
     * @param artist
     */
    @Override
    public void updateArtist(Long id, Artist artist) {
        try {
            String query1 = "UPDATE user SET firstname=?, lastname=?, age=?, password=? WHERE id = ?";
            Object[] args = new Object[]{artist.getFirstName(), artist.getLastName(), artist.getAge(), artist.getPassword(), id};
            int rowsAffected = jdbcTemplate.update(query1, args);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to update Artist");
            }

            String query2 = "UPDATE artist SET spec_id=?, photo=? WHERE user_id = ?";
            args = new Object[]{artist.getSpecialization().getId(), artist.getArtistPhoto(), id};
            rowsAffected = jdbcTemplate.update(query2, args);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to update Artist");
            }
        } catch (DataAccessException e) {
            String error = "Failed to update Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
    }


    /**
     * @see ArtistDao#deleteArtist(Long)
     * @param id
     */
    @Override
    public Boolean deleteArtist(Long id) {

        Boolean status;
        try {
            String query1 = "DELETE FROM artist WHERE user_id=?";
            int rowsAffected = jdbcTemplate.update(query1, id);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to delete Artist");
            }

            String query2 = "DELETE FROM user WHERE id=?";
            rowsAffected = jdbcTemplate.update(query2, id);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to delete Artist");
            } else {
                status = true;
            }

        } catch (DataAccessException e) {
            String error = "Failed to delete Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return status;
    }
}
