package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.dao.rowmappers.ArtistMapper;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.Artist;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by ASUS on 27-May-17
 */
@Component
public class ArtistDaoImpl extends BaseDaoImpl implements ArtistDao {

    private static final Logger LOGGER = Logger.getLogger(ArtistDaoImpl.class);
    @Autowired
    private SessionFactory sessionFactory;

    public ArtistDaoImpl(SessionFactory sf) {
        this.sessionFactory = sf;
    }


    /**
     * @see ArtistDao#addArtist(Artist)
     * @param artist
     */
    @Override
    public void addArtist(Artist artist) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(artist);
        LOGGER.info("Person saved successfully, Person Details="+artist);
    }

    /**
     * @see ArtistDao#findArtist(Long)
     * @param id
     * @return
     */
    @Override
    public Artist findArtist(Long id) {
        Artist artist;
        Session session = this.sessionFactory.getCurrentSession();
        artist =  (Artist) session.get(Artist.class, id);
        return artist;
    }


    /**
     * @see ArtistDao#findArtist(String)
     * @param email
     * @return
     */
    @Override
    public Artist findArtist(String email) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Artist.class);
        return (Artist) criteria.add(Restrictions.eq("email", email))
                .uniqueResult();

    }


    /**
     * @see ArtistDao#updateArtist(Artist)
     * @param artist
     */
    @Override
    public Boolean updateArtist(Artist artist) {
        Boolean status = false;
        Session session = this.sessionFactory.getCurrentSession();

        session.saveOrUpdate(artist);

        status = true;
        LOGGER.info("Artist saved successfully, Artist Details="+ artist);
        return status;
    }


    /**
     * @see ArtistDao#deleteArtist(Long)
     * @param id
     */
    @Override
    public Boolean deleteArtist(Long id) {

        Boolean status = false;
        Session session = this.sessionFactory.getCurrentSession();

        Artist user =  (Artist) session.get(Artist.class, id);
        session.delete(user);

        status = true;
        return status;

    }
}
