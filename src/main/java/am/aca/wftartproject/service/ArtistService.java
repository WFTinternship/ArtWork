package am.aca.wftartproject.service;

import am.aca.wftartproject.model.Artist;

/**
 * Created by surik on 6/3/17
 */
public interface ArtistService {

    /**
     * Adds artist and ShoppingCard into the database
     *
     * @param artist
     */
    void addArtist(Artist artist);

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

    /**
     * Updates artist info in database
     *
     * @param id
     * @param artist
     */
    void updateArtist(Long id, Artist artist);

    /**
     * Deletes artist from database
     *
     * @param id
     */
    void deleteArtist(Long id);

}
