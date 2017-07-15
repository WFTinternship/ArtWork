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
     * Finds artist from springconfig.database by email
     *
     * @param email
     * @return
     */
    Artist findArtistByEmail(String email);


}
