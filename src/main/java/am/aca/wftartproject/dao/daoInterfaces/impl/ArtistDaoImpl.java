package am.aca.wftartproject.dao.daoInterfaces.impl;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

/**
 * Created by ASUS on 27-May-17.
 */
public class ArtistDaoImpl implements ArtistDao {

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
            PreparedStatement ps = conn.prepareStatement("INSERT INTO user(firstname, lastname, age, email, password) VALUE (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, artist.getFirstName());
            ps.setString(2, artist.getLastName());
            ps.setInt(3, artist.getAge());
            ps.setString(4, artist.getEmail());
            ps.setString(5, artist.getPassword());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    artist.setId(rs.getLong(1));
                }
            } else {
                throw new RuntimeException("There is a problem with user insertion");
            }



            // JPEG file path should be specifed.  ${filepath}
            ps = conn.prepareStatement("INSERT INTO artist(spec_id, photo, user_id) VALUE (?,?,?)");

            FileInputStream fileInputStream = new FileInputStream("src\\main\\resources\\itemphotos\\test.jpg");
            ps.setInt(1, artist.getSpecialization().getSpecId());
            ps.setBinaryStream(2, fileInputStream, fileInputStream.available());
            ps.setLong(3, artist.getId());
            rowsAffected = ps.executeUpdate();
            if (!(rowsAffected > 0)) {

                throw new RuntimeException("There is a problem with data inserting");

            }
        } catch (SQLException | IOException e) {
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
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM artist JOIN user ON artist.user_id = user.id WHERE id=?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                artist.setId(rs.getLong(1));
                artist.setSpecialization(ArtistSpecialization.valueOf(rs.getString(2)));
                artist.setArtistPhoto(rs.getBytes(3));
            }
//                byte barr[] = artist.getArtistPhoto().getBytes(1,(int)artist.getArtistPhoto().length());
//                FileOutputStream fout=new FileOutputStream("d:\\sonoo.jpg");    // file path should be specified
//                fout.write(barr);
//                fout.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM artist JOIN user ON artist.user_id = user.id WHERE email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                artist.setId(rs.getLong(1));
                artist.setSpecialization(ArtistSpecialization.valueOf(rs.getString(2)));
                artist.setArtistPhoto(rs.getBytes(3));
            }
//                byte barr[] = artist.getArtistPhoto().getBytes(1,(int)artist.getArtistPhoto().length());
//                FileOutputStream fout=new FileOutputStream("d:\\sonoo.jpg");    // file path should be specified
//                fout.write(barr);
//                fout.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artist;
    }

    /**
     * @param id
     * @param specialization
     * @see ArtistDao#updateArtist(Long, String)
     */
    @Override
    public void updateArtist(Long id, String specialization) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE artist SET specialization=? WHERE id = ?");
            ps.setString(1, specialization);
            ps.setLong(2, id);
            int rowsAffected = ps.executeUpdate();
            if (!(ps.executeUpdate() > 0)) {
                throw new RuntimeException("There is a problem with artist info updating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param id
     * @see ArtistDao#deleteArtist(Long)
     */
    @Override
    public void deleteArtist(Long id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM artist WHERE user_id=?");
            ps.setLong(1, id);
            int rowsAffected = ps.executeUpdate();
            if (!(ps.executeUpdate() > 0)) {
                throw new RuntimeException("There is a problem with artist info deleting");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
