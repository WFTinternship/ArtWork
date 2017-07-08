package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.UserDao;
import am.aca.wftartproject.dao.rowmappers.UserMapper;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
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

    @Autowired
    public UserDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * @param user
     * @see UserDao#addUser(User)
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
    }


    /**
     * @param id
     * @return
     * @see UserDao#findUser(Long)
     */
    @Override
    public User findUser(Long id) {

        try {
            String query = "SELECT * FROM user WHERE id = ?";
            return jdbcTemplate.queryForObject(query, new Object[]{id}, (rs, rowNum) -> new UserMapper().mapRow(rs, rowNum));

        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String error = "Failed to get User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
    }


    /**
     * @param email
     * @return
     * @see UserDao#findUser(String)
     */
    @Override
    public User findUser(String email) {
        try {
            String query = "SELECT * FROM user WHERE email = ?";
            return jdbcTemplate.queryForObject(query, new Object[]{email}, (rs, rowNum) -> new UserMapper().mapRow(rs, rowNum));

        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String error = "Failed to get User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
    }


    /**
     * @param id
     * @param user
     * @see UserDao#updateUser(Long, User)
     */
    @Override
    public Boolean updateUser(Long id, User user) {
        Boolean status;
        try {
            String query = "UPDATE user SET firstname=? , lastname=?, age=? , password=? WHERE id = ?";

            Object[] args = new Object[]{user.getFirstName(), user.getLastName(), user.getAge(), user.getPassword(), id};
            int rowsAffected = jdbcTemplate.update(query, args);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to update User");
            } else {
                status = true;
            }
        } catch (DataAccessException e) {
            String error = "Failed to update User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return status;
    }


    /**
     * @param id
     * @see UserDao#deleteUser(Long)
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
    }
}
