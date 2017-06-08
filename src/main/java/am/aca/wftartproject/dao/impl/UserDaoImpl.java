package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.UserDao;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.User;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by ASUS on 27-May-17
 */
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    public UserDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    /**
     * @param user
     * @see UserDao#addUser(User)
     */
    @Override
    public void addUser(User user) {
        Connection conn = null;
        try {
            conn = getDataSource().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO user(firstname, lastname, age, email, password) VALUE (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
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
            ps.close();
        } catch (SQLException e) {
            String error = "Failed to add User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @param id
     * @return
     * @see UserDao#findUser(Long)
     */
    @Override
    public User findUser(Long id) {
        Connection conn = null;
        User user = new User();
        try {
            conn = getDataSource().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user.setId(rs.getLong("id"))
                        .setFirstName(rs.getString("firstname"))
                        .setLastName(rs.getString("lastname"))
                        .setAge(rs.getInt("age"))
                        .setEmail(rs.getString("email"))
                        .setPassword(rs.getString("password"));
            } else {
                return null;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            String error = "Failed to get User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        Connection conn = null;
        User user = new User();
        try {
            conn = getDataSource().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user.setId(rs.getLong("id"))
                        .setFirstName(rs.getString("firstname"))
                        .setLastName(rs.getString("lastname"))
                        .setAge(rs.getInt("age"))
                        .setEmail(rs.getString("email"))
                        .setPassword(rs.getString("password"));
            } else {
                return null;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            String error = "Failed to get User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }


    /**
     * @param id
     * @param user
     * @see UserDao#updateUser(Long, User)
     */
    @Override
    public Boolean updateUser(Long id, User user) {
        Connection conn = null;
        Boolean success = false;
        try {
            conn = getDataSource().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE user SET firstname=? , lastname=?, age=? , password=? WHERE id = ?");
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setInt(3, user.getAge());
            ps.setString(4, user.getPassword());
            ps.setLong(5, id);
            if (ps.executeUpdate() > 0) {
                success = true;
            }
            ps.close();
        } catch (SQLException e) {
            String error = "Failed to update User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }


    /**
     * @param id
     * @see UserDao#deleteUser(Long)
     */
    @Override
    public Boolean deleteUser(Long id) {
        Connection conn = null;
        Boolean success = false;
        try {
            conn = getDataSource().getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM user WHERE id =?");
            ps.setLong(1, id);
            if (ps.executeUpdate() > 0) {
                success = true;
            }
            ps.close();
        } catch (SQLException e) {
            String error = "Failed to delete User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }
}
