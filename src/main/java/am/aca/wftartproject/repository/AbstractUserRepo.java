package am.aca.wftartproject.repository;

import am.aca.wftartproject.entity.AbstractUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Armen on 7/14/2017.
 */
@Repository
public interface AbstractUserRepo extends JpaRepository<AbstractUser,Long> {
    /**
     * Finds user/artist from database by email
     *
     * @param email
     * @return
     */
    AbstractUser findByEmail(String email);

    AbstractUser findOne(Long id);
}
