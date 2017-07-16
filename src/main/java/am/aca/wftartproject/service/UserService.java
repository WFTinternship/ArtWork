package am.aca.wftartproject.service;

import am.aca.wftartproject.model.AbstractUser;
import am.aca.wftartproject.model.User;

/**
 * Created by surik on 6/3/17
 */
public interface UserService {

    /**
     * Adds User to the springconfig.database
     *
     * @param user
     */
    void addUser(User user);

    /**
     * Finds User with the following id.
     *
     * @param id
     * @return
     */
    User findUser(Long id);


    /**
     * Finds User with the following email.
     *
     * @param email
     * @return
     */
    User findUser(String email);

    /**
     * Updates user info
     *
     * @param id
     * @param user
     */
    void updateUser(Long id, User user);

    /**
     * Deletes user with the following id.
     *
     * @param id
     */
    void deleteUser(Long id);

    /**
     * Authenticates user info.
     *
     * @param email
     * @param password
     * @return
     */
    User login(String email, String password);

    /**
     * Sends email to user with further instructions after buying item
     *
     * @param abstractUser
     */
    void sendEmailAfterBuyingItem(AbstractUser abstractUser);
}
