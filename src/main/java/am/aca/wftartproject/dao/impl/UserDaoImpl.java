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

    private Connection conn = null;
    private static final Logger LOGGER = Logger.getLogger(UserDao.class);

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
                Statement.RETURN_GENERATED_KEYS)){
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
            LOGGER.error("Failed to add User");
            throw new DAOFailException("Failed to add User", e);
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
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE id = ?")){
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
            LOGGER.error("Failed to get User");
            throw new DAOFailException("Failed to get User", e);
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
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE email = ?")){
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
            LOGGER.error("Failed to get User");
            throw new DAOFailException("Failed to get User", e);
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
                "UPDATE user SET firstname=? AND lastname=? AND age=? AND email=? and password=? WHERE id = ?")){
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setInt(3, user.getAge());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.setLong(6, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Failed to update User");
            throw new DAOFailException("Failed to update User",e);
        }
    }


    /**
     * @param id
     * @see UserDao#deleteUser(Long)
     */
    @Override
    public void deleteUser(Long id) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM user WHERE id =?")){
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Failed to delete User");
            throw new DAOFailException("Failed to delete User",e);
        }
    }

}
