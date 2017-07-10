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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

@Component
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @param user
     * @see UserDao#addUser(User)
     */
    @Override
    public void addUser(User user) {
        try {
            entityManager.persist(user);
            entityManager.flush();
            LOGGER.info("Person saved successfully, Person Details=" + user);
        } catch (Exception e) {
            String error = "Failed to add User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }

    }


    /**
     * @param id
     * @return
     * @see UserDao#findUser(Long)
     */
    @Override
    public User findUser(Long id) {
        User user = null;
        try {
            user =  (User) entityManager.find(User.class, id);
            entityManager.flush();
        } catch (Exception e) {
            String error = "Failed to get User by id: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
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
        User user = null;
        try {
            user = (User) entityManager.createQuery("select c from User c where c.email = :email").setParameter("email",email).getSingleResult();
            entityManager.flush();
        } catch (Exception e) {
            String error = "Failed to get User by email: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        return user;
    }


    /**
     * @param user
     * @see UserDao#updateUser(User)
     */
    @Override
    public Boolean updateUser(User user) {
        Boolean result = false;
        try {
            entityManager.merge(user);
            entityManager.flush();
            result = true;
            LOGGER.info("User saved successfully, Person Details=" + user);
        } catch (Exception e) {
            String error = "Failed to update User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        return result;

    }

    /**
     * @see UserDao#deleteUser(User)
     */
    @Override
    public Boolean deleteUser(User user) {
        Boolean result = false;
        try {
            entityManager.find(User.class,user.getId());
            entityManager.remove(user);
            result = true;
        } catch (Exception e) {
            String error = "Failed to delete User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        return result;
    }
}
