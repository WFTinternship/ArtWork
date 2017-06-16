package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.UserDao;
import am.aca.wftartproject.dao.rowmappers.UserMapper;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.User;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by ASUS on 27-May-17
 */
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    public UserDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * @see UserDao#addUser(User)
     * @param user
     */
    @Override
    public void addUser(User user) {

        try {
            String query = "INSERT INTO user(firstname, lastname, age, email, password) VALUE (?,?,?,?,?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            

            PreparedStatementCreator psc = con -> {
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getFirstName());
                ps.setString(2, user.getLastName());
                ps.setInt(3, user.getAge());
                ps.setString(4, user.getEmail());
                ps.setString(5, user.getPassword());
                return ps;
            };

            int rowsAffected = jdbcTemplate.update(psc, keyHolder);
            if (rowsAffected > 0) {
                user.setId(keyHolder.getKey().longValue());
            }
        } catch (DataAccessException e) {
            String error = "Failed to add User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }

//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            conn = getDataSource().getConnection();
//
//            ps = conn.prepareStatement(
//                    "INSERT INTO user(firstname, lastname, age, email, password) VALUE (?,?,?,?,?)",
//                    Statement.RETURN_GENERATED_KEYS);
//            ps.setString(1, user.getFirstName());
//            ps.setString(2, user.getLastName());
//            ps.setInt(3, user.getAge());
//            ps.setString(4, user.getEmail());
//            ps.setString(5, user.getPassword());
//            ps.executeUpdate();
//            rs = ps.getGeneratedKeys();
//            if (rs.next()) {
//                user.setId(rs.getLong(1));
//            }
//        } catch (SQLException e) {
//            String error = "Failed to add User: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(rs, ps, conn);
//        }

    }


    /**
     * @see UserDao#findUser(Long)
     * @param id
     * @return
     */
    @Override
    public User findUser(Long id) {
        User user;
        try {
            String query = "SELECT * FROM user WHERE id = ?";
            

            user = jdbcTemplate.queryForObject(query, new Object[]{id}, (rs, rowNum) -> {
             return new UserMapper().mapRow(rs,rowNum);
            });
        } catch (DataAccessException e) {
            String error = "Failed to get User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return user;

//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        User user = new User();
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement("SELECT * FROM user WHERE id = ?");
//            ps.setLong(1, id);
//            rs = ps.executeQuery();
//            if (rs.next()) {
//                getUserFromResultSet(user, rs);
////                user.setId(rs.getLong("id"))
////                        .setFirstName(rs.getString("firstname"))
////                        .setLastName(rs.getString("lastname"))
////                        .setAge(rs.getInt("age"))
////                        .setEmail(rs.getString("email"))
////                        .setPassword(rs.getString("password"));
//            } else {
//                return null;
//            }
//        } catch (SQLException e) {
//            String error = "Failed to get User: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(rs, ps, conn);
//        }
//        return user;
    }


    /**
     * @see UserDao#findUser(String)
     * @param email
     * @return
     */
    @Override
    public User findUser(String email) {

        User user;
        try {
            String query = "SELECT * FROM user WHERE email = ?";
            
            user = jdbcTemplate.queryForObject(query, new Object[]{email}, (rs, rowNum) -> {
                return new UserMapper().mapRow(rs,rowNum);
            });

        } catch (DataAccessException e) {

            String error = "Failed to get User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return user;


//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        User user = new User();
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement("SELECT * FROM user WHERE email = ?");
//            ps.setString(1, email);
//            rs = ps.executeQuery();
//            if (rs.next()) {
//                getUserFromResultSet(user, rs);
////                user.setId(rs.getLong("id"))
////                        .setFirstName(rs.getString("firstname"))
////                        .setLastName(rs.getString("lastname"))
////                        .setAge(rs.getInt("age"))
////                        .setEmail(rs.getString("email"))
////                        .setPassword(rs.getString("password"));
//            } else {
//                return null;
//            }
//        } catch (SQLException e) {
//            String error = "Failed to get User: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(rs, ps, conn);
//        }
//        return user;
    }


    /**
     * @see UserDao#updateUser(Long, User)
     * @param id
     * @param user
     */
    @Override
    public Boolean updateUser(Long id, User user) {

        try {
            String query = "UPDATE user SET firstname=? , lastname=?, age=? , password=? WHERE id = ?";
            
            Object[] args = new Object[]{user.getFirstName(), user.getLastName(), user.getAge(), user.getPassword(), id};
            int rowsAffected = jdbcTemplate.update(query, args);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to update User");
            }
        } catch (DataAccessException e) {
            String error = "Failed to update User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return true;

//        Connection conn = null;
//        PreparedStatement ps = null;
//        Boolean success = false;
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement(
//                    "UPDATE user SET firstname=? , lastname=?, age=? , password=? WHERE id = ?");
//            ps.setString(1, user.getFirstName());
//            ps.setString(2, user.getLastName());
//            ps.setInt(3, user.getAge());
//            ps.setString(4, user.getPassword());
//            ps.setLong(5, id);
//            if (ps.executeUpdate() > 0) {
//                success = true;
//            }
//        } catch (SQLException e) {
//            String error = "Failed to update User: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(ps, conn);
//        }
//        return success;
    }


    /**
     * @see UserDao#deleteUser(Long)
     * @param id
     */
    @Override
    public Boolean deleteUser(Long id) {

        try {
            String query = "DELETE FROM user WHERE id =?";
            
            int rowsAffected = jdbcTemplate.update(query, id);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to delete User");
            }
        } catch (DataAccessException e) {
            String error = "Failed to delete User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return true;


//        Connection conn = null;
//        PreparedStatement ps = null;
//        Boolean success = false;
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement("DELETE FROM user WHERE id =?");
//            ps.setLong(1, id);
//            if (ps.executeUpdate() > 0) {
//                success = true;
//            }
//        } catch (SQLException e) {
//            String error = "Failed to delete User: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(ps, conn);
//        }
//        return success;
    }


}
