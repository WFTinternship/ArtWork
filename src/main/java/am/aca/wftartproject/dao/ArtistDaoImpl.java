package am.aca.wftartproject.dao;

import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

            /*
             JPEG file path should be specifed.  ${filepath}
             */
            PreparedStatement ps = conn.prepareStatement("INSERT INTO artist(specilization, photo, user_id) VALUE (?,?,?)");

            FileInputStream fileInputStream = new FileInputStream("itemphotos\\test.jpg");
            ps.setString(1, artist.getSpecialization().toString());
            ps.setBinaryStream(2, fileInputStream, fileInputStream.available());
            ps.setInt(3, artist.getUser().getId());
            if (ps.executeUpdate() > 0) {
                System.out.println("The artist info was successfully inserted");
            } else {
                System.out.println("There is the problem with data inserting");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param id
     * @param specialization
     * @see ArtistDao#updateArtist(int, String)
     */
    @Override
    public void updateArtist(int id, String specialization) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE artist SET specialization=? WHERE id = ?");
            ps.setString(1, specialization);
            ps.setInt(2, id);
            if (ps.executeUpdate() > 0) {
                System.out.println("The artist info was successfully updated");
            } else {
                System.out.println("There is problem with artist info updating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param id
     * @see ArtistDao#deleteArtist(int)
     */
    @Override
    public void deleteArtist(int id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM artist WHERE user_id=?");
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                System.out.println("The artist info was successfully deleted");
            } else {
                System.out.println("There is a problem with artist info deleting");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param id
     * @return
     * @see ArtistDao#findArtist(int)
     */
    @Override
    public Artist findArtist(int id) {

        Artist artist = new Artist();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM artist WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                artist.setId(rs.getInt(1));
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
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM artist WHERE email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                artist.setId(rs.getInt(1));
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
}
