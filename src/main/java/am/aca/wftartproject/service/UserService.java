package am.aca.wftartproject.service;

import am.aca.wftartproject.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by surik on 6/3/17
 */
@Repository
public interface UserService {

    /**
     * Adds User to the springconfig.database
     *
     * @param user *
     */
    void addUser(User user);

    /**
     * Finds User with the following id.
     *
     * @param id *
     * @return User
     */
    User findUser(Long id);


    /**
     * Finds User with the following email.
     *
     * @param email *
     * @return User
     */
    User findUser(String email);

    /**
     * Updates user info
     *
     * @param user *
     */
    void updateUser(User user);

    /**
     * Deletes user with the following id.
     *
     * @param user *
     */
    void deleteUser(User user);

    /**
     * Authenticates user info.
     * @param email *
     * @param password *
     * @return User
     */
    User login(String email, String password);

}
