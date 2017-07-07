package am.aca.wftartproject.dao.impl;
import static org.hibernate.testing.transaction.TransactionUtil.*;
import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.entity.Artist;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;


@Component
public class ArtistDaoImpl extends BaseDaoImpl implements ArtistDao {

    private static final Logger LOGGER = Logger.getLogger(ArtistDaoImpl.class);
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public ArtistDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    /**
     * @param artist
     * @see ArtistDao#addArtist(Artist)
     */
    @Override
    public void addArtist(Artist artist) {
        EntityTransaction tx = null;
        try {
            EntityManager entityManager = this.entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.persist(artist);
            entityManager.flush();
            tx.commit();
            LOGGER.info("Artist saved successfully, Artist Details=" + artist);
        } catch (Exception e) {
            String error = "Failed to add Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(e.getMessage(), error));
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }

    }

    /**
     * @param id
     * @return
     * @see ArtistDao#findArtist(Long)
     */
    @Override
    public Artist findArtist(Long id) {
       EntityTransaction tx = null;
        try {
            Artist artist;
            EntityManager entityManager = this.entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                entityManager.getTransaction().begin();
            }
            artist = (Artist) entityManager.find(Artist.class, id);
            tx.commit();
            return artist;
        } catch (Exception e) {
            String error = "Failed to get Artist by ID: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(e.getMessage(), error));
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }

    }


    /**
     * @param email
     * @return
     * @see ArtistDao#findArtist(String)
     */
    @Override
    public Artist findArtist(String email) {
        Artist artist = null;
        EntityTransaction tx = null;
        try {
            EntityManager entityManager = this.entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                entityManager.getTransaction().begin();
            }
            artist = (Artist) entityManager.createQuery("select c from Artist c where c.email = :email").setParameter("email",email).getSingleResult();
            tx.commit();
        } catch (Exception e) {
            String error = "Failed to get Artist by Email: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
        return artist;


    }


    /**
     * @param artist
     * @see ArtistDao#updateArtist(Artist)
     */

    @Override
    public Boolean updateArtist(Artist artist) {
       EntityTransaction tx = null;
        Boolean result = false;
        try {
            EntityManager entityManager = this.entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.merge(artist);
            tx.commit();
            result = true;
            LOGGER.info("Artist saved successfully, Artist Details=" + artist);
        } catch (Exception e) {
            String error = "Failed to update Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
        return result;
    }


    /**
     * @see ArtistDao#deleteArtist(Artist)
     */
    @Override
    public Boolean deleteArtist(Artist artist) {
       EntityTransaction tx = null;
        Boolean result = false;
        try {
            EntityManager entityManager = this.entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.remove(entityManager.contains(artist)  ? artist : entityManager.merge(artist));
            tx.commit();
            result = true;
        } catch (Exception e) {
            String error = "Failed to delete Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }

        return result;
    }

}
