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

    Connection conn = null;

    public ArtistDaoImpl() throws SQLException, ClassNotFoundException {
        conn = new DBConnection().getDBConnection();
    }


    @Override
    public void addArtist(Artist artist) {
        try {
            PreparedStatement ps1 = conn.prepareStatement("INSERT INTO user(firstname, lastname, age, email, password) VALUE (?,?,?,?,?)");
            ps1.setString(1,artist.getUser().getFirstName());
            ps1.setString(2,artist.getUser().getLastName());
            ps1.setInt(3,artist.getUser().getAge());
            ps1.setString(4,artist.getUser().getEmail());
            ps1.setString(5,artist.getUser().getPassword());



            /**
             JPEG file path should be specifed.  ${filepath}
             */
            PreparedStatement ps2 = conn.prepareStatement("INSERT INTO artist(specilization, photo, user_id) VALUE (?,?,?)");

            FileInputStream fileInputStream = new FileInputStream("${filepath}");
            ps2.setString(1,artist.getSpecialization().toString());
            ps2.setBinaryStream(2,fileInputStream,fileInputStream.available());
            ps2.setInt(3,artist.getUser().getId());

            if(ps1.executeUpdate()>0) {
                if(ps2.executeUpdate()>0){
                    System.out.println("The artist info was successfully inserted");
                }else{
                    System.out.println("There is the problem with data inserting");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateArtist(int id, String firstName, String lastName) {

        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE TABLE user SET firstname=? and lastname=? WHERE id = ?");
            ps.setString(1,firstName);
            ps.setString(2,lastName);
            ps.setInt(3,id);
            if(ps.executeUpdate()>0){
                System.out.println("The artist info was successfully updated");
            }else {
                System.out.println("There is problem with artist info updating");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void deleteArtist(int id) {
        try {
            PreparedStatement ps1 = conn.prepareStatement("DELETE FROM artist WHERE user_id=?");
            PreparedStatement ps2 = conn.prepareStatement("DELETE FROM user WHERE id=?");

            ps1.setInt(1, id);
            ps2.setInt(1, id);

            if (ps1.executeUpdate()>0) {
                if (ps2.executeUpdate()>0) {
                    System.out.println("The artist info was successfully deleted");
                }else{
                    System.out.println("There is a problem with artist info deleting");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Artist findArtist(int id) {

        Artist artist = new Artist();
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM user us INNER JOIN artist ar ON us.id=ar.user_id WHERE us.id=?")) {
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                artist.getUser().setId(rs.getInt(1));
                artist.getUser().setFirstName(rs.getString(2));
                artist.getUser().setLastName(rs.getString(3));
                artist.getUser().setAge(rs.getInt(4));
                artist.getUser().setEmail(rs.getString(5));
                artist.getUser().setPassword(rs.getString(6));
                artist.setSpecialization(ArtistSpecialization.valueOf(rs.getString(7)));
                artist.setArtistPhoto(rs.getBytes(8));
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

    @Override
    public Artist findArtist(String email) {
        Artist artist = new Artist();
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM user us INNER JOIN artist ar ON us.id=ar.user_id WHERE us.email=?")) {
            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                artist.getUser().setId(rs.getInt(1));
                artist.getUser().setFirstName(rs.getString(2));
                artist.getUser().setLastName(rs.getString(3));
                artist.getUser().setAge(rs.getInt(4));
                artist.getUser().setEmail(rs.getString(5));
                artist.getUser().setPassword(rs.getString(6));
                artist.setSpecialization(ArtistSpecialization.valueOf(rs.getString(7)));
                artist.setArtistPhoto(rs.getBytes(8));
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
