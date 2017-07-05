package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.UserDao;
import am.aca.wftartproject.dao.rowmappers.UserMapper;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.AbstractUser;
import am.aca.wftartproject.model.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Component
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    private SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    /**
     * @param user
     * @see UserDao#addUser(User)
     */
    @Override
    public void addUser(User user) {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            session.save(user);
            LOGGER.info("Person saved successfully, Person Details=" + user);
        } catch (Exception e) {
            String error = "Failed to add User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error));
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
            Session session = this.sessionFactory.getCurrentSession();
            return (User) session.get(User.class, id);
        } catch (Exception e) {
            String error = "Failed to get User by id: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error));
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
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
            return (User) criteria.add(Restrictions.eq("email", email))
                    .uniqueResult();
        } catch (Exception e) {
            String error = "Failed to get User by email: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error));
        }
    }


    /**
     * @param user
     * @see UserDao#updateUser(User)
     */
    @Override
    public Boolean updateUser(User user) {
        Boolean result = false;
        try {
            Session session = this.sessionFactory.getCurrentSession();
            session.saveOrUpdate(user);
            result = true;
            LOGGER.info("User saved successfully, Person Details=" + user);
        } catch (Exception e) {
            String error = "Failed to update User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error));
        }


        return result;

    }

    /**
     * @param id
     * @see UserDao#deleteUser(Long)
     */
    @Override
    public Boolean deleteUser(Long id) {
        Boolean result = false;
        try {
            Session session = this.sessionFactory.getCurrentSession();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            result = true;
        } catch (Exception e) {
            String error = "Failed to delete User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error));
        }

        return result;
    }
}
