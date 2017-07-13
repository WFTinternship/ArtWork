package am.aca.wftartproject.repository;

import am.aca.wftartproject.entity.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

/**
 * Created by ASUS on 27-May-17
 */
@Repository
public interface ArtistRepo extends JpaRepository<Artist,Long> {

    /**
     * Adds new artist info to the springconfig.database
     *
     * @param artist
     */
   // Artist saveAndFlush(Artist artist);

    /**
     * Finds artist from springconfig.database by id
     *
     * @param id
     * @return
     */
    Artist findOne(Long id);

    /**
     * Finds artist from springconfig.database by email
     *
     * @param email
     * @return
     */
    Artist findArtistByEmail(String email);

    /**
     * Deletes artist from springconfig.database
     *
     * @param artist
     */
    void delete(Artist artist);


}
