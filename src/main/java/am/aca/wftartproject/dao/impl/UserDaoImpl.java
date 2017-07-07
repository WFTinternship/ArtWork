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

@Component
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public UserDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * @param user
     * @see UserDao#addUser(User)
     */
    @Override
    public void addUser(User user) {
        EntityTransaction tx = null;
        EntityManager entityManager = null;
        try {
            entityManager = this.entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.persist(user);
            entityManager.flush();
            tx.commit();
            LOGGER.info("Person saved successfully, Person Details=" + user);
        } catch (Exception e) {
            tx.rollback();
            String error = "Failed to add User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if(entityManager.isOpen()){
                entityManager.close();
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
        EntityTransaction tx = null;
        EntityManager entityManager = null;
        User user = null;
        try {
            entityManager = this.entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                entityManager.getTransaction().begin();
            }
            user =  (User) entityManager.find(User.class, id);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            String error = "Failed to get User by id: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if(entityManager.isOpen()){
                entityManager.close();
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
        EntityTransaction tx = null;
        EntityManager entityManager = null;
        Session session = null;
        User user = null;
        try {
            entityManager = this.entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                entityManager.getTransaction().begin();
            }
            user = (User) entityManager.createQuery("select c from User c where c.email = :email").setParameter("email",email).getSingleResult();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            String error = "Failed to get User by email: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if(entityManager.isOpen()){
                entityManager.close();
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
        EntityTransaction tx = null;
        EntityManager entityManager = null;
        Boolean result = false;
        try {
            entityManager = this.entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.merge(user);
            tx.commit();
            result = true;
            LOGGER.info("User saved successfully, Person Details=" + user);
        } catch (Exception e) {
            tx.rollback();
            String error = "Failed to update User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if(entityManager.isOpen()){
                entityManager.close();
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
        EntityManager entityManager = null;
        Boolean result = false;
        EntityTransaction tx = null;
        try {
            entityManager = this.entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                entityManager.getTransaction().begin();
            }
            User user = (User) entityManager.find(User.class, id);
            entityManager.remove(entityManager.contains(user)  ? user : entityManager.merge(user));
            tx.commit();
            result = true;
        } catch (Exception e) {
            tx.rollback();
            String error = "Failed to delete User: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        finally {
            if(entityManager.isOpen()){
                entityManager.close();
            }
        }

        return result;
    }
}
