package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.UserDao;
import am.aca.wftartproject.dao.rowmappers.UserMapper;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.AbstractUser;
import am.aca.wftartproject.model.User;
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

/**
 * Created by ASUS on 27-May-17
 */
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

            Session session = this.sessionFactory.getCurrentSession();
            session.save(user);
            LOGGER.info("Person saved successfully, Person Details="+user);

    }


    /**
     * @param id
     * @return
     * @see UserDao#findUser(Long)
     */
    @Override
    public User findUser(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        return (User) session.get(User.class, id);
    }


    /**
     * @param email
     * @return
     * @see UserDao#findUser(String)
     */
    @Override
    public User findUser(String email) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        return (User) criteria.add(Restrictions.eq("email", email))
                .uniqueResult();
    }


    /**
     * @param user
     * @see UserDao#updateUser(User)
     */
    @Override
    public Boolean updateUser(User user) {
        Boolean status = false;
        Session session = this.sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
        status = true;
        LOGGER.info("User saved successfully, Person Details="+user);

        return status;

    }

    /**
     * @param id
     * @see UserDao#deleteUser(Long)
     */
    @Override
    public Boolean deleteUser(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        User user =  (User) session.get(User.class, id);
        session.delete(user);
        return true;
    }
}
