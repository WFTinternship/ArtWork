package am.aca.wftartproject.dao;

import am.aca.wftartproject.model.Artist;

/**
 * Created by ASUS on 27-May-17
 */
public interface ArtistDao {

    /**
     * Adds new artist info to the springconfig.database
     *
     * @param artist
     */
    void addArtist(Artist artist);

    /**
     * Finds artist from springconfig.database by id
     *
     * @param id
     * @return
     */
    Artist findArtist(Long id);

    /**
     * Finds artist from springconfig.database by email
     *
     * @param email
     * @return
     */
    Artist findArtist(String email);

    /**
     * Updates artist info in springconfig.database
     *
     * @param id
     * @param artist
     */
    void updateArtist(Long id, Artist artist);

    /**
     * Deletes artist from springconfig.database
     *
     * @param id
     */
    Boolean deleteArtist(Long id);
}
