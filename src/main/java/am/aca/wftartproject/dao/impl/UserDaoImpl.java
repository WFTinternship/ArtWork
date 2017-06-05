package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.UserDao;
import am.aca.wftartproject.exception.DAOFailException;
import am.aca.wftartproject.model.User;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Created by ASUS on 27-May-17
 */
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);
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
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO user(firstname, lastname, age, email, password) VALUE (?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setInt(3, user.getAge());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getLong(1));
            }
            rs.close();
        } catch (SQLException e) {
            String error = "Failed to add User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOFailException(error, e);
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
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user.setId(rs.getLong("id"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setAge(rs.getInt("age"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
            }
            rs.close();
        } catch (SQLException e) {
            String error = "Failed to get User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOFailException(error, e);
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
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE email = ?")) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user.setId(rs.getLong("id"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setAge(rs.getInt("age"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
            }
            rs.close();
        } catch (SQLException e) {
            String error = "Failed to get User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOFailException(error, e);
        }
        return user;
    }


    /**
     * @param id
     * @param user
     * @see UserDao#updateUser(Long, User)
     */
    @Override
    public void updateUser(Long id, User user) {
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE user SET firstname=?, lastname=?, age=?, password=? WHERE id = ?")) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setInt(3, user.getAge());
//            ps.setString(4, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setLong(5, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            String error = "Failed to update User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOFailException(error, e);
        }
    }


    /**
     * @param id
     * @see UserDao#deleteUser(Long)
     */
    @Override
    public void deleteUser(Long id) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM user WHERE id =?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            String error = "Failed to delete User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOFailException(error, e);
        }
    }

}
