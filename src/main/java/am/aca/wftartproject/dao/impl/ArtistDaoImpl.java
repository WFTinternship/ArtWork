package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.exception.DAOFailException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Created by ASUS on 27-May-17
 */

public class ArtistDaoImpl implements ArtistDao {

    private static final Logger LOGGER = Logger.getLogger(ArtistDaoImpl.class);
    private Connection conn = null;

    public ArtistDaoImpl(Connection conn) {
        this.conn = conn;
    }


    /**
     * @param artist
     * @see ArtistDao#addArtist(Artist)
     */
    @Override
    public void addArtist(Artist artist) {
        try {
            //Start Transaction
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO user(firstname, lastname, age, email, password) VALUE (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, artist.getFirstName());
            ps.setString(2, artist.getLastName());
            ps.setInt(3, artist.getAge());
            ps.setString(4, artist.getEmail());
            ps.setString(5, artist.getPassword());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                artist.setId(rs.getLong(1));
            }
            ps.close();


            ps = conn.prepareStatement("INSERT INTO artist(specialization, photo, user_id) VALUE (?,?,?)");
            ps.setString(1, artist.getSpecialization().toString());
            ps.setBytes(2, artist.getArtistPhoto());
            ps.setLong(3, artist.getId());
            ps.executeUpdate();
            ps.close();

            conn.commit();
        } catch (SQLException e) {
            String error = "Failed to add Artist: %s";
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOGGER.error(String.format(error, e1.getMessage()));
            }
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOFailException(String.format(error, e.getMessage()));
        }
    }

    /**
     * @param id
     * @return
     * @see ArtistDao#findArtist(Long)
     */
    @Override
    public Artist findArtist(Long id) {
        Artist artist = new Artist();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE id=?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                artist.setId(rs.getLong(1));
                artist.setFirstName(rs.getString(2));
                artist.setLastName(rs.getString(3));
                artist.setAge(rs.getInt(4));
                artist.setEmail(rs.getString(5));
                artist.setPassword(rs.getString(6));
            }
            ps.close();

            ps = conn.prepareStatement(
                    "SELECT ar.photo,art.spec_type FROM artist ar INNER JOIN artist_specialization art ON ar.spec_id=art.id WHERE user_id=?");
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                artist.setArtistPhoto(rs.getBytes(1));
                artist.setSpecialization(ArtistSpecialization.valueOf(rs.getString(2)));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            String error = "Failed to get Artist: %s";
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOGGER.error(String.format(error, e1.getMessage()));
            }
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOFailException(String.format(error, e.getMessage()));
        }
        return artist;
    }

    /**
     * @param email
     * @return
     * @see ArtistDao#findArtist(String)
     */
    @Override
    public Artist findArtist(String email) {
        Artist artist = new Artist();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                artist.setId(rs.getLong(1));
                artist.setFirstName(rs.getString(2));
                artist.setLastName(rs.getString(3));
                artist.setAge(rs.getInt(4));
                artist.setEmail(rs.getString(5));
                artist.setPassword(rs.getString(6));
            }
            ps.close();

            ps = conn.prepareStatement(
                    "SELECT ar.photo,art.spec_type FROM artist ar INNER JOIN artist_specialization art ON ar.spec_id=art.id WHERE user_id=?");
            ps.setLong(1, artist.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                artist.setArtistPhoto(rs.getBytes(1));
                artist.setSpecialization(ArtistSpecialization.valueOf(rs.getString(2)));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            String error = "Failed to get Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOFailException(error, e);
        }
        return artist;
    }

    /**
     * @param id
     * @param artist
     * @see ArtistDao#updateArtist(Long, Artist)
     */
    @Override
    public void updateArtist(Long id, Artist artist) {
        try {
            //Start Transaction
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE user SET firstname=? AND lastname=? AND age=? AND password=? WHERE id = ?");
            ps.setString(1, artist.getFirstName());
            ps.setString(2, artist.getLastName());
            ps.setInt(3, artist.getAge());
//            ps.setString(4, artist.getEmail());
            ps.setString(4, artist.getPassword());
            ps.setLong(5, id);
            ps.executeUpdate();

            ps = conn.prepareStatement(
                    "UPDATE artist SET spec_id=? AND photo=? WHERE id = ?");
            ps.setInt(1, artist.getSpecialization().getId());
            ps.setBytes(2, artist.getArtistPhoto());
            ps.setLong(3, id);
            ps.executeUpdate();
            ps.close();

            conn.commit();
        } catch (SQLException e) {
            String error = "Failed to update Artist: %s";
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOGGER.error(String.format(error, e1.getMessage()));
            }
            LOGGER.error(error);
            throw new DAOFailException(error, e);
        }
    }

    /**
     * @param id
     * @see ArtistDao#deleteArtist(Long)
     */
    @Override
    public void deleteArtist(Long id) {
        try {
            //Start Transaction
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement("DELETE FROM user WHERE id=?");
            ps.setLong(1, id);
            ps.executeUpdate();
            ps.close();

            ps = conn.prepareStatement("DELETE FROM artist WHERE user_id=?");
            ps.setLong(1, id);
            ps.executeUpdate();
            ps.close();

            conn.commit();
        } catch (SQLException e) {
            String error = "Failed to delete Artist: %s";
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOGGER.error(String.format(error, e1.getMessage()));
            }
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOFailException(error, e);
        }
    }
}
