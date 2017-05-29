package am.aca.wftartproject.dao;

import am.aca.wftartproject.model.Artist;

/**
 * Created by ASUS on 27-May-17.
 */
public interface ArtistDao {

    void addArtist(Artist user);
    void updateArtist(int id, String firstName, String lastName);
    void deleteArtist(int id);
    Artist findArtist(int id);
    Artist findArtist(String email);

}
