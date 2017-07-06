package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.Artist;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class ArtistDaoImpl extends BaseDaoImpl implements ArtistDao {

    private static final Logger LOGGER = Logger.getLogger(ArtistDaoImpl.class);
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    public ArtistDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    /**
     * @param artist
     * @see ArtistDao#addArtist(Artist)
     */
    @Override
    public void addArtist(Artist artist) {
        try {
            Session session = this.sessionFactory.openSession();
             Transaction tx = session.getTransaction();
        if(!tx.isActive()){
            tx = session.beginTransaction();
        }
            session.save(artist);
            tx.commit();
            LOGGER.info("Artist saved successfully, Artist Details=" + artist);
        } catch (Exception e) {
            String error = "Failed to add Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(e.getMessage(),error));
        }

    }

    /**
     * @param id
     * @return
     * @see ArtistDao#findArtist(Long)
     */
    @Override
    public Artist findArtist(Long id) {
        try {
            Artist artist;
            Session session = this.sessionFactory.getCurrentSession();
             Transaction tx = session.getTransaction();
        if(!tx.isActive()){
            tx = session.beginTransaction();
        }
            artist = (Artist) session.get(Artist.class, id);
            tx.commit();
            return artist;
        } catch (Exception e) {
            String error = "Failed to get Artist by ID: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(e.getMessage(),error));
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
        try {
            Session session = sessionFactory.getCurrentSession();
             Transaction tx = session.getTransaction();
        if(!tx.isActive()){
            tx = session.beginTransaction();
        }
            Criteria criteria = session.createCriteria(Artist.class);
            artist =  (Artist) criteria.add(Restrictions.eq("email", email))
                    .uniqueResult();
            tx.commit();
        } catch (Exception e) {
            String error = "Failed to get Artist by Email: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        return artist;


    }


    /**
     * @param artist
     * @see ArtistDao#updateArtist(Artist)
     */

    @Override
    public Boolean updateArtist(Artist artist) {

        Boolean result = false;
        try {
            
            Session session = this.sessionFactory.getCurrentSession();
             Transaction tx = session.getTransaction();
        if(!tx.isActive()){
            tx = session.beginTransaction();
        }
            session.saveOrUpdate(artist);
            tx.commit();
            result = true;
            LOGGER.info("Artist saved successfully, Artist Details=" + artist);
        } catch (Exception e) {
            String error = "Failed to update Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }

        return result;
    }


    /**
     * @see ArtistDao#deleteArtist(Artist)
     */
    @Override
    public Boolean deleteArtist(Artist artist) {

        Boolean result = false;
        try {
            Session session = this.sessionFactory.getCurrentSession();
             Transaction tx = session.getTransaction();
        if(!tx.isActive()){
            tx = session.beginTransaction();
        }
            session.delete(artist);
            tx.commit();
            result = true;
        } catch (Exception e) {
            String error = "Failed to delete Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }

        return result;

    }
}
