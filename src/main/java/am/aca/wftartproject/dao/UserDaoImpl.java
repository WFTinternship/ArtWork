package am.aca.wftartproject.dao;

import am.aca.wftartproject.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ASUS on 27-May-17.
 */
public class UserDaoImpl implements UserDao {

    private Connection conn = null;

    public UserDaoImpl(Connection conn) {
        this.conn = conn;
    }

    /**
     * @param user
     * @see UserDao#addUser(User)
     */
    @Override
    public void addUser(User user) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO user(firstname, lastname, age, email, password) VALUE (?,?,?,?,?)")) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setInt(3, user.getAge());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            if (ps.executeUpdate() > 0) {
                System.out.println("The user was successfully inserted");
            } else {
                System.out.println("There is problem with user insertion");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param id
     * @param user
     * @see UserDao#updateUser(int, User)
     */
    @Override
    public void updateUser(int id, User user) {

        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE user SET firstname=? WHERE id = ?");
            ps.setString(1, user.getFirstName());
            ps.setInt(2, id);
            if (ps.executeUpdate() > 0) {
                System.out.println("The user info was successfully updated");
            } else {
                System.out.println("There is problem with user info updating");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * @param id
     * @see UserDao#deleteUser(int)
     */
    @Override
    public void deleteUser(int id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM user WHERE id =?");
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                System.out.println("The user was successfully deleted");
            } else {
                System.out.println("There is problem with user deletion");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param id
     * @return
     * @see UserDao#findUser(int)
     */
    @Override
    public User findUser(int id) {
        User user = new User();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt(1));
                user.setFirstName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setAge(rs.getInt(4));
                user.setEmail(rs.getString(5));
                user.setPassword(rs.getString(6));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    /**
     * @param email
     * @return
     * @see UserDao#findUser(String)
     */
    @Override
    public User findUser(String email) {
        User user = new User();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user.setId(rs.getInt(1));
                user.setFirstName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setAge(rs.getInt(4));
                user.setEmail(rs.getString(5));
                user.setPassword(rs.getString(6));
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

}
