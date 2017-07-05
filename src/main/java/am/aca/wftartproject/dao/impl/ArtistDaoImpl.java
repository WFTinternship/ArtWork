package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.Artist;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ArtistDaoImpl extends BaseDaoImpl implements ArtistDao {

    private static final Logger LOGGER = Logger.getLogger(ArtistDaoImpl.class);
    @Autowired
    private SessionFactory sessionFactory;

    public ArtistDaoImpl(SessionFactory sf) {
        this.sessionFactory = sf;
    }


    /**
     * @param artist
     * @see ArtistDao#addArtist(Artist)
     */
    @Override
    public void addArtist(Artist artist) {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            session.save(artist);
            LOGGER.info("Person saved successfully, Person Details=" + artist);
        } catch (DAOException e) {
            String error = "Failed to add Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error));
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
            artist = (Artist) session.get(Artist.class, id);
            return artist;
        } catch (DAOException e) {
            String error = "Failed to get Artist by ID: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error));
        }


    }


    /**
     * @param email
     * @return
     * @see ArtistDao#findArtist(String)
     */
    @Override
    public Artist findArtist(String email) {
        try {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Artist.class);
            return (Artist) criteria.add(Restrictions.eq("email", email))
                    .uniqueResult();
        } catch (DAOException e) {
            String error = "Failed to get Artist by Email: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error));
        }


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
            session.saveOrUpdate(artist);
            result = true;
            LOGGER.info("Artist saved successfully, Artist Details=" + artist);
        } catch (DAOException e) {
            String error = "Failed to update Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error));
        }

        return result;
    }


    /**
     * @param id
     * @see ArtistDao#deleteArtist(Long)
     */
    @Override
    public Boolean deleteArtist(Long id) {

        Boolean result = false;
        try {
            Session session = this.sessionFactory.getCurrentSession();
            Artist user = (Artist) session.get(Artist.class, id);
            session.delete(user);
            result = true;
        } catch (DAOException e) {
            String error = "Failed to delete Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error));
        }

        return result;

    }
}
