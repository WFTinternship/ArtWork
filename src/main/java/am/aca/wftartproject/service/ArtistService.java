package am.aca.wftartproject.service;

import am.aca.wftartproject.entity.Artist;

/**
 * Created by surik on 6/3/17
 */
public interface ArtistService {

    /**
     * Adds artist into the springconfig.database
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
     * @param artist
     */
    void updateArtist(Artist artist);

    /**
     * Deletes artist from springconfig.database
     *
     * @param artist
     */
    void deleteArtist(Artist artist);

    /**
     * LogIn for Artist
     *
     * @param email
     * @param password
     */
    Artist login(String email, String password);

}
