package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.UserDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    private SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @param user
     * @see UserDao#addUser(User)
     */
    @Override
    public void addUser(User user) {
        Transaction tx = null;
        try {
            Session session = this.sessionFactory.getCurrentSession();
              tx = session.getTransaction();
        if(!tx.isActive()){
            tx = session.beginTransaction();}
            session.save(user);
            tx.commit();
            LOGGER.info("Person saved successfully, Person Details=" + user);
        } catch (Exception e) {
            String error = "Failed to add User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
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
        Transaction tx = null;
        User user = null;
        try {
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.getTransaction();
            if (!tx.isActive()) {
                tx = session.beginTransaction();
            }
            user =  (User) session.get(User.class, id);
            tx.commit();
        } catch (Exception e) {
            String error = "Failed to get User by id: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
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
        Transaction tx = null;
        Session session = null;
        User user = null;
        try {
            session = sessionFactory.getCurrentSession();
            tx = session.getTransaction();
            if (!tx.isActive()) {
                tx = session.beginTransaction();
            }
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
            user = (User) criteria.add(Restrictions.eq("email", email))
                    .uniqueResult();
            tx.commit();
        } catch (Exception e) {
            String error = "Failed to get User by email: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
        return user;
    }


    /**
     * @param user
     * @see UserDao#updateUser(User)
     */
    @Override
    public Boolean updateUser(User user) {
        Transaction tx = null;
        Boolean result = false;
        try {
            Session session = this.sessionFactory.getCurrentSession();
             tx = session.getTransaction();
        if(!tx.isActive()){
            tx = session.beginTransaction();}
            session.saveOrUpdate(user);
            tx.commit();
            result = true;
            LOGGER.info("User saved successfully, Person Details=" + user);
        } catch (Exception e) {
            String error = "Failed to update User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
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
        Transaction tx = null;
        try {
            Session session = this.sessionFactory.getCurrentSession();
             tx = session.getTransaction();
        if(!tx.isActive()){
            tx = session.beginTransaction();}
            User user = (User) session.get(User.class, id);
            session.delete(user);
            tx.commit();
            result = true;
        } catch (Exception e) {
            String error = "Failed to delete User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }

        return result;
    }
}
