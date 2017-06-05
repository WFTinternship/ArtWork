package am.aca.wftartproject.service;

import am.aca.wftartproject.model.Artist;

import java.sql.SQLException;

/**
 * Created by ASUS on 30-May-17
 */
public interface ArtistService {

    /**
     * Adds
     *
     * @param artist
     */
    void addArtist(Artist artist) throws SQLException;

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
