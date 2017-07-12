package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.entity.Artist;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;


@Repository
public class ArtistDaoImpl extends BaseDaoImpl implements ArtistDao {

    private static final Logger LOGGER = Logger.getLogger(ArtistDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @param artist
     * @see ArtistDao#addArtist(Artist)
     */
    @Override
    public void addArtist(Artist artist) {

        try {
            entityManager.persist(artist);
            entityManager.flush();
            LOGGER.info("Artist saved successfully, Artist Details=" + artist);
        } catch (Exception e) {
            String error = "Failed to add Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(e.getMessage(), error));
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
            artist = (Artist) entityManager.find(Artist.class, id);
            
            return artist;
        } catch (Exception e) {
            String error = "Failed to get Artist by ID: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(e.getMessage(), error));
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
            artist = (Artist) entityManager.createQuery("select c from Artist c where c.email = :email").setParameter("email", email).getSingleResult();
            
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
            entityManager.merge(artist);
            entityManager.flush();
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
            Artist artist1 = entityManager.find(Artist.class,artist.getId());
            entityManager.remove(entityManager.merge(artist1));
            result = true;
        } catch (Exception e) {
            String error = "Failed to delete Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(String.format(error, e.getMessage()));
        }
        return result;
    }

}
