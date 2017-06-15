package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by ASUS on 27-May-17
 */

public class ArtistDaoImpl extends BaseDaoImpl implements ArtistDao {

    private static final Logger LOGGER = Logger.getLogger(ArtistDaoImpl.class);

    public ArtistDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }


    /**
     * @see ArtistDao#addArtist(Artist)
     * @param artist
     */
    @Override
    public void addArtist(Artist artist) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getDataSource().getConnection();

            // start Transaction
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(
                    "INSERT INTO user(firstname, lastname, age, email, password) VALUE (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, artist.getFirstName());
            ps.setString(2, artist.getLastName());
            ps.setInt(3, artist.getAge());
            ps.setString(4, artist.getEmail());
            ps.setString(5, artist.getPassword());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                artist.setId(rs.getLong(1));
            }

            ps = conn.prepareStatement("INSERT INTO artist(spec_id, photo, user_id) VALUE (?,?,?)");
            ps.setInt(1, artist.getSpecialization().getId());
            ps.setBytes(2, artist.getArtistPhoto());
            ps.setLong(3, artist.getId());
            ps.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            String error = "Failed to add Artist: %s";
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e1) {
                LOGGER.error(String.format(error, e1.getMessage()));
            }
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        } finally {
            closeResources(rs, ps, conn);
        }
    }

    /**
     * @see ArtistDao#findArtist(Long)
     * @param id
     * @return
     */
    @Override
    public Artist findArtist(Long id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Artist artist = new Artist();
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("SELECT * FROM user WHERE id=?");
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                getArtistFromResultSet(artist, rs);
//                artist.setId(rs.getLong("id"))
//                        .setFirstName(rs.getString("firstname"))
//                        .setLastName(rs.getString("lastname"))
//                        .setAge(rs.getInt("age"))
//                        .setEmail(rs.getString("email"))
//                        .setPassword(rs.getString("password"));
            } else {
                LOGGER.error(String.format("Failed to get user by this id: %s", id));
                throw new DAOException("Failed to get user by this id!");
            }
            closeResources(rs, ps);

            ps = conn.prepareStatement(
                    "SELECT ar.photo,art.spec_type FROM artist ar " +
                            "INNER JOIN artist_specialization_lkp art ON ar.spec_id=art.id WHERE user_id=?");
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                artist.setArtistPhoto(rs.getBytes("photo"))
                        .setSpecialization(ArtistSpecialization.valueOf(rs.getString("spec_type")));
            }
            else {
                LOGGER.error(String.format("Failed to get artist by this id: %s", id));
                throw new DAOException("Failed to get artist by this id!");
            }
        } catch (SQLException e) {
            String error = "Failed to get Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        } finally {
            closeResources(rs, ps, conn);
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
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Artist artist = new Artist();
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement("SELECT * FROM user WHERE email=?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                getArtistFromResultSet(artist, rs);
//                artist.setId(rs.getLong("id"))
//                        .setFirstName(rs.getString("firstname"))
//                        .setLastName(rs.getString("lastname"))
//                        .setAge(rs.getInt("age"))
//                        .setEmail(rs.getString("email"))
//                        .setPassword(rs.getString("password"));
            } else {
                LOGGER.error(String.format("Failed to get user by this email: %s", email));
                throw new DAOException("Failed to get user by this email!");
            }
            closeResources(rs, ps);

            ps = conn.prepareStatement(
                    "SELECT ar.photo,art.spec_type FROM artist ar INNER JOIN " +
                            "artist_specialization_lkp art ON ar.spec_id=art.id WHERE user_id=?");
            ps.setLong(1, artist.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                artist.setArtistPhoto(rs.getBytes("photo"))
                        .setSpecialization(ArtistSpecialization.valueOf(rs.getString("spec_type")));
            }
            else {
                LOGGER.error(String.format("Failed to get artist by this email: %s", email));
                throw new DAOException("Failed to get artist by this email!");
            }
        } catch (SQLException e) {
            String error = "Failed to get Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            closeResources(rs, ps, conn);
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
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getDataSource().getConnection();

            // start Transaction
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(
                    "UPDATE user SET firstname=?, lastname=?, age=?, password=? WHERE id = ?");
            ps.setString(1, artist.getFirstName());
            ps.setString(2, artist.getLastName());
            ps.setInt(3, artist.getAge());
            ps.setString(4, artist.getPassword());
            ps.setLong(5, id);
            ps.executeUpdate();
            closeResources(ps);

            ps = conn.prepareStatement(
                    "UPDATE artist SET spec_id=?, photo=? WHERE user_id = ?");
            ps.setInt(1, artist.getSpecialization().getId());
            ps.setBytes(2, artist.getArtistPhoto());
            ps.setLong(3, id);
            ps.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            String error = "Failed to update Artist: %s";
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e1) {
                LOGGER.error(String.format(error, e1.getMessage()));
            }
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            closeResources(ps, conn);
        }
    }


    /**
     * @see ArtistDao#deleteArtist(Long)
     * @param id
     */
    @Override
    public Boolean deleteArtist(Long id) {
        Connection conn = null;
        PreparedStatement ps = null;
        Boolean success = false;
        try {
            conn = getDataSource().getConnection();

            // start Transaction
            conn.setAutoCommit(false);
            ps = conn.prepareStatement("DELETE FROM artist WHERE user_id=?");
            ps.setLong(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                success = true;
            }
            closeResources(ps);

            ps = conn.prepareStatement("DELETE FROM user WHERE id=?");
            ps.setLong(1, id);
            if (ps.executeUpdate() < 0) {
                success = false;
            }

            conn.commit();
        } catch (SQLException e) {
            String error = "Failed to delete Artist: %s";
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e1) {
                LOGGER.error(String.format(error, e1.getMessage()));
            }
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            closeResources(ps, conn);
        }
        return success;
    }


    private void getArtistFromResultSet(Artist artist, ResultSet rs) throws SQLException {
        artist.setId(rs.getLong("id"))
                .setFirstName(rs.getString("firstname"))
                .setLastName(rs.getString("lastname"))
                .setAge(rs.getInt("age"))
                .setEmail(rs.getString("email"))
                .setPassword(rs.getString("password"));
    }

}
