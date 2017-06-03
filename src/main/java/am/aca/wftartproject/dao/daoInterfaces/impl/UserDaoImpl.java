package am.aca.wftartproject.dao.daoInterfaces.impl;

import am.aca.wftartproject.dao.UserDao;
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
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO user(firstname, lastname, age, email, password) VALUE (?,?,?,?,?)");
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setInt(3, user.getAge());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            int rowsAffected = ps.executeUpdate();
            if (!(rowsAffected > 0)) {

                throw new RuntimeException("There is a problem with user insertion");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param id
     * @param user
     * @see UserDao#updateUser(Long, User)
     */
    @Override
    public void updateUser(Long id, User user) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE user SET firstname=? WHERE id = ?");
            ps.setString(1, user.getFirstName());
            ps.setLong(2, id);
            int rowsAffected = ps.executeUpdate();
            if (!(rowsAffected > 0)) {
                throw new RuntimeException("There is a problem with user info updating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param id
     * @see UserDao#deleteUser(Long)
     */
    @Override
    public void deleteUser(Long id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM user WHERE id =?");
            ps.setLong(1, id);
            int rowsAffected = ps.executeUpdate();
            if (!(rowsAffected > 0)) {
                throw new RuntimeException("There is a problem with user info deleting");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param id
     * @return
     * @see UserDao#findUser(Long)
     */
    @Override
    public User findUser(Long id) {
        User user = new User();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user.setId(rs.getLong(1));
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
                user.setId(rs.getLong(1));
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
