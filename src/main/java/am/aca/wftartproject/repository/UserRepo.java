package am.aca.wftartproject.repository;

import am.aca.wftartproject.entity.Artist;
import am.aca.wftartproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ASUS on 27-May-17
 */
@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    /**
     * Finds user with the following email.
     *
     * @param email
     * @return
     */
    User findUserByEmail(String email);


}
