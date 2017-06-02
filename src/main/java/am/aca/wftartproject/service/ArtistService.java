package am.aca.wftartproject.service;

import am.aca.wftartproject.model.Artist;

/**
 * Created by ASUS on 30-May-17.
 */
public interface ArtistService {

    /**
     * Adds
     *
     * @param artist
     */
    void addArtist(Artist artist);

    /**
     * Updates artist info in database
     *
     * @param id
     * @param specialization
     */
    void updateArtist(Long id, String specialization);

    /**
     * Deletes artist from database
     *
     * @param id
     */
    void deleteArtist(Long id);

    /**
     * Finds artist from database by id
     *
     * @param id
     * @return
     */
    Artist findArtist(Long id);

    /**
     * Finds artist from database by email
     *
     * @param email
     * @return
     */
    Artist findArtist(String email);

}
