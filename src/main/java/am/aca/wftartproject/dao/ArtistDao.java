package am.aca.wftartproject.dao;

import am.aca.wftartproject.model.Artist;

/**
 * Created by ASUS on 27-May-17.
 */
public interface ArtistDao {

    /**
     * Adds new artist info to the database
     *
     * @param user
     */
    void addArtist(Artist user);

    /**
     * Updates artist info in database
     *
     * @param id
     * @param specialization
     */
    void updateArtist(int id, String specialization);

    /**
     * Deletes artist from database
     *
     * @param id
     */
    void deleteArtist(int id);

    /**
     * Finds artist from database by id
     *
     * @param id
     * @return
     */
    Artist findArtist(int id);

    /**
     * Finds artist from database by email
     *
     * @param email
     * @return
     */
    Artist findArtist(String email);

}
