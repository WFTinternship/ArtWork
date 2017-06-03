package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.exception.DAOFailException;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

/**
 * Created by ASUS on 27-May-17
 */

public class ArtistDaoImpl implements ArtistDao {

    private Connection conn = null;
    private static final Logger LOGGER = Logger.getLogger(ArtistDao.class);

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

            // JPEG file path should be specifed.  ${filepath}
            ps = conn.prepareStatement("INSERT INTO artist(specialization, photo, user_id) VALUE (?,?,?)");

            FileInputStream fileInputStream = new FileInputStream("src\\main\\resources\\itemphotos\\test.jpg");
            ps.setString(1, artist.getSpecialization().toString());
            ps.setBinaryStream(2, fileInputStream, fileInputStream.available());
            ps.setLong(3, artist.getId());
            ps.executeUpdate();

            ps.close();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOGGER.error("Failed to add Artist");
            }
            LOGGER.error("Failed to add Artist");
            throw new DAOFailException("Failed to add Artist", e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
            //Start Transaction
            conn.setAutoCommit(false);

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

            ps = conn.prepareStatement("SELECT * FROM artist WHERE user_id=?");
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                artist.setSpecialization(ArtistSpecialization.SCULPTOR);      // #TODO  Should be configured dynamically by rs.getInt(2).
                artist.setArtistPhoto(rs.getBytes(3));
            }
            ps.close();
            rs.close();

//                byte barr[] = artist.getArtistPhoto().getBytes(1,(int)artist.getArtistPhoto().length());
//                FileOutputStream fout=new FileOutputStream("d:\\sonoo.jpg");    // file path should be specified
//                fout.write(barr);
//                fout.close();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOGGER.error("Failed to get Artist");
            }
            LOGGER.error("Failed to get Artist");
            throw new DAOFailException("Failed to get Artist", e);
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
            //Start Transaction
            conn.setAutoCommit(false);

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

            ps = conn.prepareStatement("SELECT * FROM artist WHERE user_id=?");
            ps.setLong(1, artist.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                artist.setSpecialization(ArtistSpecialization.SCULPTOR);      // #TODO  Should be configured dynamically by rs.getInt(2).
                artist.setArtistPhoto(rs.getBytes(3));
            }
            ps.close();
            rs.close();
//                byte barr[] = artist.getArtistPhoto().getBytes(1,(int)artist.getArtistPhoto().length());
//                FileOutputStream fout=new FileOutputStream("d:\\sonoo.jpg");    // file path should be specified
//                fout.write(barr);
//                fout.close();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOGGER.error("Failed to get Artist");
            }
            LOGGER.error("Failed to get Artist");
            throw new DAOFailException("Failed to get Artist", e);
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
                    "UPDATE user SET firstname=? AND lastname=? AND age=? AND email=? AND password=? WHERE id = ?");
            ps.setString(1, artist.getFirstName());
            ps.setString(2, artist.getLastName());
            ps.setInt(3, artist.getAge());
            ps.setString(4, artist.getEmail());
            ps.setString(5, artist.getPassword());
            ps.setLong(6, id);
            ps.executeUpdate();

            ps = conn.prepareStatement(
                    "UPDATE artist SET spec_id=? AND photo=? WHERE id = ?");
            ps.setInt(1, artist.getSpecialization().getSpecValue());
            ps.setBytes(2, artist.getArtistPhoto());
            ps.setLong(3, id);
            ps.executeUpdate();

            ps.close();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOGGER.error("Failed to update Artist");
            }
            LOGGER.error("Failed to update Artist");
            throw new DAOFailException("Failed to update Artist", e);
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

            ps = conn.prepareStatement("DELETE FROM artist WHERE user_id=?");
            ps.setLong(1, id);
            ps.executeUpdate();
            ps.close();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOGGER.error("Failed to delete Artist");
            }
            LOGGER.error("Failed to delete Artist");
            throw new DAOFailException("Failed to delete Artist", e);
        }
    }
}
